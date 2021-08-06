package com.souchy.randd.data.s1.spells.fire;

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
import com.souchy.randd.data.s1.main.Elements;

public class Meteor extends Spell {

	public Damage e1;
	
	
	public Meteor(Fight f) {
		super(f);

		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.fire, new IntStat(50, 0, 10, 0));
		e1 = new Damage(AoeBuilders.circle.apply(4), TargetType.enemies.asStat(), formula);

		this.effects.add(e1);
//		var d = new Damage(AoeBuilders.circle.apply(3));
	}

	@Override
	public int modelid() {
		return 10;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
//		s.costs.put(Resource.mana, new IntStat(5));
		stats.maxRangeRadius.baseflat = 10;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.fire);
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
		var s = new Meteor(fight);
		s.stats = this.stats.copy();
		return s;
	}
	
}
