package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import data.new1.Effect;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.Stats;
import gamemechanics.status.Status;
import gamemechanics.status.Status.Passive;

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
