package com.souchy.randd.data.s1.spells.secondary.ice;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;

public class IceWall extends Spell {

	public IceWall(Fight f) {
		super(f);
	}

	@Override
	public int modelid() {
		return 6;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.maxRangePattern.base = (r) -> AoeBuilders.cross.apply(r);
		stats.maxRangeRadius.baseflat = 5;
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
		var s = new IceWall(fight);
		s.stats = stats.copy();
		return s;
	}
	
	
}
