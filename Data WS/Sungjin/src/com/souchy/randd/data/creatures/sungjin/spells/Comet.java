package com.souchy.randd.data.creatures.sungjin.spells;

import com.souchy.randd.data.creatures.sungjin.SungjinModel;

import data.new1.*;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.Stats;

public class Comet extends SpellModel {
	
	@Override
	public int id() {
		return SungjinModel.id + 0001;
	}
	
	@Override
	protected Stats initBaseStats() {
		var stats = new Stats();
		stats.setSpellProperies(false, 0, 2, 2, 1, 7);
		return stats;
	}
	
	@Override
	protected Effect[] initEffects() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCast(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		for (var e : effects) {
			
		}
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
	
	/*
	 * @Override public int id() { return 1; }
	 * 
	 * @Override public String getIconName() { //getNameID(creature) return id() +
	 * ".png"; }
	 * 
	 * public String getPfxName() { return id() + ".pfx"; }
	 * 
	 * public String getAnimationName() { return "normal_cast"; }
	 * 
	 * public Comet() { //set effects }
	 * 
	 * public void onCast(/** creature source, cell target * /) { // things like
	 * effect.get(0).apply(target) } /** check if u can cast the spell at all,
	 * regardless of target * / public boolean canCast(/** creature source * /) { //
	 * check resource costs, cooldown, uses per turn boolean can = true; return can;
	 * } /** check if you can cast on a certain target after having checked if you
	 * can cast at all * / public boolean canTarget(/** creature source, cell target
	 * * /) { // check targetability, uses per target if(!canCast()) return false;
	 * // still check if u can cast at all in case ppl send packets manually boolean
	 * can = true; return can; }
	 */
	
}
