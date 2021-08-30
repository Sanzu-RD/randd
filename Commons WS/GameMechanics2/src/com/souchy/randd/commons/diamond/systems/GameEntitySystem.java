package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.ecs.Engine.AddEntityEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine.RemoveEntityEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class GameEntitySystem extends Family<Entity> { 

	public GameEntitySystem(Engine engine) {
		super(engine, Entity.class);
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond entity system update");
		//foreach(c -> {
		//});
	}
	
	@Override
	public void onAddedEntity(AddEntityEvent event) {
		if (Creature.class.isInstance(event.entity) || Cell.class.isInstance(event.entity)) {
			add(Entity.class.cast(event.entity));
		}
	}
	@Override
	public void onRemovedEntity(RemoveEntityEvent event) {
		if (Creature.class.isInstance(event.entity) || Cell.class.isInstance(event.entity)) {
			remove(Entity.class.cast(event.entity));
		}
	}

	public Entity get(int instanceid) {
		var f = (Fight) getEngine();
		Entity e = f.creatures.get(instanceid);
		if(e != null) return e;
		return f.cells.get(instanceid);
//		return first(s -> {
//			return s.id == instanceid;
//		});
	}
	
}
