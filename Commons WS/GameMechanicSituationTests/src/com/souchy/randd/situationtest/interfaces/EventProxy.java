package com.souchy.randd.situationtest.interfaces;

import java.util.List;
import java.util.function.Function;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.events.turn.TurnEndEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;

/**
 * 
 * Interface to wrap EventBus so that my interfaces can implement it
 * 
 * @author Souchy
 *
 */
public interface EventProxy {
	
	
	public EventBus bus();
	
	public default void post(Event event) {
		bus().post(event);
	}
	
	/*public default <T extends Event> EventHandler<T> register(EventHandler<T> handler) {
		bus().register(handler);
		return handler;
	}*/
	public default <T extends EventHandler<?>> T register(T handler) {
		bus().register(handler);
		return handler;
	}
	
	public default <T extends Event> EventHandler<T> unregister(EventHandler<T> handler) {
		bus().unregister(handler);
		return handler;
	}

	/*public default <T extends Event> EventHandler<T> route(EventProxy proxy) {
		EventHandler<T> handler = (T event) -> {
			proxy.post(event);
		};
		register(handler);
		return handler;
	}*/
	@SuppressWarnings("unchecked")
	public default <T extends EventHandler<?>> T route(EventProxy proxy) {
		EventHandler<?> handler = (Event event) -> {
			proxy.post(event);
		};
		register(handler);
		return (T) handler;
	}
	
	/**
	 * Utile par exemple pour appliquer un event sur une liste de cellules, ou appliquer sur une liste de personnages.
	 * @param list - Liste de trucs à foreach qu'on utilise pour créer et poster l'event
	 * @param eventCreator
	 */
	public default <T> void multipost(List<T> list, Function<T, Event> eventCreator) {
		for(T t : list) {
			Event event = eventCreator.apply(t);
			post(event);
		}
	}

	/**
	 * Utile par exemple pour appliquer un event sur une liste de cellules, ou appliquer sur une liste de personnages.
	 * @param list - Liste de trucs à foreach qu'on utilise pour créer et poster l'event
	 * @param eventCreator
	 */
	public default <T extends EventProxy> void broadcast(List<T> list, Function<T, Event> eventCreator) {
		for(T t : list) {
			Event event = eventCreator.apply(t);
			t.post(event);
		}
	}
	
	
}
