package com.souchy.randd.situationtest.eventshandlers;

import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.statschange.StatChangeEvent;
import com.souchy.randd.situationtest.properties.StatProperty;

public class OnStatChangeHandler implements EventHandler<StatChangeEvent> {
	
	private final boolean inflict;
	private final IEntity handlerEntity;
	private final Consumer<StatProperty> method;
	
	/**
	 * 
	 * @param handlerEntity - The entity owning this handler, to check if it's the source or the target
	 * @param inflict - If we're trying handle inflicting dmg or receiving dmg
	 * @param method - What to do when the event occurs
	 */
	public OnStatChangeHandler(IEntity handlerEntity, boolean inflict, Consumer<StatProperty> method) {
		this.handlerEntity = handlerEntity;
		this.inflict = inflict;
		this.method = method;
	}
	
	@Override
	@Subscribe
	public void handle(StatChangeEvent event) {
		if(handlerEntity == event.source && inflict) {
			method.accept(event.changedProp);
		} 
		if(handlerEntity == event.target && !inflict){
			method.accept(event.changedProp);
		}
	}
	
}
