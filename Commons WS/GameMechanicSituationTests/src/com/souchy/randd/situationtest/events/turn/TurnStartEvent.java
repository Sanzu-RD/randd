package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.Event;

public class TurnStartEvent extends Event {

	public TurnStartEvent(IEntity source) {
		super(source);
	}

}
