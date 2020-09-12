package com.souchy.randd.commons.diamond.models;

import com.souchy.randd.commons.diamond.models.stats.CreatureStats;

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

	/**
	 * Base stats for the model
	 */
	public final CreatureStats baseStats;
	
	/**
	 * Amount of points the creature can use on affinities during customization. Some creatures are more flexible than others (ex: ditto pokemon)
	 */
	public final int affinityPoints;
	
	public CreatureModel() { //Fight f) {
//		super(f);
		baseStats = initBaseStats();
		affinityPoints = initAffinityPoints();
	}
	
	protected abstract CreatureStats initBaseStats();

	protected int initAffinityPoints() {
		return 30;
	}
	
}
