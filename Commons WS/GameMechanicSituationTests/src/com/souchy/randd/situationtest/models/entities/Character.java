package com.souchy.randd.situationtest.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.situationtest.interfaces.*;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.Item;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.properties.Stats;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;
import com.souchy.randd.situationtest.scripts.Status;

/**
 * 
 * initialize via JSON <p>
 * 
 * A character is either a player or a summon. <br>
 * You can differentiate them by using c.isSummon() <br>
 */
public class Character extends IEntity {
	
	/**
	 * initialize via JSON
	 */
	public final Stats stats = context.injector.getInstance(Stats.class);

	/** This character's current buffs */
	public final List<Status> statuss;
	/** This character's current spells. Can change during fight or in preparation */
	public final List<ScriptedSkill> spells;
	/** This character's items */
	public final List<Item> items;
	
	public Character(FightContext context, int id, Point3D pos) {
		super(context, id);
		
		statuss = new ArrayList<>();
		spells = new ArrayList<>();
		items = new ArrayList<>();
	}
	

	/**
	 * 
	 * @return - Cellules sur lesquelles l'entité est. 
	 */
	public void occupiedCells() {
		 // ou Cell[][] getArea(); qui renvoie une matrice montrant [1,1;1,1]
	}
	
	/**
	 * False by default (Character = Player)<p>
	 * Implemented to true in Summon (Character = Summon)
	 */
	public boolean isSummon() {
		return false;
	}
	
}
