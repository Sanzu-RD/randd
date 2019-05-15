package com.souchy.randd.ecs;

/**
 * Dont really know what to do with a system / what methods to have.
 * 
 * @author Plants
 *
 */
public interface System<T extends Component> {


	public default boolean has(Entity entity) {
		return entity.getComponents().containsKey(getType());
	}
	
	@SuppressWarnings("unchecked")
	public default T get(Entity entity) {
		return (T) entity.getComponents().get(getType());
	}
	
	public Class<T> getType();
	
}
