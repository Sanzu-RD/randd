package com.souchy.randd.situationtest.events;

import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.models.Effect;

public class ApplyEffectEvent extends Event {

	public ApplyEffectEvent(IEntity source, Effect e) {
		super(source);
	}
	

}
