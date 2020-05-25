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
public interface Handler { //<E extends Event> {
	
	public static enum HandlerType {
		Interceptor,
		Modifier,
		Reactor;
	}
	
	
//	@Subscribe
	/**
	 * Default subcribe function which should stay like this
	 */
//	public default void handle(E e) {
//		if(e.markedHandlers.contains(this)) return;
//		// e.markedHandlers.add(this); ? 
//		handle0(e);
//	}
	
	/**
	 * This is to be implemented by subclass handlers and therefore statuses
	 */
//	public void handle0(E e);
	
	/**
	 * Return true if the event must be processed by this handler.
	 * Return false if the event must be ignored by this handler.
	 */
	public default boolean check(Event e) {
		if(e.markedHandlers.contains(this)) return false;
		return true;
	}
	
	
	/** Implement the type of the handler */
	public HandlerType type();
	
	

	public static interface ahandler<E extends Event> {
		public default boolean checks(Event e) {
			return e.markedHandlers.contains(this);
		}
//		public default void handle(E e) {
//			
//		}
		public HandlerType type();
	}
	public static interface onwalkhandler extends ahandler {
		@Subscribe
		public default void onWalk0(Event e) {
			if(checks(e)) onWalk(e);
		}
		public void onWalk(Event e);
	}
	public static interface onentercellhandler extends ahandler {
		@Subscribe
		public default void onEnterCell0(Event e) {
			if(checks(e)) onEnterCell(e);
		}
		public void onEnterCell(Event e);
	}
	public static class aglyph implements onwalkhandler, onentercellhandler {
		@Override
		public void onWalk(Event e) {
		}

		@Override
		public void onEnterCell(Event e) {
		}

		@Override
		public HandlerType type() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	
}
