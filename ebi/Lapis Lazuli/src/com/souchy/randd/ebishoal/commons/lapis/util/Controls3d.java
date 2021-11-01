package com.souchy.randd.ebishoal.commons.lapis.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.ControlsConfig.Controls3dConfig;
import com.souchy.randd.ebishoal.commons.lapis.util.KeyCombination.KeyCombinationListener;

/**
 * Basic 3d controls with the mouse and keyboard with a JsonConfig and KeyCombinations
 * 
 * @author Blank
 * @date 26 oct. 2021
 */
public class Controls3d extends CameraInputController {
	
	public boolean activateBaseCamControl = false;
	protected float translationSpeed = 10;
	protected float rotationSpeed = 60;
	protected Vector3 translation = Vector3.Zero.cpy();
	protected Vector3 rotationUnit = Vector3.Zero.cpy();
	protected Vector3 rotation = Vector3.Zero.cpy();
	protected float zoom = 0;
	/** previous position touched down */
	protected int previousX;
	/** previous position touched down */
	protected int previousY;
	/** var floorHeight = Constants.floorZ; constant from jade */
	public float floorHeight = 0;
	
	public Viewport viewport;
	public Controls3dConfig conf;
	public KeyCombinationListener keys = new KeyCombinationListener(this::initCombos);
	
	public Controls3d(Camera camera, Viewport viewport) {
		super(camera);
		this.viewport = viewport;
		keys.initCombos();
	}
	
	public void initCombos() {
		keys.putCombo(conf.translateUpX, () -> translation.add( camera.up.x,  camera.up.y, 0));
		keys.putCombo(conf.translateDownX, () -> translation.add(-camera.up.x, -camera.up.y, 0));
		keys.putCombo(conf.translateLeftX, () -> translation.add(-camera.up.y,  camera.up.x, 0));
		keys.putCombo(conf.translateRightX, () -> translation.add( camera.up.y, -camera.up.x, 0));
		
		keys.putCombo(conf.rotateUpX, () -> camera.rotate(45, -camera.up.y, camera.up.x, 0));
		keys.putCombo(conf.rotateDownX, () -> camera.rotate(45, camera.up.y, -camera.up.x, 0));
		keys.putCombo(conf.rotateRightX, () -> camera.rotate(45, 0, 0, 1));
		keys.putCombo(conf.rotateLeftX, () -> camera.rotate(45, 0, 0, -1));
	}

