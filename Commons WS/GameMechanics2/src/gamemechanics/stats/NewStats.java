package gamemechanics.stats;

import com.google.common.collect.Table;

import data.new1.Effect;
import gamemechanics.data.effects.other.StatEffect;
import gamemechanics.models.entities.Creature;

import static gamemechanics.stats.StatProperty.*;
import static gamemechanics.stats.StatProperty.resource.*;
import static gamemechanics.stats.StatProperty.element.*;
import static gamemechanics.stats.StatProperty.property.*;
import static gamemechanics.stats.StatProperty.spellProperty.*;
import static gamemechanics.stats.StatProperty.targetingProperty.*;

import java.util.ArrayList;
import java.util.List;

import static gamemechanics.stats.Modifier.*;
import static gamemechanics.stats.Modifier.resourceMod.*;
import static gamemechanics.stats.Modifier.eleMod.*;
import static gamemechanics.stats.Modifier.mathMod.*;

public class NewStats {

	private List<StatModConverter> tempConverters = new ArrayList<>();
	
	public void compile(Creature target) {
		// wipe everything except fight mods (used/lost resources)
		this.wipe();

		// commence par prendre tous les boosts des items et status
		for(var i : target.items)
			for(var e : i.effects)
				if(e instanceof StatEffect) e.apply(target, target);
		
		target.getStatus().forEach(s -> {
			for(var e : s.effects)
				if(e instanceof StatEffect) e.apply(s.source, target);
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
	
	/**
	 * - Property
	 * - compiled mod bits (tot = mod1 | mod2 | mod3...), each mod is 1 bit in the int
	 * - value
	 */
	private Table<StatProperty, Integer, Double> table;

	private void set(double value, StatProperty p, Modifier... mods) {
		table.put(p, Modifier.compile(mods), value);
	}
	private Double get(StatProperty p, Modifier... mods) {
		return table.get(p, Modifier.compile(mods));
	}
	public void add(double value, StatProperty prop,  Modifier... mods) {
		int tot = Modifier.compile(mods);
		add(value, prop, tot);
	}
	
	public void add(double value, StatProperty prop, int mods) {
		var value0 = table.get(prop,  mods);
		if(value0 == null) value0 = Double.valueOf(0);
		table.put(prop, mods, value0 + value);
	}
	
	/**
	 * Currently used/lost resource (ex 3000/5000 hp, this returns 2000)
	 * The sum of stats give the max value of the resource while the fight mod gives the difference to the max value
	 * @param r what resource
	 * @param shield if we want the value of the resource's shield instead
	 */
	public int getResourceFightMod(resource r, boolean shield) {
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
	public int getResourceCurrent(resource r, boolean shield) {
		return getResourceMax(r) + getResourceFightMod(r, shield);
		//return table.get(r.val(), 1).intValue();
	}
	/**
	 * (ex 3000/5000 hp, this returns 5000)
	 * The sum of stats give the max value of the resource while the fight mod gives the difference to the max value
	 * shield have no max value
	 */
	public int getResourceMax(resource r) {
		var a = get(r, flat) * get(r, scl) * get(r, more);
		return (int) Math.round(a);
	}
	
	/**
	 * Gets the total of an Element + GlobalEle on the chosen mods
	 */
	public double getEle(element e, Modifier... mods) {
		double a = get(element.globalEle, mods) + get(e, mods);
		return (int) Math.round(a);
	}
	
	
	public double getCostMods(resource r, mathMod m) {
		return get(r, resourceMod.cost, m);
	}
	/** action cost for each resource genre. array must include every resources. unsused resources have cost of 0. */
	public void setCosts(int[] costs) {
		for(int i = 0; i < resource.values().length; i++) 
			set(costs[i], resource.values()[i], cost, flat);
	}
	

	/**
	 * Set spell properties with normal range patterns
	 */
	public void setSpellProperies(boolean isInstant, int cooldown, int maxCastsPerTurn, int maxCastsPerTurnPerTarget, int minRange, int maxRange) {
		setSpellProperies(isInstant, cooldown, maxCastsPerTurn, maxCastsPerTurnPerTarget, RangePattern.normal.val(), RangePattern.normal.val());
	}
	/**
	 * @param minRangePattern - made from rangePattern enum (ex : pattern = line | diago | square)
	 * @param maxRangePattern - made from rangePattern enum (ex : pattern = line | diago | square)
	 */
	public void setSpellProperies(boolean isInstant, int cooldown, int maxCastsPerTurn, int maxCastsPerTurnPerTarget, int minRange, int maxRange, int minRangePattern, int maxRangePattern) {
		set(isInstant ? 1 : 0, spellProperty.isInstant, bool);
		set(minRangePattern, spellProperty.minRangePattern, flat);
		set(maxRangePattern, spellProperty.maxRangePattern, flat);
		
		set(cooldown, spellProperty.cooldown, flat);
		set(maxCastsPerTurn, spellProperty.maxCastsPerTurn, flat);
		set(maxCastsPerTurnPerTarget, spellProperty.maxCastsPerTurnPerTarget, flat);
		set(minRange, spellProperty.minRange, flat);
		set(maxRange, spellProperty.maxRange, flat);
	}

	
}
