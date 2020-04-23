package com.bet.matches.api.core.betresults;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetMatchResultRepository {

    Optional<BetMatchResult> getBetMatchResult(final BetMatchResultId id);

    List<BetMatchResult> getAllBetMatchResultsOfSpecificUser(final UUID userId);

    List<BetMatchResult> getAllBetMatchResultsOfSpecificMatch(final int matchId);

    BetMatchResult addOrUpdateBetMatchResult(final BetMatchResult betMatchResult);

    void deleteBetMatchResult(final BetMatchResultId id);
}
