package com.souchy.randd.ebi.ammolite;

import java.util.HashMap;
import java.util.Map;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent.OnCastSpellHandler;
import com.souchy.randd.commons.diamond.statusevents.status.AddStatusEvent;
import com.souchy.randd.commons.diamond.statusevents.status.AddStatusEvent.OnAddStatusHandler;
import com.souchy.randd.commons.diamond.statusevents.status.AddTerrainEvent;
import com.souchy.randd.commons.diamond.statusevents.status.AddTerrainEvent.OnAddTerrainHandler;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.ecs.Engine.AddEntityEvent;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;

import br.com.johnathan.gdx.effekseer.api.EffekseerManager;
import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

/**
 * EbiShoal Library to play Visual and Audio FX
 * 
 * @author Souchy
 * @date 11 janv. 2021
 */
public class Ammolite implements OnCastSpellHandler, OnAddStatusHandler, OnAddTerrainHandler {

	/** updates fx on render */
	public static class FXFamily extends Family<FXPlayer> {
		public FXFamily(Engine engine) {
			super(engine, FXPlayer.class);
		}
		@Override
		public void update(float delta) {
			foreach(s -> s.update(delta));
		}
	}

	
	public static EffekseerManager manager;
	public static Fight fight;
	
	public static Map<Class<? extends Spell>, FXPlayer> spellsFX = new HashMap<>();
	public static Map<Class<? extends Status>, FXPlayer> statusFX = new HashMap<>();
	public static Map<Class<? extends TerrainEffect>, FXPlayer> terrainFX = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public Ammolite(Fight f, EffekseerManager manager) {
		Ammolite.fight = f;
		Ammolite.manager = manager;
		
		f.statusbus.reactors.register(this);

		ParticleEffekseer.worldScaleX = 1f;
		ParticleEffekseer.worldScaleY = 1f;
		ParticleEffekseer.worldScaleZ = -1f;
		
		ParticleEffekseer.worldOffsetX = 0.5f;
		ParticleEffekseer.worldOffsetY = 1f;
		ParticleEffekseer.worldOffsetZ = -0.5f;
		
		
		new FXFamily(f);

		Engine nullEngine = null;
		var fxPlayers = new DefaultClassDiscoverer<FXPlayer>(FXPlayer.class).explore("com.souchy.randd.ebi.ammolite");
		fxPlayers.forEach(p -> {
			try {
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
	
	public static ParticleEffekseer particle() {
		return new ParticleEffekseer(Ammolite.manager);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCastSpell(CastSpellEvent event) {
		var fx = spellsFX.get(event.spell.getClass());
		if(fx == null) return;
		
		var inst = fx.copy(fight);
		inst.onCreation(event);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAddStatus(AddStatusEvent event) {
		var fx = statusFX.get(event.getStatus().getClass());
		if(fx == null) return;
		
		var inst = fx.copy(fight);
		event.getStatus().add(inst);
		inst.onCreation(event);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onAddTerrain(AddTerrainEvent event) {
		var fx = terrainFX.get(event.getStatus().getClass());
		if(fx == null) return;
		
		var inst = fx.copy(fight);
		event.getStatus().add(inst);
		inst.onCreation(event);
	}
	
	
}
