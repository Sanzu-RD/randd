package creatures.types.necromancer;

import com.souchy.randd.ebishoal.sapphire.data.EbiSpellData;

/**
 * These spells need to be registered to their creature type to be available to creatures of that type when the creature data is created
 * 
 * @author Blank
 *
 */
public class SummonSkeleton implements EbiSpellData {

	@Override
	public int id() {
		return 6;
	}

	@Override
	public String getIconName() {
		return "SpellBook01_55.PNG";
	}
	
}
