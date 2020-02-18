package data.new1.timed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import gamemechanics.events.new1.status.OnStatusAdd;
import gamemechanics.events.new1.status.OnStatusLose;

public class StatusList {
	
	//private Map<Class<? extends Status>, Status> statuses;
	private List<Status> statuses;
	
	public StatusList() {
		//statuses = new HashMap<>();
		statuses = new ArrayList<>();
	}
	
	public boolean has(Class<? extends Status> c) {
		//return statuses.containsKey(c);
		for(var s : statuses)
			if(s.getClass() == c)
				return true;
		return false;
	}
	
	public Status getFirst(Class<? extends Status> c) {
		for(var s : statuses)
			if(s.getClass() == c)
				return s;
		return null;
		//return statuses.get(c);
	}
	
	public void add(Status s) {
		boolean fused = false;
		if(has(s.getClass())) {
			fused = getFirst(s.getClass()).fuse(s); //.stackAdd(s.stacks);
		} 
		if(!fused){
			//statuses.put(s.getClass(), s);
			statuses.add(s);
			s.onAdd();
			//s.target.fight.bus.post(new OnStatusAdd(s));
		}
	}
	
//	public void remove(Class<? extends Status> s) {
//		if(has(s)) {
//			get(s).onLose();
//			get(s).dispose();
//			//statuses.remove(s.getClass()); 
//			statuses.remove(s);
//		}
//	}

	/** remove one status */
	public void remove(Status s) {
		//remove(s.getClass()); 
		var removed = statuses.remove(s);
		if(removed) {
            removeProcess(s);
		}
	}
	/** remove all */
	public void removeIf(Predicate<Status> filter) {
        var it = statuses.iterator();
        while (it.hasNext()) {
        	var s = it.next();
            if (filter.test(s)) {
                it.remove();
                removeProcess(s);
            }
        }
		//statuses.removeIf(filter);
	}
	/** remove all instances of the status class */
	public void remove(Class<? extends Status> c) {
		removeIf(s -> s.getClass() == c);
	}
	
	private void removeProcess(Status s) {
    	s.onLose();
		//s.target.fight.bus.post(new OnStatusLose(s));
    	s.dispose();
	}
	
	public void forEach(Consumer<Status> c) {
		//statuses.values().forEach(s -> c.accept(s));
		statuses.forEach(s -> c.accept(s));
	}

	public int size() {
		return statuses.size();
	}
	
}