package com.souchy.randd.tools.mapeditor.entities;

import com.souchy.randd.commons.tealwaters.ecs.Entity;

/**
 * Actions are taken on every T object of a system/family
 * 
 * The system/family foreaches its entities and calls action.update(e) on them
 * 
 * The Action maps the entity to whatever specificed in the getTClass() implementation 
 * 
 * @param <T>
 * @author Blank
 * @date 6 nov. 2021
 */
public interface SystemAction<T> {
	
	
	@SuppressWarnings("unchecked")
	public default void update(float delta, Entity e) {
		if(getTClass() == Entity.class) {
			process(delta, (T) e);
		} else {
			var obj = e.get(getTClass());
			if(obj == null) return;
			T inst = (T) obj;
			process(delta, inst);
		}
	}
	
	public Class<T> getTClass();
	
	public void process(float delta, T t);
	
}
