package com.souchy.randd.ebi.ammolite.spells;

import java.util.function.Supplier;

import com.souchy.jeffekseer.Effect;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

public abstract class SimpleOnCastFX extends FXPlayer<CastSpellEvent> {

	protected Supplier<Position> getTarget; 
	protected Effect fx; 
	
	public SimpleOnCastFX(Engine engine) {
		super(engine);
	}
	
	/**
	 * example : "fx/fire/fire.efk"
	 * @return path to effekseer efk file
	 */
	protected abstract String getFxPath();
	
	@Override
	public void onCreation(CastSpellEvent e) {
		try {
			getTarget = () -> e.target.pos;
			fx = Ammolite.manager.loadEffect(getFxPath(), 1);
			
			if(fx == null) {
				Log.info("SimpleOnCastFX play null");
				dispose();
				return;
			}
			Log.info("SimpleOnCastFX play @" + fx.hashCode() + ", " + getTarget.get());
			
			fx.onComplete = this::dispose;
			fx.play();
			fx.setPosition((float) getTarget.get().x, (float) getTarget.get().y, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void update(float delta) {
		if(fx == null) Log.info("update SimpleOnCastFX null");
		if(fx == null) dispose();
		if(fx == null) return;
//		Log.info("update SimpleOnCastFX @" + hash() + ", " + getTarget.get());
		fx.setPosition((float) getTarget.get().x, (float) getTarget.get().y, 0);
	}

	@Override
	public void dispose() {
		super.dispose();
		if(fx == null) return;
		Log.info("SimpleOnCastFX fx dispose @" + fx.hashCode() + ", " + getTarget.get());
		//fx.pause();
		fx.delete();
		fx = null;
		getTarget = null;
	}

}
