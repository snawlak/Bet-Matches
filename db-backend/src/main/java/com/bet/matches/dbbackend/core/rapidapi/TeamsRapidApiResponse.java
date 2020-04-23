package com.bet.matches.dbbackend.core.rapidapi;

import com.bet.matches.dbbackend.core.rapidapi.model.TeamRapidApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TeamsRapidApiResponse {
    @JsonProperty("api")
    private TeamResponseInner teamResponseInner;

    @Data
    public static class TeamResponseInner {

        private int results;

        @JsonProperty("teams")
        private List<TeamRapidApi> teamsRapidApi;
    }
}
