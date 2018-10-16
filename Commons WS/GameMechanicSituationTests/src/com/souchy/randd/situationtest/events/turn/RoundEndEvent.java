package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.Event;

public class RoundEndEvent extends Event {

	public RoundEndEvent(IEntity source) {
		super(source);
	}

}
