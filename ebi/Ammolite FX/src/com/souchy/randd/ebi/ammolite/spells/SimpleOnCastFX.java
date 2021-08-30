package com.souchy.randd.ebi.ammolite.spells;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public abstract class SimpleOnCastFX extends FXPlayer<CastSpellEvent> {

	protected Supplier<Position> getTarget; 
	protected ParticleEffekseer fx;
	
	public SimpleOnCastFX(Engine engine) {
		super(engine);
	}
	
	/**
	 * example : "fx/fire/fire.efk"
	 * @return path to effekseer efk file
	 */
	protected abstract String getFxPath();
	
	@Override
	public void update(float delta) {
		if(fx == null) Log.info("update SimpleOnCastFX null");
		if(fx == null) return;
//		Log.info("update SimpleOnCastFX @" + hash() + ", " + getTarget.get());
		fx.setPosition(getTarget.get().x, 0.5f, getTarget.get().y);
	}

	@Override
	public void onCreation(CastSpellEvent e) {
		try {
			getTarget = () -> e.target.pos;
			fx = Ammolite.particle();
			fx.load(getFxPath(), true);
			
			Log.info("SimpleOnCastFX play @" + fx.hashCode() + ", " + getTarget.get());
			
			fx.play();
			fx.setOnAnimationComplete(this::dispose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void dispose() {
		Log.info("SimpleOnCastFX fx dispose @" + fx.hashCode() + ", " + getTarget.get());
		super.dispose();
		//fx.pause();
		fx.delete();
		fx = null;
		getTarget = null;
	}

}
