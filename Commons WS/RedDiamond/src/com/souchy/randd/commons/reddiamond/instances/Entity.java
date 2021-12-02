package com.souchy.randd.commons.reddiamond.instances;

import java.util.List;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.reddiamond.stats.ContextStats;

public class Entity {

	public int modelid;
	public int id;
	public ContextStats context;
	
//	public Entity(ContextStats context) {
//		this.context = new ContextStats(context);
//	}
	
	
//	private EventBus bus;
//	private List<Object> components;
//
//	
//	public void addComponent(Object o) {
//		components.add(o);
//		bus.register(o);
//	}
//	public void removeComponent(Object o) {
//		components.remove(o);
//		bus.unregister(o);
//	}
//	
//	public void dispose() {
//		for(var c : components)
//			if(c instanceof Entity e)
//				e.dispose();
//	}
	
}
