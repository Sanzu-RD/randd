package com.souchy.randd.commons.tealwaters.commons;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * External environment
 * 
 * @author Blank
 *
 */
public class Environment {
	
	/**
	 * Application root directory
	 */
	public static Path root = Paths.get("./");
	/**
	 * Application logs directory
	 */
	public static Path logs = Paths.get(root.toAbsolutePath() + "/logs/");
	
	public static Path fromRoot(String path) {
		return Paths.get(root + "/" + path);
	}
	
	/**
	 * App data directory
	 */
	public static final Path appData = Paths.get(System.getProperty("user.home")).toAbsolutePath(); // System.getenv("user.home")
	
	public static File getFile(String path) {
		return new File(fromRoot(path).toString());
	}
	
	public static boolean exists(String path) {
		return getFile(path).exists();
	}
	
}
