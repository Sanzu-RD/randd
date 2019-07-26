package com.souchy.randd.ebishoal.sapphire.main;

import java.io.File;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCoreClient;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.node.NodeManager;

public class SapphireOwl extends LapisCoreClient implements EntryPoint {
	
	/**
	 * would be final if we didnt instantiate i
	 */
	public static SapphireOwl core = new SapphireOwl();
	public static SapphireGame game;
	public static SapphireOwlConf conf;
	public static NodeManager manager;
	public static EventBus bus;
	
	private static String ip = "192.168.2.15"; // default, but the real ip should come from main(String[] args)
	private static int port = 11000;
	private static boolean ssl = false;
	
	public static void main(String[] args) throws Exception {
		// prob wont need this when we will hook Black Moonstone
		if(args != null && args.length >= 2) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
		}
		// launch sapphire core
		launch(core);
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		// load sapphire config
		conf = JsonConfig.readExternal(SapphireOwlConf.class, "./modules/");
		
		// need an event bus since this is an entry point
		bus = new EventBus();
		
		// make a node manager to load creatures data
		manager = new NodeManager(this);
		
		// load all creatures data modules
		manager.explore(new File("./data/"));
		manager.instantiateAll();
	}
	
	@Override
	protected LapisGame createGame() {
		return game = new SapphireGame();
	}

	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.sapphire" };
	}
	
	@Override
	public SapphireGame getGame() {
		return (SapphireGame) super.getGame();
	}

	@Override
	public void addIcon(LwjglApplicationConfiguration config) {
		config.addIcon("G:\\Assets\\pack\\fantasy bundle\\tcgcardspack\\Tex_krakken_icon.png", FileType.Absolute);
	}

	protected String getIp() {
		return ip;
	}
	protected int getPort() {
		return port;
	}
	protected boolean getSsl() {
		return ssl;
	}

	@Override
	public EventBus getBus() {
		return bus;
	}

	
}
