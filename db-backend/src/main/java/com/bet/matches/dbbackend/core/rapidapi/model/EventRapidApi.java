package com.bet.matches.dbbackend.core.rapidapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventRapidApi {

    private String elapsed;

    @JsonProperty("team_id")
    private int teamId;

    private String teamName;

    @JsonProperty("player_id")

    private int playerId;

    private String player;

    @JsonProperty("assist_id")
    private int assistId;

    private String assist;

    private String type;

    private String detail;
}
