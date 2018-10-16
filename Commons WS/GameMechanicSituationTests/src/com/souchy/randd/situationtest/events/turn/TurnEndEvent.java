package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.Event;

public class TurnEndEvent extends Event {

	public TurnEndEvent(IEntity source) {
		super(source);
	}

}
