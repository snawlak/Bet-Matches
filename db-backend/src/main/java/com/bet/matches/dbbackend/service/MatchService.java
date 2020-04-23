package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.configuration.BetMatchesApiWeb;
import com.bet.matches.dbbackend.configuration.RapidApiWeb;
import com.bet.matches.dbbackend.core.betmatchesapi.mapper.MatchMapper;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Match;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Stadium;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Team;
import com.bet.matches.dbbackend.core.rapidapi.MatchesRapidApiResponse;
import com.bet.matches.dbbackend.core.rapidapi.model.MatchRapidApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static reactor.core.publisher.Mono.just;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final RapidApiWeb rapidApiWeb;
    private final BetMatchesApiWeb betMatchesApiWeb;

    private final WebClient betMatchesWebClient;
    private final WebClient rapidApiWebClient;

    private final List<MatchRapidApi> matchesRapidApi = new ArrayList<>();
    private final List<Match> matchesBetMatches = new ArrayList<>();

    private String matchBetMatchesUri;
    private String matchesFromLeagueRapidApiUri;
    private String matchRapidApiUri;
    private boolean matchesFetched = false;
    private boolean matchesSent = false;

    public void fetchMatchesFromRapidApi() {
        if (!matchesFetched) {
            fetchRapidApiMatches();
            matchesFetched = true;
            log.info("Matches (" + matchesRapidApi.size() + ") from Rapid Api has been fetched");
        }
    }

    public void sendMatchesToBetMatchesApi(final List<Team> teamsBetMatches, final List<Stadium> stadiums) {
        if (!matchesSent && matchesFetched && !matchesRapidApi.isEmpty() && !stadiums.isEmpty()) {
            sendMatchesToBetMatches(teamsBetMatches, stadiums);
            matchesSent = true;
            log.info("Matches (" + matchesBetMatches.size() + ") has been added to Bet Matches");
        }
    }

    public MatchRapidApi getMatchFromRapidApi(final int id) {
        final String uri = matchRapidApiUri + id;
        final MatchesRapidApiResponse matchesResponse = rapidApiWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MatchesRapidApiResponse.class)
                .block();

        if (matchesResponse == null) {
            throw new RuntimeException("Something went wrong with consuming Rapid Api Team endpoints");
        }

        return matchesResponse.getMatchResponseInner().getMatchesRapidApi().get(0);
    }

    public List<Match> getAllMatchesFromDate(final OffsetDateTime matchDate) {
        final String date = matchDate.toString().replace("+", "%2B");
        final String uri = matchBetMatchesUri + "?matchDate=" + date;
        return this.getMatches(uri);
    }

    public Match getMatchFromBetMatchApi(final int id) {
        final String uri = matchBetMatchesUri + id;
        return this.getMatch(uri);
    }

    public Match updateMatchInBetMatchesApi(final int id, final Match match) {
        final String uri = matchBetMatchesUri + id;
        return this.updateMatch(uri, match);
    }

    @PostConstruct
    private void init() {
        final String matchesRapidApiEndpoint = rapidApiWeb.getEndpoint().getMatchesFromLeague();
        final int leagueSpainId = rapidApiWeb.getId().getLeagueSpain();
        matchesFromLeagueRapidApiUri = matchesRapidApiEndpoint + leagueSpainId;
        matchRapidApiUri = rapidApiWeb.getEndpoint().getMatch();
        matchBetMatchesUri = betMatchesApiWeb.getEndpoint().getMatches();
    }

    private void fetchRapidApiMatches() {
        final MatchesRapidApiResponse matchesResponse = rapidApiWebClient
                .get()
                .uri(matchesFromLeagueRapidApiUri)
                .retrieve()
                .bodyToMono(MatchesRapidApiResponse.class)
                .block();

        if (matchesResponse == null) {
            throw new RuntimeException("Something went wrong with consuming Rapid Api Team endpoints");
        }

        matchesRapidApi.addAll(matchesResponse.getMatchResponseInner().getMatchesRapidApi());
    }

    private void sendMatchesToBetMatches(final List<Team> teamsBetMatches, final List<Stadium> stadiums) {
        final List<Match> matches = MatchMapper.toMatches(matchesRapidApi, teamsBetMatches, stadiums);
        matches.forEach(match -> matchesBetMatches.add(sendMatch(match)));
    }

    private Match sendMatch(final Match match) {
        return betMatchesWebClient
                .post()
                .uri(matchBetMatchesUri)
                .body(just(match), Match.class)
                .retrieve()
                .bodyToMono(Match.class)
                .block();
    }

    private Match getMatch(final String uri) {
        return betMatchesWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Match.class)
                .block();
    }

    private List<Match> getMatches(final String uri) {
        return betMatchesWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Match.class)
                .collectList()
                .block();
    }

    private Match updateMatch(final String uri, final Match match) {
        return betMatchesWebClient
                .put()
                .uri(uri)
                .body(just(match), Match.class)
                .retrieve()
                .bodyToMono(Match.class)
                .block();
    }
}
