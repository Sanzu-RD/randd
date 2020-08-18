package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.common.ecs.Engine;
import com.souchy.randd.commons.diamond.common.ecs.Family;
import com.souchy.randd.commons.diamond.models.Creature;

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
