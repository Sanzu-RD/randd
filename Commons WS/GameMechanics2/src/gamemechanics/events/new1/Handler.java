package gamemechanics.events.new1;

import com.google.common.eventbus.Subscribe;

/**
 * Statuses have to implement Handlers (1 per status, if you have multiple handler types (intercept,modify,react), you shoud prob have multiple statuses)
 * <p>
 * 
 * Receive handlers have to check for event level > 0 to not cancel entire effects like a outward interceptor 
 * <br>
 * ---	aka differentiate between interceptors(caster, event) and interceptors(target, event) even if the target @ lvl 1 is the caster @ lvl 0
 * 
 * 
 * @param <E> Event type to handle
 * @author Blank
 * @date 4 mars 2020
 */
public interface Handler<E extends Event> {
	
	public static enum HandlerType {
		Interceptor,
		Modifier,
		Reactor;
	}
	
	
	@Subscribe
	/**
	 * Default subcribe function which should stay like this
	 */
	public default void handle(E e) {
		if(e.markedHandlers.contains(this)) return;
		// e.markedHandlers.add(this); ? 
		handle0(e);
	}
	
	/**
	 * This is to be implemented by subclass handlers and therefore statuses
	 */
	public void handle0(E e);
	
	
	/** Implement the type of the handler */
	public HandlerType type();
	
}
