package com.bet.matches.api.infrastructure.persistence.betresults;

import com.bet.matches.api.core.betresults.BetMatchResult;
import com.bet.matches.api.core.betresults.BetMatchResultId;
import com.bet.matches.api.core.betresults.BetMatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class DbBetMatchResultRepository implements BetMatchResultRepository {

    private final DbBetMatchResultJpaRepository repository;

    @Override
    public Optional<BetMatchResult> getBetMatchResult(final BetMatchResultId id) {
        return repository.findById(id);
    }

    @Override
    public List<BetMatchResult> getAllBetMatchResultsOfSpecificUser(final UUID userId) {
        return repository.findByBetMatchResultIdUser(userId);
    }

    @Override
    public List<BetMatchResult> getAllBetMatchResultsOfSpecificMatch(final int matchId) {
        return repository.findByBetMatchResultIdMatch(matchId);
    }

    @Override
    public BetMatchResult addOrUpdateBetMatchResult(final BetMatchResult betMatchResult) {
        return repository.save(betMatchResult);
    }

    @Override
    public void deleteBetMatchResult(final BetMatchResultId id) {
        repository.deleteById(id);
    }
}
