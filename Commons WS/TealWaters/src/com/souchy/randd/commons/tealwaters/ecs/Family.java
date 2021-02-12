package com.souchy.randd.commons.tealwaters.ecs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.ecs.Engine.AddEntityEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine.RemoveEntityEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * Synchronizes all access to a family of entities
 * 
 * @param <T>
 * @author Blank
 * @date 4 juill. 2020
 */
public abstract class Family<T> extends com.souchy.randd.commons.tealwaters.ecs.System {
	
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
	 * Returns a copy of the member list
	 */
	public List<T> all(){
		return copy();
	}
	
	/**
	 * Returns a copy of the member list
	 */
	public List<T> copy() {
		synchronized (family) {
			return new ArrayList<>(family);
		}
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
	 * Returns a new list of members fitting the filter
	 */
	public List<T> where(Predicate<T> filter) {
		var list = new ArrayList<T>();
		foreach(c -> {
			if(filter.test(c)) list.add(c);
		});
		return list;
	}
	/**
	 * Returns a new list of members fitting the filter
	 */
	public List<T> list(Predicate<T> filter){
		return where(filter);
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
	 * Checks if any member matches the predicate by checking if <br> <code>first(pred) != null</code>
	 */
	public boolean any(Predicate<T> filter) {
		return first(filter) != null;
	}
	
	/**
	 * Finds the member with the highest of a property
	 */
	public <V> T findMax(Function<T, Double> extractor) {
		synchronized (family) {
			double max = Double.MIN_VALUE;
			T result = null;
			for (var member : family) {
				var val = extractor.apply(member);
				if(val > max) {
					 max = val;
					 result = member;
				}
			}
			return result;
		}
	}

	/**
	 * Finds the member with the lowest of a property
	 */
	public <V> T findMin(Function<T, Double> extractor) {
		synchronized (family) {
			double min = Double.MAX_VALUE;
			T result = null;
			for (var member : family) {
				var val = extractor.apply(member);
				if(val < min) {
					 min = val;
					 result = member;
				}
			}
			return result;
		}
	}
	
	/**
	 * Clear the list of members
	 */
	public void clear() {
		synchronized(family) {
			family.clear();
		}
	}
	
	/**
	 * Dispose and clear
	 */
	@Override
	public void dispose() {
		super.dispose();
		clear();
	}
	
	/**
	 * Adds a member to the list
	 */
	public void add(T entity) {
		synchronized(family) {
			family.add(entity);
		}
	}
	
	/**
	 * Removes a member from the list
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
			if(this.getClass().getName().contains("CellSystem") == false)
				Log.info("Family " + this.getClass().getSimpleName() + " ("+clazz.getSimpleName()+") add " + event.entity);
			add(clazz.cast(event.entity));
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	@Subscribe
	public void onRemovedEntity(RemoveEntityEvent event) {
		if (clazz.isInstance(event.entity)) {
			if(this.getClass().getName().contains("CellSystem") == false)
				Log.info("Family " + this.getClass().getSimpleName() + " ("+clazz.getSimpleName()+") remove " + event.entity);
			remove(clazz.cast(event.entity));
		}
	}
	
}