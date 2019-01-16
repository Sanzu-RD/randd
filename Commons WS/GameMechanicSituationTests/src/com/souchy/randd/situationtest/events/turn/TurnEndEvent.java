package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.Event;

public class TurnEndEvent extends Event {

	public TurnEndEvent(AEntity source) {
		super(source);
	}

}
