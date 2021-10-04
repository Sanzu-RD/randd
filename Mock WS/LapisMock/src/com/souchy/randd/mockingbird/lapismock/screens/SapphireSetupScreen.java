package com.souchy.randd.mockingbird.lapismock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisFiles;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireOwlConf;
import com.souchy.randd.ebishoal.sapphire.controls.Commands;
import com.souchy.randd.ebishoal.sapphire.controls.SapphireController;
import com.souchy.randd.ebishoal.sapphire.gfx.Highlight;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.ebishoal.sapphire.main.MusicPlayer;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;

public class SapphireSetupScreen extends SapphireScreen {

	private SapphireController controller;
	
	// light cycle
	private float time = 0; // current time
	private float period = 30; // period time in seconds
	private double radius = 2; // circle radius
	
	public SapphireSetupScreen() {
		SapphireOwl.conf = JsonConfig.readExternal(SapphireOwlConf.class, "./modules/");
		Gdx.files = new LapisFiles(Environment.root.toString());
		
		SapphireGame.gfx = this;

		LapisAssets.loadTextures(Gdx.files.internal("res/textures/Tex_krakken.PNG"));
		LapisAssets.loadModels(Gdx.files.internal("res/models/tileselector.g3dj"));
		LapisAssets.loadI18NBundles(Gdx.files.internal("res/i18n/"));
		// init elements
		Elements.values();
		// models configurations (creatures, spells, statuses)
		AssetData.loadResources();
		// init creatures & spells models
		DiamondModels.instantiate("com.souchy.randd.data.s1");
		// init commands
		Commands.init();
		// init music player
		SapphireGame.music = new MusicPlayer();
		Highlight.init();
		
		init(); 
	}

	@Override
	public void init() {
		super.init();
	}
	
	@Override
	protected void act(float delta) {
		// update lights cycle
		time += delta;
		if(time >= period) time = 0;
		double radian = ((period - time) / period) * 2 * Math.PI;
		if(getShadowLight() != null) {
			getShadowLight().direction.x = (float) (Math.sin(radian) / radius);
			getShadowLight().direction.y = (float) (Math.cos(radian) / radius);
			//getShadowLight().direction.z = -0.5f;
		}
		// update title
		Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
		// controller
		if(controller != null) controller.act(delta);
		// camera 
		getCamera().update();
	}
	
	@Override
	public World createWorld() {
		return new SapphireWorld();
	}

	@Override
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
		float viewportSize = 16;  // acts as a zoom (lower number is closer zoom)
		var viewport = new ExtendViewport(viewportSize * 16/9, viewportSize, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return viewport;
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
	public LapisHud createUI() {
		return null;
	}


	/**
	 * copy of LapisScreenRenderer
	 * Render the world models, characters, particle effects, etc
	 */
	public void renderWorld() {
		// render the cache (static terrain)
		if(RenderOptions.renderCache) getModelBatch().render(getWorld().cache, getEnvironment());
		
		// render dynamic instances (cursor, creatures, terrain effects like glyphs and traps, highlighting effects ..)
		getModelBatch().render(getWorld().instances, getEnvironment());
	}

	/*
	 * copy of LapisScreenRenderer
	 * Render the shadowmap for the world
	 */
	public void renderShadows() {
		// TODO create shadows *only for characters and z>0 blocks* then project *only onto blocks z=0*
		if(RenderOptions.renderCache) getShadowBatch().render(getWorld().cache, getEnvironment());
		// getShadowBatch().render(getWorld().instances, getEnvironment()); 
	}
	
	
	
}
