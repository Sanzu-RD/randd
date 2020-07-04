package data.new1.ecs;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;

/**
 * 
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public class Engine {
	
	protected static int engineIdCounter = 0;

	private final List<System> systems = new ArrayList<>();
	
	/* *
	 * might delete this and just let systems take care of entities..
	 * @date 2020-07-04
	 */
//	public final List<Entity> entities = new ArrayList<>();
	
	public EventBus bus = new EventBus();
	
	public void add(System system) {
		systems.add(system);
	}
	
	public void remove(System system) {
		systems.remove(system);
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
			systems.forEach(s -> s.update(delta));
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
