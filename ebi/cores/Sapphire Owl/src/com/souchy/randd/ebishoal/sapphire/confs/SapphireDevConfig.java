package com.souchy.randd.ebishoal.sapphire.confs;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;

public class SapphireDevConfig extends JsonConfig {

	public static /* final */ SapphireDevConfig conf;
	static {
		conf = JsonConfig.read(SapphireDevConfig.class);
		//new DevConfig().save();
		//conf = JsonConfig.readExternal(DevConfig.class, "modules/");
	}
	
	public boolean logSapphireWidgets = false;
	public boolean logLapisResource = false;
	public boolean logSkinResources = false;
	
	public boolean debugCursorPos = true;
	
}
