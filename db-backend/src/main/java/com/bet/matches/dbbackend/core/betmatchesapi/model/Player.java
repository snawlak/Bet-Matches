package com.bet.matches.dbbackend.core.betmatchesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private int id;

    @JsonProperty("id_rapid_api")
    private int idRapidApi;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Team team;

    private int goals;

    private String position;

    private String nationality;

    private int age;
}

