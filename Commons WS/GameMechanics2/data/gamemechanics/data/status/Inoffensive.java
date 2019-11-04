package gamemechanics.data.status;

import data.new1.timed.Status;
import gamemechanics.events.OnLifeDmgInstance;
import gamemechanics.events.OnLifeDmgInstance.OnLifeDmgInstanceHandler;

/** could also be coded as "do 1000% less damage" (basicmod, globalDmg, more, -1000) */
public class Inoffensive extends Status implements OnLifeDmgInstanceHandler {
	private final int originalDuration;
	public Inoffensive(int duration) {
		this.duration = this.originalDuration = duration;
	}
	@Override
	public void onLifeDmgInstance(OnLifeDmgInstance e) { 
		// if the dmg source is the same as the status target, cannot do damage
		if(e.source == this.target) {
			e.lifeShield = 0;
			e.life = 0;
		}
	}
	@Override
	public void fuse(Status s) {
		refreshSet(originalDuration);
	}
}