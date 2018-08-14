package com.souchy.randd.situationtest.eventshandlers.turn;

import com.souchy.randd.situationtest.events.turn.RoundStartEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;

@FunctionalInterface
public interface OnRoundStartHandler extends EventHandler<RoundStartEvent> {

	
}
