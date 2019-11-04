package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import data.new1.Effect;
import data.new1.timed.Status;
import data.new1.timed.Status.Passive;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.stats.Stats;

public abstract class Item {
	
	public List<Passive> passives = new ArrayList<>();
	public Stats stats = new Stats();
	
	//public List<Effect> effects;
	
	/**
	 * Item id, same for all instances of an item
	 */
	public abstract Integer id();
	
	/**
	 * Initialise stats and passives
	 */
	public abstract void init();
	
	public void onEquip(Creature creature) {
		for(Passive s : passives) creature.getStatus().add(s);
	}

	
}
