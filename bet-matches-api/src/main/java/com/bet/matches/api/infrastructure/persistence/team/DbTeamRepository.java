package com.bet.matches.api.infrastructure.persistence.team;

import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class DbTeamRepository implements TeamRepository {

    private final DbTeamJpaRepository repository;

    @Override
    public List<Team> getAllTeams() {
        return repository.findAll();
    }

    @Override
    public Optional<Team> getTeam(final int id) {
        return repository.findById(id);
    }

    @Override
    public Team getTeamOnPlace(final int place) {
        return repository.findTeamByPlace(place);
    }

    @Override
    public Team addOrUpdateTeam(final Team team) {
        return repository.save(team);
    }

    @Override
    public void deleteTeam(final int id) {
        repository.deleteById(id);
    }

}
