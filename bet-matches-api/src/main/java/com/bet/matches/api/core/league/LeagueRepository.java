package com.bet.matches.api.core.league;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository {

    List<League> getAllLeagues();

    Optional<League> getLeague(final int id);

    League addOrUpdateLeague(final League league);

    void deleteLeague(final int id);


}
