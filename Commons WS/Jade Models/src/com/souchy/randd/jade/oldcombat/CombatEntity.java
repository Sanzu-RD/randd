package com.souchy.randd.jade.oldcombat;

public interface CombatEntity {
	
	
	/**
	 * <b>Primary / survivability resource, ex. HP.</b>
	 * <br>
	 * - Quelques classes pourraient faire des trucs cools comme renvoyer la secondary resource � la place de la primary si la primary est trop basse,
	 * comme �a quand t'es low tu prend du dommage dans ta mana � la place de ton HP.
	 * Un peu comme MoM sur POE.
	 */
	public void getPrimaryResource();
	
	/**
	 * <b>Secondary / spellcasting resource, ex. PA, mana, rage, energy</b>
	 * <br>
	 * - Certaines classe peuvent renvoyer leur primaryResource � la place de la secondary et donc utiliser du HP pour caster leur sorts (ex vlad, mundo)
	 * <br>
	 * - Certaines classes on juste pas de secondary resource. Ou pas de primary.
	 */
	public void getSecondaryResource();
	
	/**
	 * <b>Tertiary resource ...</b>
	 * <br>
	 * Special resource can be anything if needed. Ex sacrieur's rage, xelor's time, ...
	 */
	public void getTertiaryResource();
	
	/**
	 * 
	 * @return The base movement points before buffs
	 */
	public void getMovementBase();
	
	/**
	 * 
	 * @return The current movement points
	 */
	public void getMovement();
	/**
	 * 
	 * @return The jump points (maximum height that can be jumped)
	 */
	public void getJump();
	
	/**
	 * 
	 * @return This character's current buffs
	 */
	public IEffect[] getBuffs();
	
	/**
	 * 
	 * @return - Get this character's current spells. Can change during fight or in preparation
	 */
	public Spell[] getSpells();
	
	/**
	 * 
	 * @return - Cellules sur lesquelles l'entit� est. 
	 */
	public void occupiedCells(); // ou Cell[][] getArea(); qui renvoie une matrice montrant [1,1;1,1]
	
	//
	// movement/action points ?  action points would be mana, aka Secondary Resource
	// movement could be tertiary resource ... ? 
	
	
	// Quick list of things for a Combat Entity : 
	// hp
	// mana
	// movement points
	// jump height
	// spells
	// buffs (shields inclus ?)
	// resistances
	// dodge/block/accuracy/evasion mechanic ?
	// 
	
	
}
