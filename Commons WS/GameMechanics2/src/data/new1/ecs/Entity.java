package data.new1.ecs;

import java.util.HashMap;

public class Entity {
	
	//public HashMap<Class<? extends Component>, Component> components;
	public final HashMap<Class<?>, Object> components = new HashMap<>();
	
	public Entity() {
		Engine.add(this);
		Engine.bus.register(this);
	}
	
	public void dispose() {
		components.clear();
		Engine.remove(this);
	}
	
	public void update(float delta) {
		
	}

	public void add(Object c) {
		if(c != null)
			components.put(c.getClass(), c);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> c) {
//		if(!has(c)) return null;
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
	/*
	public void add(Component c) {
		components.put(c.getClass(), c);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T get(Class<T> c) {
		if(!has(c)) return null;
		return (T) components.get(c);
	}

	public Component remove(Class<? extends Component> c) {
		return components.remove(c);
	}
	
	public boolean remove(Component c) {
		return components.remove(c.getClass(), c);
	}
	
	public boolean has(Class<? extends Component> c) {
		return components.containsKey(c);
	}*/
}
