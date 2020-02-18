package gamemechanics.data.effects.damage;

import java.util.Map;

import data.new1.Effect;
import data.new1.spellstats.base.IntStat;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.damage.OnDmg;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;
import gamemechanics.statics.Element;

/**
 * Damage always applies to the primary resource of the targets (ex: life)
 * Then it can be modified by the target to mitigate damage with another resource
 * 
 * @author Blank
 * @date 19 janv. 2020
 */
public class Damage extends Effect {

	public Damage(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
	}
	
	/** propagate */
	public void pub() {
		/*
		caster.interceptors.pub(this);
		if(this.intercepted) return;
		caster.modifiers.pub(this); 
		foreach(target2) {  
			var copy = this.copy(target2); 
			target.interceptors.pub(copy);
			if(copy.intercepted) break;
			target.modifiers.pub(copy);
				copy.apply(target); //target.appliers.pub(copy);
			caster.reactors.pub(copy); 
			target.reactors.pub(copy);
		} 
		*/
	}

	@Override
	public void apply0(Entity caster, Cell target) {
		
		Map<Element, IntStat> eleDmg = null;
		
		double baseDmg;
		double sclDmg;
		
		var crea = (Creature) caster;
		
		eleDmg.forEach((ele, i) -> {
			var af = crea.getStats().affinity.get(ele);
			var result = (
							(i.base + af.baseflat)
							 + (i.inc * af.inc)
						 )
						 * (i.more + af.more);
						 
		});
		
	}

	@Override
	public Event createAssociatedEvent() {
		return new OnDmg(this);
	}
	
}
