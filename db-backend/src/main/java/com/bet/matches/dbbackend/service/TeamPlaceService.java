package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.configuration.RapidApiWeb;
import com.bet.matches.dbbackend.core.rapidapi.TeamPlaceRapidApiResponse;
import com.bet.matches.dbbackend.core.rapidapi.model.TeamPlaceRapidApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPlaceService {

    private final WebClient rapidApiWebClient;
    private final RapidApiWeb rapidApiWeb;

    private String teamsPositionRapidApiUri;
    private List<TeamPlaceRapidApi> teamsPosition;

    public void fetchCurrentTeamsPlace() {
        teamsPosition = this.fetchTeamsPosition();
    }

    public int getTeamPlace(final int idRapidApi) {
        for (final TeamPlaceRapidApi team : teamsPosition) {
            if (team.getTeamId() == idRapidApi) {
                return team.getRank();
            }
        }
        throw new RuntimeException("Could not find team position with given id: " + idRapidApi);
    }

    @PostConstruct
    private void init() {
        teamsPositionRapidApiUri = rapidApiWeb.getEndpoint().getTeamsPosition() + rapidApiWeb.getId().getLeagueSpain();
    }

    private List<TeamPlaceRapidApi> fetchTeamsPosition() {
        final TeamPlaceRapidApiResponse teamPlaceRapidApiResponse = rapidApiWebClient
                .get()
                .uri(teamsPositionRapidApiUri)
                .retrieve()
                .bodyToMono(TeamPlaceRapidApiResponse.class)
                .block();

        if (teamPlaceRapidApiResponse != null) {
            log.info("Teams positions from Rapid Api has been fetched");
            return teamPlaceRapidApiResponse.getTeamPlaceResponseInner().getLeagueTable().get(0);
        }

        throw new RuntimeException("Something went wrong with fetching teams position");
    }

}
