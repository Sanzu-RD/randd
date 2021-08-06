package com.souchy.randd.data.s1.spells;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.effects.displacement.Move;
import com.souchy.randd.commons.diamond.effects.resources.ResourceGainLoss;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.data.s1.status.Rooted;

public class Movement extends Spell {
	
	public Move tp;

	public Movement(Fight f) {
		super(f);
		this.stats.target = TargetType.empty.asStat();
		tp = new Move(AoeBuilders.single.get(), TargetType.empty.asStat());
	}

	@Override
	public int modelid() {
		return 0;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
//		stats.costs.put(Resource.move, new IntStat(0));
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
	public boolean canCast(Creature caster) {
		if(caster.statuses.hasStatus(Rooted.class)) return false;
		return super.canCast(caster);
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		var path = Pathfinding.aStar(caster.get(Fight.class).board, caster, caster.getCell(), target);
		tp.apply(caster, target);
		var cost = new HashMap<Resource, Integer>();
		cost.put(Resource.move, path.size());
		ResourceGainLoss.use(caster, cost);
	}

	@Override
	public Spell copy(Fight fight) {
		return new Movement(fight);
	}
	
}
