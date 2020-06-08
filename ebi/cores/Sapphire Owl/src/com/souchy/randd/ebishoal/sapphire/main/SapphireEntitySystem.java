package com.souchy.randd.ebishoal.sapphire.main;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;

import data.new1.ecs.Engine;
import data.new1.ecs.Entity;
import data.new1.timed.Status;
import data.new1.timed.TerrainEffect;
import gamemechanics.components.Position;
import gamemechanics.models.entities.Creature;

public class SapphireEntitySystem extends data.new1.ecs.System {
	
	public static List<Entity> family = new ArrayList<>();
	
	static {
		new SapphireEntitySystem();
	}
	
	public void dispose() {
		family.clear();
		Engine.remove(this);
	}
	
	@Override
	public void update(float delta) {
//		Log.info("sapphire entity system update");
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
	
	
	@Subscribe
	public void onAddedEntity(Entity e) {
		if(e instanceof Creature || e instanceof TerrainEffect)
			family.add(e);
	}
	@Subscribe
	public void onRemovedEntity(Entity e) {
		if(e instanceof Creature || e instanceof TerrainEffect)
			family.remove(e);
	}
	
}
