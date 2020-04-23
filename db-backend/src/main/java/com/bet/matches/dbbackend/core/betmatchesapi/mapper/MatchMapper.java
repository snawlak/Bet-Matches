package com.bet.matches.dbbackend.core.betmatchesapi.mapper;

import com.bet.matches.dbbackend.core.betmatchesapi.model.Match;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Stadium;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Team;
import com.bet.matches.dbbackend.core.rapidapi.model.MatchRapidApi;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class MatchMapper {

    public static List<Match> toMatches(final List<MatchRapidApi> matchesRapidApi,
                                        final List<Team> teamsBetMatches,
                                        final List<Stadium> stadiums) {

        final List<Match> matches = new ArrayList<>();
        for (final MatchRapidApi matchRapidApi : matchesRapidApi) {
            final int teamFirstRapidApiId = matchRapidApi.getHomeTeam().getTeamId();
            final int teamSecondRapidApiId = matchRapidApi.getAwayTeam().getTeamId();
            final Team teamFirstBetMatches = getTeam(teamsBetMatches, teamFirstRapidApiId);
            final Team teamSecondBetMatches = getTeam(teamsBetMatches, teamSecondRapidApiId);
            final Stadium stadium = getStadium(stadiums, teamFirstRapidApiId);
            matches.add(toMatch(matchRapidApi, teamFirstBetMatches, teamSecondBetMatches, stadium));
        }
        return matches;
    }

    private static Match toMatch(final MatchRapidApi matchRapidApi,
                                 final Team teamFirstBetMatches,
                                 final Team teamSecondBetMatches,
                                 final Stadium stadium) {

        return Match.builder()
                .idRapidApi(matchRapidApi.getFixtureId())
                .teamFirst(teamFirstBetMatches)
                .teamSecond(teamSecondBetMatches)
                .matchDate(matchRapidApi.getEventDate())
                .matchDay(getMatchDay(matchRapidApi))
                .stadium(stadium)
                .status(getBetMatchesStatus(matchRapidApi))
                .teamFirstScore(matchRapidApi.getGoalsHomeTeam())
                .teamSecondScore(matchRapidApi.getGoalsAwayTeam())
                .build();
    }

    private static Team getTeam(final List<Team> teams, final int idTeamRapidApi) {
        for (final Team team : teams) {
            if (team.getIdRapidApi() == idTeamRapidApi) {
                return team;
            }
        }
        throw new RuntimeException("Find Team in Match Mapper went wrong");
    }

    private static Stadium getStadium(final List<Stadium> stadiums, final int idTeamHome) {
        for (final Stadium stadium : stadiums) {
            if (stadium.getHomeTeamRapidApiId() == idTeamHome) {
                return stadium;
            }
        }
        throw new RuntimeException("Find Stadium in Match Mapper went wrong");
    }

    private static String getBetMatchesStatus(final MatchRapidApi matchRapidApi) {
        if (matchRapidApi.getStatusShort() != null) {
            return Objects.requireNonNull(MatchStatusMapper.toBetMatchesStatus(matchRapidApi.getStatusShort())).getValue();
        }
        throw new IllegalStateException("Wrong match status from Rapid Api");
    }

    private static int getMatchDay(final MatchRapidApi matchRapidApi) {
        String matchDay = matchRapidApi.getRound();
        matchDay = matchDay.replaceAll("\\D+", "");
        return Integer.parseInt(matchDay);
    }
}
