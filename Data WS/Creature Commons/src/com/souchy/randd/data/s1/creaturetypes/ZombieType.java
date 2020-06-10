package com.souchy.randd.data.s1.creaturetypes;

import data.new1.CreatureTypeModel;

public class ZombieType extends CreatureTypeModel {

	public static final CreatureTypeModel inst = new ZombieType();
	
	private ZombieType() {
		// resources
//		stats.addResource(400, Resource.life); 
//		stats.addResource(8, Resource.mana); 
//		stats.addResource(4, Resource.move); 
//		// affinities
//		stats.addAffinity(25, Elements.dark);
//		// res
//		stats.addResistance(25, Elements.dark);
//		stats.addResistance(25, Elements.physical);
		
		
	}
	
	/*
	public class ZombiePassive extends Passive implements OnEleDmgInstanceHandler {
		public static final int dmgReduction = 10;
		public ZombiePassive(Entity source) { 
			super(source);  
		}
		@Override
		public void onEleDmgInstance(OnEleDmgInstance e) {
			if(e.target == this.target) {
				Elements.values.forEach(ele -> {
					if(e.eledmg.containsKey(ele)) {
						double dmg = e.eledmg.get(ele);
						e.eledmg.put(ele, dmg - dmgReduction);
					}
				});
			}
			
		}
		@Override
		public int id() {
			return 0;
		}
	}
	*/
	
}
