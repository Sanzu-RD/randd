package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.RemoveTerrainEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class RemoveTerrainEvent extends Event {
	
	public interface OnRemoveTerrainHandler extends Handler { 
		@Subscribe
		public default void handle0(RemoveTerrainEvent event) {
			if(check(event)) onRemoveTerrain(event);
		}
		public void onRemoveTerrain(RemoveTerrainEvent event);
	}
	
	public RemoveTerrainEvent(Creature source, Cell target, RemoveTerrainEffect effect) {
		super(source, target, effect);
	}

	@Override
	public RemoveTerrainEvent copy0() {
		return new RemoveTerrainEvent(source, target, (RemoveTerrainEffect) effect.copy());
	}
	
}
