package data.new1.ecs;

import java.util.ArrayList;
import java.util.List;

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

	public final List<System> systems = new ArrayList<>();
	public final List<Entity> entities = new ArrayList<>();
	
	public EventBus bus = new EventBus();
	
	public void add(System system) {
		systems.add(system);
	}
	
	public void remove(System system) {
		systems.remove(system);
	}
	
	public void add(Entity entity) {
//		Log.info("engine add entity " + entity);
		entities.add(entity);
		bus.post(new AddEntityEvent(entity));
	}
	
	public void remove(Entity entity) {
		entities.remove(entity);
		bus.post(new RemoveEntityEvent(entity));
	}

	public void update(float delta) {
		systems.forEach(s -> s.update(delta));
		entities.forEach(e -> e.update(delta));
	}

	/** fired after an entity is added to the engine */
	public static class AddEntityEvent {
		public Entity entity;
		public AddEntityEvent(Entity e) {
			this.entity = e;
		}
	}
	/** fired after an entity is removed from the engine */
	public static class RemoveEntityEvent {
		public Entity entity;
		public RemoveEntityEvent(Entity e) {
			this.entity = e;
		}
	}
	
}
