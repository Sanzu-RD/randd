package com.souchy.randd.data.s1.spells.water;

import com.souchy.randd.commons.diamond.models.Spell;

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
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;

/**
 * 
 * 
 * @author Blank
 * @date 6 août 2021
 */
public class WaterPillar extends Spell {

	public static final int waterpillarID = Elements.water.makeid(1, 14);

	public Damage e1;
	
	public WaterPillar(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.water, new IntStat(70, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		this.effects.add(e1);
	}
	
	@Override
	public int modelid() {
		return waterpillarID;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(5));
		stats.maxRangeRadius.baseflat = 8;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.water);
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		e1.apply(caster, target);
		e1.apply(caster, target);
	}

	
	@Override
	public Spell copy(Fight fight) {
		var s = new WaterPillar(fight);
		s.stats = stats.copy();
		return s;
	}

}
