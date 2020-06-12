package com.souchy.randd.commons.tealwaters.io.files;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.souchy.randd.commons.tealwaters.commons.Discoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * 
 * @author Souchy
 *
 * @param <T> - type de classe à charger
 */
public abstract class ClassDiscoverer<T> implements Discoverer<String, Class<?>, Class<T>> {


	public static class DefaultClassDiscoverer<T> extends ClassDiscoverer<T> {
		private Class<T> superClass;
		public DefaultClassDiscoverer(Class<T> superClass) {
			this.superClass = superClass;
		}
		@Override
		public boolean identify(Class<?> subClass) {
			return superClass != subClass && superClass.isAssignableFrom(subClass); // default impl. It's different for other impls.
		}

		@Override
		public List<Class<T>> explore(String packageName) {
			List<Class<T>> classes = new ArrayList<Class<T>>();
			try {
				Reflections reflections = new Reflections(packageName, new SubTypesScanner(false)); 
				Set<Class<? extends T>> resourceList = reflections.getSubTypesOf(superClass);
				for(var res : resourceList) {
//					Log.info("ClassDiscoverer.explore : reflection res : " + res);
					if(identify(res)) // the implementation of this makes sure the res is the correct class type
						classes.add((Class<T>) res);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return classes;
		}
		
	}
	
	
	@Override
	public List<Class<T>> explore(String packageName) {
		List<Class<T>> classes = new ArrayList<Class<T>>();
		try {
			Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
			Set<Class<?>> resourceList = reflections.getSubTypesOf(Object.class);
			for(var res : resourceList) {
//				Log.info("ClassDiscoverer.explore : reflection res : " + res);
				if(identify(res)) // the implementation of this makes sure the res is the correct class type
					classes.add((Class<T>) res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	public void recursive(List<Class<T>> classes, URL resource) throws IOException, ClassNotFoundException {
		var resourceStr = resource.toString();
//		resourceStr = resourceStr.replace("%20", "").replace("file:/", "");
//		resourceStr = resourceStr.substring(resourceStr.toString().lastIndexOf("/bin/") + 5);
		boolean isFolder = false;
		Enumeration<URL> resources = FilesManager.getResources(resourceStr);
		if(resources != null) {
			while (resources.hasMoreElements()) {
				URL sub = resources.nextElement();
				if(sub == resource) continue;
				//Log.info("ClassDiscoverer.recursive : sub = " + sub);
				recursive(classes, sub);
				isFolder = true;
			}
		}
		//Log.info("ClassDiscoverer.recursive : isFolder ["+isFolder+"], resourceStr = " + resourceStr);
		if(!isFolder && resource.toString().endsWith(".class")) {
			accept(classes, resourceStr);
		}
	}
	

	@SuppressWarnings("unchecked")
	public void accept(List<Class<T>> classes, String resource) throws ClassNotFoundException {
		var fileName = resource;
//		fileName = fileName.replace("%20", "");
//		fileName = fileName.replace("\\", "/");
//		fileName = fileName.replace("file:/", "");
//		fileName = fileName.substring(fileName.toString().lastIndexOf("/bin/") + 5);
//		fileName = fileName.substring(0, fileName.length() - 6);
//		fileName = fileName.replace("/", ".");
		//Log.info("ClassDiscoverer.accept : fileName = " + fileName);
		Class<?> c = Class.forName(fileName);
		if(identify(c)) {
			try {
				var clazz = (Class<T>) c;
				if(!classes.contains(clazz))
					classes.add(clazz);
				//Log.info("ClassDiscoverer.accept : class = " + c);
			} catch (Exception e) {
				Log.info("ClassDiscoverer.accept error : fileName = " + fileName, e);
			}
		}
	}
	
	public void recursiveFiles(List<Class<T>> classes, File file) throws ClassNotFoundException {
		if(file.isDirectory()) {
			for(var f : file.listFiles())
				recursiveFiles(classes, f);
		} else {
			accept(classes, file.toString());
		}
	}
	
	/*
	 * private void recursiveFileFind(File directory, List<File> files,
	 * Predicate<File> shouldAdd) { File[] children = directory.listFiles();
	 * if(children != null && children.length != 0) { for(File c : children) {
	 * if(shouldAdd.test(c)) files.add(c); if(c.isDirectory()) recursiveFileFind(c,
	 * files, shouldAdd); } } }/
	 */

/*
	private List<Class<T>> findClasses(File directory, String packageName, Predicate<Class<?>> filter) throws ClassNotFoundException {
		List<Class<T>> classes = new ArrayList<>();
		if(!directory.exists()) {
			Log.warning("ClassDiscoverer directory does not exist. (" + directory + ")");
			return classes;
		}
		File[] files = directory.listFiles();
		// System.out.println("found ["+files.length+"] files in dir");
		for (File file : files) {
			String filename = packageName + "." + file.getName();
			// System.out.println("file name = ["+filename+"]");
			if(file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, filename, filter));
			} else if(file.getName().endsWith(".class")) {
				Class<?> c = Class.forName(filename.substring(0, filename.length() - 6));
				if(filter.test(c))
					classes.add((Class<T>) c);
			}
		}
		return classes;
	}
*/
	
}
