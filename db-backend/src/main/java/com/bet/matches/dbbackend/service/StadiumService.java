package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.configuration.BetMatchesApiWeb;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Stadium;
import com.bet.matches.dbbackend.core.rapidapi.model.TeamRapidApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.bet.matches.dbbackend.core.betmatchesapi.mapper.StadiumMapper.toStadiums;
import static reactor.core.publisher.Mono.just;

@Slf4j
@Service
@RequiredArgsConstructor
public class StadiumService {

    private final WebClient betMatchesWebClient;
    private final BetMatchesApiWeb betMatchesApiWeb;

    private final List<Stadium> stadiums = new ArrayList<>();

    private String stadiumBetMatchesUri;

    public void sendStadiumsToBetMatchesApi(final List<TeamRapidApi> teamsRapidApi) {
        toStadiums(teamsRapidApi).forEach(stadium -> stadiums.add(sendStadium(stadium)));
        log.info("Stadiums (" + stadiums.size() + ") to Bet Matches Api has been sent");
    }

    public List<Stadium> getStadiums() {
        return stadiums;
    }

    @PostConstruct
    private void init() {
        stadiumBetMatchesUri = betMatchesApiWeb.getEndpoint().getStadiums();
    }


    private Stadium sendStadium(final Stadium stadiumToSend) {
        final int homeTeamId = stadiumToSend.getHomeTeamRapidApiId();

        final Stadium stadium = betMatchesWebClient
                .post()
                .uri(stadiumBetMatchesUri)
                .body(just(stadiumToSend), Stadium.class)
                .retrieve()
                .bodyToMono(Stadium.class)
                .block();

        if (stadium != null) {
            stadium.setHomeTeamRapidApiId(homeTeamId);
            return stadium;
        }
        throw new RuntimeException("Error in sending Stadium to Bet Matches Api");
    }
}
