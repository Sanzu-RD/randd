package com.souchy.randd.commons.tealwaters.io.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

public class FilesManager {
	
	private FilesManager() { }

	public static ClassLoader getClassLoader() {
		return FilesManager.class.getClassLoader();
	}
	

	/**
	 * Gets a file on the classpath (included in the jar export) or in the relative file system path (eclipse bin/ folder)
	 */
	public static URL getResource(String name) {
		var url = getClassLoader().getResource(name);
		
		// si on est pas capable de le trouver sur le classpath, on regarde pour un fichier avec chemin relatif et transforme en URL absolu.
		// cela est utile pour trouver des resources quand on roule sur eclipse
		if(url == null) {
			var dir = new File("bin/" + name);
			if(dir.exists()) {
				try {
					url = dir.toURI().toURL();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		if(url == null) {
			var dir = new File(name);
			if(dir.exists()) {
				try {
					url = dir.toURI().toURL();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		return url;
	}
	
	public static InputStream getResourceAsStream(String name) {
		return getClassLoader().getResourceAsStream(name);
	}
	
	public static Enumeration<URL> getResources(String name) throws IOException {
		return getClassLoader().getResources(name);
	}
	
	public static File getFileOutside(String name) {
		return new File(name);
	}
	
	public static boolean hasExtension(File fileName, String extension) {
		return hasExtension(fileName.getName(), extension);
	}
	
	public static boolean hasExtension(String fileName, String extension) {
		return fileName.endsWith("." + extension);
	}
	
	/*
	 * public void hey() { FileSystem sys; Paths.get(""); }
	 */
	
}
