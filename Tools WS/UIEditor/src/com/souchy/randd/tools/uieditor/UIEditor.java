package com.souchy.randd.tools.uieditor;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;

public class UIEditor extends LapisCore {
	

	public static UIEditor core;
	public static UIEditorGame game;
	public static UIEditorScreen screen;

	public static void main(String[] args) throws Exception {
		LapisCore.arguments(args);
		core = new UIEditor(args);
		core.start();
	}
	
	public UIEditor(String[] args) throws Exception {
		super(args);
	}
	
	@Override
	protected UIEditorGame createGame() {
		return game = new UIEditorGame();
	}

	@Override
	public void addIcon(LwjglApplicationConfiguration config) { 
		config.addIcon("res/textures/Tex_krakken.PNG", FileType.Internal);
	}
	
	private static class UIEditorGame extends LapisGame {
		@Override
		public void init() {
			UIEditor.screen = new UIEditorScreen();
		}
		@Override
		public UIEditorScreen getStartScreen() {
			return UIEditor.screen;
		}
	}

}
