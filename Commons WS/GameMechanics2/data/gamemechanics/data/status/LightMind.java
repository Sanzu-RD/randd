package gamemechanics.data.status;

import data.new1.timed.Status;

/**
 * Unnaffected by -mana Slow Effects
 */
public class LightMind extends Status {
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}