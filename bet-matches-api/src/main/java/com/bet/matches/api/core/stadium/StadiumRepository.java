package com.bet.matches.api.core.stadium;

import java.util.List;
import java.util.Optional;

public interface StadiumRepository {

    List<Stadium> getAllStadiums();

    Optional<Stadium> getStadium(final int id);

    Stadium addOrUpdateStadium(final Stadium stadium);

    void deleteStadium(final int id);
}
