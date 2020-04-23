package com.bet.matches.dbbackend.core.rapidapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TeamPlaceRapidApi {

    private int rank;

    @JsonProperty("team_id")
    private int teamId;

    private String teamName;

    private String logo;

    private String group;

    private String forme;

    private String description;

    @JsonProperty("all")
    private SeasonStatistics statistics;

    private int goalsDiff;

    private int points;

    private String lastUpdate;


    @Data
    private static class SeasonStatistics {

        @JsonProperty("matchsPlayed")
        private int matchesPlayed;

        private int win;

        private int draw;

        private int lose;

        private int goalsFor;

        private int goalsAgainst;
    }

}
