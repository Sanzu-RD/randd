package com.souchy.randd.commons.diamond.statusevents.damage;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class DamageEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnDamageHandler extends Handler { //<OnDmgEvent> {
		@Subscribe
		public default void handle0(DamageEvent event) {
			if(check(event)) onDamage(event);
		}
		public void onDamage(DamageEvent e);
	}
	
	public DamageEvent(Creature caster, Cell target, Damage effect) {
		super(caster, target, effect);
	}

	@Override
	public DamageEvent copy0() {
		return new DamageEvent(source, target, (Damage) effect.copy());
	}
	
	@Override
	public String testMessage() {
		if(this.level == 0) return "";
		
		var dmg = ((Damage) effect);
		double totalDmg = 0;
		for(var ele : dmg.targetDmg.keySet()) totalDmg += dmg.targetDmg.get(ele).value();
		
		var creature = this.target.getCreature(this.effect.height);
		var msg = "[" + (creature == null ? "null" : creature.id) + "] dmg: " + totalDmg;

		Log.info("dmg event level " + this.level + " react on " + this.target.pos + " // " + msg);
		
		return msg;
	}
	
}
