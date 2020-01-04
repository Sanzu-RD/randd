package gamemechanics.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.jade.meta.JadeCreature;

import data.modules.AzurCache;
import data.new1.CreatureModel;
import data.new1.SpellCache;
import data.new1.SpellModel;
import gamemechanics.common.Vector2;
import gamemechanics.models.Fight;
//import gamemechanics.models.Item;
import gamemechanics.statics.stats.Stats;

public class Creature extends Entity {

	/**
	 * Creature basic data : spell data it can learn, creature types, creature colors, base stats, base properties<br>
	 * 
	 * The Ebi version also contains : textures, i18n, default model file
	 */
	public CreatureModel model;
	
	/**
	 * temporary calculated stats
	 */
	private Stats stats; 
	/**
	 * items
	 */
	//public List<Item> items;
	/**
	 * spell book containing spells //~~spell groups containing spell~~
	 */
	public List<SpellModel> spellbook;
	

	public Creature(CreatureModel model, JadeCreature jade, AzurCache dep, Vector2 pos) {
		this.model = model;
		this.stats = new Stats();
		this.spellbook = new ArrayList<>();
		//this.items = new ArrayList<>();
		//this.fight = fight;
		this.pos = pos;
		
		for(int si : jade.spellIDs) {
			var s = dep.spells.get(si);
			if(s != null) spellbook.add(s);
//			for(var sm : model.spells) {
//				if(sm.id() == si) {
//					spellbook.add(sm); //new Spell(sm));
//				}
//			}
		}
//		for(int ii : jade.itemIDs) {
//			var i = dep.items.get(ii);
//			if(i != null) items.add(i);
//		}
	}
	
	/**
	 * Same as getStats() except this one doesn't compile before
	 */
	public Stats getTempStats() {
		return stats;
	}
	/**
	 * Same as getTempStats() except this one compiles them before returning
	 */
	@Override
	public Stats getStats() {
		stats.compile(this);
		return stats;
	}
	
}