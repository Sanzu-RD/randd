package data.new1.ecs;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class Engine {

	public static final List<System> systems = new ArrayList<>();
	public static final List<Entity> entities = new ArrayList<>();
	
	public static EventBus bus = new EventBus();
	
	public static void add(System system) {
		systems.add(system);
	}
	
	public static void remove(System system) {
		systems.remove(system);
	}
	
	public static void add(Entity entity) {
		entities.add(entity);
		bus.post(new AddEntityEvent(entity));
	}
	
	public static void remove(Entity entity) {
		entities.remove(entity);
		bus.post(new RemoveEntityEvent(entity));
	}

	public static void update(float delta) {
		systems.forEach(s -> s.update(delta));
		entities.forEach(e -> e.update(delta));
	}

	/** fired after an entity is added to the engine */
	public static class AddEntityEvent {
		public Entity e;
		public AddEntityEvent(Entity e) {
			this.e = e;
		}
	}
	/** fired after an entity is removed from the engine */
	public static class RemoveEntityEvent {
		public Entity e;
		public RemoveEntityEvent(Entity e) {
			this.e = e;
		}
	}
	
}
