package com.souchy.randd.data.s1.spells;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.displacement.Move;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;

public class Movement extends Spell {
	
	public Move tp;

	public Movement(Fight f) {
		super(f);
		tp = new Move(f, AoeBuilders.single.get(), TargetType.empty.asStat());
	}

	@Override
	public int modelid() {
		return 0;
	}

	@Override
	protected SpellStats initBaseStats() {
		var stats = new SpellStats();
//		stats.costs.put(Resource.mana, new IntStat(0));
		return stats;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of();
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		tp.apply(caster, target);
	}

	@Override
	public Spell copy(Fight fight) {
		return new Movement(fight);
	}
	
}
