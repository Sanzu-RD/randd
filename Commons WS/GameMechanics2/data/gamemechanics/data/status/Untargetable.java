package gamemechanics.data.status;

import data.new1.timed.Status;

public class Untargetable extends Status {
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}