package com.souchy.randd.ebi.ammolite.spells.air;

import java.util.function.Supplier;

import com.souchy.jeffekseer.Effect;
import com.souchy.randd.commons.diamond.effects.status.AddTerrainEffect;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.commons.diamond.statusevents.status.AddTerrainEvent;
import com.souchy.randd.commons.diamond.statusevents.status.RemoveTerrainEvent;
import com.souchy.randd.commons.diamond.statusevents.status.RemoveTerrainEvent.OnRemoveTerrainHandler;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.creatures.tsukuyo.spells.Mist.MistEffect;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;


public class MistFX extends FXPlayer<AddTerrainEvent> implements Reactor, OnEnterCellHandler, OnRemoveTerrainHandler {
	
	private Effect fx;
	private TerrainEffect terrain;
	private Supplier<Position> getTarget;
	private FXDuration fxDuration;
	
	public MistFX(Engine engine) {
		super(engine);
	}
	
	@Override
	public Class<?> modelClass() {
		return MistEffect.class;
	}
	
	@Override
	public void onEnterCell(EnterCellEvent event) {
		// pfx.setPosition(event.source.pos.x, 0, event.source.pos.y);
		// pfx.play();
	}
	
	@Override
	public void onCreation(AddTerrainEvent e) {
		try {
			terrain = ((AddTerrainEffect) e.effect).terrain;
			getTarget = () -> terrain.get(Position.class); // e.target.pos;
			
			Log.info("MistFX creation @" + hash() + ", " + getTarget.get());
			
			
//			fx = Ammolite.particle();
//			if(fx == null) {
//				Log.error("MistFX creation fx == null");
//				dispose();
//				return;
//			}
//			fx.load("fx/air/mist.efk", true);
			fx = Ammolite.manager.loadEffect("fx/air/mist.efk", 1);
			
			terrain.add(fx);
			
			Log.info("MistFX play @" + hash() + ", " + getTarget.get()); // getTarget.get());
			fxDuration = new FXDuration(70d / 100d);
			fx.play();
			fx.onComplete = this::dispose; 
		} catch (Exception ex) {
			Log.error("", ex);
		}
		// e.target.add(fx);
		// fx.play();
	}
	
	private void pause() {
		//Log.format("MistFX @%s pause %s", hash(), getTarget.get());
		if(fx != null) fx.pause(true);
	}
	
	@Override
	public void update(float delta) {
		//if(fx == null) Log.info("update MistFX null");
		if(fx == null || fxDuration == null) return;
		
		if(fxDuration.update(delta)) 
			pause();
		
		//Log.info("update MistFX @" + hash() + ", " + getTarget.get());
		
		fx.setPosition(getTarget.get().x, getTarget.get().y, 0);
		
//		var pos = getTarget.get();
//		fx.setPosition(
//			(float) pos.x + Constants.cellHalf, 
//			fxheight, 
//			(float) -pos.y - Constants.cellHalf
//		);
	}
	
	@Override
	public void dispose() {
		Log.info("MistFX dispose1 @" + hash()); // getTarget.get());
		super.dispose();
		if(fx != null) {
			fx.delete();
			fx = null;
			Log.info("MistFX dispose2 @" + hash());
		}
		terrain = null;
		getTarget = null;
		fxDuration = null;
	}
	
	@Override
	public void onRemoveTerrain(RemoveTerrainEvent event) {
		dispose();
	}
	
}
