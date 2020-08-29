package com.souchy.randd.commons.diamond.common.ecs;

import java.util.HashMap;

/**
 * 
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public class Entity implements EntityF {
	
	public final HashMap<Class<?>, Object> components = new HashMap<>();
	
	public Entity(Engine engine) {
		register(engine);
	}
	
	public void register(Engine engine) {
		if(engine == null) return; // for models cases like Spell, Status
		this.add(engine);
		engine.bus.register(this);
		engine.add(this);
	}
	
	public void dispose() {
		components.clear();
		get(Engine.class).remove(this);
	}
	
	public void update(float delta) {
		// could update all components here if we wanted an update function on them
	}
	
	/**
	 * set a component
	 */
	public void add(Object component) {
		if(component != null)
			components.put(component.getClass(), component);
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
		return (T) components.remove(componentClass);
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
	

//	/** fired after a component is added to the entity */
//	public static class AddComponentEvent {
//		public Component c;
//		public AddComponentEvent(Component c) {
//			this.c = c;
//		}
//	}
//	/** fired after a component is removed from the entity */
//	public static class RemoveComponentEvent {
//		public Component c;
//		public RemoveComponentEvent(Component c) {
//			this.c = c;
//		}
//	}
	
}
