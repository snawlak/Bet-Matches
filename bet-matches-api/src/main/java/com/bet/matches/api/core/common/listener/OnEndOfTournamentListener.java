package com.bet.matches.api.core.common.listener;

import com.bet.matches.api.core.common.calculation.AfterTournamentCalc;
import com.bet.matches.api.core.common.event.OnEndOfTournamentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnEndOfTournamentListener {

    private final AfterTournamentCalc afterTournamentCalc;

    @EventListener
    public void onEndOfTournament(final OnEndOfTournamentEvent event) {
        afterTournamentCalc.addPointsToUsersWhoBetTopScorer();
        afterTournamentCalc.addPointsToUsersWhoBetWinningTeam();
    }
}
