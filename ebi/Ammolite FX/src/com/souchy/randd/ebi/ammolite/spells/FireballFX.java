package com.souchy.randd.ebi.ammolite.spells;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.fire.Fireball;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class FireballFX extends FXPlayer<CastSpellEvent> {

	private Supplier<Position> getTarget; 
	private ParticleEffekseer fx;
	
	public FireballFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return Fireball.class;
	}
	
	@Override
	public void update(float delta) {
		if(fx == null) Log.info("update fireball null");
		if(fx == null) return;
//		Log.info("update fireball fx @" + hash() + ", " + getTarget.get());
		fx.setPosition(getTarget.get().x, 0.5f, getTarget.get().y);
	}

	@Override
	public void onCreation(CastSpellEvent e) {
		try {
//			var pos = e.target.pos;
			getTarget = () -> e.target.pos;
			fx = Ammolite.particle();
			fx.load("fx/fire/fire.efk", true);
//			effect.setPosition(pos.x, 0, pos.y);
			
			Log.info("fireball play @" + fx.hashCode() + ", " + getTarget.get());
			fx.play();
			fx.setOnAnimationComplete(this::dispose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void dispose() {
		Log.info("fireball fx dispose @" + fx.hashCode() + ", " + getTarget.get());
		super.dispose();
		fx.pause();
		fx.delete();
		fx = null;
		getTarget = null;
	}

	
}
