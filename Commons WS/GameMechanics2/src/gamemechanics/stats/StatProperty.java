package gamemechanics.stats;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;

public interface StatProperty {
	
	public static class StatPropertyID {
		private static int i = 1;
		private static Map<StatProperty, Integer> propertyIDs = new HashMap<>();
		public static int register(StatProperty s) {
			if(propertyIDs.containsKey(s) == false) {
				propertyIDs.put(s, i++); //(int) Math.pow(2, i++));
			}
			return get(s);
		}
		public static int get(StatProperty s) {
			return propertyIDs.get(s);
		}
	}
	
	public default int val() {
		return StatPropertyID.get(this);
	}
	
	public static enum type {
		resource, affinity, property, spellProperty;
	}
	
	public static enum resource implements StatProperty {
		life, mana, move, special;
		private resource() {
			StatPropertyID.register(this);
		}
	}
	
	public static enum element implements StatProperty {
		globalEle, red, green, blue, yellow, white, black;
		private element() {
			StatPropertyID.register(this);
		}
	}
	
	public static enum property implements StatProperty {
		visible, maxSummons;
		private property() {
			StatPropertyID.register(this);
		}
	}
	
	public static enum spellProperty implements StatProperty {
		isInstant, cooldown, maxCastsPerTurn, maxCastsPerTurnPerTarget, minRange, maxRange, minRangePattern, maxRangePattern; //inLine(19), inDiagonal(20);
		private spellProperty() {
			StatPropertyID.register(this);
		}
		
	}
	
	/** which kind of target the spell/caster can hit (each of them are a bool as to if they can be targeted) (empty means an empty cell) */
	public static enum targetingProperty implements StatProperty {
		empty, allies, enemies, summons, summoners, needsLineOfSight;
		private targetingProperty() {
			StatPropertyID.register(this);
		}
		
	}
	
}
