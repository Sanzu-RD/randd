package com.souchy.randd.situationtest.events;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.models.Effect;

public class ApplyEffectEvent extends Event {

	public ApplyEffectEvent(AEntity source, Effect e) {
		super(source);
	}
	

}
