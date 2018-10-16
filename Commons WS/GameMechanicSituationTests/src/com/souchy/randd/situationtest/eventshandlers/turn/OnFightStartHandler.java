package com.souchy.randd.situationtest.eventshandlers.turn;

import com.souchy.randd.situationtest.events.turn.FightStartEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;

@FunctionalInterface
public interface OnFightStartHandler extends EventHandler<FightStartEvent> {

	
}
