package com.souchy.randd.deathshadows.blackmoonstone.main;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.jade.matchmaking.Team;
import com.souchy.randd.jade.meta.JadeCreature;

public class MockFight {

	public static Fight createFight() {
		
		// init fight with cells, creatures and spells
		Fight fight = new Fight();
		
		// Team A
		var ca1 = testCreateCreature(fight, 5, 1, 10, 11, 1501); // fiera
		ca1.team = Team.A;
		ca1.pos.set(3, 17);
		ca1.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var ca2 = testCreateCreature(fight, 6, 2, 3, 4, 5, 6); // flora
		ca2.team = Team.A;
		ca2.pos.set(4, 17);
		ca2.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var ca3 = testCreateCreature(fight, 7, 1308, 1313, 1314, 1315, 1316, 2, 3, 4, 5, 6); // naia
		ca3.team = Team.A;
		ca3.pos.set(5, 17);
		ca3.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var ca4 = testCreateCreature(fight, 8, 1501); // orea
		ca4.team = Team.A;
		ca4.pos.set(6, 17);
		ca4.add(new ObjectId("5efbf61f0856d10feb48c193"));

		// Team B
		var cb1 = testCreateCreature(fight, 1, 1, 2, 3, 4, 5, 10, 9); // mesmer
		cb1.team = Team.B;
		cb1.pos.set(15, 1);
		cb1.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var cb2 = testCreateCreature(fight, 2, 1, 2, 3, 4, 5, 10, 9); // sungjin
		cb2.team = Team.B;
		cb2.pos.set(16, 1);
		cb2.add(new ObjectId("5efbf61f0856d10feb48c193"));
		
		// Timeline
		fight.timeline.add(ca1.id);
		fight.timeline.add(cb1.id);
		fight.timeline.add(ca2.id);
		fight.timeline.add(cb2.id);
		fight.timeline.add(ca3.id);
		fight.timeline.add(ca4.id);
		return fight;
	}

	private static Creature testCreateCreature(Fight fight, int modelid, int... spellids) {
		// jade customization
		JadeCreature jade = new JadeCreature();
		jade.affinities = new int[Element.values.size()];
		jade.affinities[Elements.water.ordinal()] = 30; // personalized 30% water affinity
		jade.creatureModelID = modelid; 
		jade.spellIDs = spellids;
		
		// base model
		CreatureModel model = DiamondModels.creatures.get(jade.creatureModelID);
//		Log.info("");
		// override model stats
//		model.baseStats.resources.put(Resource.life, new IntStat(30)); 
		// instance
		Creature creature = new Creature(fight, model, jade, new Position(0, 0));
		creature.stats.resources.get(Resource.life).fight = -150; 
		creature.stats.shield.get(Resource.life).fight = 200; 
		
		return creature;
	}
	
	
}
