package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.Event;

public class RoundStartEvent extends Event {

	public RoundStartEvent(IEntity source) {
		super(source);
	}

}
