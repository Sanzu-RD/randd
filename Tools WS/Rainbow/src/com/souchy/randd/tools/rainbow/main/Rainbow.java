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
	
	public String host;
	public int port;
	
	public static void main(String[] args) throws Exception {
		new Rainbow(args);
	}
	
	public Rainbow(String[] args) throws Exception {
		super(args);
		core = this;
		
		this.host = "127.0.0.1";
		this.port = 1000;
		if(args.length > 0) host = args[0];
		if(args.length > 1) port = Integer.parseInt(args[1]);
		connect();

		Log.info("Rainbow start " + host + ":" + port);
		Application.launch(RainbowApp.class);
		Log.info("Rainbow stopped.");
//		Platform.runLater(() -> RainbowApp.stage.setScene(RainbowApp.mainScene));
	}

	public void connect() {
		try {
			client = new EbiShoalTCP(host, port, this);
		} catch (Exception e) {
			Log.error("", e);
		}
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { 
				"com.souchy.randd.commons.deathebi", 
				"com.souchy.randd.tools.rainbow", 
				"com.souchy.randd.tools.rainbow.handlers",
				"com.souchy.randd.deathshadows.nodes.pearl.messaging" };
	}
	
}
