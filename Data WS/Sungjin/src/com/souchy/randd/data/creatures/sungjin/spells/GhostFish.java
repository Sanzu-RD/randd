package com.souchy.randd.data.creatures.sungjin.spells;


import com.souchy.randd.data.creatures.sungjin.SungjinModel;

import data.new1.Effect;
import data.new1.SpellModel;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.stats.Stats;

public class GhostFish  extends SpellModel { //implements EbiSpellData {
	
	@Override
	public int id() {
		return SungjinModel.id + 9;
	}

	@Override
	public String getIconName() {
		return "SpellBook01_72.PNG";
	}

	@Override
	protected Stats initBaseStats() {
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
		new SummonEffect();
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
