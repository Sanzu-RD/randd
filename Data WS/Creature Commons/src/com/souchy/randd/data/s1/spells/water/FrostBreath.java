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
 * 4 small hits
 * 
 * @author Blank
 * @date 6 août 2021
 */
public class FrostBreath extends Spell {

	public static final int frostbreathID = Elements.water.makeid(1, 13);

	public Damage e1;
	
	public FrostBreath(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.water, new IntStat(7, 0, 10, 0));
		e1 = new Damage(AoeBuilders.cone.apply(2), TargetType.full.asStat(), formula);
		
		this.effects.add(e1);
	}
	
	@Override
	public int modelid() {
		return frostbreathID;
	}

	@Override
	protected SpellStats initBaseStats() {
		var stats = new SpellStats();
		stats.costs.put(Resource.mana, new IntStat(3));
		stats.maxRangeRadius.baseflat = 4;
		return stats;
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
		e1.apply(caster, target);
		e1.apply(caster, target);
	}

	
	@Override
	public Spell copy(Fight fight) {
		var s = new FrostBreath(fight);
		s.stats = stats.copy();
		return s;
	}

}
