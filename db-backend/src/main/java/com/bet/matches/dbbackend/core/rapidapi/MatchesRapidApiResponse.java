package com.bet.matches.dbbackend.core.rapidapi;

import com.bet.matches.dbbackend.core.rapidapi.model.MatchRapidApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MatchesRapidApiResponse {

    @JsonProperty("api")
    private MatchResponseInner matchResponseInner;

    @Data
    public static class MatchResponseInner {

        private int results;

        @JsonProperty("fixtures")
        private List<MatchRapidApi> matchesRapidApi;
    }
}
