package com.souchy.randd.ebishoal.amethyst.main;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiCore;

import javafx.application.Application;


/**
 * 
 * java fx application
 * 
 * @author Blank
 *
 */
public class Amethyst extends EbiCore {

	public static Amethyst core;
	public static AmethystApp app;
	public static AmethystConf conf;
	public static AmethystModule module;
	//public static ExecutorService service;
	
	
	public static void main(String[] args) throws Exception {
		launch(core = new Amethyst());
	}
	
	@Override
	public void init() throws Exception {
		Log.info("Amethyst init.");
		
		conf = JsonConfig.readExternal(AmethystConf.class, "./modules/");
	}

	@Override
	public void start() {
		Log.info("Amethyst start.");
		try {
//			var service = Executors.newSingleThreadExecutor();
//			service.execute(() -> Application.launch(AmethystApp.class));
			Application.launch(AmethystApp.class);
			Log.info("Amethyst stopped.");
		} catch (Exception e) {
			Log.warning("Amethyst start error : ", e);
		}
	}

	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.amethyst" };
	}

}
