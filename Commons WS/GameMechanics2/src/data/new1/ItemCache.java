package data.new1;

import java.util.Map;

import gamemechanics.models.Item;

public class ItemCache {
	
	// feel like i shouldnt have to do it like this
	// should be on redis
	public static Map<Integer, Item> items;
	
	public static Item get(int i) {
		if(items.containsKey(i) == false) return null;
		return items.get(i);
	}
	
}
