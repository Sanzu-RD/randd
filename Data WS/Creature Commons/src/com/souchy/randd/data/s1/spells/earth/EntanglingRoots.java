package com.souchy.randd.data.s1.spells.earth;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.resources.ResourceGainLoss;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
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
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.status.Rooted;

public class EntanglingRoots extends Spell {
	
	public static final int modelid = Elements.earth.makeid(1, 2);
	
	public AddStatusEffect e1;
	public ResourceGainLoss e2;
	
	public EntanglingRoots(Fight f) {
		super(f);
		e1 = new AddStatusEffect(AoeBuilders.single.get(), TargetType.full.asStat(), (ff) -> {
			var b = new Rooted(ff, 0, 0);
			b.duration = 1;
			b.stacks = 1;
			b.canDebuff = false;
			b.canRemove = false;
			return b;
		});
		e2 = new ResourceGainLoss(AoeBuilders.single.get(), TargetType.enemies.asStat(), false, Map.of(), Map.of(Resource.move, 3));
	}

	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(3));
		stats.maxRangeRadius.baseflat = 5;
		stats.maxRangePattern.base = (t) -> AoeBuilders.cross.apply(t);
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
	protected void cast0(Creature caster, Cell target) {
		e1.apply(caster, target);
		e2.apply(caster, target);
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new EntanglingRoots(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
