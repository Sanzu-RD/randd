package com.souchy.randd.modules.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.souchy.randd.commons.tealwaters.commons.Discoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;

public interface ModuleDiscoverer extends Discoverer<File, File, File> {
	
	//public <T extends ModuleInformation> Map<String, T> explore(File directory);
	
	
	default List<File> explore(File directory) {
		Log.info("ModuleDiscoverer explore : " + directory);
		if(!directory.exists()) {
			Log.info("ModuleDiscoverer dir does not exist.");
			 return new ArrayList<>();
		}
		//Log.info("ModuleDiscoverer dir files : " + String.join(", ", Arrays.stream(directory.listFiles()).map(f -> f.getPath()).collect(Collectors.toList())));
		return Arrays.stream(directory.listFiles()).filter(this::identify).collect(Collectors.toList());
	}
	
	public boolean identify(File f);
	

//	@Override
//	default List<File> explore(File directory, Predicate<File> filter) {
//		return Arrays.stream(directory.listFiles()).filter(filter).collect(Collectors.toList());
//	}
	 
}
