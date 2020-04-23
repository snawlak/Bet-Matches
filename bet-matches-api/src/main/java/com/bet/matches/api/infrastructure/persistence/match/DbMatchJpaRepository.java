package com.bet.matches.api.infrastructure.persistence.match;

import com.bet.matches.api.core.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

interface DbMatchJpaRepository extends JpaRepository<Match, Integer> {

    List<Match> findAllByMatchDateGreaterThanEqualAndMatchDateLessThan(
            final OffsetDateTime startDate, final OffsetDateTime endDate);

    List<Match> findAllByMatchDay(final int matchDay);

}
