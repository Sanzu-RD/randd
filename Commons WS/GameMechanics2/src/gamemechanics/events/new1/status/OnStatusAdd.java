package gamemechanics.events.new1.status;

import com.google.common.eventbus.Subscribe;

import data.new1.timed.Status;
import gamemechanics.common.FightEvent;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;

public class OnStatusAdd extends Event {
	
	public Status s;
	
	public OnStatusAdd(Status s) {
		this.s = s;
	}
	
	public interface OnStatusAddHandler extends Handler {
		@Subscribe
		public void onStatusAdd(OnStatusAdd event);
	}
	
}
