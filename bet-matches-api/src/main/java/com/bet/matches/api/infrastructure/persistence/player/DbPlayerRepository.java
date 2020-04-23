package com.bet.matches.api.infrastructure.persistence.player;

import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.player.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class DbPlayerRepository implements PlayerRepository {

    private final DbPlayerJpaRepository repository;

    @Override
    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    @Override
    public Optional<Player> getPlayer(final int id) {
        return repository.findById(id);
    }

    @Override
    public List<Player> getPlayersByRapidApiId(final int rapidApiId) {
        return repository.findAllByIdRapidApi(rapidApiId);
    }

    @Override
    public List<Player> getPlayersWithHighestScore() {
        final List<Player> players = getAllPlayers();
        final int highestScore = players.stream()
                .mapToInt(Player::getGoals)
                .max()
                .orElseThrow(RuntimeException::new);
        return repository.findPlayerByGoals(highestScore);
    }

    @Override
    public Player addOrUpdatePlayer(final Player player) {
        return repository.save(player);
    }

    @Override
    public void deletePlayer(final int id) {
        repository.deleteById(id);
    }
}
