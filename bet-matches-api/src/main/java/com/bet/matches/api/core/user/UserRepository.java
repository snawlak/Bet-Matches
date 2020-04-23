package com.bet.matches.api.core.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    List<User> getAllUsers();

    Optional<User> getUser(final UUID id);

    User addOrUpdateUser(final User user);

    void deleteUser(final UUID id);
}
