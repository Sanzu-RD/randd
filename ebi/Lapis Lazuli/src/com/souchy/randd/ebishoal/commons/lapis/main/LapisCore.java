package com.souchy.randd.ebishoal.commons.lapis.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.souchy.randd.commons.ebishoal.EbiCore;

/**
 * 
 * To write in LapisCore implementation : 
 * <pre>
 * public static void main(String[] args) {  
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

	/**
	 * @inheritDoc
	 */
	@Override
	public void init() throws Exception {
		config = new LwjglApplicationConfiguration();
	    config.title = getAppTitle();
	    //config.samples = AConstants.ANTI_ALIASING;
		//config.width = AConstants.WINDOW_WIDTH;
	    //config.height = AConstants.WINDOW_HEIGHT;
	    
	    config.width = (int) getSize().x;
	    config.height = (int) getSize().y;
	    config.samples = getSamples();
	    
	    game = createGame();
	}
	
	/**
	 * Application window size at creation
	 * @return
	 */
	public abstract Vector2 getSize();
	/**
	 * Antialiasing samples
	 * @return
	 */
	public abstract int getSamples();
	
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
	public abstract String getAppTitle();
	
	
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
