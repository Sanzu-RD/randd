package com.souchy.randd.data.s1.spells.earth;

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
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.spells.water.Bubble;

public class SunnyDay extends Spell {

	public static final int modelid = Elements.earth.makeid(1, 3);
	
	public Damage e1;
	
	public SunnyDay(Fight f) {
		super(f);
		var formula = new ElementMap();
		formula.put(Elements.earth, new IntStat(15, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		this.effects.add(e1);
	}

	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(1));
		stats.maxRangeRadius.baseflat = 8;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.earth);
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		e1.apply(caster, target);
	}

	
	@Override
	public Spell copy(Fight fight) {
		var s = new SunnyDay(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
