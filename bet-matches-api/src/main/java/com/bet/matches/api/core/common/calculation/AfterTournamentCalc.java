package com.bet.matches.api.core.common.calculation;

import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.player.PlayerRepository;
import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.team.TeamRepository;
import com.bet.matches.api.core.user.User;
import com.bet.matches.api.core.user.UserRepository;
import com.bet.matches.api.core.betextra.BetExtra;
import com.bet.matches.api.core.betextra.BetExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class AfterTournamentCalc {

    private final Points points;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final BetExtraRepository betExtraRepository;

    private int extraPoints;

    public void addPointsToUsersWhoBetTopScorer() {
        final List<Player> topScorers = playerRepository.getPlayersWithHighestScore();
        final List<Team> allTeams = teamRepository.getAllTeams();

        if (topScorers.size() <= 1) {
            // IF ONLY THERE IS ONE TOP SCORER
            betExtraRepository.getAllWhoBetTopScorer(topScorers.get(0)).forEach(this::addPoints);
        } else {
            // IF THERE ARE MORE TOP SCORERS - NEED TO LOOK AT PLACE OF THEIRS TEAM.
            // WHEN HIGHEST POSITION OF TEAM - THIS PLAYER IS TOP SCORER
            // IF THE SAME TEAM - BOTH ARE TOP SCORER
            final Team topScorersBestTeam = getTopScorersBestTeam(topScorers, allTeams);
            final List<Player> topScorersInBestTeam = getTopScorersInBestTeam(topScorers, topScorersBestTeam);

            topScorersInBestTeam.forEach(topScorer -> {
                betExtraRepository.getAllWhoBetTopScorer(topScorer).forEach(this::addPoints);
            });
        }
    }

    public void addPointsToUsersWhoBetWinningTeam() {
        final Team bestTeam = teamRepository.getTeamOnPlace(1);
        betExtraRepository.getAllWhoBetWinningTeam(bestTeam).forEach(this::addPoints);
    }

    @PostConstruct
    private void init() {
        extraPoints = points.getExtra();
    }

    private void addPoints(final BetExtra betExtra) {
        final User user = betExtra.getUser();
        final int userPoints = user.getPoints();
        user.setPoints(userPoints + extraPoints);
        userRepository.addOrUpdateUser(user);
    }

    private Team getTopScorersBestTeam(final List<Player> topScorers, final List<Team> teams) {
        final AtomicInteger bestTeamPlace = new AtomicInteger(teams.size());
        topScorers.forEach(topScorer -> {
            final Team team = topScorer.getTeam();
            final int place = team.getPlace();
            if (place < bestTeamPlace.get()) {
                bestTeamPlace.set(place);
            }
        });
        return teamRepository.getTeamOnPlace(bestTeamPlace.get());
    }

    private List<Player> getTopScorersInBestTeam(final List<Player> topScorers, final Team topScorersBestTeam) {
        final List<Player> topScorersInBestTeam = new ArrayList<>();

        topScorers.forEach(topScorer -> {
            if (topScorer.getTeam().equals(topScorersBestTeam)) {
                topScorersInBestTeam.add(topScorer);
            }
        });
        return topScorersInBestTeam;
    }

}
