package com.souchy.randd.data.s1.spells.ice;

import com.google.common.collect.ImmutableList;

import data.new1.spellstats.SpellStats;
import gamemechanics.models.SpellModel;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;

public class IceShield extends SpellModel {

	@Override
	public int id() {
		return 3;
	}

	@Override
	protected SpellStats initBaseStats() {
		// TODO Auto-generated method stub
		return null;
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
	
}
