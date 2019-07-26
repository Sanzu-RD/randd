package com.souchy.randd.data.creatures.sungjin.spells;


import data.new1.Effect;
import data.new1.SpellModel;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.NewStats;

public class IceShield extends SpellModel { //implements EbiSpellData {

	@Override
	public int id() {
		return 8;
	}
	
//	@Override
//	public String getIconName() {
//		return "SpellBook01_59.PNG";
//	}

	@Override
	protected NewStats initBaseStats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Effect[] initEffects() {
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
