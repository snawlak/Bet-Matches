package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.configuration.BetMatchesApiWeb;
import com.bet.matches.dbbackend.configuration.RapidApiWeb;
import com.bet.matches.dbbackend.core.betmatchesapi.mapper.TeamMapper;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Team;
import com.bet.matches.dbbackend.core.rapidapi.TeamsRapidApiResponse;
import com.bet.matches.dbbackend.core.rapidapi.model.TeamRapidApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static reactor.core.publisher.Mono.just;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final RapidApiWeb rapidApiWeb;
    private final BetMatchesApiWeb betMatchesApiWeb;
    private final TeamPlaceService teamPlaceService;

    private final WebClient betMatchesWebClient;
    private final WebClient rapidApiWebClient;

    private final List<TeamRapidApi> teamsRapidApi = new ArrayList<>();
    private final List<Team> teamsBetMatches = new ArrayList<>();

    private String teamBetMatchesUri;
    private String teamRapidApiUri;
    private boolean teamsSent = false;
    private boolean teamsFetched = false;

    public void fetchTeamsFromRapidApi() {
        if (!teamsFetched) {
            teamsRapidApi.addAll(this.fetchRapidApiTeams());
            teamsFetched = true;
            log.info("Teams (" + teamsRapidApi.size() + ") From Rapid Api has been fetched");
        }
    }

    public void sendTeamsToBetMatchesApi() {
        if (!teamsSent && teamsFetched && !teamsRapidApi.isEmpty()) {
            sendTeamsToBetMatchesApi(teamsRapidApi);
            teamsSent = true;
            log.info("Teams (" + teamsBetMatches.size() + ") to Bet Matches Api has been sent");
        }
    }

    public List<TeamRapidApi> getTeamsFromRapidApi() {
        return teamsRapidApi;
    }

    public List<Team> getTeamsBetMatches() {
        return teamsBetMatches;
    }

    public Runnable updateAllTeamsPlace() {
        return this::updateTeamsPlaces;
    }

    @PostConstruct
    private void init() {
        teamRapidApiUri = rapidApiWeb.getEndpoint().getTeams() + rapidApiWeb.getId().getLeagueSpain();
        teamBetMatchesUri = betMatchesApiWeb.getEndpoint().getTeams();
    }

    private void sendTeamsToBetMatchesApi(final List<TeamRapidApi> teamsRapidApi) {
        final List<Team> teams = TeamMapper.toBetMatchesTeams(teamsRapidApi);
        this.setTeamsPlaces(teams);
        teams.forEach(team -> teamsBetMatches.add(this.sendTeam(team)));
    }

    private List<Team> setTeamsPlaces(final List<Team> teams) {
        teamPlaceService.fetchCurrentTeamsPlace();
        teams.forEach(this::setTeamPlace);
        return teams;
    }

    private void updateTeamsPlaces() {
        final String uri = betMatchesApiWeb.getEndpoint().getTeams();
        final List<Team> teamsBetMatches = getTeamsFromBetMatchesApi(uri);
        teamPlaceService.fetchCurrentTeamsPlace();
        teamsBetMatches.forEach(this::setTeamPlace);
        teamsBetMatches.forEach(team -> updateTeam((uri + team.getId()), team));
        log.info("League table has been updated");
    }

    private void setTeamPlace(final Team team) {
        final int place = teamPlaceService.getTeamPlace(team.getIdRapidApi());
        team.setPlace(place);
    }

    private List<TeamRapidApi> fetchRapidApiTeams() {
        final TeamsRapidApiResponse teamsResponse = rapidApiWebClient
                .get()
                .uri(teamRapidApiUri)
                .retrieve()
                .bodyToMono(TeamsRapidApiResponse.class)
                .block();

        if (teamsResponse != null) {
            return teamsResponse.getTeamResponseInner().getTeamsRapidApi();
        }

        throw new RuntimeException("Something went wrong with consuming Rapid Api Team endpoints");
    }

    private Team sendTeam(final Team team) {
        return betMatchesWebClient
                .post()
                .uri(teamBetMatchesUri)
                .body(just(team), Team.class)
                .retrieve()
                .bodyToMono(Team.class)
                .block();
    }

    private Team updateTeam(final String uri, final Team team) {
        return betMatchesWebClient
                .put()
                .uri(uri)
                .body(just(team), Team.class)
                .retrieve()
                .bodyToMono(Team.class)
                .block();
    }

    private List<Team> getTeamsFromBetMatchesApi(final String uri) {
        return betMatchesWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Team.class)
                .collectList()
                .block();
    }
}
