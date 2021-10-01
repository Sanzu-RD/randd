package com.souchy.randd.ebishoal.sapphire.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent.OnTurnEndHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent.OnCastSpellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.ecs.Engine.AddEntityEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine.RemoveEntityEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.status.Shocked;
import com.souchy.randd.ebi.ammolite.FXPlayer;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.gfx.AnimationAdapter;
import com.souchy.randd.ebishoal.sapphire.gfx.Highlight;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.damage.DamageEvent;
import com.souchy.randd.commons.diamond.statusevents.damage.DamageEvent.OnDamageHandler;
import com.souchy.randd.commons.diamond.statusevents.displacement.MoveStartEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.MoveStartEvent.OnMoveStartHandler;
import com.souchy.randd.commons.diamond.statusevents.displacement.MoveStopEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.MoveStopEvent.OnMoveStopHandler;


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
public class SapphireEntitySystem extends Family<Entity> implements Reactor, OnCastSpellHandler, OnDamageHandler, OnMoveStartHandler, OnMoveStopHandler { //data.new1.ecs.System {
	
	public SapphireEntitySystem(Engine engine) {
		super(engine, Entity.class);
		((Fight) engine).statusbus.register(this);
	}
	
	private boolean applicableType(Entity entity) {
		return (entity instanceof Creature || entity instanceof Cell || entity instanceof TerrainEffect || entity instanceof Status);
	}
	
//	private Creature playing;
	
	@Override
	public void update(float delta) {
		// Log.info("sapphire entity system update");
		
		foreach(e -> {
			
			e.update(delta);
			
			// pas besoin d'update les fxplayer ici car on le fait déjà dans Ammolite Family
			// var fx = e.get(FXPlayer.class);
			// if(fx != null) fx.update(delta);
			
			var alpha = 1f;
			float fxheight = Constants.floorZ;
			if(e instanceof Creature) {
				fxheight = Constants.floorZ + Constants.cellHalf;
				var c = (Creature) e;
				if(c.stats.invisible.value()) {
					//Log.info("SapphireEntitySystem creature invisible %s", c.id);
					alpha = 0.0f;
				} else {
					//Log.info("SapphireEntitySystem creature visible %s", c.id);
				}
			} else
			if(e instanceof Cell) {

			} else 
			if(e instanceof TerrainEffect) {
				//Log.format("SapphireEntitySystem TerrainEffect %s %s", e.get(ParticleEffekseer.class), e.get(Position.class));
				//this.remove(e);
			} else 
			if(e instanceof Status) {
					
			}
			
			// animation
			var anime = e.get(AnimationController.class);
			if(anime != null) anime.update(delta);
						
			// position model & fx
			var pos = e.get(Position.class);
			if(pos == null) return;
			if(pos != null) {
				var model = e.get(ModelInstance.class);
				if(model != null) {
					model.transform.setTranslation(
							(float) pos.x + Constants.cellHalf, 
							(float) pos.y + Constants.cellHalf, 
							Constants.floorZ
						);
					for(int i = 0; i < model.materials.size; i++) {
						var blend = model.materials.get(i).get(BlendingAttribute.class, BlendingAttribute.Type);
						if(blend != null) 
							blend.opacity = alpha;
					}
				}
				
				var effect = e.get(com.souchy.jeffekseer.Effect.class);
				if(effect != null) {
					effect.setPosition(
						pos.x, 
						pos.y, 
						fxheight
					);
				}
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
		makeRenderable(event.entity);

		if(applicableType(event.entity)) {
			add(event.entity);
		}
	}
	
	@Subscribe
	@Override
	public void onRemovedEntity(RemoveEntityEvent event) {
		if(applicableType(event.entity)) {
			remove(event.entity);
		}
	}
	
	public void makeRenderable(Entity entity) {
//		Log.info("Sapphire Entity System on add");
		if(entity instanceof Creature) {
//			Log.info("Sapphire Entity System makeRenderable " + entity);

			var crea = (Creature) entity;
			Log.format("-----------model %s, Assets : %s", crea.modelid, AssetData.creatures.size());
			
			var modelpath = AssetData.creatures.get(crea.modelid).models[0]; 
			var model3d = LapisAssets.assets.<Model>get(modelpath);
			var modelinstance = new ModelInstance(model3d);
			modelinstance.transform.rotate(1, 0, 0, 90); // l'exportation blender en "Z up" ne suffit pas malheureusement/apparemment.

			for(int i = 0; i < modelinstance.materials.size; i++) {
				modelinstance.materials.get(i).set(new BlendingAttribute(1f));
			}
			//modelinstance.transform.scale(0.4f, 0.4f, 0.4f);
			
			var animController = new AnimationController(modelinstance);
			animController.setAnimation("CharacterArmature|Idle", -1);
			
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
			crea.add(animController);
			crea.add(modelinstance);
		} else 
		if(entity instanceof Cell) {
			
		} else 
		if(entity instanceof TerrainEffect) {

		} else 
		if(entity instanceof Status) {
		} 
	}

	@Override
	public void onCastSpell(CastSpellEvent event) {
		try {
			var animController = event.source.get(AnimationController.class);
			animController.animate("CharacterArmature|Shoot_OneHanded", 0, -1, 0, 1, new AnimationAdapter() {
				@Override
				public void onEnd(AnimationDesc animation) {
					animController.animate("CharacterArmature|Idle", 0, -1, -1, 1, new AnimationAdapter(), 5);
				}
			}, 5);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void onDamage(DamageEvent event) {
		try {
			var animController = event.target.get(AnimationController.class);
			animController.animate("CharacterArmature|RecieveHit", 0, -1, 0, 1, new AnimationAdapter() {
				@Override
				public void onEnd(AnimationDesc animation) {
					animController.animate("CharacterArmature|Idle", 0, -1, -1, 1, new AnimationAdapter(), 5);
				}
			}, 5);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void onMoveStop(MoveStopEvent e) {
		try {
			var animController = e.target.get(AnimationController.class);
			animController.animate("CharacterArmature|Idle", 0, -1, -1, 1, new AnimationAdapter(), 5);
		} catch(Exception ex) {
			
		}
	}

	@Override
	public void onMoveStart(MoveStartEvent e) {
		try {
			var animController = e.target.get(AnimationController.class);
			animController.animate("CharacterArmature|Walk", 0, -1, -1, 1, new AnimationAdapter(), 5);
		} catch(Exception ex) {
			
		}
	}
	

}
