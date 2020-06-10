package com.souchy.randd.data.s1.spells.fire;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.spells.water.Bubble;

import data.new1.spellstats.SpellStats;
import data.new1.spellstats.base.IntStat;
import gamemechanics.data.effects.damage.Damage;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;
import gamemechanics.models.Spell;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.Resource;

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
