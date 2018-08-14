package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;

public class TurnStartEvent extends Event {

	public TurnStartEvent(IEntity source) {
		super(source);
	}

}
