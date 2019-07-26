package gamemechanics.data.status;

import gamemechanics.status.Status;

/**
 * Unnaffected by -move Slow Effects
 */
public class LightFeet extends Status {
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}