package com.souchy.randd.data.s1.spells.water;

import com.google.common.collect.ImmutableList;

import data.new1.spellstats.SpellStats;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;
import gamemechanics.models.Spell;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;

public class Bubble extends Spell {

	public Bubble(Fight f) {
		super(f);
	}

	@Override
	public int modelid() {
		return 8;
	}

	@Override
	protected SpellStats initBaseStats() {
		var stats = new SpellStats();
		return stats;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCast(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canCast(Creature caster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canTarget(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Bubble(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
