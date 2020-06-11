package com.souchy.randd.ebishoal.sapphire.gfx;

import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Collections;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shadows.LapisDSL;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.ebishoal.sapphire.confs.AssetConfs;
import com.souchy.randd.ebishoal.sapphire.controls.SapphireController;
import com.souchy.randd.ebishoal.sapphire.main.SapphireEntitySystem;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;

import data.new1.ecs.Engine;

public class SapphireScreen extends LapisScreen {
	
	// light cycle
	private float time = 0; // current time
	private float period = 30; // period time in seconds
	private double radius = 2; // circle radius
	
	public SapphireHud hud;
	private SapphireController controller;
	
	public SapphireScreen() {
//		activateShadows = false;
	}
	
	@Override
	protected void act(float delta) {
		// update title
		Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
		// update lights
		time += delta;
		if(time >= period) time = 0;
		double radian = ((period - time) / period) * 2 * Math.PI;
		if(getShadowLight() != null) {
			getShadowLight().direction.x = (float) (Math.sin(radian) / radius);
			getShadowLight().direction.y = (float) (Math.cos(radian) / radius);
			//getShadowLight().direction.z = -0.5f;
		}
		if(controller != null) controller.act(delta);
		getCamera().update();
		
		// update all systems and entities
		SapphireGame.fight.update(delta);
	}
	
	
	@Override
	public LineDrawing createLining(Camera cam, BoundingBox worldBB) {
		return null; //super.createLining(cam, worldBB);
	}
	
	@Override
	public LapisHud createUI() {
		hud = new SapphireHud();
		//Log.info("life : " + view.life.getColor() + ", " + view.life.getStyle().fontColor);
		return hud;
	}
	
	public InputProcessor createInputProcessor() {
		var multi = new InputMultiplexer();
		if(getView() != null) multi.addProcessor(getView().getStage());
		multi.addProcessor(controller = new SapphireController(getCamera()));
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
		// how many meters high is the screen (ex 1920/1080 = 28.4/16)
		float viewportSize = 16; // getWorldCenter().x * 2; // acts as a zoom (lower number is closer zoom)
		var viewport = new ExtendViewport(viewportSize * 16/9, viewportSize, cam);
		//var viewport = new SapphireViewport(viewportSize * 16 / 9, viewportSize, cam);
		//var viewport = new ScreenViewport(cam);
		//viewport.setUnitsPerPixel(0.02f);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return viewport;
	}
	
	@Override
	public World createWorld() {
		return new SapphireWorld();
	}

	public void topView() {
//		var viewportSize = 17f; // acts as a zoom (lower number is closer zoom)
//		var factor = 0.6f;
		var center = getWorldCenter(); //getWorldBB();
//		getCamera().viewportWidth = viewportSize * 16 / 9;
//		getCamera().viewportHeight = viewportSize ;
		getCamera().direction.set(0, 0, -1f);
		getCamera().up.set(1, 1, -1f);
		getCamera().position.set(center.x, center.y, center.z); //bb.max.x * factor, bb.max.y * (1 - factor), 0); //bb.max.z * factor);
		getCamera().near = -30f;
		getCamera().far = 120f;
		getCamera().update();
	}
	
	@Override
	public void resetCamera() {
//		super.resetCamera();
		topView();
		getCamera().rotate(45, getCamera().up.y, -getCamera().up.x, 0); 
	}
	
	
	@Override
	public void renderWorld() {
		super.renderWorld();
		// render dynamic instances (cursor, creatures, terrain effects like glyphs and traps, highlighting effects ..)

		SapphireEntitySystem.family.forEach(e -> {
			var model = e.get(ModelInstance.class);
			if (model != null)
				getModelBatch().render(model);
		});
		
	}

}
