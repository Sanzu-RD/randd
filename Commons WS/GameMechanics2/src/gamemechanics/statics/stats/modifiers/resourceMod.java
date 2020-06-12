package gamemechanics.statics.stats.modifiers;

public enum resourceMod implements Modifier {
	cost, shield, 
	regen,
	/** fight mod is the sum of everything used/healed/lost in a resource */
	fight;
	private resourceMod() { 
		ModifierID.register(this); 
	}
}