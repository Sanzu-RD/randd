package gamemechanics.main;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;

import data.new1.timed.Status;
import gamemechanics.models.CreatureModel;
import gamemechanics.models.Fight;
import gamemechanics.models.SpellModel;
import gamemechanics.models.entities.Entity.EntityRef;

/**
 * Class models for creatures, spells, and perhaps statuses
 * 
 * @author Blank
 * @date 9 mai 2020
 */
public class DiamondModels {
	

	public static final Map<Integer, CreatureModel> creatures = new HashMap<>();
	public static final Map<Integer, SpellModel> spells = new HashMap<>();
	public static final Map<Integer, Status> statuses = new HashMap<>();

	/**
	 * @param packag : "com.souchy.randd.data.s1" depends on the season
	 */
	public static void instantiate(String packag) {
		var creaturelist = new DefaultClassDiscoverer<CreatureModel>(CreatureModel.class).explore(packag);
		Log.info("Diamond creatures models " + creaturelist);
		creaturelist.forEach(c -> {
			try {
				var model = c.getDeclaredConstructor().newInstance();
				creatures.put(model.id(), model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		var spelllist = new DefaultClassDiscoverer<SpellModel>(SpellModel.class).explore(packag);
		Log.info("Diamond spell models " + spelllist);
		spelllist.forEach(c -> {
			try {
				var model = c.getDeclaredConstructor().newInstance();
				spells.put(model.id(), model);
				//Log.info("Diamond spell model [" + model.id() + "] = " + model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		var statuslist = new DefaultClassDiscoverer<Status>(Status.class).explore(packag);
		Log.info("Diamond status models " + statuslist);
		statuslist.forEach(c -> {
			try {
				var model = c.getDeclaredConstructor(Fight.class, EntityRef.class, EntityRef.class).newInstance(null, null, null);
				statuses.put(model.modelID(), model);
				//Log.info("Diamond status model [" + model.id() + "] = " + model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}
