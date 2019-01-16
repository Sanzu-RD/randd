package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.Event;

public class RoundEndEvent extends Event {

	public RoundEndEvent(AEntity source) {
		super(source);
	}

}
