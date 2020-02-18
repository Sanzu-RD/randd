package gamemechanics.statics.stats;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import data.new1.SpellModel;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.modifiers.Modifier;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.modifiers.resourceMod;
import gamemechanics.statics.stats.properties.Resource;
import gamemechanics.statics.stats.properties.StatProperty;
import gamemechanics.statics.stats.properties.spells.SpellProperty;

import static gamemechanics.statics.stats.modifiers.mathMod.*;
import static gamemechanics.statics.stats.modifiers.resourceMod.*;
import static gamemechanics.statics.stats.modifiers.eleMod.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Stats {

	/**
	 * - Property
	 * - compiled mod bits (tot = mod1 | mod2 | mod3...), each mod is 1 bit in the int
	 * - value
	 */
	private Table<StatProperty, Integer, Double> table = HashBasedTable.create();
	
	private List<StatModConverter> tempConverters = new ArrayList<>();
	
//	private BiPredicate<Creature, SpellModel> condition;
	
//	public Stats() {
//		
//	}
//	public Stats(BiPredicate<Creature, SpellModel> condition) {
//		this.condition = condition;
//	}
//	
//	public boolean canApply(Creature creature, SpellModel spell) {
//		if(condition == null) return true;
//		
//		return false;
//	}
	
	
	
 	public void compile(Creature target) {
		// wipe everything except fight mods (used/lost resources)
		this.wipe();

		// commence par prendre tous les boosts des items et status
		//for(var i : target.items)
		//	i.stats.table.cellSet().forEach(c -> 
		//		add(c.getValue(), c.getRowKey(), c.getColumnKey())
		//	);
		
		target.getStatus().forEach(s -> {
//			for(var e : s.effects)
//				if(e instanceof StatEffect) e.apply(s.source, target, e);
			//s.buffs.forEach(sm -> add(sm.value, sm.prop, sm.mods));
			s.buffs.table.cellSet().forEach(cell -> add(cell.getValue(), cell.getRowKey(), cell.getColumnKey()));
		});

		// applique les conversions et nullifiers de stats
		tempConverters.forEach(c -> {
			double v1 = table.get(c.prop, c.mods);
			double rest = v1 % c.value;
			if(!c.extra) {
				table.put(c.prop, c.mods, rest);
			}
			double toconvert = v1 - rest;
			double valueConverted = toconvert / c.value * c.value2;
			add(valueConverted, c.prop2, c.mods2);
		});
		
		// applique les bases du personnage
		target.model.baseStats.table.cellSet().forEach(c -> 
			add(c.getValue(), c.getRowKey(), c.getColumnKey())
		);
	}
	
	public void addConverter(StatModConverter m) {
		tempConverters.add(m);
	}
	
	
	public void wipe() {
		// clear all temporary converters
		tempConverters.clear();
		// clear all stats that DONT have the fight mod (= used/lost resources)
		table.cellSet().forEach(c -> {
			if((c.getColumnKey() & resourceMod.fight.val()) == 0)
				table.put(c.getRowKey(), c.getColumnKey(), 0d);
		});
	}
	

	private void set(double value, StatProperty p, Modifier... mods) {
		table.put(p, Modifier.compile(mods), value);
	}
	private Double get(StatProperty p, Modifier... mods) {
		var tot = Modifier.compile(mods);
		return get(p, tot);
	}
	private Double get(StatProperty p, int mods) {
		var val = table.get(p, mods);
		if(val == null) return 0d;
		return val;
	}
	public void add(double value, StatProperty prop,  Modifier... mods) {
		int tot = Modifier.compile(mods);
		add(value, prop, tot);
	}
	
	public void add(double value, StatProperty prop, int mods) {
		var value0 = get(prop, mods);
		table.put(prop, mods, value0 + value);
	}
	

	/** easier shortcut */
	public void addResource(double value, Resource r) {
		add(value, r, flat);
	}
	/** easier shortcut */
	public void addAffinity(double value, Element e) {
		add(value, e, affinity, scl);
	}
	/** easier shortcut */
	public void addResistance(double value, Element e) {
		add(value, e, res, scl);
	}
	/** easier shortcut */
	public void addFightResource(double value, Resource r) {
		add(value, r, fight, flat);
	}
	/** easier shortcut */
	public void addShield(double value, Resource r) {
		add(value, r, fight, flat, resourceMod.shield);
	}
	/** easier shortcut */
//	public void addFightResource(double value, Resource r, boolean shield) {
//		if(shield) add(value, r, fight, flat, resourceMod.shield);
//		else addFightResource(value, r);
//	}
	
	
	
	
	/**
	 * Currently used/lost/gained resource (ex 3000/5000 hp, this returns -2000 meanwhile shield is supposed to be a positive value)
	 * For shield this equals to getResourceCurrent
	 * This is always flat mods
	 * The sum of stats give the max value of the resource while the fight mod gives the difference to the max value
	 * @param r what resource
	 * @param shield if we want the value of the resource's shield instead
	 */
	public int getResourceFight(Resource r, boolean shield) {
		if(shield) 
			return get(r, fight, flat, resourceMod.shield).intValue();
		else
			return get(r, fight, flat).intValue();
	}
	/**
	 * Current value of the target resource (ex 3000/5000 hp, this returns 3000)
	 * For shields this equals to getResourceFight
	 * @param r what resource
	 * @param shield if we want the value of the resource's shield instead
	 */
	public int getResourceCurrent(Resource r, boolean shield) {
		if(shield)
			return getResourceFight(r, true);
		else
			return getResourceMax(r) + getResourceFight(r, false);
		//return table.get(r.val(), 1).intValue();
	}
	/**
	 * (ex 3000/5000 hp, this returns 5000)
	 * The sum of stats give the max value of the resource while the fight mod gives the difference to the max value
	 * shield have no max value
	 */
	public int getResourceMax(Resource r) {
		var a = get(r, flat) * (1d + get(r, scl) / 100d) * (1d + get(r, more) / 100d);
		return (int) Math.round(a);
	}
	
	/**
	 * Gets the total of an Element + GlobalEle on the chosen mods
	 */
	public double getEle(Element e, Modifier... mods) {
		double a = get(Element.global, mods) + get(e, mods);
		return (int) Math.round(a);
	}
	
	
	public double getCostMods(Resource r, mathMod m) {
		return get(r, resourceMod.cost, m);
	}
	/** action cost for each resource genre. array must include every resources. unsused resources have cost of 0. */
	public void setCosts(int[] costs) {
		for(int i = 0; i < Resource.values().length; i++) 
			set(costs[i], Resource.values()[i], cost, flat);
	}
	

	/**
	 * Set spell properties with normal range patterns
	 */
	public void setSpellProperies(boolean isInstant, int cooldown, int maxCastsPerTurn, int maxCastsPerTurnPerTarget, int minRange, int maxRange) {
		setSpellProperies(isInstant, cooldown, maxCastsPerTurn, maxCastsPerTurnPerTarget, minRange, maxRange, RangePattern.normal.val(), RangePattern.normal.val());
	}
	/**
	 * @param minRangePattern - bits made from rangePattern enum (ex : pattern = line | diago | square)
	 * @param maxRangePattern - bits made from rangePattern enum (ex : pattern = line | diago | square)
	 */
	public void setSpellProperies(boolean isInstant, int cooldown, int maxCastsPerTurn, int maxCastsPerTurnPerTarget, int minRange, int maxRange, int minRangePattern, int maxRangePattern) {
		set(isInstant ? 1 : 0, SpellProperty.isInstant, bool);
		set(minRangePattern, SpellProperty.minRangePattern, flat);
		set(maxRangePattern, SpellProperty.maxRangePattern, flat);
		
		set(cooldown, SpellProperty.cooldown, flat);
		set(maxCastsPerTurn, SpellProperty.maxCastsPerTurn, flat);
		set(maxCastsPerTurnPerTarget, SpellProperty.maxCastsPerTurnPerTarget, flat);
		set(minRange, SpellProperty.minRange, flat);
		set(maxRange, SpellProperty.maxRange, flat);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
