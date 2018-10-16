package com.souchy.randd.jade.api;


/**
 * 
 * Sort.
 * 
 * <br>
 * Un sort a plusieurs effets.
 *
 * <br>
 * Chaque effet a son propre AoE et ses propres conditions (ldv, cellules vides, cellules pleines, minimum de stats, etc)
 * 
 * <br>
 * 
 * 
 * 
 * @author Souchy
 *
 */
public interface Spell { //extends Identifiable<Integer> {

	/**
	 * 
	 * @return - Name of the spell
	 */
	public void getName();
	
	/**
	 * 
	 * @return - Description of the spell
	 */
	public void getDesc();
	
	/**
	 * 
	 * @return - The resource cost, might be primary, secondary or tertiary depending.
	 */
	public void getCost();
	
	/**
	 * 
	 * @return - True if the spell requires a line of sight to be casted
	 */
	public boolean lineOfSight();
	
	/**
	 * 
	 * @return - True if the spell needs to be casted on a target (CombatEntity)
	 */
	public boolean targetsEntity();
	
	/**
	 * 
	 * @return - True if the spell needs to be casted on an empty cell
	 */
	public boolean targetsEmptyCell();
	
	/**
	 * 
	 * @return - True if the spell has an area of effect -> délégé aux Effect
	 */
	public boolean hasAreaOfEffect();
	
	/**
	 * 
	 * @return - Cells affected by the spell -> délégé aux Effect
	 */
	public void areaOfEffect();

	/**
	 * 
	 * @return - Matrix representing the area of Cells where the spell can be casted. 
	 */
	public ICell[][] castableCells();
	
	/**
	 * 
	 * @return - Effects to apply when casting this spell
	 */
	public void effects();
	
	/**
	 * 
	 * @return - Conditions to cast the spell
	 */
	public void conditions(); 
	/*-> Way to manage spell vs effect conditions : 
		each effect has its own conditions for it to apply and the spell also has conditions for the whole process 
		and it can include : 
			spellcond |= thisEffectConditionsHaveToBeTrueForTheSpellToWork 
			spellcond |= meanwhileThisEffectConditionsDontMatterAtAll
		So the spell checks whatever conditions it wants *and* also checks important conditional effects, 
		and if there's a "less-important" effect, the spell will still apply everything else except this effect.
	*/
	
	
	/*
	 * 
	 * Displacement requirements
	 * 
	 * 
	 */
	
}
