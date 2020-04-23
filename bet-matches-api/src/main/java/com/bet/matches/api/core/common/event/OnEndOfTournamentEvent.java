package com.bet.matches.api.core.common.event;

import org.springframework.context.ApplicationEvent;

public class OnEndOfTournamentEvent extends ApplicationEvent {

    public OnEndOfTournamentEvent(final Object source) {
        super(source);
    }
}
