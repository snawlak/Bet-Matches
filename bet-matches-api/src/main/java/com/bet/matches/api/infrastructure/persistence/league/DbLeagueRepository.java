package com.bet.matches.api.infrastructure.persistence.league;

import com.bet.matches.api.core.league.League;
import com.bet.matches.api.core.league.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class DbLeagueRepository implements LeagueRepository {

    private final DbLeagueJpaRepository repository;

    @Override
    public List<League> getAllLeagues() {
        return repository.findAll();
    }

    @Override
    public Optional<League> getLeague(final int id) {
        return repository.findById(id);
    }

    @Override
    public League addOrUpdateLeague(final League league) {
        return repository.save(league);
    }

    @Override
    public void deleteLeague(final int id) {
        repository.deleteById(id);
    }

}
