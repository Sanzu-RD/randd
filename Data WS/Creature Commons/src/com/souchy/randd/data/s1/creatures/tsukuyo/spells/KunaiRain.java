package com.souchy.randd.data.s1.creatures.tsukuyo.spells;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.effects.other.SummonEffect;
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
import com.souchy.randd.data.s1.creatures.tsukuyo.Tsukuyo;
import com.souchy.randd.data.s1.main.Elements;

public class KunaiRain extends Spell {

	public static final int modelid = Elements.normal.makeid(1, Tsukuyo.modelid, 2);
	
	public Damage e1;
	
	public KunaiRain(Fight f) {
		super(f);
		var formula = new ElementMap();
		formula.put(Elements.normal, new IntStat(50, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
	}

	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(0));
		stats.maxRangeRadius.baseflat = 6;
		//stats.maxRangePattern.base = (t) -> AoeBuilders.cross.apply(t); 
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
	public void cast0(Creature caster, Cell target) {
		e1.apply(caster, target);
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new KunaiRain(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
