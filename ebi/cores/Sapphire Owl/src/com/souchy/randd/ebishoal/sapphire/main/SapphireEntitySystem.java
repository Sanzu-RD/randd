package com.souchy.randd.ebishoal.sapphire.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;

import data.new1.ecs.Engine;
import data.new1.ecs.Engine.AddEntityEvent;
import data.new1.ecs.Engine.RemoveEntityEvent;
import data.new1.ecs.Entity;
import data.new1.timed.TerrainEffect;
import gamemechanics.components.Position;
import gamemechanics.ext.AssetData;
import gamemechanics.models.Creature;

public class SapphireEntitySystem extends data.new1.ecs.System {
	
	public static List<Entity> family = new ArrayList<>();
	
	public SapphireEntitySystem(Engine engine) {
		super(engine);
	}
	
	@Override
	public void update(float delta) {
//		Log.info("sapphire entity system update");
		synchronized (family) {
			family.forEach(e -> {
				var model = e.get(ModelInstance.class);
				var pos = e.get(Position.class);
				if(model == null || pos == null) return;
	//			Log.info("set sapphire entity model pos : " + pos + "; " + model);
				model.transform.setTranslation(
						(float) pos.x - 0.5f, 
						(float) pos.y - 0.5f, 
						1f
						);
				var anime = e.get(AnimationController.class);
				if(anime != null) {
					anime.update(delta);
				}
			});
		}
	}
	
	public void dispose() {
		super.dispose();
		family.clear();
	}

	@Subscribe
	public void onAddedEntity(AddEntityEvent event) {
//		Log.info("Sapphire Entity System on add");
		if(event.entity instanceof Creature) {
			Log.info("Sapphire Entity System on add " + event);
			var modelpath = AssetData.creatures.get(((Creature) event.entity).modelid).models[0]; 
			var model3d = LapisAssets.assets.<Model>get(modelpath);
			var modelinstance = new ModelInstance(model3d);
			modelinstance.transform.rotate(1, 0, 0, 90);
			var animController = new AnimationController(modelinstance);
			animController.setAnimation("CharacterArmature|Walk", -1);

			var rnd = new Random();
			var baseColor = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1f);
					
			// clothes
			modelinstance.materials.get(1).set(ColorAttribute.createDiffuse(baseColor));
			// hat
			modelinstance.materials.get(4).set(ColorAttribute.createDiffuse(baseColor));
			// hair darker
			float ratio = 1f /  2f;
			modelinstance.materials.get(5).set(ColorAttribute.createDiffuse(baseColor.mul(ratio, ratio, ratio, 1f)));

			// add model and animation to entity as components
			event.entity.add(animController);
			event.entity.add(modelinstance);
			
			synchronized (SapphireEntitySystem.family) {
				family.add(event.entity);
			}
		} else 
		if(event.entity instanceof TerrainEffect) {
			
		}
	}
	@Subscribe
	public void onRemovedEntity(RemoveEntityEvent event) {
		if(event.entity instanceof Creature || event.entity instanceof TerrainEffect) {
			synchronized (SapphireEntitySystem.family) {
				family.remove(event.entity);
			}
		}
	}
	
}
