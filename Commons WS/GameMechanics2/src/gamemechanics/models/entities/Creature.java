package gamemechanics.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.jade.meta.JadeCreature;

import data.modules.AzurCache;
import data.new1.CreatureModel;
import data.new1.SpellCache;
import data.new1.SpellModel;
import data.new1.spellstats.CreatureStats;
import gamemechanics.common.generic.Vector2;
import gamemechanics.models.Fight;
import gamemechanics.statics.Element;

public class Creature extends Entity {

	/**
	 * Creature basic data : spell data it can learn, creature types, creature colors, base stats, base properties<br>
	 * 
	 * The Ebi version also contains : textures, i18n, default model file
	 */
	public CreatureModel model;
	
	/**
	 * compiled stats (includes model, jade and statuses stats)
	 */
	private CreatureStats stats; 
	/**
	 * spell book containing spells //~~spell groups containing spell~~
	 */
	public List<SpellModel> spellbook;
	

	public Creature(CreatureModel model, JadeCreature jade, AzurCache dep, Vector2 pos) {
		this.model = model;
		this.spellbook = new ArrayList<>();
		this.pos = pos;

		// copy model stats into instance stats
		this.stats = model.baseStats.copy(); 
		// then add jade stats
		for(int i = 0; i < Element.count(); i++) {
			this.stats.affinity.get(Element.values.get(i)).inc += jade.affinities[i];
		}
		
		// chosen spells
		for(int i : jade.spellIDs) {
			var s = dep.spells.get(i);
			if(s != null) spellbook.add(s);
		}

	}
	
	/**
	 * Same as getTempStats() except this one compiles them before returning
	 */
	@Override
	public CreatureStats getStats() {
		return stats;
	}
	
}