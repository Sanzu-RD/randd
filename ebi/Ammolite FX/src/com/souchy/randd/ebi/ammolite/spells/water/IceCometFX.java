package com.souchy.randd.ebi.ammolite.spells.water;

import java.util.function.Supplier;

import com.google.common.primitives.Floats;
import com.souchy.jeffekseer.Effect;
import com.souchy.jeffekseer.Jeffekseer;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.secondary.ice.IceComet;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

public class IceCometFX extends FXPlayer<CastSpellEvent> {

	private CastSpellEvent event;
	private Effect fx;
	private FXInterpolation<Vector2> interpolation;
	
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
			this.event = e;
			Log.format("FX IceComet create");
			fx = Ammolite.manager.loadEffect("fx/comet/comet.efk", 1);
//			fx.load("fx/test.efk", true);

			var diff = event.target.pos.copy().sub(event.source.pos);
			Log.format("IceCometFX start %s, end %s, diff %s", event.source.pos, event.target.pos, diff);
			interpolation = new FXInterpolationV2(83d / 100d, diff);
			
			//if(false) testNodes();
			
			fx.play();
			fx.onComplete = this::dispose;
        } catch (Exception ex) {
        	Log.error("", ex);
        }
	}
	
	@Override
	public void update(float delta) {
		if(interpolation == null) return;
		interpolation.update(delta);
		
		//var diff = event.target.pos.copy().sub(event.source.pos);
		//diff.mult(interpolation.percent());
		var diff = interpolation.value();
		fx.setPosition((float) (event.source.pos.x + diff.x), (float) (event.source.pos.y + diff.y), 0); // getTarget.get().x, 0.5f, getTarget.get().y);
//		Log.format("IceCometFX update [%f, %f, %f]", event.source.pos.x + diff.x, 0.5f, event.source.pos.y + diff.y);
	}
	
	/*
	private void testNodes() {
		try {

//			fx.foreachNode(n -> {
//				var type = ParameterTranslationType.swigToEnum(n.GetPositionType());
//				var pos = n.GetPosition(type);
//				Log.info("FX IceComet Node (" + n.GetName() + "), pos ("+type+") " + pos);
////				Log.info("FX IceComet Node " + n);
//			});

			//  var n = fx.getNode();
			//  var n = fx.getNode().getNode(0);
			//  var n = fx.getNode().getNode(0).getNode(0);
			//  var n = fx.getNode().getNode(0).getNode(1);
			
			var n = fx.getNode();
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
				
				var newpos = new float[] { 80, 70, 70 };
				n.SetPosition(ParameterTranslationType.ParameterTranslationType_Fixed, newpos);

				n = fx.getNode();
				type = ParameterTranslationType.swigToEnum(n.GetPositionType());
				pos = n.GetPosition(type);
				Log.info("FX IceComet Node (" + n.GetName() + "), pos (" + n.GetPositionType() + "/" + type + ") " + pos + ", setted " + Floats.asList(newpos));
			}
		} catch(Exception e) {
			Log.error("", e);
		}
	}
	
	*/
}
