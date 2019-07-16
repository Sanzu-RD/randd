package com.souchy.randd.ebishoal.commons.lapis.main;

import javax.sound.sampled.Port;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.ebishoal.commons.EbiCoreClient;

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
public abstract class LapisCoreClient extends EbiCoreClient {

	private LwjglApplicationConfiguration config;
	private LapisProperties properties;
	private LapisGame game;

	/**
	 * @inheritDoc
	 */
	@Override
	public void init() throws Exception {
		// load configs
		config = new LwjglApplicationConfiguration();
		addIcon(config);
		properties = new LapisProperties(config);
		properties.load();
		// init netty client 
		super.init();
		// create game
	    game = createGame();
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public void start() {
		// start netty client
		
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


	public abstract void addIcon(LwjglApplicationConfiguration config);
	
}
