package com.souchy.randd.jade.api;

import java.util.List;

import com.souchy.randd.situationtest.models.Item;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.properties.Stats;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;
import com.souchy.randd.situationtest.scripts.Status;

public abstract class ICombatEntity extends IEntity {

	public Stats stats;
	
	public ICombatEntity(FightContext context, int id) {
		super(context, id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <b>Primary / survivability resource, ex. HP.</b>
	 * <br>
	 * - Quelques classes pourraient faire des trucs cools comme renvoyer la secondary resource à la place de la primary si la primary est trop basse,
	 * comme ça quand t'es low tu prend du dommage dans ta mana à la place de ton HP.
	 * Un peu comme MoM sur POE.
	 */
	//public abstract void getPrimaryResource();
	
	/**
	 * <b>Secondary / spellcasting resource, ex. PA, mana, rage, energy</b>
	 * <br>
	 * - Certaines classe peuvent renvoyer leur primaryResource à la place de la secondary et donc utiliser du HP pour caster leur sorts (ex vlad, mundo)
	 * <br>
	 * - Certaines classes on juste pas de secondary resource. Ou pas de primary.
	 */
	//public void getSecondaryResource();
	
	/**
	 * <b>Tertiary resource ...</b>
	 * <br>
	 * Special resource can be anything if needed. Ex sacrieur's rage, xelor's time, ...
	 */
	//public void getTertiaryResource();
	
	/**
	 * 
	 * @return The base movement points before buffs
	 */
	//public void getMovementBase();
	
	/**
	 * 
	 * @return The current movement points
	 */
	//public void getMovement();
	/**
	 * 
	 * @return The jump points (maximum height that can be jumped)
	 */
	//public void getJump();
	
	/**
	 * 
	 * @return This character's current buffs
	 */
	public List<Status> getBuffs();
	
	/**
	 * 
	 * @return - Get this character's current spells. Can change during fight or in preparation
	 */
	public List<ScriptedSkill> getSpells();
	
	/**
	 * 
	 * @return This character's items
	 */
	public List<Item> getItems();
	
	/**
	 * 
	 * @return - Cellules sur lesquelles l'entité est. 
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
