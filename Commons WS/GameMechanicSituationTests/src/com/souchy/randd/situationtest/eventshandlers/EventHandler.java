package com.souchy.randd.situationtest.eventshandlers;

import com.souchy.randd.situationtest.events.Event;

//@FunctionalInterface
public interface EventHandler<T extends Event>  {

	// FIXME @Subscribe dans eventhandler bug tous les handlers prenennt tous les events � cause que le type generic est effac�, 
	// FIXME du coup faut mettre le @Subscribe dans l'interface qui impl�mente eventhandler (ex OnStatChangeHandler ou OnHitReceived)
	public void handle(T event);
	
}
