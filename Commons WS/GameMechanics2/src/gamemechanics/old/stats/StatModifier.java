package gamemechanics.old.stats;

import static gamemechanics.stats.StatModifier.mo.costFlat;
import static gamemechanics.stats.StatModifier.mo.costMore;
import static gamemechanics.stats.StatModifier.mo.costScl;
import static gamemechanics.stats.StatModifier.mo.flat;
import static gamemechanics.stats.StatModifier.mo.more;
import static gamemechanics.stats.StatModifier.mo.scl;
import static gamemechanics.stats.StatModifier.st.blackEle;
import static gamemechanics.stats.StatModifier.st.blackPen;
import static gamemechanics.stats.StatModifier.st.blackRes;
import static gamemechanics.stats.StatModifier.st.blueEle;
import static gamemechanics.stats.StatModifier.st.bluePen;
import static gamemechanics.stats.StatModifier.st.blueRes;
import static gamemechanics.stats.StatModifier.st.globalDmgPen;
import static gamemechanics.stats.StatModifier.st.globalHealPen;
import static gamemechanics.stats.StatModifier.st.globalPen;
import static gamemechanics.stats.StatModifier.st.greenEle;
import static gamemechanics.stats.StatModifier.st.greenPen;
import static gamemechanics.stats.StatModifier.st.greenRes;
import static gamemechanics.stats.StatModifier.st.life;
import static gamemechanics.stats.StatModifier.st.lifeShield;
import static gamemechanics.stats.StatModifier.st.mana;
import static gamemechanics.stats.StatModifier.st.manaShield;
import static gamemechanics.stats.StatModifier.st.move;
import static gamemechanics.stats.StatModifier.st.redEle;
import static gamemechanics.stats.StatModifier.st.redPen;
import static gamemechanics.stats.StatModifier.st.redRes;
import static gamemechanics.stats.StatModifier.st.special;
import static gamemechanics.stats.StatModifier.st.whiteEle;
import static gamemechanics.stats.StatModifier.st.whitePen;
import static gamemechanics.stats.StatModifier.st.whiteRes;
import static gamemechanics.stats.StatModifier.st.yellowEle;
import static gamemechanics.stats.StatModifier.st.yellowPen;
import static gamemechanics.stats.StatModifier.st.yellowRes;

import java.util.Map;

/**
 * 
 * how does this become an Effect
 *
 */
public abstract class StatModifier {

	/*
	 * 
	 * Used,
	 * Cost,
	 * Scaling,
	 * Res,
	 * Pen
	 * 
	 */
	/*public static enum column {
		base,
		used,
		lost,
		bonus,
	}*/
	
	
	public static enum st {
		// resources
		life, lifeShield, mana, manaShield, move, special,
		
		// costs modifiers
		//lifeCost, manaCost, moveCost, specialCost,
		
		// elements
		globalEle, globalDmgEle, globalHealEle, redEle, greenEle, blueEle, yellowEle, whiteEle, blackEle,
		globalRes, globalDmgRes, globalHealRes, redRes, greenRes, blueRes, yellowRes, whiteRes, blackRes,
		globalPen, globalDmgPen, globalHealPen, redPen, greenPen, bluePen, yellowPen, whitePen, blackPen,
		globalDmgSummon, // damage against summons
		globalHealSummon, // heal against summons

		// other 
		MaxSummons,
		
		// spells
		Cooldown,
		UserPerTurn,
		UsePerTurnPerTarget,
		Range,
		
		// statuses 
		// edit i feel like these should be directlyin the statuses, then can be modified onStatusApply 
		// by looking at other statuses for things that can affect duration etc.
		// the debuffable one is a sum of status.debuffable | creature.hexproof
//		Duration,
//		MaxStacks,
//		Debuffable, // bool
	}
	public static enum mo {
		//all, // utilisé pour le NulliferMod, pour mettre tous les modificateurs d'une stat à 0
		
		// all
		//baseFlat, baseScl, 
		flat, scl, more, 

		// element
		//resFlat, resScl, resMore, // resMore = dmg received
		//healReceived, // heal received (ex: grievous wounds)  
		// ^ on peut simplement utiliser la combinaison [st.globalHeal] + les 3 resistances  (-flat healing, -%healing, -%more healing)
		// wakfu a la même chose avec la résistance au soin aussi
		
		//penFlat, penScl,
		
