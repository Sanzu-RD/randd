package com.souchy.randd.commons.diamond.main;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.tealwaters.commons.Bean;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * Class models for creatures, spells, and perhaps statuses
 * 
 * @author Blank
 * @date 9 mai 2020
 */
public class DiamondModels {
	private static boolean init = false;

	public static final Map<Integer, CreatureModel> creatures = new HashMap<>();
	public static final Map<Integer, Spell> spells = new HashMap<>();
	public static final Map<Integer, Status> statuses = new HashMap<>();

	/**
	 * @param packag : "com.souchy.randd.data.s1" depends on the season
	 */
	public static void instantiate(String packag) {
//		if(init == true) return; // happens in MockFight when I creaate 2 fights
		init = true;
		var creaturelist = new DefaultClassDiscoverer<CreatureModel>(CreatureModel.class).explore(packag);
		Log.info("Diamond creatures models " + creaturelist);
		creaturelist.forEach(c -> {
			try {
				var model = c.getDeclaredConstructor().newInstance();
				if(creatures.containsKey(model.id())) 
					Log.error("Duplicate creature model id " + model.id() + " for class " + c.getSimpleName() + ". ");
				else 
					creatures.put(model.id(), model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		var spelllist = new DefaultClassDiscoverer<Spell>(Spell.class).explore(packag);
		Log.info("Diamond spell models " + spelllist);
		spelllist.forEach(c -> {
			try {
				var model = c.getDeclaredConstructor(Fight.class).newInstance(new Object[] { null });
				if(spells.containsKey(model.modelid()))
					Log.error("Duplicate spell model id " + model.modelid() + " for class " + c.getSimpleName() + ". ");
				else
					spells.put(model.modelid(), model);
//				Log.info("Diamond spell model [" + model.modelid() + "] = " + model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		var statuslist = new DefaultClassDiscoverer<Status>(Status.class).explore(packag);
		Log.info("Diamond status models " + statuslist);
		statuslist.forEach(c -> {
			try {
				var model = c.getDeclaredConstructor(Fight.class, Integer.TYPE, Integer.TYPE).newInstance(null, 0, 0);
				if(statuses.containsKey(model.modelid())) 
					Log.error("Duplicate status model id " + model.modelid() + " for class " + c.getSimpleName() + ". ");
				else
					statuses.put(model.modelid(), model);
				//Log.info("Diamond status model [" + model.id() + "] = " + model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
//		StatusSystem.family.clear(); // enlève les models de status du système d'instances
		
	}
	
}
