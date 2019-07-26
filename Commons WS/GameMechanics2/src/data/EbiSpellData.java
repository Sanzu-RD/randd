package data;

import data.CreatureData;
import data.SpellData;

public interface EbiSpellData extends SpellData {

	/**
	 * Actual spell name in the correct language
	 */
	public default String getName(EbiCreatureData creature) {
		return creature.i18n.get(getI18NNameKey(creature)); //SungjinModule.i18n.get(SungjinModule.name(i));
	}

	public default String getFullID(CreatureData creature) {
		String i = Integer.toString(id());
		if(id() < 10) i = "000" + i;
		else if(id() < 100) i = "00" + i;
		else if(id() < 1000) i = "0" + i;
		return i;
	}
	
	/**
	 * Name identifier for i18n and skin
	 */
	public default String getI18NNameKey(CreatureData creature) {
		//String iden = "sungjin.spell.0005";
		return "Creatures." + creature.getIDName() + ".Spell." + getFullID(creature);
	}
	
	/**
	 * Icon file name
	 */
	public String getIconName();

	
	/**
	 * Add this spell's required resources to the creature data skin (textures, particle effects...)
	 */
	public default void addTo(EbiCreatureData data) {
		// add icon
		data.skin.add(getI18NNameKey(data), data.file("gfx/" + getIconName())); //spells stay square, not round!
		// add pfx
		// ...
	}
	
}
