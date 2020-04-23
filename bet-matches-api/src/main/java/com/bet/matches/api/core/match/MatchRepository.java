package com.bet.matches.api.core.match;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    List<Match> getAllMatches();

    List<Match> getAllMatchesFromDate(final OffsetDateTime startDate, final OffsetDateTime endDate);

    List<Match> getAllMatchesFromMatchDay(final int matchDay);

    Optional<Match> getMatch(final int id);

    Match addOrUpdateMatch(final Match match);

    void deleteMatch(final int id);

}
