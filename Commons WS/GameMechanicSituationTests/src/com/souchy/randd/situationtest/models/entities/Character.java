package com.souchy.randd.situationtest.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.eventshandlers.OnStatChangeHandler;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.items.Item;
import com.souchy.randd.situationtest.models.items.Item.CompoundStats;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.properties.Stats;
import com.souchy.randd.situationtest.properties.types.StatProperties;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;

/**
 * 
 * initialize via JSON <p>
 * 
 * A character is either a player or a summon. <br>
 * You can differentiate them by using c.isSummon() <br>
 */
public class Character extends AEntity {

	/**
	 * initialize stats via JSON
	 */
	public /*final*/ Stats baseStats;
	public /*final*/ CompoundStats stats;
	/** This character's current spells. Can change during fight or in preparation */
	public final List<ScriptedSkill> spells;
	/** This character's items and weapons */
	public final List<Item> items;

	/** This character's current buffs  // d�plac� dans IEntity */
	//public final List<Status> statuss;
	
	public Character(FightContext context, int id, Point3D pos) {
		super(context, id);
		
		// TODO baseStats = context.injector.getInstance(Stats.class);
		// TODO stats = context.injector.getInstance(CompoundStats.class);
		baseStats = new Stats(context.injector);
		stats = new CompoundStats(this);
		//statuss = new ArrayList<>(); // d�plac� dans IEntity
		spells = new ArrayList<>();
		items = new ArrayList<>();

		// on inflicting dmg
		bus().register(new OnStatChangeHandler(this, true, (e) -> {
		}));
		// on receiving dmg
		bus().register(new OnStatChangeHandler(this, false, (e) -> {
			baseStats.get(StatProperties.Resource1).value -= e.changedProp.value;
			if(stats.get(StatProperties.Resource1).value <= 0){
				// die
			}
		}));
	}
	

	/**
	 * 
	 * @return - Cellules sur lesquelles l'entit� est. 
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
