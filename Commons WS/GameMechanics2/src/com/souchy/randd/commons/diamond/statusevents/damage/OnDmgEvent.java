package com.souchy.randd.commons.diamond.statusevents.damage;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnDmgEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnDmgHandler extends Handler { //<OnDmgEvent> {
		@Subscribe
		public default void handle0(OnDmgEvent event) {
			if(check(event)) onDmg(event);
		}
		public void onDmg(OnDmgEvent e);
	}
	
	public OnDmgEvent(Creature caster, Cell target, Damage effect) {
		super(caster, target, effect);
	}

	@Override
	public OnDmgEvent copy0() {
		return new OnDmgEvent(source, target, (Damage) effect.copy());
	}
	
}
