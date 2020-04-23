package com.bet.matches.dbbackend.core.rapidapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TeamRapidApi {

    @JsonProperty("team_id")
    private int teamId;

    private String name;

    private Object code;

    private String logo;

    private String country;

    private int founded;

    @JsonProperty("venue_name")
    private String venueName;

    @JsonProperty("venue_surface")
    private String venueSurface;

    @JsonProperty("venue_address")
    private String venueAddress;

    @JsonProperty("venue_city")
    private String venueCity;

    @JsonProperty("venue_capacity")
    private int venueCapacity;
}
