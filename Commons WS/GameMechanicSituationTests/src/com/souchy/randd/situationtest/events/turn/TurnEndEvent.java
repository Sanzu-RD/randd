package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;

public class TurnEndEvent extends Event {

	public TurnEndEvent(IEntity source) {
		super(source);
	}

}
