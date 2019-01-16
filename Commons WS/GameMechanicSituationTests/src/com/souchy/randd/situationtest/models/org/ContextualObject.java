package com.souchy.randd.situationtest.models.org;

public abstract class ContextualObject {

	public final FightContext context;
	
	public ContextualObject(FightContext context) {
		this.context = context;
	}
	
	public FightContext getContext() {
		return context;
	}
	
}
