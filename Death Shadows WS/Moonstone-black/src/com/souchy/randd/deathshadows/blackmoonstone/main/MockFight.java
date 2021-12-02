package com.souchy.randd.deathshadows.blackmoonstone.main;

import java.io.File;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.diamond.common.BoardGenerator;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.mapio.MapData;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.jade.matchmaking.Team;
import com.souchy.randd.jade.meta.JadeCreature;

public class MockFight {

	public static Fight createFight() {
		
		// init fight with cells, creatures and spells
		Fight fight = new Fight();

//		Log.info("MockFight path " + new File("").getAbsolutePath());
        MapData data = MapData.read("bin/goulta7b.map"); //Gdx.files.internal(mapFolder + "goulta7b.map").path());
		new BoardGenerator().generate(fight, data);
		Log.info("MockFight board cell count : " + fight.board.cells.size());
		
		// Team A
		var ca1 = testCreateCreature(fight, 5, 100002001, 100002002, 100002003, 100002004, 100002005, 100002006, 100002007, 100002008); // 1, 10, 11, 1501); // fiera// fiera
		ca1.team = Team.A;
		ca1.pos.set(9, 9);
		ca1.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var ca2 = testCreateCreature(fight, 6, 100004001, 100004002, 100004003, 100004004); //2, 3, 4, 5, 6); // flora
		ca2.team = Team.A;
		ca2.pos.set(3, 14);
		ca2.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var ca3 = testCreateCreature(fight, 7, 100003001, 100003002, 100003003, 100003004, 100003005, 100003006, 100003007, 4); //1308, 1313, 1314, 1315, 1316, 2, 3, 4, 5, 6); // naia
		ca3.team = Team.A;
		ca3.pos.set(9, 14);
		ca3.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var ca4 = testCreateCreature(fight, 8, 100005001, 100001001, 100001002); // orea
		ca4.team = Team.A;
		ca4.pos.set(3, 9);
		ca4.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var ca5 = testCreateCreature(fight, 13, 100131001, 100131002, 100131003, 100135004); // tsukoyo
		ca5.team = Team.A;
		ca5.pos.set(2, 12);
		ca5.add(new ObjectId("5efbf61f0856d10feb48c193"));

		// Team B
		var cb1 = testCreateCreature(fight, 1, 1, 2, 3, 4, 5, 9, 10); // mesmer
		cb1.team = Team.B;
		cb1.pos.set(14, 6);
		cb1.add(new ObjectId("5efbf61f0856d10feb48c193"));
		var cb2 = testCreateCreature(fight, 2, 1, 2, 3, 4, 5, 9, 10); // sungjin
		cb2.team = Team.B;
		cb2.pos.set(14, 1);
		cb2.add(new ObjectId("5efbf61f0856d10feb48c193"));
		
		// Timeline
		fight.timeline.add(ca1.id);
		fight.timeline.add(ca2.id);
		fight.timeline.add(ca3.id);
		fight.timeline.add(ca4.id);
		fight.timeline.add(ca5.id);
		
		fight.timeline.add(cb1.id);
		fight.timeline.add(cb2.id);
		
		return fight;
	}

	private static Creature testCreateCreature(Fight fight, int modelid, int... spellids) {
		// jade customization
		JadeCreature jade = new JadeCreature();
		jade.affinities = new int[Element.values.size()];
//		jade.affinities[Elements.water.ordinal()] = 30; // personalized 30% water affinity
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
