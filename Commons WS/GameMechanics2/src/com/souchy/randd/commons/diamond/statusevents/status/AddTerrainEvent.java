package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.effects.status.AddTerrainEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class AddTerrainEvent extends Event {
	
	public interface OnAddTerrainHandler extends Handler { 
		@Subscribe
		public default void handle0(AddTerrainEvent event) {
			if(check(event)) onAddTerrain(event);
		}
		public void onAddTerrain(AddTerrainEvent event);
	}
	
	public AddTerrainEvent(Creature source, Cell target, AddTerrainEffect effect) {
		super(source, target, effect);
	}

	@Override
	public AddTerrainEvent copy0() {
		return new AddTerrainEvent(source, target, (AddTerrainEffect) effect.copy());
	}

	public TerrainEffect getStatus() {
		return ((AddTerrainEffect) effect).terrain;
	}

	
}