	/**
	 * Called on every frame for overtime control
	 * @param delta
	 */
	public void act(float delta) {
		Vector3 up  = camera.up;
		Vector3 dir = camera.direction;
		Vector3 pos = camera.position;
		
		translation.set(0, 0, 0);
		rotation.set(0, 0, 0);
		rotationUnit.set(0, 0, 0);

		// Translation XY
		if(conf.translateUpFree.isPressed()) translation.add( up.x,  up.y, 0);
		if(conf.translateDownFree.isPressed()) translation.add(-up.x, -up.y, 0);
		if(conf.translateLeftFree.isPressed()) translation.add(-up.y,  up.x, 0);
		if(conf.translateRightFree.isPressed()) translation.add( up.y, -up.x, 0);
		// Rotation XZ only if there's no translation (sorry)
		if(translation.isZero()) {
			if(conf.rotateUpFree.isPressed()) rotationUnit.add(-1,  1, 0); // look up
			if(conf.rotateDownFree.isPressed()) rotationUnit.add( 1, -1, 0); // look down
			if(conf.rotateLeftFree.isPressed()) rotationUnit.add(0, 0, -1f); // look left
			if(conf.rotateRightFree.isPressed()) rotationUnit.add(0, 0,  1f); // look right
		}
		
		// Zoom
		if(conf.zoomInFree.isPressed()) 
			scrolledFloat(0.2f);
		if(conf.zoomOutFree.isPressed())
			scrolledFloat(-0.2f);
		
		
		// keep it a unit vector even if we move in two directions at the same time
		rotationUnit.nor();
		
		scrolledFloat(zoom);

		// p = le point qu'on regarde avec la caméra
		float scl = Math.abs(camera.position.z / dir.z);
		Vector3 p = new Vector3(dir.x * scl + pos.x, dir.y * scl + pos.y, 0);

		float distance = translationSpeed * delta;
		Vector3 movement = translation.cpy().scl(distance);

		float angle = rotationSpeed * delta;
		rotation.x = rotationUnit.x * up.y;
		rotation.y = rotationUnit.y * up.x;
		rotation.z = rotationUnit.z;

		camera.position.add(movement);
		camera.rotateAround(p, rotation, angle);

		if(autoUpdate) camera.update();
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		//System.out.println("touch Down " + button + " / " + x + ", " + y);
		previousX = x;
		previousY = y;

		if(!activateBaseCamControl) return true;
		return super.touchDown(x, y, pointer, button);
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if(!conf.controlCameraWithMouse) return super.touchDragged(x, y, pointer);
		
		Vector3 up  = camera.up;
		//Vector3 dir = camera.direction;
		//Vector3 pos = camera.position;
		var worldpos = getCursorWorldPos();
		translation.set(0, 0, 0);
		rotation.set(0, 0, 0);
		rotationUnit.set(0, 0, 0);

		// delta entre le dernier point dragged de la souris et le présent
		float dx = x - previousX;
		float dy = y - previousY;
		
		
		if(Gdx.input.isButtonPressed(conf.buttonDrag)) {
			//Log.info("drag translate");
			// can't get them to work together so let's split them unfortunately
			if(Math.abs(dx) > Math.abs(dy)) {
				if(dx < 0) translation.add(up.y, -up.x, 0);
				if(dx > 0) translation.add(-up.y, up.x, 0);
			} else {
				if(dy > 0) translation.add(up.x, up.y, 0);
				if(dy < 0) translation.add(-up.x, -up.y, 0);
			}
			
			float distance = 3 * (Math.abs(dx) + Math.abs(dy)) / Gdx.graphics.getFramesPerSecond(); // * Gdx.graphics.getDeltaTime(); // * delta;
			Vector3 movement = translation.scl(distance);
			//Log.info("drag delta " + new Vector3(dx, dy, 0) + ", move " + movement);
			camera.position.add(movement);
		}

		if(Gdx.input.isButtonPressed(conf.buttonRotate)) {
			//Log.info("drag rotate");
			if(dx > 0) rotation.add( -up.y, -up.x, 0);
			if(dx < 0) rotation.add( up.y, up.x, 0);
			if(dy > 0) rotation.add( 0, 0, -1);
			if(dy < 0) rotation.add( 0, 0, 1);

			float angle = rotationSpeed / 10f;// * 5;// * (Math.abs(dx) + Math.abs(dx)); // * delta;
			camera.rotateAround(worldpos, rotation, angle);
		}
		if(autoUpdate)
			camera.update();
		
		previousX = x;
		previousY = y;

		if(!activateBaseCamControl) return true;
		return super.touchDragged(x, y, pointer);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		keys.keyDown(keycode);
		if(!activateBaseCamControl) return true;
		return super.keyDown(keycode);
	}
	@Override
	public boolean keyUp(int keycode) {
		keys.keyUp(keycode);
		if(!activateBaseCamControl) return true;
		return super.keyUp(keycode);
	}

	@Override
	public boolean scrolled(int amount) {
		scrolledFloat(amount);
		if(!activateBaseCamControl) return true;
		return super.scrolled(amount);
	}
	
	
	@Override
	public boolean keyTyped(char character) {
		if(!activateBaseCamControl) return true;
		return super.keyTyped(character);
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(!activateBaseCamControl) return true;
		return super.mouseMoved(screenX, screenY);
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(!activateBaseCamControl) return true;
		return super.touchUp(screenX, screenY, pointer, button);
	}
	
	
	// for lwjgl 3
//	@Override 
//	public boolean scrolled(float amountX, float amountY) {
//		scrolledFloat(amountY);
//		return true;
//	}
	
	protected void scrolledFloat(float amount) {
		var ratio = camera.viewportWidth / camera.viewportHeight;
		camera.viewportWidth += amount;
		camera.viewportHeight = camera.viewportWidth / ratio;
		if (autoUpdate)
			camera.update();
	}
	
	public Vector3 getCursorWorldPos() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		return getCursorWorldPos(x, y);
	}
	
	public Vector3 getCursorWorldPos(int x, int y) {
		var vp = viewport;
		Ray ray = camera.getPickRay(x, y, vp.getScreenX(), vp.getScreenY(), vp.getScreenWidth(), vp.getScreenHeight());
		var distance = (floorHeight - ray.origin.z) / ray.direction.z;
		Vector3 v = new Vector3();
		v.set(ray.direction).scl(distance).add(ray.origin);
		v.x = (float) Math.floor(v.x) + 0f;
		v.y = (float) Math.floor(v.y) + 0f;
		v.z = floorHeight;
		return v;
	}
	
}
