package com.souchy.randd.tools.mapeditor.main;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.tools.mapeditor.configs.EditorConf;
import com.souchy.randd.tools.mapeditor.configs.EditorConf.HudConfig.ComponentConfig;

public class MapEditorCore { //extends LapisCore {
	
	public static final boolean debug = false;

	public static MapEditorCore core;
	public static MapEditorGame game;
	public static EditorConf conf;
	
	public MapEditorCore() throws Exception {
		
	}
	
	public static void main(String[] args) throws Exception {  
	    //launch(core);
//		LapisCore.arguments(args);
//		// init les messages, messagehandlers, le logging, la configuration lwjgl, les lapis properties, l'icone, le game
//		core = new MapEditorCore(args);
//		core.start();

		conf = JsonConfig.read(EditorConf.class);
		conf.reset = false;
		Log.info("editor conf " + conf);
		
		game = new MapEditorGame();
		var config = getDefaultConfiguration();
	    new Lwjgl3Application(game, config);
	 }

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("HiddenEditor");
		//configuration.useVsync(true);
		//// Limits FPS to the refresh rate of the currently active monitor.
		//configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
		config.useVsync(conf.gfx.vsync);
		config.setForegroundFPS(conf.gfx.fps);
		config.setIdleFPS(conf.gfx.backgroundFps);
		//// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
		//// useful for testing performance, but can also be very stressful to some hardware.
		//// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
		config.setWindowedMode(conf.gfx.width, conf.gfx.height); // 1600, 900); // Liftoff.WIDTH, Liftoff.HEIGHT);
		config.setWindowPosition(conf.gfx.x, conf.gfx.y);
		//configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		config.setWindowIcon("res/textures/91757405.jpg");
		return config;
	}
	
}
