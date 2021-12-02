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
import com.souchy.randd.commons.diamond.models.stats.maps.ElementMap;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.spells.fire.FireStorm;

public class Uppercut extends Spell {

	public static final int modelid = Elements.normal.makeid(1, 2);

	public Damage e1;
	
	public Uppercut(Fight f) {
		super(f);
		var formula = new ElementMap();
		formula.put(Elements.normal, new IntStat(15, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		this.effects.add(e1);
	}

	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.normal);
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	protected void cast0(Creature caster, Cell target) {
		e1.apply(caster, target);
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Uppercut(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
