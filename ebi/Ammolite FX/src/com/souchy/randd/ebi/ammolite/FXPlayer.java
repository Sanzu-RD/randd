package com.souchy.randd.ebi.ammolite;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

public abstract class FXPlayer<T extends Event> extends Entity {
	
	public FXPlayer(Engine engine) {
		super(engine);
	}
	
	public abstract Class<?> modelClass();
	public abstract void onCreation(T event);
	
	@SuppressWarnings("unchecked")
	public FXPlayer<T> copy(Engine e) {
		try {
			var copy = this.getClass().getDeclaredConstructor(Engine.class).newInstance(e);
			return copy;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * When the parent entity is disposed, dispose the FXPlayer as well
	 */
	@Subscribe
	public void onParentDispose(DisposeEntityEvent e) {
		this.dispose();
	}
	
}
