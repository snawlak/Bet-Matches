package com.bet.matches.dbbackend.core.betmatchesapi.mapper;

import com.bet.matches.dbbackend.core.betmatchesapi.model.Player;
import com.bet.matches.dbbackend.core.betmatchesapi.model.Team;
import com.bet.matches.dbbackend.core.rapidapi.model.PlayerRapidApi;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PlayerMapper {

    public static List<Player> toPlayers(final List<PlayerRapidApi> playersRapidApi, final Team team) {
        final List<Player> players = new ArrayList<>();
        playersRapidApi.forEach(playerRapidApi -> players.add(toPlayer(playerRapidApi, team)));
        return players;
    }

    public static Player toPlayer(final PlayerRapidApi playerRapidApi, final Team team) {
        return Player.builder()
                .firstName(playerRapidApi.getFirstName())
                .lastName(playerRapidApi.getLastName())
                .age(playerRapidApi.getAge())
                .nationality(playerRapidApi.getNationality())
                .goals(playerRapidApi.getGoals().getTotal())
                .team(team)
                .position(playerRapidApi.getPosition().toUpperCase())
                .idRapidApi(playerRapidApi.getPlayerId())
                .build();
    }

}
