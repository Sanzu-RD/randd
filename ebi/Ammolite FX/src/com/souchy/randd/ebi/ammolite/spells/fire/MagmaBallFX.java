package com.souchy.randd.ebi.ammolite.spells.fire;

import java.util.function.Supplier;

import com.badlogic.gdx.math.Vector3;
import com.souchy.jeffekseer.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.fire.Fireball;
import com.souchy.randd.data.s1.spells.secondary.ice.IceShield;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

public class MagmaBallFX extends FXPlayer<CastSpellEvent> {

	private Supplier<Position> getTarget; 
	private Effect effect;
	
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
//			effect = Ammolite.particle();
			effect = Ammolite.manager.loadEffect("fx/fire/magmaballs.efk", 1);
			//effect.rotate(Vector3.Z, 90);
			effect.play();
			effect.onComplete = this::dispose;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void update(float delta) {
//		Log.info("update Magma fx @" + hash());
		if(effect != null)
			effect.setPosition((float) getTarget.get().x, (float) getTarget.get().y, 0);
	}

	@Override
	public void dispose() {
		Log.info("Magma fx dispose @" + hash());
		super.dispose();
		effect.pause(true);
		effect.delete();
		effect = null;
	}
	
	
}
