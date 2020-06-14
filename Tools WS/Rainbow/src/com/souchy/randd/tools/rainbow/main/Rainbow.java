package com.souchy.randd.tools.rainbow.main;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;

import javafx.application.Application;


/**
 * hey we should have a web version of rainbow 
 * web client would connect to Opal that would then talk to Pearl
 * so we could monitor servers from a browser
 * 
 * @author Blank
 * @date 11 juin 2020
 */
public class Rainbow extends EbiShoalCore {

	public static Rainbow core;
	public static RainbowApp app;
	public static RainbowConf conf;
	public static EbiShoalTCP client;
	
	public static void main(String[] args) throws Exception {
		new Rainbow(args);
	}
	
	public Rainbow(String[] args) throws Exception {
		super(args);
		core = this;
		client = new EbiShoalTCP("127.0.0.1", 1000, this);

		Log.info("Rainbow start.");
		Application.launch(RainbowApp.class);
		Log.info("Rainbow stopped.");
//		Platform.runLater(() -> RainbowApp.stage.setScene(RainbowApp.mainScene));
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.commons.deathebi", "com.souchy.randd.tools.rainbow", "com.souchy.randd.tools.rainbow.handlers", "com.souchy.randd.deathshadows.nodes.pearl.messaging" };
	}
	
	
}
