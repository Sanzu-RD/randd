package com.souchy.randd.ebi.ammolite.spells;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.ice.Hail;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class PowerUpFX extends FXPlayer<CastSpellEvent> {

	private Supplier<Position> getTarget; 
	private ParticleEffekseer effect;
	
	public PowerUpFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return Hail.class;
	}

	@Override
	public void onCreation(CastSpellEvent e) {
		Log.info("PowerUpFX play");
		try {
			getTarget = () -> e.source.pos;
			effect = Ammolite.particle();
			effect.load("fx/aura/aura.efk", true);
			effect.play();
			effect.setOnAnimationComplete(this::dispose);
//			var status = DiamondModels.statuses.get(1).copy(e.target.get(Fight.class));
//        	status.add(effect);
//        	e.source.add(status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void update(float delta) {
		effect.setPosition(getTarget.get().x, 0, getTarget.get().y);
	}
	
	@Override
	public void dispose() {
		Log.info("PowerUpFX fx dispose @" + hash());
		super.dispose();
		effect.pause();
		effect.delete();
		effect = null;
	}
	
}
