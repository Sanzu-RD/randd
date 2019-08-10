package data.new1;

import com.souchy.randd.commons.tealwaters.commons.Identifiable;

import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.Stats;

public abstract class SpellModel {
	
	/** return source owner id (creature or type) + spell id to make a 0000 0000 0000 number */
	public abstract int id();
	
	public final Stats baseStats;
	public final Effect[] effects;
	
	public SpellModel() {
		baseStats = initBaseStats();
		effects = initEffects();
	}
	
	protected abstract Stats initBaseStats();
	protected abstract Effect[] initEffects();

	/**
	 * Actual casting action. Applies all effects.
	 */
	public abstract void onCast(Creature caster, Cell target);

	/**
	 * Check if the spell can be cast at all.
	 */
	public abstract boolean canCast(Creature caster);

	/**
	 * Check if the spell can have a specific target.
	 */
	public abstract boolean canTarget(Creature caster, Cell target);

	
	//		v- it's the same file as for the creature itself so it contains creature name, creature desc, and all the same for the spells
	// to get the i18n : it's in the creature's or type's i18n : res/creatures/creatureID/i18n/bundle_*.properties || res/creatures/types/typeID/i18n/bundle_*.properties
	
	/** int id or string name of the animation to play on the caster's creature model */
	//public abstract int animationID(); // just have a standard "cast" animation on every creature
	
	/**
	 * Default casting animation id
	 * // to get the anim, it should be an id or a string name
	 */
	public int getAnimationID() {
		return 2; 
	}
	/**
	 * Default pfx played on the caster's position (none by default)
	 * // to get the pfx  : it's in the creature's or type's folder : res/creatures/creatureID/spells/spellid.pfx  || res/creatures/types/typeID/spells/spellid.pfx
	 */
	public String getPfxCaster() {
		return "";
	}
	/**
	 * Default pfx played on the target position (none by default)
	 * // to get the pfx  : it's in the creature's or type's folder : res/creatures/creatureID/spells/spellid.pfx  || res/creatures/types/typeID/spells/spellid.pfx
	 */
	public String getPfxTarget() {
		return "";
	}
	/**
	 * Default icon name
	 * // to get the icon : it's in the creature's or type's folder : res/creatures/creatureID/spells/spellid.png  || res/creatures/types/typeID/spells/spellid.png
	 */
	public String getIconName() {
		return "spell" + Integer.toString(id()) + ".png";
	}
	
	/**
	 * Need to compile creature stats before compiling them into the spell stats (spell stats = baseSpellStats + creatureStats)
	 * 
	 * @author Blank
	 */
	public static class SpellInstance {
		public final SpellModel model;
		public Stats currStats;
		public SpellInstance(SpellModel model) {
			this.model = model;
			currStats = new Stats();
		}
	}
	
}
