package com.souchy.randd.mockingbird.lapismock;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.mockingbird.lapismock.screens.MockScreen3;

public class LapisMock extends LapisCore {
	
	public LapisMock(String[] args) throws Exception {
		super(args);
		// TODO Auto-generated constructor stub
	}

	public static /* final */ LapisMock core;
	
	public static void main(String[] args) throws Exception {
		LapisCore.arguments(args);
		core = new LapisMock(args);
//		launch(core);
		core.start();
	}
	
	@Override
	protected LapisGame createGame() {
		return new MockGame();
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.mockingbird.lapismock" };
	}
	
	public MockGame getGame() {
		return (MockGame) super.getGame();
	}
	
	public static class MockGame extends LapisGame {

		public BaseScreen screen;
		
		@Override
		public BaseScreen getStartScreen() {
			return screen;
		}

		@Override
		public void init() {
			screen = new MockScreen3();
		} 
		
	}

	@Override
	public void addIcon(LwjglApplicationConfiguration config) {
		// TODO Auto-generated method stub
		
	}
	
	
}
