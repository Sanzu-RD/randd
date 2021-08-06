package com.souchy.randd.data.s1.spells.normal;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.data.s1.main.Elements;

public class Jab extends Spell {
	
	

	public Damage e1;
	
	public Jab(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.normal, new IntStat(15, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		this.effects.add(e1);
	}

	@Override
	public int modelid() {
		return 100;
	}

	@Override
	protected SpellStats initBaseStats() {
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
