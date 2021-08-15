package com.souchy.randd.ebishoal.sapphire.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.ecs.Engine.AddEntityEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine.RemoveEntityEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.status.Shocked;
import com.souchy.randd.ebi.ammolite.FXPlayer;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.gfx.Highlight;

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

/**
 * Stores all renderables
 * 
 * Creates their model and animation controller on new.
 * 
 * Updates their position on the board and updates their animation on update;
 * 
 * @author Blank
 * @date 4 juill. 2020 - date was way before, maybe 1-2 month
 */
public class SapphireEntitySystem extends Family<Entity> { //data.new1.ecs.System {
	
	public SapphireEntitySystem(Engine engine) {
		super(engine, Entity.class);
	}
	
	@Override
	public void update(float delta) {
		// Log.info("sapphire entity system update");
		foreach(e -> {
			
			e.update(delta);
			
			// pas besoin d'update les fxplayer ici car on le fait déjà dans Ammolite Family
//			var fx = e.get(FXPlayer.class);
//			if(fx != null) fx.update(delta);
			
			if(e instanceof Creature) {
				var model = e.get(ModelInstance.class);
				var pos = e.get(Position.class);
				if(model == null || pos == null) return;
				// Log.info("set sapphire entity model pos : " + pos + "; " + model);
				model.transform.setTranslation((float) pos.x + 0.5f, (float) pos.y + 0.5f, 1f);
				var anime = e.get(AnimationController.class);
				if(anime != null) {
					anime.update(delta);
				}
				var status = e.get(Shocked.class);
				if(status != null) {
					ParticleEffekseer effect = status.get(ParticleEffekseer.class);
					if(effect != null) {
						effect.setPosition((float) pos.x + 0.5f, 1.5f, (float) -pos.y - 0.5f);
					}
				}
			} else 
			if (e instanceof Status) {
				
			} else 
			if (e instanceof TerrainEffect) {
					
			}
			
		});
	}
	
	public void dispose() {
		super.dispose();
		clear();
	}
	
	/**
	 * this actually overrides the event handler from Family
	 */
	@Subscribe
	@Override
	public void onAddedEntity(AddEntityEvent event) {
//		Log.info("Sapphire Entity System on add");
		if(event.entity instanceof Creature) {
			Log.info("Sapphire Entity System on add " + event);
			var crea = (Creature) event.entity;
			Log.format("-----------model %s, Assets : %s", crea.modelid, AssetData.creatures.size());
			var modelpath = AssetData.creatures.get(crea.modelid).models[0]; 
			var model3d = LapisAssets.assets.<Model>get(modelpath);
			var modelinstance = new ModelInstance(model3d);
			modelinstance.transform.rotate(1, 0, 0, 90);
			var animController = new AnimationController(modelinstance);
			animController.setAnimation("CharacterArmature|Walk", -1);

			/*
			// Random colors
			var rnd = new Random();
			var baseColor = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1f);
					
			// clothes
			modelinstance.materials.get(1).set(ColorAttribute.createDiffuse(baseColor));
			// hat
			modelinstance.materials.get(4).set(ColorAttribute.createDiffuse(baseColor));
			// hair darker
			float ratio = 1f /  2f;
			modelinstance.materials.get(5).set(ColorAttribute.createDiffuse(baseColor.mul(ratio, ratio, ratio, 1f)));
			*/
			
			var teamHighlight = Highlight.team(crea.team);
			
			modelinstance.nodes.addAll(teamHighlight.nodes);
			
			// add model and animation to entity as components
			event.entity.add(animController);
			event.entity.add(modelinstance);
			
			add(event.entity);
//			synchronized (SapphireEntitySystem.family) {
//				family.add(event.entity);
//			}
		} else 
		if(event.entity instanceof TerrainEffect) {
			add(event.entity);
		} else 
		if(event.entity instanceof Status) {
			add(event.entity);
		} 
	}
	
	@Subscribe
	@Override
	public void onRemovedEntity(RemoveEntityEvent event) {
		if(event.entity instanceof Creature || event.entity instanceof TerrainEffect  || event.entity instanceof Status) {
			remove(event.entity);
		}
	}
	
}
