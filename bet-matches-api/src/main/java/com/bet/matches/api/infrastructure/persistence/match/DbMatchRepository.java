package com.bet.matches.api.infrastructure.persistence.match;

import com.bet.matches.api.core.match.Match;
import com.bet.matches.api.core.match.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class DbMatchRepository implements MatchRepository {

    private final DbMatchJpaRepository repository;

    @Override
    public List<Match> getAllMatches() {
        return repository.findAll();
    }

    @Override
    public List<Match> getAllMatchesFromDate(final OffsetDateTime startDate, final OffsetDateTime endDate) {
        return repository.findAllByMatchDateGreaterThanEqualAndMatchDateLessThan(startDate, endDate);
    }

    @Override
    public List<Match> getAllMatchesFromMatchDay(final int matchDay) {
        return repository.findAllByMatchDay(matchDay);
    }

    @Override
    public Optional<Match> getMatch(final int id) {
        return repository.findById(id);
    }

    @Override
    public Match addOrUpdateMatch(final Match match) {
        return repository.save(match);
    }

    @Override
    public void deleteMatch(final int id) {
        repository.deleteById(id);
    }
}
