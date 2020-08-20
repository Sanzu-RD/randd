package com.souchy.randd.ebishoal.blueberyl;

import com.souchy.randd.ebishoal.commons.EbiShoalCore;

public class BlueBeryl extends EbiShoalCore {

	public static BlueBeryl beryl;

	public static void main(String[] args) throws Exception {
		new BlueBeryl(args);
	}
	
	
	public BlueBeryl(String[] args) throws Exception {
		super(args);
		beryl = this;
	}


	@Override
	protected String[] getRootPackages() {
		return new String[] { 
					"com.souchy.randd.commons.deathebi.msg", 
					"com.souchy.randd.deathshadows.nodes.pearl.messaging",
					"com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone",
					"com.souchy.randd.deathshadows.greenberyl"
				};
	}
	
}
