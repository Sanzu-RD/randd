package com.souchy.randd.commons.diamond.common.ecs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.common.ecs.Engine.AddEntityEvent;
import com.souchy.randd.commons.diamond.common.ecs.Engine.RemoveEntityEvent;

/**
 * Synchronizes all access to a family of entities
 * 
 * @param <T>
 * @author Blank
 * @date 4 juill. 2020
 */
public abstract class Family<T> extends com.souchy.randd.commons.diamond.common.ecs.System {
	
	/**
	 * might use CopyOnWriteArrayList ? 
	 */
	private final List<T> family = new ArrayList<>();
	private final Class<T> clazz;
	
	public Family(Engine engine, Class<T> clazz) {
		super(engine);
		this.clazz = clazz;
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		synchronized(family) {
			return family.size();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<T> copy(){
		return new ArrayList<>(family);
	}
	
	/**
	 * 
	 * @param action
	 */
	public void foreach(Consumer<T> action) {
		synchronized(family) {
			for(var member : family) {
				action.accept(member);
			}
		}
	}
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	public List<T> where(Predicate<T> filter){
		var list = new ArrayList<T>();
		foreach(c -> {
			if(filter.test(c)) list.add(c);
		});
		return list;
	}
	
	/**
	 * Return the first member if it exists
	 * @return
	 */
	public T first() {
		synchronized(family) {
			for(var member : family) 
				return member;
		}
		return null;
	}
	/**
	 * Return the first member matching the filter or null if no member fits 
	 */
	public T first(Predicate<T> filter) {
		synchronized(family) {
			for(var member : family) {
				if(filter.test(member))
					return member;
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	public void clear() {
		synchronized(family) {
			family.clear();
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void dispose() {
		super.dispose();
		clear();
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void add(T entity) {
		synchronized(family) {
			family.add(entity);
		}
	}
	/**
	 * 
	 * @param entity
	 */
	public void remove(T entity) {
		synchronized(family) {
			family.remove(entity);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	@Subscribe
	public void onAddedEntity(AddEntityEvent event) {
		if (clazz.isInstance(event.entity)) {
//			Log.info("Family " + this + " ("+clazz+") add " + event.entity);
			add(clazz.cast(event.entity));
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	@Subscribe
	public void onRemovedEntity(RemoveEntityEvent event) {
		if (clazz.isInstance(event.entity)) 
			remove(clazz.cast(event.entity));
	}
	
}