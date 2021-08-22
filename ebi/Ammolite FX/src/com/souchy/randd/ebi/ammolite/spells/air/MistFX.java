package com.souchy.randd.ebi.ammolite.spells.air;

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

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class MistFX extends FXPlayer<AddTerrainEvent> implements Reactor, OnEnterCellHandler, OnRemoveTerrainHandler {
	
	private ParticleEffekseer fx;
	private TerrainEffect terrain;
//	private Supplier<Position> getTarget; 
	
	public MistFX(Engine engine) {
		super(engine);
	}
	
	@Override
	public Class<?> modelClass() {
		return MistEffect.class;
	}

	@Override
	public void onEnterCell(EnterCellEvent event) {
//		pfx.setPosition(event.source.pos.x, 0, event.source.pos.y);
//		pfx.play();
	}

	@Override
	public void onCreation(AddTerrainEvent e) {
		try {
			terrain = ((AddTerrainEffect) e.effect).terrain;
			
//			getTarget = () -> terrain.get(Position.class); //e.target.pos;
			fx = Ammolite.particle();
			fx.load("fx/air/mist.efk", true);
			
			terrain.add(fx);
			
			Log.info("MistFX play @" + fx.hashCode() + ", " +  terrain.get(Position.class)); //getTarget.get());
			fx.play();
			fx.setOnAnimationComplete(() -> fx.play()); // this::dispose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
//		e.target.add(fx);
//		fx.play();
	}

	@Override
	public void update(float delta) {
		if(fx == null) Log.info("update MistFX null");
		if(fx == null) return;
//		Log.info("update fireball fx @" + hash() + ", " + getTarget.get());
//		fx.setPosition(getTarget.get().x, 0.5f, getTarget.get().y);
	}

	@Override
	public void dispose() {
		Log.info("MistFX dispose @" + fx.hashCode() + ", " +  terrain.get(Position.class)); //getTarget.get());
		super.dispose();
		fx.pause();
		fx.delete();
		fx = null;
//		getTarget = null;
	}

	@Override
	public void onRemoveTerrain(RemoveTerrainEvent event) {
		dispose();
	}


}
