package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.Event;

public class TurnStartEvent extends Event {

	public TurnStartEvent(AEntity source) {
		super(source);
	}

}
