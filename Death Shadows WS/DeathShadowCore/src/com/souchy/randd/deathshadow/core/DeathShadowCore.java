package com.souchy.randd.deathshadow.core;

import com.souchy.randd.commons.deathebi.Core;
import com.souchy.randd.commons.tealwaters.logging.Logging;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;

public abstract class DeathShadowCore extends Core {
	
	public DeathShadowCore(String[] args) throws Exception {
		super(args);
		Logging.streams.add(l -> {
			Emerald.logs().insertOne(l);
		});
	}
	
}
