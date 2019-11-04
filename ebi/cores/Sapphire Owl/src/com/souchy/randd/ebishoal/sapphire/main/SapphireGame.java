package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisFiles;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHudSkin;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.jade.combat.JadeCreature;

import data.new1.CreatureModel;
import gamemechanics.common.Vector2;
import gamemechanics.models.Fight;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity.Team;

public class SapphireGame extends LapisGame {
	
	public static SapphireScreen gfx;

	public static Fight fight;
	
	@Override
	public void init() {
		if(SapphireOwl.isEclipse) {
			Gdx.files = new LapisFiles(Environment.root.toString());//"G:/www/ebishoal/");
		}
		
		gfx = new SapphireScreen(); 
		
		// load creature and creature spells assets (creature i18n bundle, creature avatar, spells icons)
//		SapphireOwl.data.creatures.values().forEach(model -> {
//			SapphireResources.loadResources(model);
//		});
		
		SapphireResources.loadResources(SapphireOwl.data);
		
		// need to load items resources and random spells resources (spells should already be loaded through creatures th)
		// ...
		
		{
			try {
				Log.info("data.creatures : " + SapphireOwl.manager.getEntry().creatures.values() + ", " + SapphireOwl.manager.getEntry());
				// create instances for players' creatures
				int id = 1000;
				CreatureModel model = SapphireOwl.data.creatures.get(id);
				JadeCreature jade = new JadeCreature();
				jade.creatureID = model.id();
				jade.itemIDs = new int[4];
				jade.spellIDs = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 };
				for(int i = 0; i < jade.spellIDs.length; i++)
					jade.spellIDs[i] += model.id();
				Creature inst = new Creature(model, jade, SapphireOwl.data, new Vector2(0, 0));
				
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
