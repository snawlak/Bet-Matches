package com.bet.matches.api.infrastructure.persistence.betresults;

import com.bet.matches.api.core.betresults.BetMatchResult;
import com.bet.matches.api.core.betresults.BetMatchResultId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface DbBetMatchResultJpaRepository extends JpaRepository<BetMatchResult, BetMatchResultId> {

    List<BetMatchResult> findByBetMatchResultIdUser(final UUID userId);

    List<BetMatchResult> findByBetMatchResultIdMatch(final int matchId);
}
