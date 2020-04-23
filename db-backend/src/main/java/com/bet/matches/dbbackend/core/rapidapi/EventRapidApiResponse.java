package com.bet.matches.dbbackend.core.rapidapi;

import com.bet.matches.dbbackend.core.rapidapi.model.EventRapidApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EventRapidApiResponse {

    @JsonProperty("api")
    private EventResponseInner eventResponseInner;

    @Data
    public static class EventResponseInner {

        private int results;

        @JsonProperty("events")
        private List<EventRapidApi> eventsRapidApi;
    }
}
