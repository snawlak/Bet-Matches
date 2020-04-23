package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.configuration.RapidApiWeb;
import com.bet.matches.dbbackend.core.rapidapi.EventRapidApiResponse;
import com.bet.matches.dbbackend.core.rapidapi.model.EventRapidApi;
import com.bet.matches.dbbackend.core.rapidapi.model.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final RapidApiWeb rapidApiWeb;
    private final WebClient rapidApiWebClient;

    private String eventsRapidApiUri;

    public List<EventRapidApi> fetchEventsFromMatch(final int idMatch) {
        return fetchEvents(idMatch);
    }

    public List<EventRapidApi> getGoalsFromEvent(final int idMatch) {
        final List<EventRapidApi> eventRapidApis = this.fetchEvents(idMatch);
        return eventRapidApis
                .stream()
                .filter(eventRapidApi -> EventType.GOAL.getType().equals(eventRapidApi.getType()))
                .collect(Collectors.toList());
    }

    @PostConstruct
    private void init() {
        eventsRapidApiUri = rapidApiWeb.getEndpoint().getEvents();
    }

    private List<EventRapidApi> fetchEvents(final int idMatch) {
        final String uri = eventsRapidApiUri + idMatch;
        final EventRapidApiResponse eventRapidApiResponse = rapidApiWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(EventRapidApiResponse.class)
                .block();

        if (eventRapidApiResponse == null) {
            throw new RuntimeException("Something went wrong with consuming Rapid Api Team endpoints");
        }

        return eventRapidApiResponse.getEventResponseInner().getEventsRapidApi();
    }
}
