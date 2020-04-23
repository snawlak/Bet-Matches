package com.bet.matches.dbbackend.core.rapidapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlayerRapidApi {

    @JsonProperty("player_id")
    private int playerId;

    @JsonProperty("player_name")
    private String playerName;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    private int number;

    private String position;

    private int age;

    @JsonProperty("birth_date")
    private String birthDate;

    @JsonProperty("birth_place")
    private String birthPlace;

    @JsonProperty("birth_country")
    private String birthCountry;

    private String nationality;

    private String height;

    private String weight;

    private String league;

    private Goals goals;

    @JsonIgnore
    private int idTeamRapidApi;

    @Data
    public static class Goals {
        private int total;
        private int conceded;
        private int assists;
    }
}
