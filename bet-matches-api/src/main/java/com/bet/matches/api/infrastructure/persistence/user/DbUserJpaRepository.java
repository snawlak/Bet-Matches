package com.bet.matches.api.infrastructure.persistence.user;

import com.bet.matches.api.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface DbUserJpaRepository extends JpaRepository<User, UUID> {

}
