package com.bet.matches.api.infrastructure.persistence.team;

import com.bet.matches.api.core.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

interface DbTeamJpaRepository extends JpaRepository<Team, Integer> {
    Team findTeamByPlace(int place);
}
