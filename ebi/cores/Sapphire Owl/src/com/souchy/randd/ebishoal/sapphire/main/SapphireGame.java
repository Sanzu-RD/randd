package com.souchy.randd.ebishoal.sapphire.main;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
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
import gamemechanics.models.Creature;
import gamemechanics.models.Creature.Team;
import gamemechanics.models.CreatureModel;
import gamemechanics.models.Fight;
import gamemechanics.statics.stats.properties.Resource;

public class SapphireGame extends LapisGame {
	
	public static SapphireScreen gfx;

	public static Fight fight; // FIXME replace this with Moonstone.fight or use the reference for now i guess
	
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
		
		// jade customization
		JadeCreature jade1 = new JadeCreature();
		jade1.affinities = new int[Elements.values().length];
		jade1.affinities[Elements.air.ordinal()] = 30; // personalized 30% air affinity
		jade1.creatureModelID = 1; 
		jade1.spellIDs = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		jadeteam1[0] = jade1;
		
		// jade customization
		JadeCreature jade2 = new JadeCreature();
		jade2.affinities = new int[Elements.values().length];
		jade2.affinities[Elements.air.ordinal()] = 30; // personalized 30% air affinity
		jade2.creatureModelID = 2; 
		jade2.spellIDs = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		jadeteam2[0] = jade2;

		var startPositions = new Position[] { new Position(2, 16), new Position(16, 2) };
		
		// then sapphire should just show these creatures from the deserialized Fight object (dont need to instantiate anything here)
		try {
			Log.info("data.creatures : " + DiamondModels.creatures);
			
			// fight
			fight = new Fight();
			// systems
			new SapphireEntitySystem(fight);
			
			// create teams A & B
			for(var jade : jadeteam1)
				if(jade != null) // wouldnt need this in the final product
				fight.add(testCreateCreature(jade), Team.A);
			for(var jade : jadeteam2)
				if(jade != null)
				fight.add(testCreateCreature(jade), Team.B);
			for(int i = 0; i < startPositions.length; i++)
				fight.timeline.get(i).pos = startPositions[i];
			
//			for(var c : fight.timeline) {
//				Log.info("timeline creature " + c + " " + c.model.id() + " " + c.pos + " " + c.team);
//			}

			Log.info("cells intances " + fight.cells.family.size());
			Log.info("creatures intances " + fight.creatures.family.size());
			Log.info("spells intances " + fight.spells.family.size());
			Log.info("status intances " + fight.status.family.size());
			
			// player hud
			SapphireHudSkin.play(fight.teamA.get(0));
		} catch (Exception e) {
			Log.error("SapphireOwl creature error : ", e);
			Gdx.app.exit();
			System.exit(0);
		}
	}
	
	
	private Creature testCreateCreature(JadeCreature jade) {
		// base model
		CreatureModel model = DiamondModels.creatures.get(jade.creatureModelID);
		// override model stats
		model.baseStats.resources.put(Resource.life, new IntStat(30)); 
		// instance
		Creature creature = new Creature(fight, model, jade, new Position(5, 5));
		creature.stats.resources.get(Resource.life).fight = -150; 
		creature.stats.shield.get(Resource.life).fight = 600; 
		// 3d instance & controller
		var modelpath = AssetConfs.creatures.get(creature.modelid).models[0]; 
		var model3d = LapisAssets.assets.<Model>get(modelpath);
		var modelinstance = new ModelInstance(model3d);
		modelinstance.transform.rotate(1, 0, 0, 90);
		var animController = new AnimationController(modelinstance);
		animController.setAnimation("CharacterArmature|Walk", -1);
		creature.add(animController);
		creature.add(modelinstance);
		SapphireEntitySystem.family.add(creature);
		
		var rnd = new Random();
		var baseColor = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1f);
				
		// clothes
		modelinstance.materials.get(1).set(ColorAttribute.createDiffuse(baseColor));
		// hat
		modelinstance.materials.get(4).set(ColorAttribute.createDiffuse(baseColor));
		// hair darker
		float ratio = 1f /  2f;
		modelinstance.materials.get(5).set(ColorAttribute.createDiffuse(baseColor.mul(ratio, ratio, ratio, 1f)));
		
		//rnd.nextFloat() * 255f, rnd.nextFloat() * 255f, rnd.nextFloat() * 255f, 1f));
		
		return creature;
	}

	@Override
	public SapphireScreen getStartScreen() {
		return gfx;
	}
	
	
}
