package com.souchy.randd.ebi.ammolite.spells;

import java.util.function.Supplier;

import com.google.common.primitives.Floats;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.ice.IceComet;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

import br.com.johnathan.gdx.effekseer.api.ParameterTranslationType;
import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class IceCometFX extends FXPlayer<CastSpellEvent> {

	private Supplier<Position> getTarget; 
	public ParticleEffekseer fx;
	
	public IceCometFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return IceComet.class;
	}
	
	@Override
	public void onCreation(CastSpellEvent e) {
		try {
			Log.format("FX IceComet create");
			getTarget = () -> e.source.pos;
			fx = Ammolite.particle();
			fx.load("fx/comet/comet.efk", true);

//			fx.foreachNode(n -> {
//				var type = ParameterTranslationType.swigToEnum(n.GetPositionType());
//				var pos = n.GetPosition(type);
//				Log.info("FX IceComet Node (" + n.GetName() + "), pos ("+type+") " + pos);
////				Log.info("FX IceComet Node " + n);
//			});
			
			var n = fx.getNode().getNode(0);
			var type = ParameterTranslationType.swigToEnum(n.GetPositionType());
			var pos = n.GetPosition(type);
			Log.info("FX IceComet Node (" + n.GetName() + "), pos (" + n.GetPositionType() + "/" + type + ") " + pos);
			
			if(true) {
//				var newpos = new float[6 * 3];
//				for(int newp = 0; newp < newpos.length; newp++) newpos[newp] = 10;
//				
//				// pos mean
//				newpos[3 * 0 + 0] = 5;
//				newpos[3 * 0 + 1] = 5;
//				newpos[3 * 0 + 2] = 5;
//
//				// speed mean
//				newpos[3 * 2 + 0] = 5;
//				newpos[3 * 2 + 1] = 5;
//				newpos[3 * 2 + 2] = 5;
//				
//				// accel mean
//				newpos[3 * 4 + 0] = 5;
//				newpos[3 * 4 + 1] = 5;
//				newpos[3 * 4 + 2] = 5;
//				
//				n.SetPosition(ParameterTranslationType.ParameterTranslationType_PVA, newpos);
				
				var newpos = new float[] { 7, 7, 7 };
				n.SetPosition(ParameterTranslationType.ParameterTranslationType_Fixed, newpos);

				n = fx.getNode().getNode(0);
				type = ParameterTranslationType.swigToEnum(n.GetPositionType());
				pos = n.GetPosition(type);
				Log.info("FX IceComet Node (" + n.GetName() + "), pos (" + n.GetPositionType() + "/" + type + ") " + pos + ", setted " + Floats.asList(newpos));
			}
			
			fx.play();
			fx.setOnAnimationComplete(this::dispose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void update(float delta) {
		fx.setPosition(getTarget.get().x, 0.5f, getTarget.get().y);
	}
	
}
