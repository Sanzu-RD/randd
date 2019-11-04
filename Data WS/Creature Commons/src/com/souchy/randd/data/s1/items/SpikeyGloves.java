package com.souchy.randd.data.s1.items;

import data.new1.timed.Status.Passive;
import gamemechanics.data.effects.damage.DmgEffect;
import gamemechanics.events.OnActionResolved;
import gamemechanics.events.OnActionResolved.OnActionResolvedHandler;
import gamemechanics.models.Item;
import gamemechanics.models.entities.Cell;
import gamemechanics.statics.stats.modifiers.Modifier.mathMod;
import gamemechanics.statics.stats.properties.Color;
import gamemechanics.statics.stats.properties.Resource;

public class SpikeyGloves extends Item {
	
	@Override
	public Integer id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		passives.add(new SpikeyGlovesPassive());
		
		stats.add(300, Resource.life, mathMod.scl);
		stats.add(20, Color.globalEle, mathMod.scl);
	}
	
	public static class SpikeyGlovesPassive extends Passive implements OnActionResolvedHandler {
		private DmgEffect effect;
		public SpikeyGlovesPassive() {
			effect = new DmgEffect();
			// effect.bases.add();
		}
		@Override
		public void onActionResolved(OnActionResolved e) {
			if(this.source == e.action.caster) {
				Cell cell = null; // e.action.cellX, cellY
				if(cell.hasCreature()) {
					var target = cell.getCreatures().get(0);
					effect.apply(this.source, target);
				}
			}
		}
	}



}
