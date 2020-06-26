package com.souchy.randd.ebishoal.amethyst.main;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.coraline.Coraline;

import gamemechanics.ext.AssetData;
import gamemechanics.main.DiamondModels;
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
		new Amethyst(args);
	}

	public Amethyst(String[] args) throws Exception {
		super(args);
		
		//Opaline.isOnline();
		core = this;
		Coraline.core = this;
		
		conf = JsonConfig.readExternal(AmethystConf.class, "./");
		
		// init elements
		Elements.values(); 
		// models configurations (creatures, spells, statuses)
		AssetData.loadResources();
		// init creatures & spells models
		DiamondModels.instantiate("com.souchy.randd.data.s1");
		
		Log.info("Amethyst start.");
		Application.launch(AmethystApp.class);
		Log.info("Amethyst stopped.");
	}
	

	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.amethyst", "com.souchy.randd.ebishoal.coraline" };
	}

}
