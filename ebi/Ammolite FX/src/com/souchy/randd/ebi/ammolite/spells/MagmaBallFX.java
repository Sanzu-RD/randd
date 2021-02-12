package com.souchy.randd.ebi.ammolite.spells;

import java.util.function.Supplier;

import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.fire.Fireball;
import com.souchy.randd.data.s1.spells.ice.IceShield;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class MagmaBallFX extends FXPlayer<CastSpellEvent> {

	private Supplier<Position> getTarget; 
	private ParticleEffekseer effect;
	
	public MagmaBallFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return IceShield.class; // 3
	}
	
	@Override
	public void onCreation(CastSpellEvent e) {
		Log.info("Magma play");
		try {
			getTarget = () -> e.source.pos;
			effect = Ammolite.particle();
			effect.load("fx/fire/magmaballs.efk", true);
			effect.rotate(Vector3.Z, 90);
			effect.play();
			effect.setOnAnimationComplete(this::dispose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void update(float delta) {
//		Log.info("update Magma fx @" + hash());
		effect.setPosition(getTarget.get().x, 0, getTarget.get().y);
	}

	@Override
	public void dispose() {
		Log.info("Magma fx dispose @" + hash());
		super.dispose();
		effect.pause();
		effect.delete();
		effect = null;
	}
	
	
}
