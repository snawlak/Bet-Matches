package com.bet.matches.api.infrastructure.persistence.betextra;

import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.betextra.BetExtra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface DbBetExtraJpaRepository extends JpaRepository<BetExtra, UUID> {
    List<BetExtra> findAllByPlayer(final Player player);

    List<BetExtra> findAllByTeam(final Team team);
}
