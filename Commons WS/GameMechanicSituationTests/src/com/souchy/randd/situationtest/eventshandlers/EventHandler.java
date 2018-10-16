package com.souchy.randd.situationtest.eventshandlers;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.situationtest.events.Event;

@FunctionalInterface
public interface EventHandler<T extends Event>  {

	// FIXME @Subscribe dans eventhandler bug tous les handlers prenennt tous les events à cause que le type generic est effacé, 
	// FIXME du coup faut mettre le @Subscribe dans l'interface qui implémente eventhandler (ex OnStatChangeHandler ou OnHitReceived)
	public void handle(T event);
	
}
