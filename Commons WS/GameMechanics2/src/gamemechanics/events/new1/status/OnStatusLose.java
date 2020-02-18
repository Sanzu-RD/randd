package gamemechanics.events.new1.status;

import data.new1.timed.Status;
import gamemechanics.common.FightEvent;
import gamemechanics.data.effects.status.RemoveStatusEffect;
import gamemechanics.events.new1.Event;

public class OnStatusLose extends Event {

	//public Status s;
	public RemoveStatusEffect effect;
	
	public OnStatusLose(RemoveStatusEffect effect) {
		this.effect = effect;
		//this.s = s;
	}
	
}
