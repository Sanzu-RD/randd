package com.souchy.randd.data.s1.spells.fire;

import com.google.common.collect.ImmutableList;
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
import com.souchy.randd.data.s1.main.Elements;

public class Fireball extends Spell {

	public Damage e1 = new Damage(null, null, null, null);
	
	public Fireball(Fight f) {
		super(f);
	}
	
	@Override
	public int modelid() {
		return 1;
	}

	@Override
	protected SpellStats initBaseStats() {
		var stats = new SpellStats();
		stats.costs.put(Resource.mana, new IntStat(5));
		return stats;
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
	public void onCast(Creature caster, Cell target) {
		e1.apply(caster, target);
		
	}

	@Override
	public boolean canCast(Creature caster) {
		return false;
	}

	@Override
	public boolean canTarget(Creature caster, Cell target) {
		return false;
	}
	
	@Override
	public Spell copy(Fight fight) {
		var s = new Fireball(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
