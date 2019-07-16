package com.souchy.randd.jade.meta;


public class Action {

	/**
	 * the action's caster / source
	 */
	public int casterID;
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
	
}
