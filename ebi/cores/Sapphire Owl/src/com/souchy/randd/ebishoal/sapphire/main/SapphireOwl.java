package com.souchy.randd.ebishoal.sapphire.main;

import java.io.File;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireOwlConf;

import data.modules.AzurCache;
import data.modules.AzurManager;

public class SapphireOwl extends LapisCore { //implements EntryPoint {
	
	/**
	 * would be final if we didnt instantiate i
	 */
	public static final SapphireOwl core = new SapphireOwl();
	public static final SapphireGame game = new SapphireGame();
	public static SapphireOwlConf conf;
	public static AzurManager azur;
	public static AzurCache data;
	

	public static void main(String[] args) throws Exception {
		LapisCore.arguments(args);
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
		
		azur = new AzurManager();
		data = azur.getEntry();
		
		// load all creatures data modules
		azur.explore(new File("data/")); //Environment.getFile("data/")); //new File("data/"));
		azur.instantiateAll();
		
//		manager.getExecutors().shutdown();
//		manager.getExecutors().awaitTermination(1000, TimeUnit.MILLISECONDS);
	}
	
	@Override
	protected LapisGame createGame() {
		return game;
	}

	@Override
	protected String[] getRootPackages(){
		return new String[] { "com.souchy.randd.ebishoal.sapphire" };
	}
	
	@Override
	public SapphireGame getGame() {
		return game;
	}

	@Override
	public void addIcon(LwjglApplicationConfiguration config) {
		config.addIcon("G:/Assets/pack/fantasy bundle/tcgcardspack/Tex_krakken_icon.png", FileType.Absolute);
	}

	
}
