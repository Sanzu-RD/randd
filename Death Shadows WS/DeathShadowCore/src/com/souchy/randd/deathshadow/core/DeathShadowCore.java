package com.souchy.randd.deathshadow.core;

import com.souchy.randd.commons.deathebi.Core;
import com.souchy.randd.commons.tealwaters.logging.Logging;
import com.souchy.randd.deathshadow.core.smoothrivers.SmoothRivers;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;

public abstract class DeathShadowCore extends Core {
	
	public static DeathShadowCore deathShadowCore;
	public int port;
	
	public SmoothRivers rivers;
	
	public DeathShadowCore(String[] args) throws Exception {
		super(args);
		
		deathShadowCore = this;
		
		Logging.streams.add(l -> {
			Emerald.logs().insertOne(l);
		});
		
		this.rivers = new SmoothRivers(this);
	}
	
	
	
	
}
