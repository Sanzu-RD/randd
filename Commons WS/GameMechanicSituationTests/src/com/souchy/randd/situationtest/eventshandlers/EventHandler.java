package com.souchy.randd.situationtest.eventshandlers;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.situationtest.events.Event;

@FunctionalInterface
public interface EventHandler<T extends Event>  {

	@Subscribe
	public void handle(T event);
	
}
