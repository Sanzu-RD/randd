package gamemechanics.data.status;

import data.new1.timed.Status;
import gamemechanics.events.OnLifeDmgInstance;
import gamemechanics.events.OnLifeDmgInstance.OnLifeDmgInstanceHandler;

/** could also be coded as "received 1000% less damage" (basicmod, globalDmgRes, more, 1000) */
public class Invulnerable extends Status implements OnLifeDmgInstanceHandler {
	private final int originalDuration;
	public Invulnerable(int duration) {
		this.duration = this.originalDuration = duration;
	}
	@Override
	public void onLifeDmgInstance(OnLifeDmgInstance e) {
		// if the dmg target is the same as the status target, cannot receive damage
		if(e.target == this.target) { 
			e.lifeShield = 0;
			e.life = 0;
		}
	}
	@Override
	public void fuse(Status s) {
		refreshSet(originalDuration);
	}
}