package com.souchy.randd.commons.tealwaters.ecs;

import java.util.HashMap;

import com.google.common.eventbus.EventBus;

/**
 * 
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public class Entity {
	
	/** inbound event to tell components to dispose */
	protected static class DisposeEntityEvent { }
	
	/**
	 * for inbound event propagation like proccing VFX when the entity receives an event
	 */
	protected EventBus componentBus = new EventBus();
	public final HashMap<Class<?>, Object> components = new HashMap<>();
	
	public Entity(Engine engine) {
		register(engine);
	}
	
	public void register(Engine engine) {
		if(engine == null) return; // for models cases like Spell, Status
		this.add(engine); // put the engine object as its child class (ex: Fight)
		components.put(Engine.class, engine); // put the engine class as Engine class
		engine.systemBus.register(this);
		engine.add(this);
	}
	
	public void dispose() {
		var engine = get(Engine.class);
		if(engine != null) {
			engine.systemBus.unregister(this);
			engine.remove(this);
		}
		componentBus.post(new DisposeEntityEvent());
		components.values().forEach(c -> componentBus.unregister(c));
		components.clear();
	}
	
	public void update(float delta) {
		// could update all components here if we wanted an update function on them
	}
	
	/**
	 * set a component
	 */
	public void add(Object component) {
		if(component != null) {
			components.put(component.getClass(), component);
			componentBus.register(component);
		}
	}

	/**
	 * get a component type or null
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> componentClass) {
		return (T) components.get(componentClass);
	}

	/**
	 * remove a component type
	 */
	@SuppressWarnings("unchecked")
	public <T> T remove(Class<T> componentClass) {
		var component = (T) components.remove(componentClass);
		componentBus.unregister(component);
		return component;
	}

	/**
	 * remove a component
	 */
	public boolean remove(Object component) {
		if(component == null) return false;
		return components.remove(component.getClass(), component);
	}

	/**
	 * has a component type
	 */
	public boolean has(Class<?> componentClass) {
		return components.containsKey(componentClass);
	}
	
	/**
	 * hashcode 
	 */
	public String hash() {
		return Integer.toHexString(hashCode());
	}
	
}
