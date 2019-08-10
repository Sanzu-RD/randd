package gamemechanics.creatures;

import com.google.inject.Inject;
import com.souchy.randd.commons.tealwaters.commons.Namespace.I18NNamespace;

public enum CreatureType {
	
	Red,
	Green,
	Blue,
	Yellow,
	White,
	Black,
	
	Skeleton,
	Zombie,
	Elemental,
	Ghost,
	Vampire, 
	Summoner,

	Feline,
	Fairy,
	;

	@Inject
	private static CreatureTypeI18N i18n;
	
	/**
	 * Namespace { Creatures.Types } for i18n properties
	 */
	public static final I18NNamespace creaturesTypes = new I18NNamespace("Creatures.Types"); //CreatureData.creatures.in("Types");
	
	/**
	 * i18n identifier example : "Creatures.Types.Skeleton"
	 */
	public String id() {
		return creaturesTypes.inString(name()); 
	}
	
	/**
	 * i18n name examples : "Skeleton", "Squelette"
	 */
	public String getName() {
		return i18n.get(this); //id());
	}
	
	
	public static class CreatureTypeI18N {
		public String get(CreatureType type) {
			return type.name();
		}
	}
	
}
