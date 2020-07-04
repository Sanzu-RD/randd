package gamemechanics.systems;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;

import data.new1.ecs.Engine;
import data.new1.ecs.Family;
import data.new1.ecs.Engine.AddEntityEvent;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;

public class CreatureSystem extends Family<Creature> {
	
	public CreatureSystem(Engine engine) {
		super(engine, Creature.class);
	}
	
	@Override
	public void update(float delta) {
		// Log.info("diamond creature system update");
		foreach(c -> {
			
		});
	}
	
	/**
	 * 
	 */
//	@Subscribe
//	@Override
//	public void onAddedEntity(AddEntityEvent event) {
//		if(event.entity instanceof Creature) {
//			add((Creature) event.entity);
//			((Fight) engine).add(event.entity);
//		}
//	}
	
}
