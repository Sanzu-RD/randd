package com.souchy.randd.ebi.ammolite.spells.fire;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Collections;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.fire.Fireball;
import com.souchy.randd.data.s1.spells.secondary.ice.IceSpear;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;

import br.com.johnathan.gdx.effekseer.api.ParameterTranslationType;
import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class PrimordialBurstFX extends FXPlayer<CastSpellEvent> {

	private Supplier<Position> getTarget; 
	private ParticleEffekseer effect;
	
	public PrimordialBurstFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return IceSpear.class; // 5
	}
	
	@Override
	public void onCreation(CastSpellEvent e) {
		Log.info("Primordial Burst play");
		try {
			getTarget = () -> e.source.pos;
			effect = Ammolite.particle();
			effect.load("fx/fire/fireball wip.efk", true);

			
			Log.info("Node root " + effect.getNode().GetName());
			var posType = ParameterTranslationType.swigToEnum(effect.getNode().GetPositionType());
			var pos = effect.getNode().GetPosition(posType);
			Log.format("PrimordialBurst FX pos ("+posType.swigValue()+") : " + String.join(", ", pos.stream().map(f -> Float.toString(f)).collect(Collectors.toList())));
			
			
			var easing = new float[3 * 4 + 2];
			easing[3 * 0 + 0] = (float) e.source.pos.x;
			easing[3 * 0 + 1] = 1.5f;
			easing[3 * 0 + 2] = (float) -e.source.pos.y;
			easing[3 * 2 + 0] = (float) e.target.pos.x;
			easing[3 * 2 + 1] = 1.5f;
			easing[3 * 2 + 2] = (float) -e.target.pos.y;
			effect.getNode().SetPosition(ParameterTranslationType.ParameterTranslationType_Easing, easing);
			
			
			var nodeCount = effect.getNode().getNodes();
			for(int n = 0; n < nodeCount; n++) {
				var node = effect.getNode().getNode(n);
				Log.info("Node " + n + " " + node.GetName());

				node.SetPosition(ParameterTranslationType.ParameterTranslationType_Easing, easing);
				
				posType = ParameterTranslationType.swigToEnum(effect.getNode().GetPositionType());
				pos = effect.getNode().GetPosition(posType);
				Log.format("Node " + n + " pos ("+posType.swigValue()+") : " + String.join(", ", pos.stream().map(f -> Float.toString(f)).collect(Collectors.toList())));
			}

			
			effect.play();
			effect.setOnAnimationComplete(this::dispose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
	public void update(float delta) {
//		Log.info("update Primordial Burst fx @" + hash());4
		effect.setPosition(getTarget.get().x, 0.5f, getTarget.get().y);
	}

	@Override
	public void dispose() {
		Log.info("Primordial Burst fx dispose @" + hash());
		super.dispose();
		effect.pause();
		effect.delete();
		effect = null;
	}
	
	
}
