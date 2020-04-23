package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.core.betmatchesapi.model.Stadium;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Team;
import com.bet.matches.dbbackend.core.rapidapi.model.TeamRapidApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitializerService {

    private final TeamService teamService;
    private final StadiumService stadiumService;
    private final PlayerService playerService;
    private final MatchService matchService;

    private final List<TeamRapidApi> teamsRapidApi = new ArrayList<>();
    private final List<Team> teamsBetMatches = new ArrayList<>();
    private final List<Stadium> stadiums = new ArrayList<>();

    @Value("${app.service.initialize}")
    private Boolean initializeService;

    @PostConstruct
    public void init() {
        if (initializeService) {
            initTeams();
            initStadiums();
            initPlayers();
            initMatches();
        }
    }

    private void initTeams() {
        teamService.fetchTeamsFromRapidApi();
        teamService.sendTeamsToBetMatchesApi();
        teamsRapidApi.addAll(teamService.getTeamsFromRapidApi());
        teamsBetMatches.addAll(teamService.getTeamsBetMatches());
    }

    private void initStadiums() {
        stadiumService.sendStadiumsToBetMatchesApi(teamsRapidApi);
        stadiums.addAll(stadiumService.getStadiums());
    }

    private void initPlayers() {
        playerService.fetchPlayersFromRapidApi(teamsRapidApi);
        playerService.sendPlayersToBetMatchesApi(teamsBetMatches);
    }

    private void initMatches() {
        matchService.fetchMatchesFromRapidApi();
        matchService.sendMatchesToBetMatchesApi(teamsBetMatches, stadiums);
    }
}
