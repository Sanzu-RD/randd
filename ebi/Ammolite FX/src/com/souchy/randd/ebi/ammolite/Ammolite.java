package com.souchy.randd.ebi.ammolite;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.souchy.jeffekseer.EffectManager;
import com.souchy.jeffekseer.Jeffekseer;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent.OnCastSpellHandler;
import com.souchy.randd.commons.diamond.statusevents.status.AddStatusEvent;
import com.souchy.randd.commons.diamond.statusevents.status.AddStatusEvent.OnAddStatusHandler;
import com.souchy.randd.commons.diamond.statusevents.status.AddTerrainEvent;
import com.souchy.randd.commons.diamond.statusevents.status.AddTerrainEvent.OnAddTerrainHandler;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * EbiShoal Library to play Visual and Audio FX
 * 
 * @author Souchy
 * @date 11 janv. 2021
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Ammolite implements Reactor, OnCastSpellHandler, OnAddStatusHandler, OnAddTerrainHandler {

	/** updates fx on render */
	public static class FXFamily extends Family<FXPlayer> {
		public FXFamily(Engine engine) {
			super(engine, FXPlayer.class);
		}
		@Override
		public void update(float delta) {
			try {
				foreach(s -> s.update(delta));
			} catch(Exception e) {
				Log.info("Fucking concurrent synchronized ", e);
			}
		}
	}

	
	public static EffectManager manager;
	public static Fight fight;
	public static FXFamily family;
	
	public static Map<Class<? extends Spell>, FXPlayer> spellsFX = new HashMap<>();
	public static Map<Class<? extends Status>, FXPlayer> statusFX = new HashMap<>();
	public static Map<Class<? extends TerrainEffect>, FXPlayer> terrainFX = new HashMap<>();
	
	public Ammolite(Fight f, EffectManager manager) {
		Ammolite.fight = f;
		Ammolite.manager = manager;

		Jeffekseer.yUp = true;
		Jeffekseer.worldScaleX = 1f;
		Jeffekseer.worldScaleY = 1f;
		Jeffekseer.worldScaleZ = 1f;
		
		Jeffekseer.worldOffsetX = 0.5f;
		Jeffekseer.worldOffsetY = 0.5f;
		Jeffekseer.worldOffsetZ = 1f;
		
		f.statusbus.reactors.register(this);

		
		family = new FXFamily(f);

		Engine nullEngine = null;
		var fxPlayers = new DefaultClassDiscoverer<FXPlayer>(FXPlayer.class).explore("com.souchy.randd.ebi.ammolite");
		fxPlayers.forEach(p -> {
			try {
				// if abstract class, dont try to instance it
				if(Modifier.isAbstract(p.getModifiers())) return;
				
				var fx = p.getDeclaredConstructor(Engine.class).newInstance(nullEngine);
				if(Spell.class.isAssignableFrom(fx.modelClass()))
					spellsFX.put((Class<? extends Spell>) fx.modelClass(), fx);
				else
				if(TerrainEffect.class.isAssignableFrom(fx.modelClass()))
					terrainFX.put((Class<? extends TerrainEffect>) fx.modelClass(), fx);
				else
				if(Status.class.isAssignableFrom(fx.modelClass()))
					statusFX.put((Class<? extends Status>) fx.modelClass(), fx);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
//	public static ParticleEffekseer particle() {
//		return new ParticleEffekseer(Ammolite.manager);
//	}
	
	
	@Override
	public void onCastSpell(CastSpellEvent event) {
		var fx = spellsFX.get(event.spell.getClass());
		if(fx == null) return;
		
		var inst = fx.copy(fight);

		trigger(inst, event);
	}
	
	@Override
	public void onAddStatus(AddStatusEvent event) {
		if(event.getStatus() == null) {
			Log.error("Ammolite onAddStatus %s", event);
			return; 
		}
		var fx = statusFX.get(event.getStatus().getClass());
		if(fx == null) return;
		
		var inst = fx.copy(fight);
		event.getStatus().add(inst);

		trigger(inst, event);
	}
	
	@Override
	public void onAddTerrain(AddTerrainEvent event) {
		// only accept the initial event as to not create an fx for every target(cell) 
		// since we keep the same status for every cell, it should also be the same fx for the entireity of the terraineffect
		if(event.level != 0)  return;
		
		var fx = terrainFX.get(event.getStatus().getClass());
		if(fx == null) return;
		
		var inst = fx.copy(fight);
		event.getStatus().add(inst);

		trigger(inst, event);
	}
	
	private void trigger(FXPlayer inst, Event event) {
		try {
			Log.info("Ammolite create fx player on " + event);
			Gdx.app.postRunnable(() -> inst.onCreation(event));
			// inst.onCreation(event);
		} catch (Exception e) {
			Log.error("", e);
		}
	}
	
	
}
