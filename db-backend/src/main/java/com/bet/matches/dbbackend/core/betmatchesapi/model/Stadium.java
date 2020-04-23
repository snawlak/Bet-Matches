package com.bet.matches.dbbackend.core.betmatchesapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stadium {
    private int id;

    private String name;

    private String country;

    private String city;

    private int capacity;

    @JsonIgnore
    private int homeTeamRapidApiId;
}
