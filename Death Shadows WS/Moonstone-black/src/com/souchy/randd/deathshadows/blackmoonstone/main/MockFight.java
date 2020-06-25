package com.souchy.randd.deathshadows.blackmoonstone.main;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.jade.matchmaking.Team;
import com.souchy.randd.jade.meta.JadeCreature;

import data.new1.spellstats.base.IntStat;
import gamemechanics.components.Position;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Creature;
import gamemechanics.models.CreatureModel;
import gamemechanics.models.Fight;
import gamemechanics.statics.stats.properties.Resource;

public class MockFight {

	public static Fight createFight() {
		// init elements and models
		Elements.values(); 
		DiamondModels.instantiate("com.souchy.randd.data.s1");
		
		// init fight with cells, creatures and spells
		Fight fight = new Fight();
		var c1 = testCreateCreature(fight, 1);
		var c2 = testCreateCreature(fight, 2);
		c1.team = Team.A;
		c2.team = Team.B;
		c1.pos.set(2, 16);
		c2.pos.set(16, 2);
		return fight;
	}

	private static Creature testCreateCreature(Fight fight, int modelid, int... spellids) {
		// jade customization
		JadeCreature jade = new JadeCreature();
		jade.affinities = new int[Elements.values().length];
		jade.affinities[Elements.air.ordinal()] = 30; // personalized 30% air affinity
		jade.creatureModelID = modelid; 
		jade.spellIDs = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		
		// base model
		CreatureModel model = DiamondModels.creatures.get(jade.creatureModelID);
		Log.info("");
		// override model stats
		model.baseStats.resources.put(Resource.life, new IntStat(30)); 
		// instance
		Creature creature = new Creature(fight, model, jade, new Position(5, 5));
		creature.stats.resources.get(Resource.life).fight = -150; 
		creature.stats.shield.get(Resource.life).fight = 600; 
		
		return creature;
	}
	
	
}
