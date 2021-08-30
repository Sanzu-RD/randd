package com.souchy.randd.ebi.ammolite.spells.fire;

import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.fire.Meteor;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;
import com.souchy.randd.ebi.ammolite.FXPlayer.FXInterpolation;
import com.souchy.randd.ebi.ammolite.FXPlayer.FXInterpolationV2;

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class MeteorFX extends FXPlayer<CastSpellEvent> {

	private CastSpellEvent event;
	private ParticleEffekseer fx;
//	private FXInterpolation<Vector2> interpolation;
	
	public MeteorFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return Meteor.class;
	}

	@Override
	public void onCreation(CastSpellEvent event) {
		try {
			this.event = event;
			Log.format("FX Meteor create");
			fx = Ammolite.particle();
			fx.load("fx/fire/meteor.efk", true);
//			fx.load("fx/test.efk", true);

//			var diff = event.target.pos.copy().sub(event.source.pos);
//			Log.format("MeteorFX start %s, end %s, diff %s", event.source.pos, event.target.pos, diff);
//			interpolation = new FXInterpolationV2(83d / 100d, diff);
			fx.setPosition(event.target.pos.x, 0.5f, event.target.pos.y);
			
			fx.play();
			fx.setOnAnimationComplete(this::dispose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	

	@Override
	public void update(float delta) {
		/*
		interpolation.update(delta);
		
		//var diff = event.target.pos.copy().sub(event.source.pos);
		//diff.mult(interpolation.percent());
		var diff = interpolation.value();
		fx.setPosition(event.source.pos.x + diff.x, 0.5f, event.source.pos.y + diff.y); // getTarget.get().x, 0.5f, getTarget.get().y);
//		Log.format("IceCometFX update [%f, %f, %f]", event.source.pos.x + diff.x, 0.5f, event.source.pos.y + diff.y);
		*/
		
		if(event != null)
			fx.setPosition(event.target.pos.x, 0.5f, event.target.pos.y); 
	}

	@Override
	public void dispose() {
		Log.info("SimpleOnCastFX fx dispose @" + fx.hashCode());// + ", " + getTarget.get());
		super.dispose();
		fx.pause();
		fx.delete();
		fx = null;
		event = null;
		//getTarget = null;
	}
	
}
