package com.bet.matches.dbbackend.core.rapidapi;

import com.bet.matches.dbbackend.core.rapidapi.model.PlayerRapidApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayersRapidApiResponse {

    @JsonProperty("api")
    private PlayersResponseInner playersResponseInner;

    @Data
    public static class PlayersResponseInner {

        private int results;

        @JsonProperty("players")
        private List<PlayerRapidApi> playersRapidApi;
    }

}
