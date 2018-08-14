package com.souchy.randd.situationtest.eventshandlers.turn;

import com.souchy.randd.situationtest.events.turn.TurnStartEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;

@FunctionalInterface
public interface OnTurnStartHandler extends EventHandler<TurnStartEvent> {

	
}
