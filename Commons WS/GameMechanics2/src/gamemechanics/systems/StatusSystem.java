package gamemechanics.systems;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import data.new1.ecs.Engine;
import data.new1.ecs.Engine.AddEntityEvent;
import data.new1.ecs.Engine.RemoveEntityEvent;
import data.new1.ecs.Entity;
import data.new1.timed.Status;
import gamemechanics.models.Fight;

public class StatusSystem extends data.new1.ecs.System {

	public List<Status> family = new ArrayList<>();
	
	public StatusSystem(Engine engine) {
		super(engine);
	}
	
	public void dispose() {
		super.dispose();
		family.clear();
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond status system update");
		family.forEach(e -> {
			
		});
	}
	
	@Subscribe
	public void onAddedEntity(AddEntityEvent event) {
		if(event.entity instanceof Status && event.entity.get(Fight.class) != null) // models have no fight component and we dont want them
			family.add((Status) event.entity);
	}
	@Subscribe
	public void onRemovedEntity(RemoveEntityEvent event) {
		if(event.entity instanceof Status)
			family.remove(event.entity);
	}
	
}
