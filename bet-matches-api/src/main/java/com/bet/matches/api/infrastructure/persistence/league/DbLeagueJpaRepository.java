package com.bet.matches.api.infrastructure.persistence.league;

import com.bet.matches.api.core.league.League;
import org.springframework.data.jpa.repository.JpaRepository;

interface DbLeagueJpaRepository extends JpaRepository<League, Integer> {

}
