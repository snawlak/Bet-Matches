package com.bet.matches.dbbackend.service.scheduler;

import com.bet.matches.dbbackend.core.betmatchesapi.model.Match;
import com.bet.matches.dbbackend.service.MatchService;
import com.bet.matches.dbbackend.service.MatchUpdatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TournamentScheduler {

    private static final long UPDATING_PERIOD = TimeUnit.MINUTES.toMillis(5);

    private final SchedulerService schedulerService;
    private final MatchUpdatingService matchUpdatingService;
    private final MatchService matchService;

    @Scheduled(cron = "0 1 1 * * ?") // EVERY DAY AT 01:01 am
//    @Scheduled(fixedRate = 500000)     // TEST
    public void getAllMatchesFromToday() {
        final OffsetDateTime todayDate = OffsetDateTime.now();
        final List<Match> todaysMatches = matchService.getAllMatchesFromDate(todayDate);

        if (todaysMatches == null) {
            throw new RuntimeException("Consuming list of matches from Bet Matches Api went wrong");
        }

        if (!todaysMatches.isEmpty()) {
            todaysMatches.forEach(this::createMatchUpdatingTask);
        }
    }

    private void createMatchUpdatingTask(final Match match) {
        schedulerService.addMatchUpdatingTaskToScheduler(match.getId(),
                                                         match.getMatchDate(),
                                                         UPDATING_PERIOD,
                                                         matchUpdatingService.updateMatch(match));
    }
}

