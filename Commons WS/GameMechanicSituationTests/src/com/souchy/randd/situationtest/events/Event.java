package com.souchy.randd.situationtest.events;

import com.souchy.randd.situationtest.interfaces.IEntity;

public abstract class Event {
	
	/**
	 * There's always a source entity to an event.
	 * Either the direct source, or the source of an effect that procs an effect.
	 * 
	 * Except for Fight and Round Start/End
	 */
	public final IEntity source;
	
	
	//public Entity target;
	
	
	public Event(IEntity source) {
		this.source = source;
	}
	
}
