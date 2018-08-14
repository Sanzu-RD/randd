package com.souchy.randd.commons.tealwaters.io.files;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;

import com.souchy.randd.commons.tealwaters.commons.Discoverer;

/**
 * 
 * @author Souchy
 *
 * @param <T> - type de classe à charger
 */
public class ClassDiscoverer<T> implements Discoverer<String, Class<?>, Class<T>> {
	
	/**
	 * Charge tous les types de classes
	 * 
	 * @author Souchy
	 *
	 */
	public static class DefaultClassDiscoverer extends ClassDiscoverer<Object> {
		
	}
	
	
	@Override
	public List<Class<T>> explore(String packageName) {
		ArrayList<Class<T>> classes = new ArrayList<>();
		String pathname = packageName.replace('.', '/');
		try {
			Enumeration<URL> resources = FilesManager.get().getResources(pathname);
			List<File> dirs = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				//System.out.println("resource : [" + resource.getFile() + "]");
				dirs.add(new File(resource.getFile().replace("%20", " ")));
			}
			for (File directory : dirs) {
				//System.out.println("for dir [" + directory + "]");
				classes.addAll(findClasses(directory, packageName, this::identify));
			}

			//System.out.println("found [" + classes.size() + "] classes");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	@Override
	public boolean identify(Class<?> f) {
		return true;
		// return f.isFile() && f.getName().endsWith(".class");
		// return p.getFileName().endsWith(".class");
	}

	/*
	 * private void recursiveFileFind(File directory, List<File> files,
	 * Predicate<File> shouldAdd) { File[] children = directory.listFiles();
	 * if(children != null && children.length != 0) { for(File c : children) {
	 * if(shouldAdd.test(c)) files.add(c); if(c.isDirectory()) recursiveFileFind(c,
	 * files, shouldAdd); } } }
	 */

	private List<Class<T>> findClasses(File directory, String packageName, Predicate<Class<?>> filter) throws ClassNotFoundException {
		List<Class<T>> classes = new ArrayList<>();
		if (!directory.exists()) {
			System.out.println("rip");
			return classes;
		}
		File[] files = directory.listFiles();
		// System.out.println("found ["+files.length+"] files in dir");
		for (File file : files) {
			String filename = packageName + "." + file.getName();
			//System.out.println("file name = ["+filename+"]");
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, filename, filter));
			} else if (file.getName().endsWith(".class")) {
				Class<?> c = Class.forName(filename.substring(0, filename.length() - 6));
				if (filter.test(c))
					classes.add((Class<T>) c);
			}
		}
		return classes;
	}

}
