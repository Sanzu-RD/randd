package gamemechanics.systems;

import data.new1.ecs.Engine;
import data.new1.ecs.Family;
import data.new1.timed.Status;

public class StatusSystem extends Family<Status> { 

	public StatusSystem(Engine engine) {
		super(engine, Status.class);
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond status system update");
		foreach(s -> {
			
		});
	}
	
}
