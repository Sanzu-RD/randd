package data.new1.ecs;

import java.util.HashMap;

/**
 * 
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public class Entity {
	
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
	
	public void add(Object c) {
		if(c != null)
			components.put(c.getClass(), c);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> c) {
		return (T) components.get(c);
	}

	@SuppressWarnings("unchecked")
	public <T> T remove(Class<T> c) {
		return (T) components.remove(c);
	}
	
	public boolean remove(Object c) {
		if(c == null) return false;
		return components.remove(c.getClass(), c);
	}
	
	public boolean has(Class<?> c) {
		return components.containsKey(c);
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
