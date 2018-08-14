package com.souchy.randd.situationtest.eventshandlers.turn;

import com.souchy.randd.situationtest.events.turn.TurnEndEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;

@FunctionalInterface
public interface OnTurnEndHandler extends EventHandler<TurnEndEvent> {

}
