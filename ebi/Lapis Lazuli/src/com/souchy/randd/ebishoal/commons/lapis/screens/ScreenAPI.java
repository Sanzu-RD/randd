package com.souchy.randd.ebishoal.commons.lapis.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class ScreenAPI implements Screen {


	/**
	 * Creates this screen's camera, view and calls the createHook. <br>
	 * {@link #BaseScreen.createCam()} <br>
	 * {@link #BaseScreen.createView(Camera cam)} <br>
	 * {@link #BaseScreen.createHook()} <br>
	 */
	public abstract void create();
	
	/**
	 * Called in {@link #BaseScreen.render(float delta)} . Overridable.
	 */
	public void clearScreen() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(getBGColor().r, getBGColor().g, getBGColor().b, getBGColor().a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		/*Gdx.gl.glClear(
				  GL20.GL_COLOR_BUFFER_BIT 
				| GL20.GL_DEPTH_BUFFER_BIT 
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));*/
	}
	public Color getBGColor(){ return Color.SKY; }
	
	/**
	 * Called in create(); <br>
	 * Just make a call to return Cameras.xxx(....);
	 * @return a new camera for this screen
	 */
	protected abstract Camera createCam();
	/**
	 * Called in create(); <br>
	 * Just make a call to return Viewports.xxx(....);
	 * @param cam - This screen's camera as it is created right before the viewport
	 * @return a new viewport for this screen
	 */
	protected abstract Viewport createView(Camera cam);
	/**
	 * Called last in {@link #BaseScreen.create()} <br>
	 * Use it to create your Batch, Environment, Lights, etc when the screen gets created.
	 */
	protected abstract void createHook();
	/**
	 * Called in {@link #BaseScreen.render(float delta)}, right after clearing the screen.
	 * @param delta - time since the last render
	 */
	protected abstract void renderHook(float delta);
	
	/** 
	 * There are things to dispose in screens implementations. Ex: modelBatch, UI components, ...  
	 */
	@Override 
	public abstract void dispose();
	
}
