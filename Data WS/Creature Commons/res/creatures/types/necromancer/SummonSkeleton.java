package creatures.types.necromancer;

import data.new1.Effect;
import data.new1.SpellModel;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.Stats;

/**
 * These spells need to be registered to their creature type to be available to creatures of that type when the creature data is created
 * 
 * @author Blank
 *
 */
public class SummonSkeleton extends SpellModel { //implements EbiSpellData {

	@Override
	public int id() {
		return 6;
	}

//	@Override
//	public String getIconName() {
//		return "SpellBook01_55.PNG";
//	}
	
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
