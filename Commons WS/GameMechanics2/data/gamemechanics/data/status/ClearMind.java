package gamemechanics.data.status;

import data.new1.timed.Status;


/**
 * Unaffected by enemy effects : ApplyStatusEffect, SlowEffect
 */
public class ClearMind extends Status {
	
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}

}