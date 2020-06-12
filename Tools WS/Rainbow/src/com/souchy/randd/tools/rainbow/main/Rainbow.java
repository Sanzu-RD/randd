package com.souchy.randd.tools.rainbow.main;

import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;

/**
 * hey rainbow should have a http version of the client
 * web client would connect to Opal that would then talk to Pearl
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
		core = new Rainbow(args);
	}
	
	public Rainbow(String[] args) throws Exception {
		super(args);
		client = new EbiShoalTCP("127.0.0.1", 1000, this);
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.tools.rainbow", "com.souchy.randd.deathshadows.nodes.pearl.messaging" };
	}
	
	/*
	
	@Override
	public void init() throws Exception {
		super.init();
		conf = JsonConfig.readExternal(RainbowConf.class); 
	}

	@Override
	public void start() {
		try {
			Executors.newSingleThreadExecutor().execute(() -> Application.launch(RainbowApp.class));
			super.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.info("client started");
	}
	
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.tools.rainbow", "com.souchy.randd.deathshadows.nodes.pearl.messaging" };
	}

	@Override
	protected String getIp() {
		return "localhost";
	}

	@Override
	protected int getPort() {
		return 10000;
	}

	@Override
	protected boolean getSsl() {
		return false;
	}
	*/
	
}
