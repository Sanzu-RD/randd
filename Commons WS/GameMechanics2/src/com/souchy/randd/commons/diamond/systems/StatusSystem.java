package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Family;

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

	public Status get(int instanceid) {
		return first(s -> s.id == instanceid);
	}
	
}
