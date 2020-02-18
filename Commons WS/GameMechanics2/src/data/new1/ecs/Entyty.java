package data.new1.ecs;

import java.util.HashMap;

public class Entyty {
	
	public HashMap<Class<? extends Component>, Component> components;
	
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
	}
	
}
