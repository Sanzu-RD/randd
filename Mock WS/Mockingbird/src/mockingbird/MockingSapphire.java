package mockingbird;

import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.jade.matchmaking.Team;
import com.souchy.randd.jade.meta.JadeCreature;

public class MockingSapphire {
	
	public static void main(String[] args) throws Exception {
		// init elements and models
		Elements.values(); 
		DiamondModels.instantiate("com.souchy.randd.data.s1");
		
		// init fight with cells, creatures and spells
		Fight fight = new Fight();
		var c1 = testCreateCreature(fight, 1);
		var c2 = testCreateCreature(fight, 2);
		c1.team = Team.A;
		c2.team = Team.B;
		fight.timeline.add(c1.id);
		fight.timeline.add(c2.id);
		c1.pos.set(2, 16);
		c2.pos.set(16, 2);
		
		Log.info("Cells family " + fight.cells.size());
		Log.info("Creatures family " + fight.creatures.size());
		Log.info("Status family " + fight.status.size());
		Log.info("Spells family " + fight.spells.size());
		
		// init black moonstone and adds fight to its pool
		String[] blackargs = new String[] { "443", "async" };
		BlackMoonstone.main(blackargs);
		BlackMoonstone.moon.fights.put(fight.id, fight);

		
		// init sapphire, auth with moonstone and join the fight id 1
		String[] sapphireargs = { "eclipse", "localhost", "443", "souchy", "z", "1" };
		SapphireOwl.main(sapphireargs);
		
		BlackMoonstone.moon.server.block();

		// init sapphire, auth with moonstone and join the fight id 1
//		String[] sapphireargs2 = { "eclipse", "localhost", "443", "robyn", "z", "1" };
//		SapphireOwl.main(sapphireargs2);
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
//		Log.info("");
		// override model stats
		model.baseStats.resources.put(Resource.life, new IntStat(30)); 
		// instance
		Creature creature = new Creature(fight, model, jade, new Position(5, 5));
		creature.stats.resources.get(Resource.life).fight = -150; 
		creature.stats.shield.get(Resource.life).fight = 600; 
		
		return creature;
	}
	
	
}
