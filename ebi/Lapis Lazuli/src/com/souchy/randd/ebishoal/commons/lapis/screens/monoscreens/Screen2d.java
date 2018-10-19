package com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Screen 
 * 
 * @author Souchy
 *
 */
public class Screen2d extends BaseScreen {

	/**
	 * Where we put all the hud actors, labels, etc
	 */
	private Stage stage;
	
	@Override
	protected Camera createCam() {
		OrthographicCamera camera = new OrthographicCamera(1600, 900); // space dimensions
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f); // camera view position (x,y) on the space (center of the space dimension)
        camera.update();
        return camera;
        /*
		Vector3 camPos = new Vector3(0, 0, 0);
		float zoom = 0f; // à mettre dans les settings public
		float near = 0.0f;   // à mettre dans les settings public
		float far = 200f;  // à mettre dans les settings public
		Camera cam = Cameras.orthogonal(camPos, Vector3.Zero, zoom, near, far);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0f);
		cam.update();
		return cam;*/
	}

	@Override
	protected Viewport createView(Camera cam) {
		return new ScreenViewport(cam);
		/*
		float aspectRatio = 16/9f;	// ratio à mettre dans les settings public
		float minWorldY = 50; 		// hauteur min à mettre ds settings privés
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		Viewport view = Viewports.extend(minWorldX, minWorldY,  cam);
		view.apply();
		return view;*/
	}

	@Override
	protected void createHook() {
		stage = new Stage(getViewport());
	}
	
	/**
	 * Lie les inputs au stage
	 */
	@Override
	public void show() {
        Gdx.input.setInputProcessor(stage);
		//Gdx.graphics.setWindowedMode(getViewport().widt, hudCamHeight)
	}
	@Override
	public void renderHook(float delta) {
		getCam().update();
		stage.act(delta);
		stage.draw();
	}
	
	/**
	 * @return Le stage servant de HUD à tous les actors, labels, etc
	 */
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	
}
