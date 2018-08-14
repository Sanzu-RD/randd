package com.souchy.randd.situationtest.asdf;

import java.util.LinkedList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.situationtest.events.Event;


/**
 * A pipeline which handles a certain type of event. <br>
 * 
 * Multiple handlers can handle the event before the default handler does. <br>
 * 
 * The process can be stopped if a handler returns false; <br>
 * 
 * @author Souchy
 *
 * @param <T>
 *            Type of Event
 */
public class HandlerPipeline<T extends Event> {
	
	private final List<Handler<T>> asdf;
	private final Handler<T> defaultHandler;
	/** True to cast the defaultHandler last. False to cast it first. */
	private final boolean last;
	
	public HandlerPipeline(Handler<T> defaultHandler) {
		asdf = new LinkedList<>();
		last = true;
		this.defaultHandler = defaultHandler;
	}
	
	public void addFirst(Handler<T> p) {
		asdf.add(0, p);//.addFirst(p);
	}
	public boolean remove(Handler<T> p) {
		return asdf.remove(p);
	}
	
	public void apply(T t) {
		boolean stop = false;
		if(!last) defaultHandler.handle(t);
		for(Handler<T> p : asdf) {
			stop = p.handle(t);
			if(stop) return;
		}
		if(last) defaultHandler.handle(t);
	}
	
	@FunctionalInterface
	public static interface Handler<T> {
		@Subscribe
		public boolean handle(T t);
	}
	
}
