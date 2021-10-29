package com.souchy.randd.mockingbird.lapismock.mains;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.mockingbird.lapismock.screens.*;

public class LapisMock extends LapisCore {
	
	public LapisMock(String[] args) throws Exception {
		super(args);
		// TODO Auto-generated constructor stub
	}

	public static LapisMock core;
	public static Screen screen;
	
	public static void main(String[] args) throws Exception {
		LapisCore.arguments(args);
		core = new LapisMock(args);
//		launch(core);
		core.start();
	}
	
	private static Screen initScreen() {
		return new MockScreen6();
	}
	
	
	
	
	
	
	
	
	@Override
	protected LapisGame createGame() {
		return new MockGame();
	}

	public MockGame getGame() {
		return (MockGame) super.getGame();
	}
	
	public static class MockGame extends LapisGame {
		@Override
		public Screen getStartScreen() {
			return LapisMock.screen;
		}
		@Override
		public void init() {
			LapisMock.screen = initScreen();
		} 
		
	}

	@Override
	public void addIcon(LwjglApplicationConfiguration config) {
		
	}
	
	
}
