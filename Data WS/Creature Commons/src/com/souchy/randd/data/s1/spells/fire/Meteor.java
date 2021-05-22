package com.souchy.randd.data.s1.spells.fire;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;

public class Meteor extends Spell {

	public Meteor(Fight f) {
		super(f);
		
		var d = new Damage(AoeBuilders.circle.apply(3))
	}

	@Override
	public int modelid() {
		return 0;
	}

	@Override
	protected SpellStats initBaseStats() {
		var s = new SpellStats();
		s.maxRangeRadius.baseflat = 10;
		
		return null;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return null;
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return null;
	}

	@Override
	protected void cast0(Creature caster, Cell target) {
		
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Meteor(fight);
		
		return s;
	}
	
}
