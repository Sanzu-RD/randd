package com.souchy.randd.commons.diamond.common.ecs;

public interface EntityF {
	
	public void register(Engine engine);
	
	public void dispose();
	
	/**
	 *  could update all components here if we wanted an update function on them
	 */
	public void update(float delta);
	
	/**
	 * set a component
	 */
	public void add(Object component);
	
	/**
	 * get a component type or null
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> componentClass);
	
	/**
	 * remove a component type
	 */
	@SuppressWarnings("unchecked")
	public <T> T remove(Class<T> componentClass);

	/**
	 * remove a component
	 */
	public boolean remove(Object component);

	/**
	 * has a component type
	 */
	public boolean has(Class<?> componentClass);
}