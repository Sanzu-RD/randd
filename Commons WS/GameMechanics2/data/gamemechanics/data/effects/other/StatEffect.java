package gamemechanics.data.effects.other;

import data.new1.Effect;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;
import gamemechanics.statics.stats.StatMod;
import gamemechanics.statics.stats.StatModConverter;

public class StatEffect extends Effect {

	public StatMod statmod;
	
	public StatEffect(StatMod statmod, int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
		this.statmod = statmod;
	}

	@Override
	public void apply(Entity caster, Entity target) {
		// get creature
		Creature t = null;
		if(target instanceof Creature) {
			t = (Creature) target;
		} else {
			t = target.getCell().creatures.get(0);
		}
		if(t == null) return;
		
		// apply statmod
		if(statmod instanceof StatModConverter) {
			t.getTempStats().addConverter((StatModConverter) statmod);
		} else {
			t.getTempStats().add(statmod.value, statmod.prop, statmod.mods);
		}
	}
	
}
