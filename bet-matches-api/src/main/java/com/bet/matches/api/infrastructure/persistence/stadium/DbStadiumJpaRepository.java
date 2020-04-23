package com.bet.matches.api.infrastructure.persistence.stadium;

import com.bet.matches.api.core.stadium.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

interface DbStadiumJpaRepository extends JpaRepository<Stadium, Integer> {

}
