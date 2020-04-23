package com.bet.matches.dbbackend.core.betmatchesapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchStatusBetMatches {
    NOT_STARTED("NOT_STARTED"),
    LIVE("LIVE"),
    FINISHED("FINISHED");

    private final String value;
}
