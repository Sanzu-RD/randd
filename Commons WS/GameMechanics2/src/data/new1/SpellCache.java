package data.new1;

import java.util.Map;

public class SpellCache {

	// feel like i shouldnt have to do it like this
	// should be on redis
	public static Map<Integer, SpellModel> spells;
	
	public static SpellModel get(int i) {
		if(spells.containsKey(i) == false) return null;
		return spells.get(i);
	}
	
}
