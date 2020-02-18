package gamemechanics.statics.stats.modifiers;

import gamemechanics.statics.stats.modifiers.Modifier.ModifierID;

public enum resourceMod implements Modifier {
	cost, shield, 
	regen,
	/** fight mod is the sum of everything used/healed/lost in a resource */
	fight;
	private resourceMod() { 
		ModifierID.register(this); 
	}
}