package gamemechanics.stats;

import static gamemechanics.stats.StatModifier.mo.fight;
import static gamemechanics.stats.StatModifier.mo.flat;
import static gamemechanics.stats.StatModifier.mo.more;
import static gamemechanics.stats.StatModifier.mo.scl;
import static gamemechanics.stats.StatModifier.st.globalDmgEle;
import static gamemechanics.stats.StatModifier.st.globalDmgPen;
import static gamemechanics.stats.StatModifier.st.globalDmgRes;
import static gamemechanics.stats.StatModifier.st.globalEle;
import static gamemechanics.stats.StatModifier.st.globalHealEle;
import static gamemechanics.stats.StatModifier.st.globalHealPen;
import static gamemechanics.stats.StatModifier.st.globalHealRes;
import static gamemechanics.stats.StatModifier.st.globalPen;
import static gamemechanics.stats.StatModifier.st.globalRes;
import static gamemechanics.stats.StatModifier.st.life;
import static gamemechanics.stats.StatModifier.st.lifeShield;
import static gamemechanics.stats.StatModifier.st.mana;
import static gamemechanics.stats.StatModifier.st.manaShield;
import static gamemechanics.stats.StatModifier.st.move;
import static gamemechanics.stats.StatModifier.st.special;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;

import gamemechanics.models.Effect.StatsEffect;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.StatModifier.CostAsResource;
import gamemechanics.stats.StatModifier.DamageAsElement;
import gamemechanics.stats.StatModifier.DamageAsResource;
import gamemechanics.stats.StatModifier.StatAsStat;
import gamemechanics.stats.StatModifier.StatNullifier;
import gamemechanics.stats.StatModifier.mo;
import gamemechanics.stats.StatModifier.st;

/**
 * Order :
 * 
 * Calc BasicMods from boosts (items, status, cell status)
 * Calc Conversions
 * Calc Nullifiers
 * Calc BasicMods from base character stats
 * 
 */
public class StatTable {
	// used life/mana/move = used as a cost for an action
	// lost life/mana/move = lost as damage, etc.
	
	// current life = (base + incFlat) + (base% * inc%) * incMore - usedLife - lostLife
	// max     life = (base + incFlat) + (base% * inc%) * incMore 
	
	// current mana = (base + incFlat) + (base% * inc%) * incMore - usedMana - lostMana
	// max     mana = (base + incFlat) + (base% * inc%) * incMore 

	// current move = (base + incFlat) + (base% * inc%) * incMore - usedMove - lostMove
	// max     move = (base + incFlat) + (base% * inc%) * incMore 
	
	/*
	 * life, lifeShield, mana, manaShield, move, special
	 * baseFlat, base%, incFlat, inc%, incMore, used, lost
	 */
	
	// si on refill la mana au complet à chaque tour, on peut faire ça et enlever toutes les instances de Used à chaque tour
	// if stats.mana > cost -> new UsedMod(mana, cost);
	// onTurnStart -> mods.remove(m -> m instanceof UsedMod && m.res = mana)
	
	// si on refill pas nécessairement la mana au complet à chaque tour :
	// if stats.mana > cost -> new UsedMod(mana, cost);
	// onTurnStart -> mods.combine(m -> m instanceof UsedMod && m.res = mana).add(manaRegen);  // combine tous les usedmana en 1 instance puis lui ajoute le regen
	
	public Table<st, mo, Double> table = ArrayTable.create(Arrays.asList(st.values()), Arrays.asList(mo.values()));
	
	public List<StatAsStat> statConversion;
	public List<StatNullifier> statNullifying;
	
	// pas vraiment besoin de ces 2 là si on utilise directement les status + DmgInstanceEvent
	// si on fait p.ex. un effet pour chacun des 2
	public List<DamageAsElement> eleDmgConversion;
	public List<DamageAsResource> resourceDmgConversion;
	
	public List<CostAsResource> resourceCostConversion;
	
	
	public StatTable() {
		setZeros(); // set zéros, mais créé pas de lists car pas besoin pour certaines utilisation de StatTable (ex pour calculer les dégpats)
	}
	/** wipe everything except { used, lost } */
	public void wipe() {
		setZeros();
		setLists();
	}
	public void setLists() {
		statConversion = new ArrayList<>();
		statNullifying = new ArrayList<>();
		
		eleDmgConversion = new ArrayList<>();
		resourceDmgConversion = new ArrayList<>();
		resourceCostConversion = new ArrayList<>();
	}
	public void setZeros() {
		for(st s : st.values())
			for(mo m : mo.values())
				if(m != fight) //used && m != lost) 
					put(s, m, 0);
	}

