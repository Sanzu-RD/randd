package com.souchy.randd.data.creatures.sungjin.spells;


import com.google.common.collect.ImmutableList;
import com.souchy.randd.data.creatures.sungjin.SungjinModel;

import data.new1.Effect;
import data.new1.SpellModel;
import data.new1.spellstats.SpellStats;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;

public class IceShield extends SpellModel { //implements EbiSpellData {

	@Override
	public int id() {
		return SungjinModel.id + 8;
	}
	
	@Override
	public String getIconName() {
		return "SpellBook01_59";
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
