package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.ModifyStatusEffect;
import com.souchy.randd.commons.diamond.effects.status.ModifyTerrainEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class ModifyTerrainEvent extends Event {

	public interface OnModifyTerrainHandler extends Handler { //<OnModifyStatusEvent> {
		@Subscribe
		public default void handle0(ModifyTerrainEvent event) {
			if(check(event)) onModifyTerrain(event);
		}
		public void onModifyTerrain(ModifyTerrainEvent event);
	}
	
	public ModifyTerrainEvent(Creature source, Cell target, ModifyTerrainEffect effect) {
		super(source, target, effect);
	}

	@Override
	public ModifyTerrainEvent copy0() {
		return new ModifyTerrainEvent(source, target, (ModifyTerrainEffect) effect.copy());
	}
	
}
