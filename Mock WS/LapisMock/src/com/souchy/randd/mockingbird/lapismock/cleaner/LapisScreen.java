package com.souchy.randd.mockingbird.lapismock.cleaner;

import com.badlogic.gdx.Gdx;
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
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

@SuppressWarnings("deprecation")
public abstract class LapisScreen implements LapisScreenCreator, LapisScreenRenderer {

	// ----------------------------------------
	// Camera ---------------------------------
	// ----------------------------------------
	private Camera cam;
	private CameraInputController camController;

	// ----------------------------------------
	// Environment ----------------------------
	// ----------------------------------------
	private Environment env;
	private DirectionalShadowLight shadowLight;

	// ----------------------------------------
	// World ----------------------------------
	// ----------------------------------------
	private World world;
	private BoundingBox worldBB;
	private Vector3 worldCenter;

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
	private FrameBuffer fbo;

	// ----------------------------------------
	// UI -------------------------------------
	// ----------------------------------------
	private AbstractLmlView view;
	
	
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
		// create camera
		cam = createCam(false);
		
		// create environment lights
		env = createEnvironment();
		shadowLight = createShadowLight(env);
		
		// create world
		world = createWorld(); //new World(env);
		worldBB = world.getBoundingBox();
		worldCenter = worldBB.getCenter(new Vector3());
		
		// create UI
		view = createUI();
		GlobalLML.getLmlParser().createView(view, view.getTemplateFile());

		// create model batches
		modelBatch = createWorldBatch();
		shadowBatch = createWorldShadowBatch();
		pfxBatch = createPfxBatch();
		
		// create FBO and its sprite batch renderer for post process
		fbo = createFBO();
		batch = createSpriteBatch();
	}

	/**
	 * Default show method. <br>
	 * Sets the Gdx input processor to this screen's cam controller.
	 */
	@Override
	public void show() {
		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
	}

	/**
	 * Default render method. <br>
	 * Updates the camera controller, then calls : act(delta), updateLights(delta), super.render(delta);
	 */
	@Override
	public void render(float delta) {
		// update input controller
		camController.update();
		
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
		batch.draw(new Texture(Gdx.files.absolute("G:\\Assets\\test\\glazedTerracotta.png")), 500, 500);
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
	
	
	@Override public void resize(int width, int height) { }
	@Override public void pause() { }
	@Override public void resume() { }
	@Override public void hide() { }
	
	
	@Override
	public void dispose() {
		world.dispose();
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
		return worldCenter;
	}

	@Override
	public ModelBatch getPfxBatch() {
		return pfxBatch;
	}
	
}
