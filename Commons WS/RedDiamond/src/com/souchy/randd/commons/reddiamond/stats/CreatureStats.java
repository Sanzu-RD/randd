package com.souchy.randd.commons.reddiamond.stats;

import java.util.Map;

import com.souchy.randd.commons.reddiamond.enums.Element;
import com.souchy.randd.commons.reddiamond.enums.Resource;

/**
 * Each creature has creature stats
 * 
 * Statuses can also have creature stats to suplement those
 * 
 * @author Blank
 * @date 7 nov. 2021
 */
public class CreatureStats {
	
	/*
	 * resource : {}
	 * resourcemax : {}
	 * shield : {}
	 * 
	 * affinity : {}
	 * resistance : {}
	 * penetration : {}
	 * 
	 * healing : {}  // could just be included in the affinities with 'global' and 'elements'
	 * healingResistance : {}
	 * 
	 * 
	 * range : {}
	 * summons : {}
	 * 
	 * visibility : {} // visible or invisible
	 * 
	 * height : {} // flying, underground or normal
	 * 
	 */
	

	/** 
	 * resources 
	 * 
	 * 
	 * refactoring here, this should just be a current value im pretty sure
	 * either current value or lost value
	 */
	private Map<Resource, Integer> resources; // IntStat 
	private Map<Resource, IntStat> resourcesMax;


	/** 
	 * shields for each resources. 
	 * 
	 * Gains are made to baseflat and damage is made to fight. This way, we can have buffs that give a multiplier to shield gained
	 * ^ this is stupid, we should use modifier statuses instead. Ex: effect gain 100 shield, status modifier multiplies it by 2 and adds 10 every time. then it does to a plain int.
	 */
	private Map<Resource, Integer> shield; // IntStat
	/** 
	 * how spells scale 
	 * sourcedmg = (baseflat + casterflat) * (1 + baseinc * casterinc / 100) * (1 + basemore * castermore / 100)
	 */
	private Map<Element, IntStat> affinity;
	/** 
	 * scales against damage 
	 * finaldmg = sourcedmg * (1 + (casterPenMore-targetResMore) / 100) * (1 + (casterPenInc-targetResInc) / 100 ) + (casterPenFlat-targetResFlat)
	 */
	private Map<Element, IntStat> resistance; 
	
	/** 
	 * penetration counters resists 
	 * finaldmg = sourcedmg * (1 + (casterPenMore-targetResMore) / 100) * (1 + (casterPenInc-targetResInc) / 100 ) + (casterPenFlat-targetResFlat)
	 */
	private Map<Element, IntStat> penetration;

	
	
	
	public int getResource(Resource key) {
		return resources.get(key);
	}
	public int setResource(Resource key, int value) {
		return resources.put(key, value);
	}
	public IntStat getResourceMax(Resource key) {
		return resourcesMax.get(key);
	}
	public IntStat setResourceMax(Resource key, IntStat value) {
		return resourcesMax.put(key, value);
	} 
	public int getShield(Resource key) {
		return shield.get(key);
	}
	public int setShield(Resource key, int value) {
		return shield.put(key, value);
	} 
	
	
	public IntStat getAffinity(Element key) {
		return affinity.get(key);
	}
	public IntStat setAffinity(Element key, IntStat value) {
		return affinity.put(key, value);
	} 
	public IntStat getResistance(Element key) {
		return resistance.get(key);
	}
	public IntStat setResistance(Element key, IntStat value) {
		return resistance.put(key, value);
	}
	public IntStat getPenetration(Element key) {
		return penetration.get(key);
	}
	public IntStat setPenetration(Element key, IntStat value) {
		return penetration.put(key, value);
	} 
	
}
