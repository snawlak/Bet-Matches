package com.bet.matches.api.infrastructure.persistence.player;

import com.bet.matches.api.core.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface DbPlayerJpaRepository extends JpaRepository<Player, Integer> {
    List<Player> findPlayerByGoals(final int goals);

    List<Player> findAllByIdRapidApi(final int rapidApiId);

}
