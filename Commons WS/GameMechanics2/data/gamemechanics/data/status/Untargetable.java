package gamemechanics.data.status;

import gamemechanics.status.Status;

public class Untargetable extends Status {
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}