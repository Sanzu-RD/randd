package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.Event;

public class FightStartEvent extends Event {

	public FightStartEvent(AEntity source) {
		super(source);
	}

}
