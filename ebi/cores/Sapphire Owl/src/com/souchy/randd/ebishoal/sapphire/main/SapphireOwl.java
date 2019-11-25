package com.souchy.randd.ebishoal.sapphire.main;

import java.nio.file.Paths;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;

import data.modules.AzurCache;
import data.modules.AzurManager;

public class SapphireOwl extends LapisCore { //implements EntryPoint {
	
	/**
	 * would be final if we didnt instantiate i
	 */
	public static SapphireOwl core = new SapphireOwl();
	public static SapphireGame game;
	public static SapphireOwlConf conf;
	public static AzurManager manager;
	public static AzurCache data;
	

	// todo instance of black moonstone
//	private static String ip = "192.168.2.15"; // default, but the real ip should come from main(String[] args)
//	private static int port = 11000;
//	private static boolean ssl = false;
	
	public static boolean isEclipse = false;

	public static void main(String[] args) throws Exception {
		// prob wont need this when we will hook Black Moonstone
//		if(args != null && args.length >= 2) {
//			ip = args[0];
//			port = Integer.parseInt(args[1]);
//		}
		// pass args to black moonstone directly 
		// ...
		// launch sapphire core
		Log.info("SapphireOwl args : " + String.join(", ", args));
		if(args.length > 0) Environment.root = Paths.get(args[0]); // first arg = root folder
		if(args.length > 1) isEclipse = args[1].contentEquals("eclipse"); // second arg = isEclipse
		if(args.length > 1) Log.info(args[1] + ", isEclipse="+isEclipse);
		launch(core);
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		
		// load sapphire config
		conf = JsonConfig.readExternal(SapphireOwlConf.class, "modules/");

		// need an event bus since this is an entry point
		//bus = new EventBus();

		// make a node manager to load creatures data
		manager = new AzurManager();
		data = manager.getEntry();
		
		// load all creatures data modules
		manager.explore(Environment.getFile("data/")); //new File("data/"));
		manager.instantiateAll();
//		manager.getExecutors().shutdown();
//		manager.getExecutors().awaitTermination(1000, TimeUnit.MILLISECONDS);
	}
	
	@Override
	protected LapisGame createGame() {
		return game = new SapphireGame();
	}

	@Override
	protected String[] getRootPackages(){
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

	
}
