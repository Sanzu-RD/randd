package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.Event;

public class FightStartEvent extends Event {

	public FightStartEvent(IEntity source) {
		super(source);
	}

}
