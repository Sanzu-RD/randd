package com.souchy.randd.situationtest.events;

import com.souchy.randd.situationtest.interfaces.IEntity;

public class OnHitEvent extends Event {
	
	/** receiver of the hit */
	public final IEntity target;

	public OnHitEvent(IEntity source, IEntity target) {
		super(source);

		this.target = target;
	}

}
