package com.souchy.randd.modules.api;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.souchy.randd.commons.tealwaters.commons.Discoverer;

public interface ModuleDiscoverer extends Discoverer<File, File, File> {
	
	//public <T extends ModuleInformation> Map<String, T> explore(File directory);
	
	
	default List<File> explore(File directory) {
		//return explore(directory, this::identify);
		return Arrays.stream(directory.listFiles()).filter(this::identify).collect(Collectors.toList());
	}
	
	public boolean identify(File f);
	

//	@Override
//	default List<File> explore(File directory, Predicate<File> filter) {
//		return Arrays.stream(directory.listFiles()).filter(filter).collect(Collectors.toList());
//	}
	 
}
