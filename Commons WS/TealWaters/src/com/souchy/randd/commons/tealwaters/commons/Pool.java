package com.souchy.randd.commons.tealwaters.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * List of objects with a backing pool of instances. <br>
 * 
 * The backing pool contains a list of private instances. <br>
 * 
 * This list is a sublist of those instances. <br>
 * 
 * This activates those instances and makes them publicly usable. <br>
 * 
 * Therefore we can use methods like anotherlist.addAll(pool) to reference all of the activated instances.
 * 
 * 
 * @param <T>
 * @author Blank
 * @date 23 août 2021
 */
public class Pool<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8565069931084718278L;
	
	/**
	 * Backing pool of instances
	 */
	public List<T> backingPool = new ArrayList<>();
	
	/**
	 * Get an instance from the backing pool and add it to this.
	 */
	public T obtain(int i){
		T t = backingPool.get(i);
		this.add(t);
		return t;
	}

	/**
	 * Add an instance to the backing pool and to this.
	 */
	public void extend(T t) {
		backingPool.add(t);
		this.add(t);
	}
	
	/**
	 * Number of instances usable in the backing pool.
	 */
	public int poolSize() {
		return backingPool.size();
	}
	
	
	
}
