package com.souchy.randd.commons.tealwaters.commons;

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
	public static final Path root = Paths.get("").toAbsolutePath();
	/**
	 * Application logs directory
	 */
	public static final Path logs = Paths.get(root + "/logs/");
	
	public static final Path fromRoot(String folder) {
		return Paths.get(root + "/" + folder + "/");
	}
	
	/**
	 * App data directory
	 */
	public static final Path appData = Paths.get(System.getProperty("user.home")).toAbsolutePath(); // System.getenv("user.home")
	
}
