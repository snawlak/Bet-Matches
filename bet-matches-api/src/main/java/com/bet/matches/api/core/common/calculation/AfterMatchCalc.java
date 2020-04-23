package com.bet.matches.api.core.common.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AfterMatchCalc {

    private final Points points;

    private MatchResult actualMatchResult;
    private int actualTeamFirstScore;
    private int actualTeamSecondScore;
    private int betCorrectlyPoints;
    private int betWinningTeamOrDrawPoints;
    private boolean isInitialized = false;

    public void initializeCalculation(final int actualTeamFirstScore, final int actualTeamSecondScore) {
        this.actualTeamFirstScore = actualTeamFirstScore;
        this.actualTeamSecondScore = actualTeamSecondScore;
        actualMatchResult = getMatchResult(this.actualTeamFirstScore, this.actualTeamSecondScore);

        isInitialized = true;
    }

    public int calculateBetPoints(final int betTeamFirstScore, final int betTeamSecondScore) {
        if (isInitialized) {
            if (betTeamFirstScore == actualTeamFirstScore && betTeamSecondScore == actualTeamSecondScore) {
                return betCorrectlyPoints;
            } else {
                final MatchResult betMatchResult = getMatchResult(betTeamFirstScore, betTeamSecondScore);
                if (betMatchResult == actualMatchResult) {
                    return betWinningTeamOrDrawPoints;
                }
                return 0;
            }
        }

        throw new RuntimeException("Calculation has not been initialize");
    }

    @PostConstruct
    private void init() {
        betCorrectlyPoints = points.getBetCorrectly();
        betWinningTeamOrDrawPoints = points.getBetWinningTeamOrDraw();
    }

    private static MatchResult getMatchResult(final int teamFirstScore, final int teamSecondScore) {
        if (teamFirstScore > teamSecondScore) {
            return MatchResult.FIRST_TEAM_WON;
        } else if (teamFirstScore < teamSecondScore) {
            return MatchResult.SECOND_TEAM_WON;
        } else {
            return MatchResult.DRAW;
        }
    }

    private enum MatchResult {
        FIRST_TEAM_WON,
        SECOND_TEAM_WON,
        DRAW;
    }
}
