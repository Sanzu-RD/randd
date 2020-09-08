package com.souchy.randd.commons.tealwaters.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * 
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public class Engine {
	
	protected static int engineIdCounter = 0;

	public final Map<Class<? extends System>, System> systems = new HashMap<>();
	
	/* *
	 * might delete this and just let systems take care of entities..
	 * @date 2020-07-04
	 */
//	public final List<Entity> entities = new ArrayList<>();
	
	public EventBus bus = new EventBus();
	
	public void add(System system) {
		systems.put(system.getClass(), system); // .add(system);
	}
	
	public void remove(System system) {
		systems.remove(system.getClass()); // .remove(system);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends System> T get(Class<T> systemclass) {
		return (T) systems.get(systemclass);
	}
	
	public void add(Entity entity) {
//		Log.info("engine add entity " + entity);
//		synchronized(entities) {
//			entities.add(entity);
			bus.post(new AddEntityEvent(entity));
//		}
	}
	
	public void remove(Entity entity) {
//		synchronized(entities) {
//			entities.remove(entity);
			bus.post(new RemoveEntityEvent(entity));
//		}
	}

	public void update(float delta) {
		synchronized(systems) {
			systems.values().forEach(s -> s.update(delta));
		}
//		synchronized(entities) {
//			 entities.forEach(e -> e.update(delta));
//		}
	}

	
	/** Event fired after an entity is added to the engine */
	public static class AddEntityEvent {
		public Entity entity;
		public AddEntityEvent(Entity e) {
			this.entity = e;
		}
	}
	/** Event fired after an entity is removed from the engine */
	public static class RemoveEntityEvent {
		public Entity entity;
		public RemoveEntityEvent(Entity e) {
			this.entity = e;
		}
	}
	
}
