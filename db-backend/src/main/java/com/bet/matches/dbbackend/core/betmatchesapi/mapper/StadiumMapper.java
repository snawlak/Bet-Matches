package com.bet.matches.dbbackend.core.betmatchesapi.mapper;

import com.bet.matches.dbbackend.core.betmatchesapi.model.Stadium;
import com.bet.matches.dbbackend.core.rapidapi.model.TeamRapidApi;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class StadiumMapper {

    private static final String COUNTRY = "Spain";

    public static List<Stadium> toStadiums(final List<TeamRapidApi> teams) {
        final List<Stadium> stadiums = new ArrayList<>();
        teams.forEach(team -> stadiums.add(toStadium(team)));
        return stadiums;
    }

    private static Stadium toStadium(final TeamRapidApi teamRapidApi) {
        return Stadium.builder()
                .name(teamRapidApi.getVenueName())
                .city(teamRapidApi.getVenueCity())
                .country(COUNTRY)
                .capacity(teamRapidApi.getVenueCapacity())
                .homeTeamRapidApiId(teamRapidApi.getTeamId())
                .build();
    }
}
