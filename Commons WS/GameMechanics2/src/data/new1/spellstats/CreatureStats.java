package data.new1.spellstats;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.tealwaters.logging.Log;

import data.new1.spellstats.base.BoolStat;
import data.new1.spellstats.base.IntStat;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.Resource;

public class CreatureStats {
	
	/** 
	 * resources 
	 */
	public Map<Resource, IntStat> resources;
	/** 
	 * shields for each resources 
	 */
	public Map<Resource, IntStat> shield;
	/** 
	 * how spells scale 
	 * sourcedmg = (baseflat + casterflat) * (1 + baseinc * casterinc / 100) * (1 + basemore * castermore / 100)
	 */
	public Map<Element, IntStat> affinity;
	/** 
	 * scales against damage 
	 * finaldmg = sourcedmg * (1 + (casterPenMore-targetResMore) / 100) * (1 + (casterPenInc-targetResInc) / 100 ) + (casterPenFlat-targetResFlat)
	 */
	public Map<Element, IntStat> resistance; 
	
	/** 
	 * penetration counters resists 
	 * finaldmg = sourcedmg * (1 + (casterPenMore-targetResMore) / 100) * (1 + (casterPenInc-targetResInc) / 100 ) + (casterPenFlat-targetResFlat)
	 */
	public Map<Element, IntStat> penetration; 
	
	/** 
	 * this adds and multiplies to healing spells casts 
	 * sourceheal =  (baseflat + casterflat) * (1 + baseinc * casterinc / 100) * (1 + basemore * castermore / 100)
	 */
	public IntStat healingAffinity;
	
	/** 
	 * this adds and multiplies to healing spells received 
	 * finalheal = sourceheal * (1 - targetResMore / 100) * (1 - targetResInc / 100 ) - targetResFlat
	 */
	public IntStat healingRes;
	
	/** 
	 * range increase value 
	 */
	public IntStat range;
	
	/** 
	 * max number of summons
	 */
	public IntStat summons;
	/**
	 * if creature is visible/invisible
	 */
	public BoolStat visible;
	
	
	public CreatureStats() {
		resources = new HashMap<>();
		shield = new HashMap<>();
		affinity = new HashMap<>();
		resistance = new HashMap<>();
		penetration = new HashMap<>();
		
		healingAffinity = new IntStat(0);
		healingRes = new IntStat(0);
		range = new IntStat(0);
		summons = new IntStat(0);
		visible = new BoolStat(true);
		// + peut-être une stat pour aoeRadiusModificator / AoeRange
		
		for(var v : Resource.values()) {
			resources.put(v, new IntStat(0));
			shield.put(v, new IntStat(0));
		}
		for(var ele : Element.values) {
			//Log.info("creature stat element : " + ele);
			affinity.put(ele, new IntStat(0));
			resistance.put(ele, new IntStat(0));
			penetration.put(ele, new IntStat(0));
		}
	}
	
	public CreatureStats copy() {
		final var s = new CreatureStats();
		
		resources.forEach((r, i) -> s.resources.put(r, i.copy()));
		shield.forEach((r, i) -> s.shield.put(r, i.copy()));
		
		affinity.forEach((r, i) -> s.affinity.put(r, i.copy()));
		resistance.forEach((r, i) -> s.resistance.put(r, i.copy()));
		penetration.forEach((r, i) -> s.penetration.put(r, i.copy()));
		
		s.healingAffinity = healingAffinity.copy();
		s.healingRes = healingRes.copy();
		s.range = range.copy();
		s.summons = summons.copy();
		s.visible = visible.copy();
		
		return s;
	}
	
	
	public int getCurrent(Resource res) {
		return resources.get(res).value();
	}
	public int getMax(Resource res) {
		return resources.get(res).value();
	}
	public int getMissing(Resource res) {
		return (int) resources.get(res).fight;
	}

//	public Predicate<Creature> predicate = (c) -> true;
//	public CreatureStats() {
//		
//	}
//	public CreatureStats(Predicate<Creature> predicate) {
//		this.predicate = predicate;
//	}
	
}
