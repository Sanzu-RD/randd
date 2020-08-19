package com.souchy.randd.commons.diamond.common;

import com.souchy.randd.commons.diamond.models.Creature;

public abstract class Action {
	
	public static final int MoveActionID = 0;
	
	/**
	 * the action's caster / source
	 */
	public Creature caster;
	/**
	 * action id. 0 = walk (Action.MoveActionID), {1-...} = spells ids
	 */
	public int id; 
	/**
	 * target cell x coordinate
	 */
	public int cellX;
	/**
	 * target cell y coordinate
	 */
	public int cellY;
	/**
	 * turn on which it was casted
	 */
	public int turn;
	
	/**
	 * apply/resolve the action on the pipeline
	 */
	public abstract void apply();
	
}
