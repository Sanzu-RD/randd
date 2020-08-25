package com.souchy.randd.commons.diamond.statusevents.damage;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class DmgEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnDmgHandler extends Handler { //<OnDmgEvent> {
		@Subscribe
		public default void handle0(DmgEvent event) {
			if(check(event)) onDmg(event);
		}
		public void onDmg(DmgEvent e);
	}
	
	public DmgEvent(Creature caster, Cell target, Damage effect) {
		super(caster, target, effect);
	}

	@Override
	public DmgEvent copy0() {
		return new DmgEvent(source, target, (Damage) effect.copy());
	}
	
}
