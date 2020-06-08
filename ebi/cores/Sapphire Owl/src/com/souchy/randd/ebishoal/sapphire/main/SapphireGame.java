package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.creatures.Sungjin;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.confs.AssetConfs;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHudSkin;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.jade.meta.JadeCreature;

import data.new1.spellstats.base.IntStat;
import gamemechanics.common.generic.Vector2;
import gamemechanics.components.Position;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.CreatureModel;
import gamemechanics.models.Fight;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity.Team;
import gamemechanics.statics.stats.properties.Resource;

public class SapphireGame extends LapisGame {
	
	public static SapphireScreen gfx;

	public static Fight fight;
	
	@Override
	public void init() {
		// init elements
		Elements.values(); 
		
		// models configurations (creatures, spells, statuses)
		AssetConfs.loadResources();

		// load asssets
		//LapisResources.loadResources(SapphireOwl.data);
		LapisAssets.loadTextures(Gdx.files.internal("res/textures/"));
		LapisAssets.loadModels(Gdx.files.internal("res/models/"));
		LapisAssets.loadMusics(Gdx.files.internal("res/musics/"));
		LapisAssets.loadSounds(Gdx.files.internal("res/sounds/"));
		LapisAssets.loadI18NBundles(Gdx.files.internal("res/i18n/"));
		
		// init creatures & spells models
		DiamondModels.instantiate("com.souchy.randd.data.s1");
		
		// screen
		gfx = new SapphireScreen();
		
		ParticleEffectLoader.ParticleEffectLoadParameter params = new ParticleEffectLoader.ParticleEffectLoadParameter(gfx.getPfxSystem().getBatches());
		LapisAssets.loadParticleEffects(Gdx.files.internal("res/fx/"), params);
		
		Log.info("LapisResources : { " + String.join(", ", LapisAssets.assets.getAssetNames()) + " }");
		
		
		// load creature and creature spells assets (creature i18n bundle, creature avatar, spells icons)
//		SapphireOwl.data.creatures.values().forEach(model -> {
//			SapphireResources.loadResources(model);
//		});
		
		
		// need to load items resources and random spells resources (spells should already be loaded through creatures th)
		// ...
		
		
		// server should initiate the Fight object with all the characters already
		var jadeteam1 = new JadeCreature[4];
		var jadeteam2 = new JadeCreature[4];
		// then sapphire should just show these creatures from the deserialized Fight object (dont need to instantiate anything here)
		
		try {
			Log.info("data.creatures : " + DiamondModels.creatures);
			// create instances for players' creatures
			int id = 1;
			// jade customization
			JadeCreature jade = new JadeCreature();
			jade.affinities = new int[Elements.values().length];
			jade.affinities[Elements.air.ordinal()] = 30; // personalized 30% air affinity
			jade.creatureModelID = id; 
			jade.spellIDs = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
//			for (int i = 0; i < jade.spellIDs.length; i++)
//				jade.spellIDs[i] += jade.creatureModelID; //model.id();
			// base model
			CreatureModel model = DiamondModels.creatures.get(jade.creatureModelID);
			// override model stats
			model.baseStats.resources.put(Resource.life, new IntStat(30)); 
			// instance
			Creature creature = new Creature(fight, model, jade, new Position(5, 5));
			// test stats//.get(Resource.life).base = 30; //).addResource(30, Resource.life);
			creature.getStats().resources.get(Resource.life).fight = -150; //.addFightResource(-130, Resource.life);
			creature.getStats().shield.get(Resource.life).fight = 600; //.addShield(600, Resource.life);

			
			var modelpath = AssetConfs.creatures.get(creature.model.id()).models[0]; //"res/models/creatures/Marian.g3dj"
			var model3d = LapisAssets.assets.<Model>get(modelpath);
			var modelinstance = new ModelInstance(model3d);
			modelinstance.transform.rotate(1, 0, 0, 90);
			var animController = new AnimationController(modelinstance);
			animController.setAnimation("CharacterArmature|Walk", -1);
			creature.add(animController);
			creature.add(modelinstance);
//			Log.info("creature 3d model : " + modelinstance);
			SapphireEntitySystem.family.add(creature);
			
			// fight
			fight = new Fight();
			fight.add(creature, Team.A);
			fight.timeline.add(creature);
			
			// player hud
			SapphireHudSkin.play(creature);
		} catch (Exception e) {
			Log.error("SapphireOwl creature error : ", e);
			Gdx.app.exit();
			System.exit(0);
		}
	}

	@Override
	public SapphireScreen getStartScreen() {
		return gfx;
	}
	
	
}
