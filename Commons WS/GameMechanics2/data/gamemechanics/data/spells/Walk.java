package gamemechanics.data.spells;

import java.util.List;

import com.google.common.collect.ImmutableList;

import data.new1.SpellModel;
import data.new1.spellstats.SpellStats;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;

public class Walk extends SpellModel {

	@Override
	public int id() {
		// TODO Auto-generated method stub
		return 0;
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

//	@Override
//	public void cast(Entity caster, Cell target) {
//		var board = caster.fight.board;
//	}
//
//	
//	@Override
//	public void setCosts(List<BaseSpellCost> costs) {
//		costs.add(new BaseSpellCost(st.move, 1));
//	}
//
//
//	@Override
//	public String getSpellFX() {
//		return "";
//	}
//
//	@Override
//	public String getCasterAnimation() {
//		return "walk0";
//	}
	
}

