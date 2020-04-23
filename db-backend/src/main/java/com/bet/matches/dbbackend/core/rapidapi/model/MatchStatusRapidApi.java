package com.bet.matches.dbbackend.core.rapidapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchStatusRapidApi {
    TBD("TBD"),     // Time To Be Defined
    NS("NS"),       // Not Started
    _1H("1H"),      // First Half, Kick Off
    HT("HT"),       // Halftime
    _2H("2H"),      // Second Half, 2nd Half Started
    ET("ET"),       // Extra Time
    P("P"),         // Penalty In Progress
    FT("FT"),       // Match Finished
    AET("AET"),     // Match Finished After Extra Time
    PEN("PEN"),     // Match Finished After Penalty
    BT("BT"),       // Break Time (in Extra Time)
    SUSP("SUSP"),   // Match Suspended
    INT("INT"),     // Match Interrupted
    PST("PST"),     // Match Postponed
    CANC("CANC"),   // Match Cancelled
    ABD("ABD"),     // Match Abandoned
    AWD("AWD"),     // Technical Loss
    WO("WO");       // WalkOver

    private final String value;
}
