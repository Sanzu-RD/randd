package data.new1.timed;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class StatusList {
	
	private Map<Class<? extends Status>, Status> statuses;
	//private List<Status> statuses;
	
	public StatusList() {
		statuses = new HashMap<>();
	}
	
	public void add(Status s) {
		if(has(s.getClass())) {
			get(s.getClass()).fuse(s); //.stackAdd(s.stacks);
		} else {
			statuses.put(s.getClass(), s);
		}
	}
	
	public boolean has(Class<? extends Status> s) {
		return statuses.containsKey(s);
	}
	
	public Status get(Class<? extends Status> s) {
		return statuses.get(s);
	}
	
	public void remove(Class<? extends Status> s) {
		if(has(s)) {
			get(s).dispose();
			statuses.remove(s.getClass()); 
		}
	}
	
	public void remove(Status s) {
		remove(s.getClass()); 
	}
	
	public void forEach(Consumer<Status> c) {
		statuses.values().forEach(s -> c.accept(s));
	}

	
}