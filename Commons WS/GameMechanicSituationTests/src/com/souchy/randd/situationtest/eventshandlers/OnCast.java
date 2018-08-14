package com.souchy.randd.situationtest.eventshandlers;

import com.souchy.randd.situationtest.events.CastSpellEvent;

@FunctionalInterface
public interface OnCast extends EventHandler<CastSpellEvent> {

	//@Subscribe
	//public void onCastSpell(CastSpellEvent event);
	
}
