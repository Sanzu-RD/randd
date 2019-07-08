package com.souchy.randd.mockingbird.lapismock;

import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.mockingbird.lapismock.screens.MockScreen3;

public class LapisMock extends LapisCore {
	
	public static final LapisMock core = new LapisMock();
	
	public static void main(String[] args) throws Exception {  
	     launch(core); 
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
		public void onCreateHook() {
			screen = new MockScreen3();
		} 
		
	}
	
	
}
