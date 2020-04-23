package com.bet.matches.dbbackend.service;

import com.bet.matches.dbbackend.configuration.BetMatchesApiWeb;
import com.bet.matches.dbbackend.configuration.RapidApiWeb;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Player;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Team;
import com.bet.matches.dbbackend.core.betmatchesapi.mapper.PlayerMapper;
import com.bet.matches.dbbackend.core.rapidapi.PlayersRapidApiResponse;
import com.bet.matches.dbbackend.core.rapidapi.model.PlayerRapidApi;
import com.bet.matches.dbbackend.core.rapidapi.model.TeamRapidApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.just;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final RapidApiWeb rapidApiWeb;
    private final BetMatchesApiWeb betMatchesApiWeb;

    private final WebClient betMatchesWebClient;
    private final WebClient rapidApiWebClient;

    private final List<PlayerRapidApi> playersRapidApi = new ArrayList<>();
    private final List<Player> playersBetMatches = new ArrayList<>();

    private String playerBetMatchesUri;
    private String playerRapidApiUri;
    private String seasonRapidApiId;
    private boolean playersFetched = false;
    private boolean playersSent = false;

    public void fetchPlayersFromRapidApi(final List<TeamRapidApi> teamsRapidApi) {
        if (!playersFetched) {
            teamsRapidApi.forEach(this::fetchRapidApiPlayers);
            playersFetched = true;
            log.info("Players (" + playersRapidApi.size() + ") from Rapid Api has been fetched");
        }
    }

    public void sendPlayersToBetMatchesApi(final List<Team> teamsBetMatches) {
        if (!playersSent && playersFetched && !playersRapidApi.isEmpty()) {
            final List<Player> playersToSend = new ArrayList<>();
            playersRapidApi.forEach(playerRapidApi -> makePlayers(playerRapidApi, teamsBetMatches, playersToSend));
            playersToSend.forEach(player -> playersBetMatches.add(sendPlayer(player)));
            playersSent = true;
            log.info("Players (" + playersBetMatches.size() + ") has been added to Bet Matches");
        }
    }

    public Player getPlayerFromBetMatchesByRapidApiId(final int rapidApiId) {
        final String uri = playerBetMatchesUri + "?rapidApiId=" + rapidApiId;
        return this.getBetMatchesPlayer(uri).get(0);
    }

    public Player updatePlayerInBetMatchesApi(final int id, final Player player) {
        final String uri = playerBetMatchesUri + id;
        return this.updatePlayer(uri, player);
    }


    @PostConstruct
    private void init() {
        playerRapidApiUri = rapidApiWeb.getEndpoint().getPlayersFromTeam();
        seasonRapidApiId = rapidApiWeb.getId().getSeason();
        playerBetMatchesUri = betMatchesApiWeb.getEndpoint().getPlayers();
    }

    private void fetchRapidApiPlayers(final TeamRapidApi teamRapidApi) {
        playersRapidApi.addAll(fetchPlayers(teamRapidApi));
    }

    private List<PlayerRapidApi> fetchPlayers(final TeamRapidApi teamRapidApi) {
        final int teamRapidApiId = teamRapidApi.getTeamId();
        final String playerUri = playerRapidApiUri + teamRapidApiId + "/" + seasonRapidApiId;
        final PlayersRapidApiResponse playersResponse = rapidApiWebClient
                .get()
                .uri(playerUri)
                .retrieve()
                .bodyToMono(PlayersRapidApiResponse.class)
                .block();

        if (playersResponse != null) {
            final List<PlayerRapidApi> playersRapidApi = playersResponse
                    .getPlayersResponseInner()
                    .getPlayersRapidApi()
                    .stream()
                    .filter(playerRapidApi ->
                                    playerRapidApi.getLeague().equals(rapidApiWeb.getId().getLeagueSpainName()))
                    .collect(Collectors.toList());

            playersRapidApi.forEach(playerRapidApi -> playerRapidApi.setIdTeamRapidApi(teamRapidApiId));

            return playersRapidApi;
        }
        throw new RuntimeException("Something went wrong with consuming Rapid Api Team endpoints");
    }

    private void makePlayers(final PlayerRapidApi playerRapidApi,
                             final List<Team> teamsBetMatches,
                             final List<Player> players) {

        final int rapidApiTeamId = playerRapidApi.getIdTeamRapidApi();
        for (final Team team : teamsBetMatches) {
            if (team.getIdRapidApi() == rapidApiTeamId) {
                players.add(PlayerMapper.toPlayer(playerRapidApi, team));
                break;
            }
        }
    }

    private Player sendPlayer(final Player player) {
        return betMatchesWebClient
                .post()
                .uri(playerBetMatchesUri)
                .body(just(player), Player.class)
                .retrieve()
                .bodyToMono(Player.class)
                .block();
    }

    private Player updatePlayer(final String uri, final Player player) {
        return betMatchesWebClient
                .put()
                .uri(uri)
                .body(just(player), Player.class)
                .retrieve()
                .bodyToMono(Player.class)
                .block();
    }

    private List<Player> getBetMatchesPlayer(final String uri) {
        return betMatchesWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Player.class)
                .collectList()
                .block();
    }
}
