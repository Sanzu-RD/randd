package com.souchy.randd.tools.mapeditor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;

public class MapEditorCore extends LapisCore {
	
	public static final boolean debug = false;

	public static MapEditorCore core;
	public static MapEditorGame game;
	
	public MapEditorCore(String[] args) throws Exception {
		super(args);
	}
	
	public static void main(String[] args) throws Exception {  
	    //launch(core);
		
		LapisCore.arguments(args);
		// init les messages, messagehandlers, le logging, la configuration lwjgl, les lapis properties, l'icone, le game
		core = new MapEditorCore(args);
		core.start();
	 }


	@Override
	protected LapisGame createGame() {
		return game = new MapEditorGame();
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.tools.mapeditor" };
	}

	@Override
	public void addIcon(LwjglApplicationConfiguration config) {
		config.addIcon("res/textures/appicon4.png", FileType.Internal);
	}

	
}
