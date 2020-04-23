package com.bet.matches.dbbackend.core.betmatchesapi.mapper;

import com.bet.matches.dbbackend.core.betmatchesapi.model.MatchStatusBetMatches;
import com.bet.matches.dbbackend.core.rapidapi.model.MatchStatusRapidApi;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MatchStatusMapper {

    public static MatchStatusBetMatches toBetMatchesStatus(final String statusToConvert) {
        MatchStatusRapidApi matchStatusRapidApi = null;

        for (final MatchStatusRapidApi status : MatchStatusRapidApi.values()) {
            if (status.getValue().equals(statusToConvert)) {
                matchStatusRapidApi = status;
                break;
            }
        }

        if (matchStatusRapidApi == null) {
            return null;
        }

        switch (matchStatusRapidApi) {
            case TBD:
            case NS:
            case BT:
            case SUSP:
            case PST:
            case ABD:
                return MatchStatusBetMatches.NOT_STARTED;
            case _1H:
            case HT:
            case _2H:
            case ET:
            case P:
            case INT:
                return MatchStatusBetMatches.LIVE;
            case FT:
            case AET:
            case PEN:
            case CANC:
            case AWD:
            case WO:
                return MatchStatusBetMatches.FINISHED;
            default:
                return null;
        }
    }
}
