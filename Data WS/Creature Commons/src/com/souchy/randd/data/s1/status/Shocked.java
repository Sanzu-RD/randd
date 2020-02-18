package com.souchy.randd.data.s1.status;

import data.new1.timed.Status;
import data.new1.timed.TimedEffect;
import gamemechanics.models.entities.Entity;

public class Shocked extends Status {

	public Shocked(Entity source, Entity target) {
		super(source, target);
	}

	@Override
	public int id() {
		return 1;
	}

	@Override
	public void fuse(TimedEffect s) {
		
	}
	
}
