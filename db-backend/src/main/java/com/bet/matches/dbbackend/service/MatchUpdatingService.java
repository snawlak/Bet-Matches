package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.core.betmatchesapi.model.Match;
import com.bet.matches.dbbackend.core.betmatchesapi.model.MatchStatusBetMatches;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Player;
import com.bet.matches.dbbackend.core.rapidapi.model.EventRapidApi;
import com.bet.matches.dbbackend.core.rapidapi.model.MatchRapidApi;
import com.bet.matches.dbbackend.service.scheduler.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bet.matches.dbbackend.core.betmatchesapi.mapper.MatchStatusMapper.toBetMatchesStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchUpdatingService {

    private final SchedulerService schedulerService;
    private final EventService eventService;
    private final MatchService matchService;
    private final PlayerService playerService;
    private final TeamService teamService;

    public Runnable updateMatch(final Match matchBetMatchesApi) {
        return () -> update(matchBetMatchesApi);
    }

    private void update(final Match matchBetMatchesApi) {
        final int idMatchBetMatchesApi = matchBetMatchesApi.getId();
        final int idMatchRapidApi = matchBetMatchesApi.getIdRapidApi();
        final MatchRapidApi matchRapidApi = matchService.getMatchFromRapidApi(idMatchRapidApi);

        final String matchStatus = matchRapidApi.getStatusShort();
        final MatchStatusBetMatches matchStatusBetMatches = toBetMatchesStatus(matchStatus);

        if (matchStatusBetMatches == null) {
            throw new IllegalStateException("Wrong bet matches api match status");
        }

        switch (matchStatusBetMatches) {
            case NOT_STARTED:
                onMatchNotStarted();
                break;
            case LIVE:
                onMatchLive(idMatchBetMatchesApi, matchBetMatchesApi, matchRapidApi);
                break;
            case FINISHED:
                onMatchFinished(idMatchBetMatchesApi, idMatchRapidApi, matchBetMatchesApi, matchRapidApi);
                break;
            default:
                throw new IllegalStateException("Wrong bet matches api status");
        }
    }

    private void onMatchNotStarted() {
        // do sth ?
    }

    private void onMatchLive(final int idMatchBetMatchesApi,
                             final Match matchBetMatchesApi,
                             final MatchRapidApi matchRapidApi) {

        if (!MatchStatusBetMatches.LIVE.getValue().equals(matchBetMatchesApi.getStatus())) {
            matchBetMatchesApi.setStatus(MatchStatusBetMatches.LIVE.getValue());
        }
        actualizeResult(matchBetMatchesApi, matchRapidApi);

        matchService.updateMatchInBetMatchesApi(idMatchBetMatchesApi, matchBetMatchesApi);
        log.info("Match with Id: " + idMatchBetMatchesApi + " has been updated");
    }

    private void onMatchFinished(final int idBetMatchesApi,
                                 final int idMatchRapidApi,
                                 final Match matchBetMatchesApi,
                                 final MatchRapidApi matchRapidApi) {

        matchBetMatchesApi.setStatus(MatchStatusBetMatches.FINISHED.getValue());
        actualizeResult(matchBetMatchesApi, matchRapidApi);
        updatePlayers(idMatchRapidApi, matchBetMatchesApi, matchRapidApi);
        finishMatch(idBetMatchesApi, matchBetMatchesApi);
    }

    private void actualizeResult(final Match matchBetMatchesApi, final MatchRapidApi matchRapidApi) {
        final int teamFirstScore = matchBetMatchesApi.getTeamFirstScore();
        final int teamSecondScore = matchBetMatchesApi.getTeamSecondScore();

        final int actualTeamFirstScore = matchRapidApi.getGoalsHomeTeam();
        final int actualTeamSecondScore = matchRapidApi.getGoalsAwayTeam();

        if (teamFirstScore != actualTeamFirstScore || teamSecondScore != actualTeamSecondScore) {
            matchBetMatchesApi.setTeamFirstScore(actualTeamFirstScore);
            matchBetMatchesApi.setTeamSecondScore(actualTeamSecondScore);
        }
    }

    private void updatePlayers(final int idMatchRapidApi, final Match matchBetMatchesApi, final MatchRapidApi matchRapidApi) {
        final int teamFirstScore = matchBetMatchesApi.getTeamFirstScore();
        final int teamSecondScore = matchBetMatchesApi.getTeamSecondScore();
        final int goalsCounted = teamFirstScore + teamSecondScore;

        if (goalsCounted != 0) {
            final Map<Player, Integer> playersGoals = this.getPlayersAndTheirsGoalsInMatch(idMatchRapidApi, goalsCounted);
            playersGoals.forEach(this::updatePlayersScore);
        }
    }

    private void updatePlayersScore(final Player player, final int goals) {
        final int newGoalsNumber = player.getGoals() + goals;
        player.setGoals(newGoalsNumber);
        playerService.updatePlayerInBetMatchesApi(player.getId(), player);
    }

    private Map<Player, Integer> getPlayersAndTheirsGoalsInMatch(final int idMatchRapidApi, final int goalsCounted) {
        final List<EventRapidApi> goals = eventService.getGoalsFromEvent(idMatchRapidApi);

        if (goals.size() != goalsCounted) {
            throw new RuntimeException("Events and Match are not consistent");
        }

        final Map<Integer, Integer> playersGoals = new HashMap<>();
        goals.forEach(goal -> {
            final int idPlayerRapidApi = goal.getPlayerId();
            if (!playersGoals.containsKey(idPlayerRapidApi)) {
                playersGoals.put(idPlayerRapidApi, 1);
            } else {
                final int playersGoalsInMatch = playersGoals.get(idPlayerRapidApi) + 1;
                playersGoals.replace(idPlayerRapidApi, playersGoalsInMatch);
            }
        });

        return this.getPlayersAndGoals(playersGoals);
    }

    private Map<Player, Integer> getPlayersAndGoals(final Map<Integer, Integer> playersIdsAndGoals) {
        final Map<Player, Integer> playersGoals = new HashMap<>();

        playersIdsAndGoals.forEach((idPlayerRapidApi, goals) -> {
            final Player playerBetMatches = playerService.getPlayerFromBetMatchesByRapidApiId(idPlayerRapidApi);
            playersGoals.put(playerBetMatches, goals);
        });

        return playersGoals;
    }

    private void finishMatch(final int idBetMatchesApi, final Match matchBetMatchesApi) {
        matchService.updateMatchInBetMatchesApi(idBetMatchesApi, matchBetMatchesApi);
        schedulerService.finishTask(idBetMatchesApi);

        updateTeamPlaces(idBetMatchesApi);

        log.info("Match with Id: " + idBetMatchesApi + " has been finished");
    }

    private void updateTeamPlaces(final int idBetMatchesApi) {
        final int idTeamPlaceTask = -1 * idBetMatchesApi;
        final OffsetDateTime startDate = OffsetDateTime.now();
        schedulerService.addTeamPlaceUpdatingTaskToScheduler(idTeamPlaceTask,
                                                             startDate,
                                                             teamService.updateAllTeamsPlace());
    }

}
