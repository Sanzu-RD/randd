package com.souchy.randd.ebishoal.commons.lapis.main;

import java.nio.file.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiCore;

/**
 * 
 * To write in LapisCore implementation : 
 * <pre>
 * public static void main(String[] args) throws Exception {  
 *     launch(new LapisCoreImp()); 
 * }
 * </pre>
 * 
 * @author Souchy
 *
 */
public abstract class LapisCore extends EbiCore {
	
	private Lwjgl3ApplicationConfiguration config;
	//private LapisProperties properties;
	private LapisGame game;

	public static boolean isEclipse = false;

	/**
	 * @inheritDoc
	 */
	@Override
	public void init() throws Exception {
		config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1600, 900);
		config.setIdleFPS(60);
		//config.useVsync(true);
		config.setTitle("Sapphire Owl");
		addIcon(config);
//		properties = new LapisProperties(config);
//		properties.load();
	    game = createGame();
	}
	
	
	/**
	 * @inheritDoc
	 */
	@Override
	public void start() {
		new Lwjgl3Application(game, config);
	}
	
	/** @return LapisGame implementation */
	public LapisGame getGame() {
		return game;
	}

//	public LapisProperties getProperties() {
//		return properties;
//	}

	/**
	 * Called when LapisCore.init is first called to create a new LapisGame() implementation
	 * @return Instance of LapisGame
	 */
	protected abstract LapisGame createGame();
	/*
	 * Example : config.addIcon("G:\\Assets\\pack\\fantasy bundle\\tcgcardspack\\Tex_krakken_icon.png", FileType.Absolute);
	 */
	public abstract void addIcon(Lwjgl3ApplicationConfiguration config);


	protected static void arguments(String[] args) {
		Log.info("Lapis args : " + String.join(", ", args));
		//if(args.length > 0) Environment.root = Paths.get(args[0]); // first arg = root folder
		if(args.length > 1) isEclipse = args[1].contentEquals("eclipse"); // second arg = isEclipse
		if(args.length > 1) Log.info(args[1] + ", isEclipse=" + isEclipse);
	}
	
	/* 
	 * might add these functions here and make the game variable completely private
	 * 
	 * public Screen getScreen(){ 
	 *     return game.getscreen(); 
	 * }
	 * public void setScreen(Screen s){
	 *     game.setScreen(s);
	 * }
	 * 
	 */
	
}
