package com.souchy.randd.situationtest.eventshandlers.turn;

import com.souchy.randd.situationtest.events.turn.RoundEndEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;

@FunctionalInterface
public interface OnRoundEndHandler extends EventHandler<RoundEndEvent> {


}
