package com.bet.matches.api.core.betextra;

import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.team.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetExtraRepository {

    Optional<BetExtra> getBetExtra(final UUID id);

    List<BetExtra> getAllWhoBetTopScorer(final Player player);

    List<BetExtra> getAllWhoBetWinningTeam(final Team team);

    BetExtra addOrUpdateBetExtra(final BetExtra playerTeam);

    void deleteBetExtra(final UUID id);
}
