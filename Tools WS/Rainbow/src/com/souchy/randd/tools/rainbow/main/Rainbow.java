package com.souchy.randd.tools.rainbow.main;

import java.util.concurrent.Executors;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiCoreClient;

import javafx.application.Application;

public class Rainbow extends EbiCoreClient {

	public static Rainbow core;
	public static RainbowApp app;
	public static RainbowConf conf;
	
	public static void main(String[] args) throws Exception {
		launch(core = new Rainbow());
	}
	
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
	
}
