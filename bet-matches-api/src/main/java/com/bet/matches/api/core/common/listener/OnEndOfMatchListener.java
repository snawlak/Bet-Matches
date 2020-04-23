package com.bet.matches.api.core.common.listener;

import com.bet.matches.api.core.betresults.BetMatchResult;
import com.bet.matches.api.core.betresults.BetMatchResultRepository;
import com.bet.matches.api.core.common.calculation.AfterMatchCalc;
import com.bet.matches.api.core.common.event.OnEndOfMatchEvent;
import com.bet.matches.api.core.match.Match;
import com.bet.matches.api.core.user.User;
import com.bet.matches.api.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OnEndOfMatchListener {

    private final AfterMatchCalc afterMatchCalc;
    private final BetMatchResultRepository betMatchResultRepository;
    private final UserRepository userRepository;

    @EventListener
    public void updateUserPoints(final OnEndOfMatchEvent event) {
        final Match match = event.getMatch();
        updateUsersPoints(match);
    }

    private void updateUsersPoints(final Match match) {
        final List<BetMatchResult> allBetResults =
                betMatchResultRepository.getAllBetMatchResultsOfSpecificMatch(match.getId());
        final int actualTeamFirstScore = match.getTeamFirstScore();
        final int actualTeamSecondScore = match.getTeamSecondScore();
        final Map<UUID, Integer> usersPoints = new HashMap<>();

        afterMatchCalc.initializeCalculation(actualTeamFirstScore, actualTeamSecondScore);

        allBetResults.forEach(betMatchResult -> getUsersWhichBetCorrectly(usersPoints, betMatchResult));
        usersPoints.forEach(this::addPointsToUsers);
    }

    private void addPointsToUsers(final UUID idUser, final Integer points) {
        final User user = this.userRepository.getUser(idUser).orElseThrow(RuntimeException::new);
        final int currentPoints = user.getPoints();
        user.setPoints(currentPoints + points);
        this.userRepository.addOrUpdateUser(user);
    }

    private void getUsersWhichBetCorrectly(final Map<UUID, Integer> usersPoints, final BetMatchResult betMatchResult) {
        final int betTeamFirstScore = betMatchResult.getTeamFirstScore();
        final int betTeamSecondScore = betMatchResult.getTeamSecondScore();
        final int pointsToAdd = afterMatchCalc.calculateBetPoints(betTeamFirstScore, betTeamSecondScore);
        if (pointsToAdd != 0) {
            usersPoints.put(betMatchResult.getBetMatchResultId().getUser(), pointsToAdd);
        }
    }
}
