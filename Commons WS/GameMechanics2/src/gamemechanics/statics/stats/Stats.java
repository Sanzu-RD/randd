package gamemechanics.statics.stats;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import gamemechanics.data.effects.other.StatEffect;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.stats.modifiers.Modifier;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.modifiers.resourceMod;
import gamemechanics.statics.stats.properties.Color;
import gamemechanics.statics.stats.properties.Resource;
import gamemechanics.statics.stats.properties.SpellProperty;
import gamemechanics.statics.stats.properties.StatProperty;

import static gamemechanics.statics.stats.modifiers.mathMod.*;
import static gamemechanics.statics.stats.modifiers.resourceMod.*;

import java.util.ArrayList;
import java.util.List;

public class Stats {

	/**
	 * - Property
	 * - compiled mod bits (tot = mod1 | mod2 | mod3...), each mod is 1 bit in the int
	 * - value
	 */
	private Table<StatProperty, Integer, Double> table = HashBasedTable.create();
	
	private List<StatModConverter> tempConverters = new ArrayList<>();
	
	
	public void compile(Creature target) {
		// wipe everything except fight mods (used/lost resources)
		this.wipe();

		// commence par prendre tous les boosts des items et status
		for(var i : target.items)
			i.stats.table.cellSet().forEach(c -> 
				add(c.getValue(), c.getRowKey(), c.getColumnKey())
			);
		
		target.getStatus().forEach(s -> {
//			for(var e : s.effects)
//				if(e instanceof StatEffect) e.apply(s.source, target, e);
			s.stats.forEach(sm -> add(sm.value, sm.prop, sm.mods));
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
	
	/**
	 * Currently used/lost resource (ex 3000/5000 hp, this returns 2000)
	 * The sum of stats give the max value of the resource while the fight mod gives the difference to the max value
	 * @param r what resource
	 * @param shield if we want the value of the resource's shield instead
	 */
	public int getResourceFightMod(Resource r, boolean shield) {
		if(shield) 
			return get(r, fight, resourceMod.shield).intValue();
		else
			return get(r, fight).intValue();
	}
	/**
	 * Current value of the target resource (ex 3000/5000 hp, this returns 3000)
	 * @param r what resource
	 * @param shield if we want the value of the resource's shield instead
	 */
	public int getResourceCurrent(Resource r, boolean shield) {
		return getResourceMax(r) + getResourceFightMod(r, shield);
		//return table.get(r.val(), 1).intValue();
	}
	/**
	 * (ex 3000/5000 hp, this returns 5000)
	 * The sum of stats give the max value of the resource while the fight mod gives the difference to the max value
	 * shield have no max value
	 */
	public int getResourceMax(Resource r) {
		var a = get(r, flat) * get(r, scl) * get(r, more);
		return (int) Math.round(a);
	}
	
	/**
	 * Gets the total of an Element + GlobalEle on the chosen mods
	 */
	public double getEle(Color e, Modifier... mods) {
		double a = get(Color.globalEle, mods) + get(e, mods);
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
	 * @param minRangePattern - made from rangePattern enum (ex : pattern = line | diago | square)
	 * @param maxRangePattern - made from rangePattern enum (ex : pattern = line | diago | square)
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
