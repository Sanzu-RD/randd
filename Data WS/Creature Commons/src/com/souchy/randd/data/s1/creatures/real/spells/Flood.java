package com.souchy.randd.data.s1.creatures.real.spells;

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
import com.souchy.randd.data.s1.creatures.real.Real;
import com.souchy.randd.data.s1.main.Elements;

public class Flood extends Spell {

	public static final int modelid = Elements.water.makeid(1, Real.modelid, 2);
	
	public Damage e1;
	
	public Flood(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.water, new IntStat(50, 0, 10, 0));
		e1 = new Damage(AoeBuilders.circle.apply(3), TargetType.full.asStat(), formula);
	}

	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(3));
		stats.maxRangeRadius.baseflat = 3;
		//stats.maxRangePattern.base = (t) -> AoeBuilders.cross.apply(t); 
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
		//this.get(Fight.class).board.cells.values().forEach(c -> e1.apply(caster, c));
		e1.apply(caster, target);
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Flood(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
