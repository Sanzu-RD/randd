package com.souchy.randd.modules.api;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface ModuleDiscoverer {
	
	//public <T extends ModuleInformation> Map<String, T> explore(File directory);
	
	
	default List<File> explore(File directory) {
		return Arrays.stream(directory.listFiles()).filter(this::identifyIsModule).collect(Collectors.toList());
	}
	
	public boolean identifyIsModule(File f);
	 
}
