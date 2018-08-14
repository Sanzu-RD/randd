package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;

public class RoundStartEvent extends Event {

	public RoundStartEvent(IEntity source) {
		super(source);
	}

}
