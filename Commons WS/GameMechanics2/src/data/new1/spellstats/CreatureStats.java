package data.new1.spellstats;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import data.new1.SpellModel;
import data.new1.spellstats.base.IntStat;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.Resource;

public class CreatureStats {
	
	/** resources */
	public Map<Resource, IntStat> resources;
	
	/** shields for each resources */
	public Map<Resource, IntStat> shield;
	
	/** how spells scale */
	public Map<Element, IntStat> affinity;
	
	/** how damage scales */
	public Map<Element, IntStat> resistance; 
	
	/** counter to resists */
	public Map<Element, IntStat> penetration; 
	
	/** this adds and multiplies to healing spells casts */
	public IntStat healing;
	
	/** this adds and multiplies to healing spells received */
	public IntStat healingRecv;
	
	/** range increase value */
	public IntStat range;
	
	/** max number of summons */
	public IntStat summons;
	
	public CreatureStats() {
		resources = new HashMap<>();
		shield = new HashMap<>();
		affinity = new HashMap<>();
		resistance = new HashMap<>();
		penetration = new HashMap<>();
		healing = new IntStat(0);
		healingRecv = new IntStat(0);
		range = new IntStat(0);
		summons = new IntStat(0);
		// peut-être une stat pour aoeRadiusModificator / AoeRange
		
		for(var v : Resource.values()) {
			resources.put(v, new IntStat(0));
			shield.put(v, new IntStat(0));
		}
	}
	
	public CreatureStats copy() {
		final var s = new CreatureStats();
		
		resources.forEach((r, i) -> s.resources.put(r, i.copy()));
		shield.forEach((r, i) -> s.shield.put(r, i.copy()));
		
		affinity.forEach((r, i) -> s.affinity.put(r, i.copy()));
		resistance.forEach((r, i) -> s.resistance.put(r, i.copy()));
		penetration.forEach((r, i) -> s.penetration.put(r, i.copy()));
		
		s.healing = healing.copy();
		s.healingRecv = healingRecv.copy();
		s.range = range.copy();
		s.summons = summons.copy();
		
		return s;
	}

//	public Predicate<Creature> predicate = (c) -> true;
//	public CreatureStats() {
//		
//	}
//	public CreatureStats(Predicate<Creature> predicate) {
//		this.predicate = predicate;
//	}
	
}
