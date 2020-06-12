package gamemechanics.models;

import data.new1.spellstats.CreatureStats;

/**
 * Creature Model
 * 
 * clean up on this date
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public abstract class CreatureModel {  //extends Entity {

	/** 
	 * model id
	 */
	public abstract int id();

	public final CreatureStats baseStats;
	
	public CreatureModel() { //Fight f) {
//		super(f);
		baseStats = initBaseStats();
	}
	
	protected abstract CreatureStats initBaseStats();
	
}
