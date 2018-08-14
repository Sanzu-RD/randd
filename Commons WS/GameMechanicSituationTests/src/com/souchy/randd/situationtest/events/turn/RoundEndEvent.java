package com.souchy.randd.situationtest.events.turn;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;

public class RoundEndEvent extends Event {

	public RoundEndEvent(IEntity source) {
		super(source);
	}

}
