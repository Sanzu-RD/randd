package com.souchy.randd.data.s1.spells.secondary.ice;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;

public class Hail extends Spell {

	public Hail(Fight f) {
		super(f);
	}

	@Override
	public int modelid() {
		return 2;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.maxRangeRadius.baseflat = 16;
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
	public void cast0(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Hail(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
