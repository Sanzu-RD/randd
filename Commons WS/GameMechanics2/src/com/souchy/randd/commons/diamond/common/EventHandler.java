package com.souchy.randd.commons.diamond.common;

@FunctionalInterface
public interface EventHandler<T extends FightEvent> {
	
	
	public void onEvent(T e);
	
}
