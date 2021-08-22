package com.souchy.randd.data.s1.creatures.flora.spells;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.data.s1.creatures.flora.Flora;
import com.souchy.randd.data.s1.main.Elements;

public class Thorns extends Spell {

	public static final int modelid = Elements.earth.makeid(1, Flora.modelid, 9);

	public Thorns(Fight f) {
		super(f);
	}

	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		// TODO Auto-generated method stub
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