	/**
	 * Order :
	 * 
	 * Calc BasicMods from boosts (items, status, cell status),
	 * Calc Conversions,
	 * Calc Nullifiers,
	 * Calc BasicMods from base character stats
	 * 
	 */
	@SuppressWarnings("unchecked")
	public StatTable compile(Creature target) {
		// wipe everything except fight mods (used/lost resources)
		this.wipe();
		
		// commence par prendre tous les boosts
		target.items.forEach(i -> i.effects.forEach(e -> {
			if(e instanceof StatsEffect) e.apply(target, target);
		}));
		target.getStatus().forEach(s -> s.effects.forEach(e -> {
			if(e instanceof StatsEffect) e.apply(s.source, target);
		}));
		// cell statuses should be automatically added and removed from creature statuses OnEnter/OnLeave
		//target.getCell().getStatus().forEach(s -> s.effects.forEach(e -> {
		//	if(e instanceof StatsEffect) e.apply(s.source, target);
		//}));
		
		// applique les conversions de stats
		this.statConversion.forEach(s -> s.convert(this));
		
		// applique les nullifiers de stats
		this.statNullifying.forEach(s -> s.nullify(this));
		
		// applique les bases du personnage
		target.data.baseMods.forEach(m -> m.apply(this));
		
		return this;
	}
	
	
	
	public double get(st s, mo m) {
		return table.get(s, m);
	}
	public void put(st s, mo m, double v) {
		table.put(s, m, v);
	}
	public void add(st s, mo m, double v) {
		put(s, m, table.get(s, m) + v);
	}
	
	
	
	/** the value is already considered negative by this method so you can input it as positive */
	public void use(st resource, double value) {
		boolean underflow = getCurrentResource(resource) - value < 0;
		if(underflow)
			value = getCurrentResource(resource);
		add(resource, fight, -value);
	}
	/** the value is already considered negative by this method so you can input it as positive */
	public void lose(st resource, double value) {
		boolean underflow = getCurrentResource(resource) - value < 0;
		if(underflow)
			value = getCurrentResource(resource);
		table.put(resource, fight, value); 
		// post event OnResourceLoss
	}
	/** the value is considered positive by this method */
	public void gain(st resource, double value) {
		boolean overflow = getCurrentResource(resource) + value > getMaxResource(resource);
		// limite les gains de resource au max, sauf pour les shields qui n'ont pas de max
		if(overflow && resource != lifeShield && resource != manaShield)
			value = getMaxResource(resource) - getCurrentResource(resource);
		table.put(resource, fight, value);
	}
	
	
	
	// life
	public double getMissingLife() { return getMissingResource(life); }
	public int getCurrentLife() { return getCurrentResource(life); }
	public int getMaxLife() { return getMaxResource(life); }
	public int getCurrentLifeShield() { return getCurrentResource(lifeShield); }
	// mana
	public double getMissingMana() { return getMissingResource(mana); }
	public int getCurrentMana() { return getCurrentResource(mana); }
	public int getMaxMana() { return getMaxResource(mana); }
	public int getCurrentManaShield() { return getCurrentResource(manaShield); }
	// movement
	public double getMissingMovement() { return getMissingResource(move); }
	public int getCurrentMovement() { return getCurrentResource(move); }
	public int getMaxMovement() { return getMaxResource(move); }
	// special
	public double getMissinSpecial() { return getMissingResource(special); }
	public int getCurrentSpecial() { return getCurrentResource(special); }
	public int getMaxSpecial() { return getMaxResource(special); }
	//
	public double getMissingResource(st resource) {
		return get(resource, fight); //used) + get(resource, lost);
	}
	public int getCurrentResource(st resource) {
		var a = getMaxResource(resource) - getMissingResource(resource);
		return (int) Math.round(a);
	}
	public int getMaxResource(st resource) {
		var a = (get(resource, flat) * get(resource, scl)) * get(resource, more);
		return (int) Math.round(a);
	}
	
	
	
	public double getDmg(st element, mo m) {
		return get(globalEle, m) + get(globalDmgEle, m) + get(element, m);
	}
	public double getHeal(st element, mo m) {
		return get(globalEle, m) + get(globalHealEle, m) + get(element, m);
	}
	public double getDmgRes(st element, mo m) {
		return get(globalRes, m) + get(globalDmgRes, m) + get(st.values()[element.ordinal() + 9], m);
	}
	public double getDmgPen(st element, mo m) {
		return get(globalPen, m) + get(globalDmgPen, m) + get(st.values()[element.ordinal() + 18], m);
	}
	public double getHealRes(st element, mo m) {
		return get(globalRes, m) + get(globalHealRes, m) + get(st.values()[element.ordinal() + 9], m);
	}
	public double getHealPen(st element, mo m) {
		return get(globalPen, m) + get(globalHealPen, m) + get(st.values()[element.ordinal() + 18], m);
	}

	
}