		// resource cost modifiers
		costFlat, costScl, costMore,
		
		// fight modifiers
		//used, lost, regen
		fight
	}

	
	public static final st[] RESOURCES = new st[] { life, lifeShield, mana, manaShield, move, special };
	public static final st[] ELEMENTS = new st[] { redEle, greenEle, blueEle, yellowEle, whiteEle, blackEle };
	public static final st[] RESISTANCES = new st[] { redRes, greenRes, blueRes, yellowRes, whiteRes, blackRes };
	public static final st[] PENETRATIONS = new st[] { globalPen, globalDmgPen, globalHealPen, redPen, greenPen, bluePen, yellowPen, whitePen, blackPen };
	
	//public static final mo[] BASES = new mo[] { baseFlat, baseScl };
	public static final mo[] SCALINGS = new mo[] { flat, scl, more };
	public static final mo[] COST = new mo[] { costFlat, costScl, costMore };
	
	
	
	public abstract void apply(StatTable table);
	
	
	// 1. how do u relate an Effect to a StatMod
	// 2. how do u show the resulting value of a StatMod on the Status tooltip (ex: Gain +200hp per dead ally. (+400 current))
			// each status can have a text description
	// 3. how do you do conditional effects / at what layer do you put the conditions.
	// 4. how do you define an Effect
	

	// you can create any kind/implementation of modifiers on top of those basic ones :

	
	/**
	 * this is a basic stat mod
	 */
	public static class BasicMod extends StatModifier {
		public st s;
		public mo m;
		public double v;
		public BasicMod(st s, mo m, double v) { this.s = s; this.m = m; this.v = v; }
		@Override
		public void apply(StatTable table) {
			table.add(s, m, v);
		}
	}
	public static class StatAsStat extends StatModifier {
		public st source;
		public st target;
		public double rate;
		public ConversionType type = ConversionType.Convert;
		@Override
		public void apply(StatTable table) {
			table.statConversion.add(this);
		}
		public void convert(StatTable table) {
			for(mo m : SCALINGS) {
				var total = table.get(source, flat); 
				var converted = total * rate;
				table.add(target, m, converted);
				if(type == ConversionType.Convert) 
					table.add(source, m, -converted);
			}
		}
	}
	/**
	 * Nullifies a stat's filtered mods to 0 
	 */
	public static class StatNullifier extends StatModifier {
		public st stat; // filter
		public mo[] mods; // filter
		@Override
		public void apply(StatTable table) {
			table.statNullifying.add(this);
		}
		public void nullify(StatTable table) {
			for(mo m : mods) {
				table.put(stat, m, 0);
			}
		}
	}
	


	public static class DamageAsElement extends StatModifier {
		public st source;
		public st target;
		public double rate;
		public boolean madeOrTaken = true;
		public ConversionType type = ConversionType.Convert;
		@Override
		public void apply(StatTable table) {
			table.eleDmgConversion.add(this);
		}
		public void convert(Map<st, Double> eleDmgs) {
			var converted = eleDmgs.get(source) * rate;
			eleDmgs.put(target, eleDmgs.get(target) + converted);
			if(type == ConversionType.Convert) 
				eleDmgs.put(source, eleDmgs.get(source) - converted);
		}
	}
	public static class DamageAsResource extends StatModifier {
		public st source;
		public st target;
		public double rate;
		public boolean madeOrTaken = true;
		public ConversionType type = ConversionType.Convert;
		@Override
		public void apply(StatTable table) {
			table.resourceDmgConversion.add(this);
		}
		public void convert(Map<st, Double> resourceDmgs) {
			var converted = resourceDmgs.get(source) * rate;
			resourceDmgs.put(target, resourceDmgs.get(target) + converted);
			if(type == ConversionType.Convert) 
				resourceDmgs.put(source, resourceDmgs.get(source) - converted);
		}
	}
	public static class CostAsResource extends StatModifier {
		public st source;
		public st target;
		public double rate;
		public ConversionType type = ConversionType.Convert;
		@Override
		public void apply(StatTable table) {
			table.resourceCostConversion.add(this);
		}
		public void convert(Map<st, Double> resourceCosts) {
			var converted = resourceCosts.get(source) * rate;
			resourceCosts.put(target, resourceCosts.get(target) + converted);
			if(type == ConversionType.Convert) 
				resourceCosts.put(source, resourceCosts.get(source) - converted);
		}
	}
	
	
	
}
