package com.souchy.randd.situationtest.models.items;

import com.google.inject.Inject;
import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.properties.ElementBundle;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.StatProperty;
import com.souchy.randd.situationtest.properties.Stats;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.properties.types.ItemType;
import com.souchy.randd.situationtest.properties.types.StatProperties;
import com.souchy.randd.situationtest.models.entities.*;
import com.souchy.randd.situationtest.models.entities.Character;

/**
 * 
 * TODO : items
 * 
 * @author Souchy
 *
 */
public class Item {
	
	public final ItemType type;
	public final Stats stats;
	
	
	/*
	 * 
	 * Like Items, Status and Characters could have an object Stass 
	 * Items having their own bonuses
	 * Statuses having their own bonuses
	 * Characters having their *base* stats
	 * 
	 * As well as having a CompoundStats that adds everything else
	 * 
	 */
	
	/*public static class Statss {
		*//** Propriétés de base (resources (hp, mana, pm, shields) *//*
	    @Inject
		private Cache<StatProperties, StatProperty> properties;

		*//** Affinités aux éléments (dmg et res, scl et flat) *//*
	    @Inject
		private Cache<Elements, ElementBundle> elementAffinities;
	    public StatProperty get(StatProperties stat) {
	    	return properties.get(stat);
	    }
	}*/
	public static class CompoundStats {
		private final Character e;
		public CompoundStats(Character character) {
			this.e = character;
		}
		public StatProperty get(StatProperties stat) {
			StatProperty val = e.baseStats.get(stat).copy(); 			  // base
			e.items.forEach(i -> val.value += i.stats.get(stat).value);   // items
			e.statuss.forEach(i -> val.value += i.stats.get(stat).value); // status
			return val;
		}
		public ElementValue scl(Elements ele) {
			ElementValue val = e.baseStats.scl(ele).copy();       // base
			e.items.forEach(i -> val.addSet(i.stats.scl(ele)));   // items
			e.statuss.forEach(i -> val.addSet(i.stats.scl(ele))); // status
			return val;
		}
		public ElementValue flat(Elements ele) {
			ElementValue val = e.baseStats.flat(ele).copy();       // base
			e.items.forEach(i -> val.addSet(i.stats.flat(ele)));   // items
			e.statuss.forEach(i -> val.addSet(i.stats.flat(ele))); // status
			return val;
		}
		public ElementValue resScl(Elements ele) {
			ElementValue val = e.baseStats.resScl(ele).copy();       // base
			e.items.forEach(i -> val.addSet(i.stats.resScl(ele)));   // items
			e.statuss.forEach(i -> val.addSet(i.stats.resScl(ele))); // status
			return val;
		}
		public ElementValue resFlat(Elements ele) {
			ElementValue val = e.baseStats.resFlat(ele).copy();       // base
			e.items.forEach(i -> val.addSet(i.stats.resFlat(ele)));   // items
			e.statuss.forEach(i -> val.addSet(i.stats.resFlat(ele))); // status
			return val;
		}
	}
	
}

