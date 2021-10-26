package com.souchy.randd.ebishoal.commons.lapis.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;

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
public abstract class LapisCore extends EbiShoalCore {

	private Lwjgl3ApplicationConfiguration config;
	private LapisProperties properties;
	private LapisGame game;

	public static boolean isEclipse = false;

	/**
	 * @inheritDoc
	 */
	public LapisCore(String[] args) throws Exception {
		super(args);
		init();
	}
	
	public void start() {
	    new Lwjgl3Application(game, config);
	}

	/**
	 * @inheritDoc
	 */
	//@Override
	public void init() throws Exception {
		config = new Lwjgl3ApplicationConfiguration();
		addIcon(config);
		properties = new LapisProperties(config);
		properties.load();
	    game = createGame();
	}
	
	
//	/**
//	 * @inheritDoc
//	 */
//	@Override
//	public void start() {
//		new LwjglApplication(game, config);
//	}
	
	/** @return LapisGame implementation */
	public LapisGame getGame() {
		return game;
	}

	public LapisProperties getProperties() {
		return properties;
	}

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
		if(args.length > 1) Log.verbose(args[1] + ", isEclipse=" + isEclipse);
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
