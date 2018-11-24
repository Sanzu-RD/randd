package com.souchy.randd.ebishoal.commons.lapis.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.ebishoal.EbiCore;

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
	
	private LwjglApplicationConfiguration config;
	private LapisGame game;
	private LapisProperties properties;

	/**
	 * @inheritDoc
	 */
	@Override
	public void init() throws Exception {
		config = new LwjglApplicationConfiguration();
		properties = new LapisProperties(config);
		properties.load();
	    game = createGame();
	}
	
	
	/**
	 * @inheritDoc
	 */
	@Override
	public void start() {
		new LwjglApplication(game, config);
	}
	
	/** @return LapisGame implementation */
	public LapisGame getGame() {
		return game;
	}

	/**
	 * Called when LapisCore.init is first called to create a new LapisGame() implementation
	 * @return Instance of LapisGame
	 */
	protected abstract LapisGame createGame();
	/**  @return The title for the application's title bar */
	//public abstract String getAppTitle();
	/** Application window size at creation */
	//public abstract Vector2 getSize();
	/** Antialiasing samples */
	//public abstract int getSamples();
	/** Maximum foreground fps (aka when the application is focused). 0 for unlimited. */
	//public abstract int getMaxFps();
	
	public LapisProperties getProperties() {
		return properties;
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
