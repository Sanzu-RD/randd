package com.souchy.randd.ebi.ammolite.status;

import com.souchy.jeffekseer.Effect;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.commons.diamond.statusevents.status.AddStatusEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.data.s1.status.HeavyStep;
import com.souchy.randd.ebi.ammolite.FXPlayer;

public class HeavyStepFX extends FXPlayer<AddStatusEvent> implements Reactor, OnEnterCellHandler {
	
	public Effect pfx;
	
	public HeavyStepFX(Engine engine) {
		super(engine);
	}
	
	@Override
	public Class<?> modelClass() {
		return HeavyStep.class;
	}

	@Override
	public void onEnterCell(EnterCellEvent event) {
		pfx.setPosition(event.source.pos.x, event.source.pos.y, 0);
		pfx.play();
	}

	@Override
	public void onCreation(AddStatusEvent event) {
		
	}


}
