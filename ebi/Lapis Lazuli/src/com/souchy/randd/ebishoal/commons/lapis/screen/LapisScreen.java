package com.souchy.randd.ebishoal.commons.lapis.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

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
	private DirectionalShadowLight shadowLight;

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
	private ModelBatch pfxBatch;

	// ----------------------------------------
	// FBO ------------------------------------
	// ----------------------------------------
	private SpriteBatch batch;
	public FrameBuffer fbo;

	// ----------------------------------------
	// UI -------------------------------------
	// ----------------------------------------
	private LapisHud view;
	private LineDrawing lining;
	private Texture background;
	


	/**
	 * Default CTOR
	 */
	public LapisScreen() {
		init();
	}
	

	/**
	 * Default initialization method. <br>
	 * Creates the camera, environment, world, UI, model batches, fbo and sprite batch
	 */
	public void init() {
		// create world
		world = createWorld(); //new World(env);
		//worldBB = world.getBoundingBox();
		//worldCenter = world.getCenter(); //getWorldBB().getCenter(new Vector3());
		
		// create camera
		cam = createCam(false);
		viewport = createViewport(cam);
		resetCamera();

		// create environment lights
		env = createEnvironment();
		shadowLight = createShadowLight(env);

		// create model batches
		modelBatch = createWorldBatch();
		shadowBatch = createWorldShadowBatch();
		pfxBatch = createPfxBatch();
		
		// create FBO and its sprite batch renderer for post process
		fbo = createFBO();
		batch = createSpriteBatch();
		
		// create UI
		view = createUI();
		
		// create lining
		lining = createLining(getCamera(), new BoundingBox(Vector3.Zero, getWorldCenter().cpy().scl(3))); //getWorldBB());
		
		background = createBackground();
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
		inputProcessor = createInputProcessor();
		if(getInputProcessor() != null) Gdx.input.setInputProcessor(getInputProcessor());
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
		if(getInputProcessor() != null && getInputProcessor() instanceof CameraInputController) ((CameraInputController) inputProcessor).update();
		
		// act
		act(delta);
		
		// update lights
		updateLight(delta);
		
		// render 
		LapisScreenRenderer.super.render(delta);
	}
	
	
	/**
	 * Draw a background texture if wanted.
	 */
	public void drawBackground(SpriteBatch batch) {
//		if(background != null && getViewport() != null) getSpriteBatch().draw(background, getViewport().getScreenX(), getViewport().getScreenY());
//		else 
		if(background != null) getSpriteBatch().draw(background, 0, 0);
		//else getSpriteBatch().draw(fboRegion, getViewport().getScreenX(), getViewport().getScreenY());
	}
	
	/*
	 * Update the lightning direction
	 */
	protected void updateLight(float delta) {
		
	}
	
	/*
	 * For stuff like animations & effects ...
	 */
	protected void act(float delta) {
		// update title
		Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
	}
	
	
	@Override public void resize(int width, int height) {
		// resize le FBO
		if(fbo != null) fbo = createFBO();
		// resize la sprite batch du fbo
		getSpriteBatch().getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		// resize le viewport du world
		if(getViewport() != null) getViewport().update(width, height, false);
		// resize le hud
		getView().resize(width, height, true);
		
		//var center = getWorldCenter();
		//getCamera().position.set(center.x, center.y, center.z); 
		//getCamera().update();
	}
	@Override public void pause() { }
	@Override public void resume() { }
	@Override public void hide() { }
	
	
	@Override
	public void dispose() {
		world.dispose();
		getView().dispose();
		getSpriteBatch().dispose();
		getModelBatch().dispose();
		getShadowBatch().dispose();
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
		return batch;
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
	public DirectionalShadowLight getShadowLight() {
		return shadowLight;
	}

	@Override
	public Vector3 getWorldCenter() {
		return world.getCenter(); 
	}

	@Override
	public ModelBatch getPfxBatch() {
		return pfxBatch;
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

	public Texture getBackground() {
		return background;
	}
	
	
}
