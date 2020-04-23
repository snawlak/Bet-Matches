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
public class Team {

    private int id;

    @JsonProperty("id_rapid_api")
    private int idRapidApi;

    private String name;

    private int place;

    private String logo;
}
