package gamemechanics.models.entities;

import java.util.List;

import com.souchy.randd.jade.combat.JadeCreature;

import data.new1.CreatureModel;
import data.new1.ItemCache;
import data.new1.SpellCache;
import data.new1.SpellModel;
import gamemechanics.models.Item;
import gamemechanics.stats.NewStats;
import gamemechanics.stats.StatTable;

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
	private NewStats stats; 
	/**
	 * items
	 */
	public List<Item> items;
	/**
	 * spell book containing spells //~~spell groups containing spell~~
	 */
	public List<SpellModel> spellbook;
	

	public Creature(CreatureModel model, JadeCreature jade) {
		for(int si : jade.spellIDs) {
			spellbook.add(SpellCache.get(si));
//			for(var sm : model.spells) {
//				if(sm.id() == si) {
//					spellbook.add(sm); //new Spell(sm));
//				}
//			}
		}
		for(int ii : jade.itemIDs) {
			items.add(ItemCache.get(ii));
		}
	}
	
	/**
	 * Same as getStats() except this one doesn't compile before
	 */
	public NewStats getTempStats() {
		return stats;
	}
	/**
	 * Same as getTempStats() except this one compiles them before returning
	 */
	@Override
	public NewStats getStats() {
		stats.compile(this);
		return stats;
	}
	
}