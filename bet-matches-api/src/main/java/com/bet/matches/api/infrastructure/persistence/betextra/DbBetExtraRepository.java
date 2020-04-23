package com.bet.matches.api.infrastructure.persistence.betextra;

import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.betextra.BetExtra;
import com.bet.matches.api.core.betextra.BetExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class DbBetExtraRepository implements BetExtraRepository {

    private final DbBetExtraJpaRepository repository;

    @Override
    public Optional<BetExtra> getBetExtra(final UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<BetExtra> getAllWhoBetTopScorer(final Player player) {
        return repository.findAllByPlayer(player);
    }

    @Override
    public List<BetExtra> getAllWhoBetWinningTeam(final Team team) {
        return repository.findAllByTeam(team);
    }

    @Override
    public BetExtra addOrUpdateBetExtra(final BetExtra playerTeam) {
        return repository.save(playerTeam);
    }

    @Override
    public void deleteBetExtra(final UUID id) {
        repository.deleteById(id);
    }
}
