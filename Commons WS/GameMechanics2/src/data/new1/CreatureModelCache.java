package data.new1;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.souchy.randd.jade.combat.JadeCreature;

import data.CreatureData;
import gamemechanics.models.entities.Creature;

public class CreatureModelCache {
	
	public static Map<Integer, CreatureModel> creatureModels = new HashMap<>();

	/**
	 * 
	 * This method combines a creature model with creature data to create an
	 * instance
	 * 
	 * Black moonstone would send the data for a JadeUserCreature (should rename to
	 * something like CreaturePattern or CreatureData or JadeCreature) it would send
	 * either default settings for a pre-constructed data set (ex: summoned
	 * creatures) or send a user-defined data set (ex:
	 * 
	 */
	public static Creature createInstance(int id, JadeCreature pattern) {
		if(!creatureModels.containsKey(id)) return null;
		var model = creatureModels.get(id);
		var inst = model.createCreature(pattern);
		
		// could also do like gdx : new Creature(model);
		return inst;
	}
	
	/**
	 * TODO load resources for a creature model idk if we put everything in a global
	 * asset manager or it all goes in the creature skin (cause theres also particle
	 * effects and obj models which i dont think can go inside the skin)
	 */
	public static void loadResources(CreatureModel c) {
		c.spells.forEach(s -> {
			// 0 pour type vu que le sort est lié à la créature
			// actually on peut pas savoir à ce point de la séquence si le sort est
			// spécifique à la créature ou à un type
			// on devrait donc prob utiliser juste l'id de sort sans l'id de creaturetype ou
			// de creature
			var fullid = String.format("%04d%04d%04d", 0, c.id(), s.id());
			var path = "";
			var tex = new Texture(Gdx.files.local(path));
			c.skin.add("Spell" + c.id(), tex);
			
			// s . get Icon
			// icon path = creaturetypes/vampire/1.png
			// ou = creatures/sungjin/1.png
			// + need a name to put it in the skin or assetmanager
			// ex s.getIconName ou getIconPath
			// maybe have s.getOwnerId() -> return #### 0000 0000 ou 0000 #### 0000
			// puis si le ownerid est >= 0001 0000 0000, ça veut dire que ça vient d'un type
			//
		});
	}
	
}
