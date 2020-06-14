package com.souchy.randd.ebishoal.amethyst.main;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;

import javafx.application.Application;



/**
 * 
 * java fx application
 * 
 * @author Blank
 *
 */
public class Amethyst extends EbiShoalCore {


	public static Amethyst core;
	public static AmethystApp app;
	public static AmethystConf conf;
	//public static AmethystModule module;
	//public static ExecutorService service;
	
	
	public static void main(String[] args) throws Exception {
		core = new Amethyst(args);
	}

	public Amethyst(String[] args) throws Exception {
		super(args);
		conf = JsonConfig.readExternal(AmethystConf.class, "./");
		//Opaline.isOnline();
		Log.info("Amethyst start.");
		Application.launch(AmethystApp.class);
		Log.info("Amethyst stopped.");
	}
	

	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.amethyst" };
	}

}
