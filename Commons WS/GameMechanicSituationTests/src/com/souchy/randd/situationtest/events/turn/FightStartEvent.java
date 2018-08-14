package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;

public class FightStartEvent extends Event {

	public FightStartEvent(IEntity source) {
		super(source);
	}

}
