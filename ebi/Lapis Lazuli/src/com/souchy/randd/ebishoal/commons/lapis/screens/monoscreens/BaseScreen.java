package com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.screens.ScreenAPI;

public abstract class BaseScreen extends ScreenAPI {

	
	private Camera cam;
	private Viewport view;
	
	@Override
	public void create() {
		cam = createCam();
		view = createView(cam);
		createHook();
	}
	
	@Override
	public void show() { 
		// what do we do on show ?
	}
	/**
	 * Calls {@link #BaseScreen.clearScreen()} then {@link #BaseScreen.renderHook(float delta)} 
	 */
	@Override
	public void render(float delta) {
		// clear the screen before calling to render things
		clearScreen();
		renderHook(delta);
	}
	/**
	 * Called when the window changes size. <br>
	 * Aka this screen changes size. <br>
	 * So we update the viewport size here. <br>
	 * <b>Screen2d</b> uses update with <b>true</b> to center screen and <b>Screend3d</b> uses <b>false</b> to keep the current camera position
	 */
	@Override public abstract void resize(int width, int height); 
	@Override public void pause() { }
	@Override public void resume() { }
	@Override public void hide() { }
	
	
	/** @return this screen's camera */
	public Camera getCam() {
		return cam;
	}
	/** @return this screen's viewport */
	public Viewport getViewport() {
		return view;
	}
	
}
