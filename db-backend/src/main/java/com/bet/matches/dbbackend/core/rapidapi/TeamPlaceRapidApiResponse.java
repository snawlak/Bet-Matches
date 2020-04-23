package com.bet.matches.dbbackend.core.rapidapi;

import com.bet.matches.dbbackend.core.rapidapi.model.TeamPlaceRapidApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TeamPlaceRapidApiResponse {
    @JsonProperty("api")
    private TeamPlaceResponseInner teamPlaceResponseInner;

    @Data
    public static class TeamPlaceResponseInner {

        private int results;

        @JsonProperty("standings")
        private List<List<TeamPlaceRapidApi>> leagueTable;
    }
}
