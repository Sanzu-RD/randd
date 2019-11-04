package data.new1.timed;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BuffList {
	
	private Map<Class<? extends Buff>, Buff> buffs;
	//private List<Buff> buffs;
	
	public BuffList() {
		buffs = new HashMap<>();
	}
	
	public void add(Buff s) {
		if(has(s.getClass())) {
			get(s.getClass()).stackAdd(s.stacks);
		} else {
			buffs.put(s.getClass(), s);
		}
	}
	
	public boolean has(Class<? extends Buff> s) {
		return buffs.containsKey(s);
	}
	
	public Buff get(Class<? extends Buff> s) {
		return buffs.get(s);
	}
	
	public void remove(Class<? extends Buff> s) {
		if(has(s)) {
			get(s).dispose();
			buffs.remove(s.getClass()); 
		}
	}
	
	public void remove(Buff s) {
		remove(s.getClass()); 
	}
	
	public void forEach(Consumer<Buff> c) {
		buffs.values().forEach(s -> c.accept(s));
	}

	
}
