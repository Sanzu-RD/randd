package data.modules;

import java.util.HashMap;
import java.util.Map;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.jade.combat.JadeCreature;
import com.souchy.randd.modules.api.EntryPoint;

import data.new1.CreatureModel;
import data.new1.SpellModel;
import gamemechanics.models.Item;
import gamemechanics.models.entities.Creature;

public class AzurEntryPoint implements EntryPoint {

	private EventBus bus;

	public Map<Integer, CreatureModel> creatures;
	public Map<Integer, Item> items;
	public Map<Integer, SpellModel> spells;
	
	public AzurEntryPoint() {
		// need an event bus since this is an entry point
		bus = new EventBus();
		creatures = new HashMap<>();
		items = new HashMap<>();
		spells = new HashMap<>();
	}
	
	@Override
	public EventBus getBus() {
		return bus;
	}

	/**
	 * Create a creature instance from a model and jade data
	 */
//	public Creature createInstance(int id, JadeCreature jade) {
//		if(!creatures.containsKey(id)) return null;
//		var model = creatures.get(id);
//		var inst = new Creature(model, jade, this); //model.createCreature(jade);
//		
//		// could also do like gdx : new Creature(model);
//		return inst;
//	}
	
	
	
}
