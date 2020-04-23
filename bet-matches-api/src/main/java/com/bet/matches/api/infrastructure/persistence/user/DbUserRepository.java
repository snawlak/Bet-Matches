package com.bet.matches.api.infrastructure.persistence.user;

import com.bet.matches.api.core.user.User;
import com.bet.matches.api.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class DbUserRepository implements UserRepository {

    private final DbUserJpaRepository repository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getUser(final UUID id) {
        return repository.findById(id);
    }

    @Override
    public User addOrUpdateUser(final User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(final UUID id) {
        repository.deleteById(id);
    }
}
