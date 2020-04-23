package com.bet.matches.dbbackend.core.betmatchesapi.mapper;

import com.bet.matches.dbbackend.core.betmatchesapi.model.Team;
import com.bet.matches.dbbackend.core.rapidapi.model.TeamRapidApi;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TeamMapper {

    public static List<Team> toBetMatchesTeams(final List<TeamRapidApi> teamsRapidApi) {
        final List<Team> teams = new ArrayList<>();
        teamsRapidApi.forEach(teamRapidApi -> teams.add(toBetMatchesTeam(teamRapidApi)));
        return teams;
    }

    private static Team toBetMatchesTeam(final TeamRapidApi teamRapidApi) {
        return Team.builder()
                .idRapidApi(teamRapidApi.getTeamId())
                .logo(teamRapidApi.getLogo())
                .name(teamRapidApi.getName())
                .place(0)
                .build();
    }
}
