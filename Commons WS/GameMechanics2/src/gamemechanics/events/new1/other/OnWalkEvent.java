package gamemechanics.events.new1.other;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;

public class OnWalkEvent extends Event {

	public interface OnWalkHandler extends Handler { //<OnWalkEvent> {
		@Subscribe
		public default void handle0(OnWalkEvent event) {
			if(check(event)) onWalk(event);
		}
		public void onWalk(OnWalkEvent event);
	}
	
	public OnWalkEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnWalkEvent copy0() {
		return new OnWalkEvent(source, target, effect.copy());
	}
}
