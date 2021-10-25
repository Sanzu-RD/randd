package com.souchy.randd.ebishoal.commons.lapis.gfx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.jeffekseer.EffectManager;
import com.souchy.randd.commons.tealwaters.commons.Profiler;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shadows.LapisDSL;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

import effekseer.swig.EffekseerBackendCore;

@SuppressWarnings("deprecation")
public abstract class LapisScreen implements LapisScreenCreator, LapisScreenRenderer {

	// ----------------------------------------
	// Camera ---------------------------------
	// ----------------------------------------
	private Camera cam;
	private Viewport viewport;
	private InputProcessor inputProcessor;

	// ----------------------------------------
	// Environment ----------------------------
	// ----------------------------------------
	private Environment env;
	private LapisDSL shadowLight;

	// ----------------------------------------
	// World ----------------------------------
	// ----------------------------------------
	private World world;
	//private BoundingBox worldBB;
	//private Vector3 worldCenter;

	// ----------------------------------------
	// Model batch ----------------------------
	// ----------------------------------------
	private ModelBatch modelBatch;
	private ModelBatch shadowBatch;

	// ----------------------------------------
	// Particle System ------------------------
	// ----------------------------------------
	private ParticleSystem pfxSystem;
	private ModelBatch pfxBatch;

	// ----------------------------------------
	// Post Processing FBO --------------------
	// ----------------------------------------
	public FrameBuffer fbo;
	private SpriteBatch ppBatch;

	// ----------------------------------------
	// UI -------------------------------------
	// ----------------------------------------
	private LapisHud view;
	
	// ----------------------------------------
	// OTHER -------------------------------------
	// ----------------------------------------
	private LineDrawing lining;
	private Texture background;

	// ----------------------------------------
	// EFFEKSEER PARTICLE EFFECTS -------------
	// ----------------------------------------
	private EffectManager effekseerManager;


	/**
	 * Default CTOR
	 */
	public LapisScreen() {
//		init();
	}
	

