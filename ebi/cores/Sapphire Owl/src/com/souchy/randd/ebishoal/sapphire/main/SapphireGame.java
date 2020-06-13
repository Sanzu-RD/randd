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
			if(Moonstone.moon != null) Moonstone.moon.write(new GetUpdate());
			
			// player hud
//			SapphireHudSkin.play(fight.teamA.get(0));

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
