package com.souchy.randd.modules.api;

public interface ModuleLoader {

	
	public Module load(ModuleInformation info);

	public void load(ModuleInformation info, ModuleManager destination);
	
}
