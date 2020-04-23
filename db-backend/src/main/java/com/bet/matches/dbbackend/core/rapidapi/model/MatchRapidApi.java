package com.bet.matches.dbbackend.core.rapidapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class MatchRapidApi {

    @JsonProperty("fixture_id")
    private int fixtureId;

    @JsonProperty("league_id")
    private int leagueId;

    @JsonProperty("event_date")
    private OffsetDateTime eventDate;

    @JsonProperty("event_timestamp")
    private Long eventTimestamp;

    private String firstHalfStart;

    private String secondHalfStart;

    private String round;

    private String status;

    private String statusShort;

    private float elapsed;

    private String venue;

    private String referee;

    private TeamRapidApi homeTeam;

    private TeamRapidApi awayTeam;

    private int goalsHomeTeam;

    private int goalsAwayTeam;
}
