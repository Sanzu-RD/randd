package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.Event;

public class RoundStartEvent extends Event {

	public RoundStartEvent(AEntity source) {
		super(source);
	}

}
