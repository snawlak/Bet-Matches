package com.bet.matches.dbbackend.core.rapidapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    YELLOW_CARD("Card", "Yellow Card"),
    RED_CARD("Card", "Red Card"),
    GOAL("Goal", "Normal Goal"),
    OWN_GOAL("Own Goal", "Own Goal"),
    PENALTY("Penalty", "Penalty"),
    MISSED_PENALTY("Missed Penalty", "Missed Panelty"),
    SUBSTITUTION("subs", "-");

    private final String type;
    private final String detail;

}
