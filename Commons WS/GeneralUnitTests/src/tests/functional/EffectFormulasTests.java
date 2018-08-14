package tests.functional;

import java.util.HashMap;
import java.util.Map;

import static tests.functional.EffectFormulasTests.CharacteristicType.*;
import static tests.functional.EffectFormulasTests.ElementType.*;

public class EffectFormulasTests {
	
	
	public static enum CharacteristicType {
		
		PRIMARY_RESOURCE, 	// hp, etc
		SECONDARY_RESOURCE, // mana, rage, energy, etc
		TERTIARY_RESOURCE, 	// optionel

		ACTION_RESOURCE, 	// pa
		MOVEMENT_RESOURCE, 	// pm

		/*FIRE,
		WATER,
		EARTH,
		WIND,
		LIGHTNING,
		CHAOS,
		ICE,
		DARK,
		LIGHT,
		PRISMATIC,*/
	}
	
	public static enum ElementType {
		FIRE,
		WATER,
		EARTH,
		WIND,
		LIGHTNING,
		CHAOS,
		ICE,
		DARK,
		LIGHT,
		PRISMATIC,
	}
	
	public static class ElementStats {
		// stats pour la source du dommage
		double inc_per;
		double inc_fix;
		double inc_more;
		
		// stats pour le target du dommage
		double res_per;
		double res_fix;
		double res_more; // "more-less dmg received"
	}
	
	public static class Character {
		
		// devrait mettre des bean plutôt que des valeur primitives directes. 
		// Bean serait automatiquement liée à toutes les increase d'item et de buffs
		
		Map<CharacteristicType, Integer> baseStats;
		Map<ElementType, ElementStats> elementsStats;
		
		public Character() {
			baseStats = new HashMap<>(); 
			elementsStats = new HashMap<>();

			baseStats.put(PRIMARY_RESOURCE, 100); 	// hp
			baseStats.put(SECONDARY_RESOURCE, 100); // mana 
			baseStats.put(TERTIARY_RESOURCE, 0); 	// optionel
			baseStats.put(ACTION_RESOURCE, 6); 		// pa
			baseStats.put(MOVEMENT_RESOURCE, 10); 	// pm
			
			for(ElementType t : ElementType.values()) {
				elementsStats.put(t, new ElementStats());
			}
			
		}
	}

	public static void main(String[] args) {
		
		// Effet de dommage en %HP scalant en Dark et PM
		
		CharacteristicType[] baseScalings = {PRIMARY_RESOURCE, MOVEMENT_RESOURCE};
		ElementType[] elementalScalings = {DARK};
		
		Character source = new Character();
		Character target = new Character();
		
		ElementStats sourcedark = source.elementsStats.get(DARK);
		ElementStats targetdark = target.elementsStats.get(DARK);
		sourcedark.inc_per = 10;
		sourcedark.inc_fix = 10;
		sourcedark.inc_more = 10;
		
		targetdark.res_per = 10;
		targetdark.res_fix = 10;
		targetdark.res_more = 10;
		
		
		double base = 5;
		
		double dmg = (
					 base 
					 * target.baseStats.get(PRIMARY_RESOURCE) / 100
					 * (100 + sourcedark.inc_per) / 100
					 + sourcedark.inc_fix
					 )
					 * sourcedark.inc_more;
		
		System.out.println("Raw dmg : " + dmg);
		
		dmg = (
			  dmg 
			  * (1 - targetdark.res_per)
			  - targetdark.res_fix
			  )
			  * targetdark.res_more
			  ;

		System.out.println("Final dmg : " + dmg);
		
	}
	

}
