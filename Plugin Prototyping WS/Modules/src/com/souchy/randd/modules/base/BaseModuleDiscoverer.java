package com.souchy.randd.modules.base;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import com.souchy.randd.modules.api.ModuleDiscoverer;

public class BaseModuleDiscoverer implements ModuleDiscoverer {

	/**
	 * 
	 * @param directory
	 * @return
	 */
//	@Override
//	public List<File> explore(File directory) {
//		return explore(directory, this::identify);
//	}

	@Override
	public boolean identify(File file) {
		if (!file.getName().endsWith(".jar"))
			return false;
		try (JarFile jar = new JarFile(file)) {
			ZipEntry entry = jar.getEntry("info.properties");
			return (entry != null);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}

// 	@Override
//	public List<File> explore(File directory, Predicate<File> filter) {
//		return Arrays.stream(directory.listFiles()).filter(filter).collect(Collectors.toList());
//	}
	
	

}