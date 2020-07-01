package gamemechanics.systems;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import data.new1.ecs.Engine;
import data.new1.ecs.Engine.AddEntityEvent;
import data.new1.ecs.Engine.RemoveEntityEvent;
import gamemechanics.models.Cell;

public class CellSystem extends data.new1.ecs.System {

	public List<Cell> family = new ArrayList<>();
	
	public CellSystem(Engine engine) {
		super(engine);
	}
	
	public void dispose() {
		super.dispose();
		family.clear();
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond cell system update");

		synchronized (family) {
			family.forEach(e -> {
				
			});
		}
	}
	
	@Subscribe
	public void onAddedEntity(AddEntityEvent event) {
		if(event.entity instanceof Cell) {
			synchronized(family) {
//				Log.info("add entity event cell " + event.entity);
				family.add((Cell) event.entity);
			}
		}
	}
	@Subscribe
	public void onRemovedEntity(RemoveEntityEvent event) {
		if(event.entity instanceof Cell) {
			synchronized(family) {
				family.remove(event.entity);
			}
		}
	}
	
	
}
