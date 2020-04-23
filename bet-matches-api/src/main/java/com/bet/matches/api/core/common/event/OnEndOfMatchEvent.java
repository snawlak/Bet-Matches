package com.bet.matches.api.core.common.event;

import com.bet.matches.api.core.match.Match;
import org.springframework.context.ApplicationEvent;


public class OnEndOfMatchEvent extends ApplicationEvent {

    private final Match match;

    public OnEndOfMatchEvent(final Object source, final Match match) {
        super(source);
        this.match = match;
    }

    public Match getMatch() {
        return this.match;
    }
}
