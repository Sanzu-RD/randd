package com.souchy.randd.jade.oldcombat;

public interface Cell {

	
	/**
	 * 
	 * @return <s>True is the cell type blocks the line of sight</s>
	 */
	public boolean blocksLineOfSight();
	
	/**
	 * 
	 * @return True if you can walk on this cell 
	 */
	public boolean walkable();
	
	/**
	 * Should be Vector3
	 * @return
	 */
	public float[] getPos(); 
	
	/**
	 * 
	 * @return - The combat entity riding this cell, if there is one, null otherwise
	 */
	public CombatEntity getCombatEntity();
	
	/**
	 * 
	 * @return - True if there is a combat entity on the cell
	 */
	public boolean hasCombatEntity();
	
	
}
