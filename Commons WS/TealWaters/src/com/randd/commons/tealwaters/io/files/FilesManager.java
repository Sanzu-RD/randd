package com.randd.commons.tealwaters.io.files;

import java.io.InputStream;
import java.net.URL;

public class FilesManager {

	
	
	/*
	public static InputStream getResourceAsStream(String name) {
		//return FilesManager.class.getClassLoader().getParent().getResourceAsStream(name);
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}
	*/
	
	
	// static ---------------------------
	
	private static FilesManager singleton = new FilesManager();
	public static void init(Class<?> clazz) {
		singleton.loader = clazz.getClassLoader();
	}
	public static FilesManager get() {
		return singleton;
	}
	
	// instance --------------------------
	
	private ClassLoader loader;
	
	private FilesManager() { }
	
	public InputStream getResourceAsStream(String name) {
		if(loader != null) return loader.getResourceAsStream(name);
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

	public URL getResource(String name) {
		if(loader != null) return loader.getResource(name);
		return Thread.currentThread().getContextClassLoader().getResource(name);
	}
	
	
	
}
