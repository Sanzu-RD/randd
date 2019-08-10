package com.souchy.randd.data.s1.items;

import java.util.function.Consumer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.common.FightEvent;
import gamemechanics.data.effects.damage.DmgEffect;
import gamemechanics.events.OnActionResolved;
import gamemechanics.events.OnActionResolved.OnActionResolvedHandler;
import gamemechanics.events.OnCanCastActionCheck;
import gamemechanics.events.OnCanCastActionCheck.OnCanCastActionHandler;
import gamemechanics.models.Item;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.Modifier.mathMod;
import gamemechanics.stats.StatProperty.element;
import gamemechanics.stats.StatProperty.resource;
import gamemechanics.status.Status;
import gamemechanics.status.Status.Passive;

public class SpikeyGloves extends Item {
	
	@Override
	public Integer id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		passives.add(new SpikeyGlovesPassive());
		
		stats.add(300, resource.life, mathMod.scl);
		stats.add(20, element.globalEle, mathMod.scl);
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
