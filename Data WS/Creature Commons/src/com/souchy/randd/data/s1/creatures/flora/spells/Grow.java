package com.souchy.randd.data.s1.creatures.flora.spells;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;

public class Grow extends Spell {

	public Grow(Fight f) {
		super(f);
	}

	@Override
	public int modelid() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected SpellStats initBaseStats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void cast0(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spell copy(Fight fight) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
