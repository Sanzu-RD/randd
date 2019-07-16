package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

public class SapphireScreen extends LapisScreen {
	
	// light cycle
	private float time = 0; // current time
	private float period = 30; // period time in seconds
	private double radius = 2; // circle radius
	
	@Override
	protected void act(float delta) {
		// update title
		Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
		// update lights
		time += delta;
		if(time >= period) time = 0;
		double radian = ((period - time) / period) * 2 * Math.PI;
		getShadowLight().direction.x = (float) (Math.sin(radian) / radius);
		getShadowLight().direction.y = (float) (Math.cos(radian) / radius);
		//getShadowLight().direction.z = -0.5f;
	}
	
	@Override
	public LineDrawing createLining(Camera cam, BoundingBox worldBB) {
		return null;// super.createLining(cam, worldBB);
	}
	
	@Override
	public LapisHud createUI() {
		var view = new SapphireHud();
		//Log.info("life : " + view.life.getColor() + ", " + view.life.getStyle().fontColor);
		return view;
	}
	
	public InputProcessor createInputProcessor() {
		var multi = new InputMultiplexer();
		if(getView() != null) multi.addProcessor(getView().getStage());
		multi.addProcessor(new SapphireController(getCamera()));
		return multi;
	}
	
	@Override
	public Camera createCam(boolean useOrtho) {
//		float viewportSize = 17; // acts as a zoom (lower number is closer zoom)
//		var cam = new OrthographicCamera(viewportSize * 16 / 9, viewportSize);
		return new OrthographicCamera();
	}
	
	@Override
	public Viewport createViewport(Camera cam) {
		float viewportSize = 16; // getWorldCenter().x * 2; // acts as a zoom (lower number is closer zoom)
		var viewport = new ExtendViewport(viewportSize * 16/9, viewportSize, cam);
		//var viewport = new SapphireViewport(viewportSize * 16 / 9, viewportSize, cam);
		//var viewport = new ScreenViewport(cam);
		//viewport.setUnitsPerPixel(0.02f);
		return viewport;
	}
	
	@Override
	public World createWorld() {
		return new SapphireWorld();
	}

	/**
	 * Get the color to clear the screen with
	 */
//	public Color getBackgroundColor() {
//		return Color.PINK;
//	}
//	
//	@Override
//	public Texture createBackground() {
//		return null; //super.createBackground();
//	}

}
