package com.souchy.randd.mockingbird.lapismock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.mockingbird.lapismock.worlds.World5;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class MockScreen5 {

	public Camera camera;
	public Viewport viewport;
	public Environment env;
	public CameraInputController controller;
	
	public ModelBatch batch;
	public ModelCache cache;

	public MockScreen5() {
		// init stuff
		camera = new OrthographicCamera();
		controller = new CameraInputController(camera);
		viewport = createViewport(camera);
		env = createEnvironment();
		batch = new ModelBatch(); 
		Gdx.input.setInputProcessor(controller);
		
		cache = new World5().cache;
		
		// set camera pos
		resetCamera();
	}

	private Environment createEnvironment() {
		Environment env = new Environment();
		float ambiant = 0.8f;
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 1f));
		return env;
	}
	

	private Viewport createViewport(Camera cam) {
		float viewportSize = 16; 
		Viewport viewport = new ExtendViewport(viewportSize * 16/9, viewportSize, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return viewport;
	}
	
	public void topView() {
		Vector3 center = new Vector3(7.5f, 7.5f, 0f);
		camera.direction.set(0, 0, -1f);
		camera.up.set(1, 1, -1f);
		camera.position.set(center.x, center.y, center.z); 
		camera.near = -30f;
		camera.far = 120f;
		camera.update();
	}
	
	public void resetCamera() {
		topView();
		camera.rotate(45, camera.up.y, -camera.up.x, 0);
	}

	
	
	
}
