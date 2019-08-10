package gamemechanics.stats;

import java.util.HashMap;
import java.util.Map;

public interface Modifier {
	
	public static class ModifierID {
		private static int i = 0;
		private static Map<Modifier, Integer> modifierBits = new HashMap<>();
		public static int register(Modifier m) {
			if(modifierBits.containsKey(m) == false) {
				modifierBits.put(m, (int) Math.pow(2, i++));
			}
			return get(m);
		}
		public static int get(Modifier m) {
			return modifierBits.get(m);
		}
	}
	
	public static int compile(Modifier... mods) {
		int tot = 0;
		for(Modifier m : mods)
			tot |= m.val();
		return tot;
	}
	
	public default int val() {
		return ModifierID.get(this);
	}
	/**
	 * checks if an int contains a modifier bit
	 */
	public default boolean has(int tot) {
		return (tot & val()) > 0;
	}
	
	public static enum resourceMod implements Modifier {
		cost, shield, 
		/** fight mod is the sum of everything used/healed/lost in a resource */
		fight;
		private resourceMod() { 
			ModifierID.register(this); 
		}
	}
	
	public static enum eleMod implements Modifier {
		affinity, res, pen,
		dmg, heal,
		summon;
		private eleMod() {
			ModifierID.register(this);
		}
	}
	
	public static enum mathMod implements Modifier {
		flat, scl, more, bool;
		private mathMod() {
			ModifierID.register(this);
		}
	}
	
}
