package gamemechanics.events.new1;

import com.google.common.eventbus.Subscribe;

/**
 * Statuses have to implement Handlers (1 per status, if you have multiple handler types (intercept,modify,react), u shoud prob have multiple statuses)
 * 
 * @param <T> Event type to handle
 * @author Blank
 * @date 4 mars 2020
 */
public interface Handler<T extends Event> {
	
	public static enum HandlerType {
		Interceptor,
		Modifier,
		Reactor;
	}
	
	
	
	public HandlerType type();
	
	@Subscribe
	public void handle(T e);
	
	/*
	public default void handle(Event e) {
		// if the event or any parent of the event has been marked by this handler, do not touch them again
		if(recurseParentsMarks(e, this)) {
			return;
		}
	}
	
	private static boolean recurseParentsMarks(Event e, Handler h) {
		if(e.parent != null) {
			return e.markedHandlers.contains(h) && recurseParentsMarks(e, h);
		} else {
			return e.markedHandlers.contains(h);
		}
	}
	*/
	
}
