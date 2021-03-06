package com.souchy.randd.situationtest.models.items;

import java.util.function.Consumer;

import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.org.ContextualObject;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.properties.ElementBundle;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.StatProperty;
import com.souchy.randd.situationtest.properties.Stats;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.properties.types.ItemType;
import com.souchy.randd.situationtest.properties.types.StatProperties;

/**
 * 
 * TODO : items
 * 
 * @author Souchy
 *
 */
public class Item extends ContextualObject {
	
	public final ItemType type;
	public final Stats stats;
	
	public Item(FightContext context, ItemType type, Stats stats) {
		super(context);
	}
	
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
		*//** Propri�t�s de base (resources (hp, mana, pm, shields) *//*
	    @Inject
		private Cache<StatProperties, StatProperty> properties;

		*//** Affinit�s aux �l�ments (dmg et res, scl et flat) *//*
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
		
		public ElementBundle get(Elements ele) {
			ElementBundle bundle = new ElementBundle();
			
			Consumer<ElementBundle> compoundStats = b -> {
				bundle.baseScl.addSet(b.baseScl);
				bundle.scl.addSet(b.scl);
				bundle.flat.addSet(b.flat);
				bundle.moreScl.addSet(b.moreScl);
				for(var e : Elements.values()) {
					bundle.convRates.get(e.ordinal()).addSet(b.convRates.get(e.ordinal()));
				}
				for(var e : Elements.values()) {
					bundle.extraAs.get(e.ordinal()).addSet(b.extraAs.get(e.ordinal()));
				}
				for(var e : Elements.values()) {
					bundle.convOutput.get(e.ordinal()).addSet(b.convOutput.get(e.ordinal()));
				}
				bundle.resFlat.addSet(b.resFlat);
				bundle.resScl.addSet(b.resScl);
				bundle.resMore.addSet(b.resMore);

				bundle.penFlat.addSet(b.penFlat);
				bundle.penScl.addSet(b.penScl);
			};
			
			compoundStats.accept(e.baseStats.get(ele)); // base stats
			e.items.forEach(i -> compoundStats.accept(i.stats.get(ele))); // items
			e.statuss.forEach(i -> compoundStats.accept(i.stats.get(ele))); // items
			
			return bundle;
		}
	}
	
}

