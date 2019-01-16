package com.souchy.randd.situationtest.eventshandlers;

import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.statschange.StatChangeEvent;
import com.souchy.randd.situationtest.properties.StatProperty;

public class OnStatChangeHandler implements EventHandler<StatChangeEvent> {
	
	private final boolean inflict;
	private final AEntity handlerEntity;
	private final Consumer<StatChangeEvent> method;
	
	/**
	 * 
	 * @param handlerEntity - The entity owning this handler, to check if it's the source or the target
	 * @param inflict - If we're trying handle inflicting dmg or receiving dmg
	 * @param method - What to do when the event occurs
	 */
	public OnStatChangeHandler(AEntity handlerEntity, boolean inflict, Consumer<StatChangeEvent> method) {
		this.handlerEntity = handlerEntity;
		this.inflict = inflict;
		this.method = method;
	}
	
	@Override
	@Subscribe
	public void handle(StatChangeEvent event) {
		if(handlerEntity == event.source && inflict) {
			method.accept(event);
		} 
		if(handlerEntity == event.target && !inflict){
			method.accept(event);
		}
	}
	
}
