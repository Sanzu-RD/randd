package com.souchy.randd.commons.tealwaters.io.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Paths;
import java.util.Enumeration;

public class FilesManager {

	/*
	 * public static InputStream getResourceAsStream(String name) { //return
	 * FilesManager.class.getClassLoader().getParent().getResourceAsStream(name);
	 * return
	 * Thread.currentThread().getContextClassLoader().getResourceAsStream(name); }
	 */

	// static ---------------------------

	private static FilesManager singleton = new FilesManager();

	//public static void init(Class<?> clazz) {
	//	singleton.loader = clazz.getClassLoader();
	//}

	public static FilesManager get() {
		return singleton;
	}

	// instance --------------------------

	//private ClassLoader loader;

	private FilesManager() {
	}

	public InputStream getResourceAsStream(String name) {
		//if (loader != null)
		//	return loader.getResourceAsStream(name);
		//return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		return getClassLoader().getResourceAsStream(name);
	}

	public URL getResource(String name) {
		//if (loader != null)
		//	return loader.getResource(name);
		//return Thread.currentThread().getContextClassLoader().getResource(name);
		return getClassLoader().getResource(name);
	}

	public Enumeration<URL> getResources(String name) throws IOException {
		//if (loader != null)
		//	return loader.getResources(name);
		return getClassLoader().getResources(name);
	}

	
	private ClassLoader getClassLoader() {
		//return Thread.currentThread().getContextClassLoader();
		return getClass().getClassLoader();
	}
	
	public void hey() {
		FileSystem sys;
		Paths.get("");
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public InputStream getFileOnRuntime(String name) {
		return getClassLoader().getResourceAsStream(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public File getFileOutside(String name) {
		return new File(name);
	}
	

	public boolean hasExtension(File fileName, String extension) {
		return hasExtension(fileName.getName(), extension);
	}
	public boolean hasExtension(String fileName, String extension) {
		return fileName.endsWith("." + extension);
	}
	
	
	
}
