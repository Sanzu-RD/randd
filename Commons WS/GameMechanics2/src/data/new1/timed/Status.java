package data.new1.timed;

import java.util.List;

import data.new1.Effect;
import gamemechanics.common.Disposable;
import gamemechanics.events.OnCanCastActionCheck.OnCanCastActionHandler;
import gamemechanics.models.entities.Entity;
import gamemechanics.statics.stats.StatMod;

/**
 * 
 * How to make a status :
 * 
 * 1. Create a subclass of status 
 * 2. Implement its fusion() policy
 * 3. Implement any event handler so it can react to events (ex : "implements OnCanCastActionHandler")
 * 
 * @author Souchy
 *
 */
public abstract class Status extends TimedEffect implements Disposable {
	
	//public List<Effect> effects;
	
	// stats à compiler dans les stats de la cible du status
	public List<StatMod> stats;
	
	public Status() { //Entity source, Entity target) {
		source.fight.bus.register(this);
	}
	
 
	@Override
	public void dispose() {
		super.dispose();
		//effects = null;
	}
	

	public static class Passive extends Status {
		@Override 
		public void fuse(TimedEffect s) {
			// no fusion
		}
	}
}
