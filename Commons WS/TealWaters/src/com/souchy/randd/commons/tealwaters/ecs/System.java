package com.souchy.randd.commons.tealwaters.ecs;

import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * 
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public abstract class System {

	// secret hack
	private final Engine engine;
	
	public System(Engine engine) {
		Log.info("System " + this + " register to " + engine);
		this.engine = engine;
		engine.add(this);
	}

	public void dispose() {
		engine.remove(this);
	}
	
	protected Engine getEngine() {
		return engine;
	}
	
	public abstract void update(float delta);
	
}
