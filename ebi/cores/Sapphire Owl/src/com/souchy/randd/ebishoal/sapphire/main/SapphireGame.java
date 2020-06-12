package com.souchy.randd.ebishoal.sapphire.main;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.confs.AssetConfs;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.jade.meta.JadeCreature;
import com.souchy.randd.moonstone.commons.packets.c2s.GetUpdate;
import com.souchy.randd.moonstone.white.Moonstone;

import data.new1.spellstats.base.IntStat;
import gamemechanics.components.Position;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Creature;
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
		
		// pfx
		ParticleEffectLoader.ParticleEffectLoadParameter params = new ParticleEffectLoader.ParticleEffectLoadParameter(gfx.getPfxSystem().getBatches());
		LapisAssets.loadParticleEffects(Gdx.files.internal("res/fx/"), params);
		
		Log.info("LapisResources : { " + String.join(", ", LapisAssets.assets.getAssetNames()) + " }");

		
		try {
			Log.info("data.creatures : " + DiamondModels.creatures);

			SapphireGame.fight = Moonstone.fight = new Fight();
			new SapphireEntitySystem(Moonstone.fight);
			
			// ask for fight data after assets have been loaded
			Moonstone.moon.write(new GetUpdate());
			
			// player hud
//			SapphireHudSkin.play(fight.teamA.get(0));

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
		
		return creature;
	}

	@Override
	public SapphireScreen getStartScreen() {
		return gfx;
	}
	
	
}
