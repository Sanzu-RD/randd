package com.souchy.randd.situationtest.events;

import com.souchy.randd.jade.api.AEntity;

public abstract class Event {
	
	/**
	 * There's always a source entity to an event.
	 * Either the direct source, or the source of an effect that procs an effect.
	 * 
	 * <b>Except for Fight and Round Start/End</b>
	 */
	public final AEntity source;
	
	
	//public Entity target;
	
	
	public Event(AEntity source) {
		this.source = source;
	}
	
}
