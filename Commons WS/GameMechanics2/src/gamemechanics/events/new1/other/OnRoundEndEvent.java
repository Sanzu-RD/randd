package gamemechanics.events.new1.other;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;

public class OnRoundEndEvent extends Event {

	public interface OnRoundEndHandler extends Handler { //<OnRoundEndEvent> {
		@Subscribe
		public default void handle0(OnRoundEndEvent event) {
			if(check(event)) onRoundEnd(event);
		}
		public void onRoundEnd(OnRoundEndEvent event);
	}
	
	public OnRoundEndEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnRoundEndEvent copy0() {
		return new OnRoundEndEvent(source, target, effect.copy());
	}
	
}
