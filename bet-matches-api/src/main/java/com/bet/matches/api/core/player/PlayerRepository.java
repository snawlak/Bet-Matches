package com.bet.matches.api.core.player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    List<Player> getAllPlayers();

    Optional<Player> getPlayer(final int id);

    List<Player> getPlayersByRapidApiId(final int rapidApiId);

    List<Player> getPlayersWithHighestScore();

    Player addOrUpdatePlayer(final Player player);

    void deletePlayer(final int id);
}
