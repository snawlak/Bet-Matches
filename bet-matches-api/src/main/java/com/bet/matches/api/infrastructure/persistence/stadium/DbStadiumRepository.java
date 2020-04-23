package com.bet.matches.api.infrastructure.persistence.stadium;

import com.bet.matches.api.core.stadium.Stadium;
import com.bet.matches.api.core.stadium.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class DbStadiumRepository implements StadiumRepository {

    private final DbStadiumJpaRepository repository;

    @Override
    public List<Stadium> getAllStadiums() {
        return repository.findAll();
    }

    @Override
    public Optional<Stadium> getStadium(final int id) {
        return repository.findById(id);
    }

    @Override
    public Stadium addOrUpdateStadium(final Stadium stadium) {
        return repository.save(stadium);
    }

    @Override
    public void deleteStadium(final int id) {
        repository.deleteById(id);
    }
}
