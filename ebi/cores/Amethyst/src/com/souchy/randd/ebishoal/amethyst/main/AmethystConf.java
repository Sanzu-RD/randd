package com.souchy.randd.ebishoal.amethyst.main;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;

public class AmethystConf extends JsonConfig {
	
	
	/**
	 * Active certains features qui pourraient être intimidants pour un nouveau joueur : <br>
	 * 
	 * - Active la gestion de dossier dans le tab de builds
	 * - 
	 */
	public boolean advancedMode = false;

	
	/**
	 * 
	 */
	public String language = "en";
	
	
	
	/**
	 * saved username
	 */
	public String username = "";
	/**
	 * saved hashedpass for quick connect
	 */
	public String hashedpass = "";
	
	
}