	/**
	 * Default initialization method. <br>
	 * Creates the camera, environment, world, UI, model batches, fbo and sprite batch
	 */
	public void init() {
		var profiler = new Profiler();
		profiler.poll("LapisScreen");
		// create world
		world = createWorld(); //new World(env);
		profiler.poll("LapisScreen create world");
		//worldBB = world.getBoundingBox();
		//worldCenter = world.getCenter(); //getWorldBB().getCenter(new Vector3());
		
		// create camera
		cam = createCam(false);
		viewport = createViewport(getCamera());
		resetCamera();
		profiler.poll("LapisScreen create cam");

		// create environment lights
		env = createEnvironment();
		shadowLight = createShadowLight(getViewport());
		if(getShadowLight() != null) {
			env.shadowMap = shadowLight;
			env.add(shadowLight);
		}
		profiler.poll("LapisScreen create env");

		// create model batches
		modelBatch = createWorldBatch();
		shadowBatch = createWorldShadowBatch();
		profiler.poll("LapisScreen create batches");
		
		// create particle effects system
		pfxSystem = createPfxSystem(getCamera());
		pfxBatch = createPfxBatch();
		effekseerManager = createEffekseer(getCamera(), getViewport());
		profiler.poll("LapisScreen create pfx");
		
		// create FBO and its sprite batch renderer for post process
		fbo = createFBO(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ppBatch = createSpriteBatch();
		profiler.poll("LapisScreen create fbo pp");
		
		// create UI
		view = createUI();
		profiler.poll("LapisScreen create ui");

		// create controller 
		inputProcessor = createInputProcessor();
		profiler.poll("LapisScreen create input");
		
		// create lining
		lining = createLining(getCamera(), new BoundingBox(Vector3.Zero, getWorldCenter().cpy().scl(3))); //getWorldBB());
		profiler.poll("LapisScreen create lining");
		
		// create background image
		background = createBackground();
		profiler.poll("LapisScreen create bkg");
	}
	
	public void resetCamera() {
//		var viewportSize = 17f; // acts as a zoom (lower number is closer zoom)
//		var factor = 0.6f;
		var center = getWorldCenter(); //getWorldBB();
//		getCamera().viewportWidth = viewportSize * 16 / 9;
//		getCamera().viewportHeight = viewportSize ;
		getCamera().direction.set(1, 1, -1.5f);
		getCamera().up.set(1, 1, 1f);	
		getCamera().position.set(center.x, center.y, center.z); //bb.max.x * factor, bb.max.y * (1 - factor), 0); //bb.max.z * factor);
		getCamera().near = -30f;
		getCamera().far = 120f;
		getCamera().update();
	}
	
	/**
	 * Default show method. <br>
	 * Sets the Gdx input processor to this screen's cam controller.
	 */
	@Override
	public void show() {
		if(getInputProcessor() != null) 
			Gdx.input.setInputProcessor(getInputProcessor());
	}
	
	public InputProcessor createInputProcessor() {
		var multi = new InputMultiplexer();
		multi.addProcessor(getView().getStage());
		multi.addProcessor(new CameraInputController(getCamera()));
		return multi;
	}

	/**
	 * Default render method. <br>
	 * Updates the camera controller, then calls : act(delta), updateLights(delta), super.render(delta);
	 */
	@Override
	public void render(float delta) {
		// update input controller
		if(getInputProcessor() != null && getInputProcessor() instanceof CameraInputController) 
			((CameraInputController) getInputProcessor()).update();
		
		// act
		act(delta);
		
		// render 
		LapisScreenRenderer.super.render(delta);
	}
	
	
	/**
	 * Draw a background texture if wanted.
	 */
	public void drawBackground(SpriteBatch batch) {
//		if(background != null && getViewport() != null) getSpriteBatch().draw(background, getViewport().getScreenX(), getViewport().getScreenY());
//		else 
		if(background != null) batch.draw(background, 0, 0);
		//else getSpriteBatch().draw(fboRegion, getViewport().getScreenX(), getViewport().getScreenY());
	}
	
	/*
	 * For stuff like animations & effects ...
	 */
	protected abstract void act(float delta);
	
	
	@Override 
	public void resize(int width, int height) {
//		Log.info("resize : " + width + ", " + height);
		// resize le FBO
		if(fbo != null) fbo = createFBO(width, height);
		// resize la sprite batch du fbo / post processing
		if(getSpriteBatch() != null) getSpriteBatch().getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		// resize le viewport du world
		if(getViewport() != null) getViewport().update(width, height, false);
		// resize le hud
		if(getView() != null) getView().resize(width, height, true);
		// resize le viewport de la shadowlight pour sa shadowmap
		if(getShadowLight() != null) getShadowLight().update(width, height);
		
		// resize la sprite batch qui n'a pas de post processing
		cleanSpriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
//		var center = getWorldCenter();
//		getCamera().position.set(center.x, center.y, center.z); 
//		getCamera().update();
	}
	@Override public void pause() { }
	@Override public void resume() { }
	@Override public void hide() { }
	
	
	@Override
	public void dispose() {
		getWorld().dispose();
		getView().dispose();
		getSpriteBatch().dispose();
		getModelBatch().dispose();
		getShadowBatch().dispose();
		if(getEffekseer() != null) {
			getEffekseer().dispose();
			EffekseerBackendCore.Terminate();
		}
	}

	@Override
	public ModelBatch getShadowBatch() {
		return shadowBatch;
	}

	@Override
	public ModelBatch getModelBatch() {
		return modelBatch;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public FrameBuffer getFBO() {
		return fbo;
	}

	@Override
	public SpriteBatch getSpriteBatch() {
		return ppBatch;
	}

	@Override
	public AbstractLmlView getView() {
		return view;
	}

	@Override
	public Camera getCamera() {
		return cam;
	}

	@Override
	public Environment getEnvironment() {
		return env;
	}

	@Override
	public LapisDSL getShadowLight() {
		return shadowLight;
	}

	@Override
	public Vector3 getWorldCenter() {
		return world.getCenter(); 
	}

	@Override
	public InputProcessor getInputProcessor() {
		return inputProcessor;
	}

	@Override
	public Viewport getViewport() {
		return viewport;
	}

	@Override
	public LineDrawing getLining() {
		return lining;
	}

	@Override
	public Texture getBackground() {
		return background;
	}

	@Override
	public ModelBatch getPfxBatch() {
		return pfxBatch;
	}
	
	@Override
	public ParticleSystem getPfxSystem() {
		return pfxSystem;
	}
	
	@Override
	public EffectManager getEffekseer() {
		return effekseerManager;
	}
	
}
