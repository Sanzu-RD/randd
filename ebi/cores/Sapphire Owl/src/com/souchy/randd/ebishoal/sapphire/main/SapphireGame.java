package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.creatures.sungjin.SungjinModel;
import com.souchy.randd.data.creatures.sungjin.SungjinModule;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisResources;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHudSkin;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.jade.meta.JadeCreature;

import data.new1.CreatureModel;
import gamemechanics.common.Vector2;
import gamemechanics.models.Fight;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity.Team;
import gamemechanics.statics.stats.properties.Resource;

public class SapphireGame extends LapisGame {
	
	public static SapphireScreen gfx;

	public static Fight fight;
	
	@Override
	public void init() {
		
		//LapisResources.loadResources(SapphireOwl.data);
		
		LapisResources.loadModels(Gdx.files.internal("res/models/"));
		LapisResources.loadI18NBundles(Gdx.files.internal("res/i18n/"));
		LapisResources.loadTextures(Gdx.files.internal("res/textures/"));
		Log.info("LapisResources : { " + String.join(", ", LapisResources.assets.getAssetNames()) + " }");
		
		gfx = new SapphireScreen(); 
		
		// load creature and creature spells assets (creature i18n bundle, creature avatar, spells icons)
//		SapphireOwl.data.creatures.values().forEach(model -> {
//			SapphireResources.loadResources(model);
//		});
		
		
		// need to load items resources and random spells resources (spells should already be loaded through creatures th)
		// ...
		
		{
			try {
				if(LapisCore.isEclipse) // for testing purposes
					SapphireOwl.data.creatures.put(1000, new SungjinModel());
				Log.info("data.creatures : " + SapphireOwl.azur.getEntry().creatures.values() + ", " + SapphireOwl.azur.getEntry());
				// create instances for players' creatures
				int id = 1000;
				// base model
				CreatureModel model = SapphireOwl.data.creatures.get(id);
				// jade customization
				JadeCreature jade = new JadeCreature();
				jade.creatureID = model.id();
				jade.itemIDs = new int[4];
				jade.spellIDs = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 };
				for(int i = 0; i < jade.spellIDs.length; i++)
					jade.spellIDs[i] += model.id();
				// instance
				Creature inst = new Creature(model, jade, SapphireOwl.data, new Vector2(0, 0));
				inst.model.baseStats.addResource(30, Resource.life);
				inst.getStats().addFightResource(-130, Resource.life);
				
				// fight
				fight = new Fight();
				fight.add(inst, Team.A);
				fight.timeline.add(inst);
				
				SapphireHudSkin.play(inst);
			} catch(Exception e) {
				Log.error("SapphireOwl creature error : ", e);
				Gdx.app.exit();
				System.exit(0);
			}
		}
	}

	@Override
	public Screen getStartScreen() {
		return gfx;
	}
	
}
