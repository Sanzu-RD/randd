package gamemechanics.systems;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;

import data.new1.ecs.Engine;
import data.new1.ecs.Entity;
import data.new1.ecs.Engine.AddEntityEvent;
import data.new1.ecs.Engine.RemoveEntityEvent;
import data.new1.timed.Status;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;

public class CreatureSystem extends data.new1.ecs.System {

	public List<Creature> family = new ArrayList<>();
	
	public CreatureSystem(Engine engine) {
		super(engine);
	}

	public void dispose() {
		super.dispose();
		family.clear();
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond creature system update");
		family.forEach(e -> {
			
		});
	}
	
	@Subscribe
	public void onAddedEntity(AddEntityEvent event) {
		if(event.entity instanceof Creature) {
//			Log.info("creature system onAddedEntity " + event.entity);
			family.add((Creature) event.entity);
		}
	}
	@Subscribe
	public void onRemovedEntity(RemoveEntityEvent event) {
		if(event.entity instanceof Creature)
			family.remove(event.entity);
	}
	
}
