package com.souchy.randd.tools.mapeditor.listeners;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.CONTROL_RIGHT;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.SHIFT_RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.ui.mapeditor.EditorScreenHud;

public class Controls3d extends CameraInputController {
	
	public Controls3d(Camera camera) {
		super(camera);
		this.cam = camera;
	}

	private Camera cam;
	
	private Vector3 ut = Vector3.Zero.cpy();
	private Vector3 ur = Vector3.Zero.cpy();
	
	private float previousX;
	private float previousY;
	
	private float translationSpeed = 10;
	private float rotationSpeed = 60;
	private float zoomSpeed = 5;
	
	
	public void update(float delta) {
		//long currentTime = System.currentTimeMillis();
		//long delta = currentTime - previousTime;
		
		Vector3 up = cam.up;
		Vector3 dir = cam.direction;
		Vector3 pos = cam.position;
		
		// Translation XY
		if(Gdx.input.isKeyPressed(A)) ut.add(-up.y, up.x, 0);
		if(Gdx.input.isKeyPressed(D)) ut.add(up.y, -up.x, 0);
		if(Gdx.input.isKeyPressed(W)) ut.add(up.x, up.y, 0);
		if(Gdx.input.isKeyPressed(S)) ut.add(-up.x, -up.y, 0);
		// Translation Z
		if(Gdx.input.isKeyPressed(SHIFT_LEFT) || Gdx.input.isKeyPressed(SHIFT_RIGHT)) {
			ut.add(dir);
		}
		if(Gdx.input.isKeyPressed(CONTROL_LEFT) || Gdx.input.isKeyPressed(CONTROL_RIGHT)) {
			ut.sub(dir);
		}
		// Rotation X
		if(Gdx.input.isKeyPressed(UP)) ur.add(-up.y, up.x, 0); // look up
		if(Gdx.input.isKeyPressed(DOWN)) ur.add(up.y, -up.x, 0); // look down
		// Rotation Z
		if(Gdx.input.isKeyPressed(LEFT)) ur.add(0, 0, -1f); // look left
		if(Gdx.input.isKeyPressed(RIGHT)) ur.add(0, 0, 1f); // look right
		
		// p = le point qu'on regarde avec la caméra
		float scl = Math.abs(cam.position.z / dir.z);
		Vector3 p = new Vector3(dir.x * scl + pos.x, dir.y * scl + pos.y, 0); // new Vector3((float)GridMap.width/2, (float)GridMap.height/2, 0); // -> si on
																				// voulait tjrs tourner autour du point central
		//System.out.println("delta : " + delta);
		float angle = rotationSpeed * delta; // * 2000;
		
		float distance = translationSpeed * delta; // * 2000;
		Vector3 movement = ut.scl(distance);
		
		cam.position.add(movement);
		cam.rotateAround(p, ur, angle); // .rotate(speed, ur.x, ur.y, ur.z);
		cam.update();
		
		ut.set(0, 0, 0);
		ur.set(0, 0, 0);
	}

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		super.touchDragged(x, y, pointer);
		Rectangle space = MapEditorGame.screen.hud.getDrawingSpace();
		if(space.contains(x, y) == false) {
			return false; // pas dans la zone
		}
		
		//System.out.println("touch Dragged [" + x + "," + y + "]");
		
		Vector3 up = cam.up;
		Vector3 dir = cam.direction;
		Vector3 pos = cam.position;
		
		// delta entre le dernier point dragged de la souris et le présent
		float dx = x - previousX;
		float dy = y - previousY;
		
		
		// p = le point qu'on regarde avec la caméra
		float scl = Math.abs(cam.position.z / dir.z);
		Vector3 target = new Vector3(dir.x * scl + pos.x, dir.y * scl + pos.y, 0);
		
		//Vector3 t = cam.unproject(new Vector3(x, y, 0));
		//System.out.println(dx + " , " + dy);
		//System.out.println("target : " + t);
		
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)) { // event.getKeyCode() == Buttons.LEFT) {
			if(dy > 0) ut.add(-up.x, -up.y, 0);
			if(dy < 0) ut.add(up.x, up.y, 0);
			if(dx > 0) ut.add(-up.y, up.x, 0);
			if(dx < 0) ut.add(up.y, -up.x, 0);
			
			float distance = translationSpeed * (Math.abs(dx) + Math.abs(dx)); // * delta;
			Vector3 movement = ut.scl(distance);
			cam.position.add(movement);
		}
		// System.out.println("code : " + event.getKeyCode());
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) { // event.getButton() == Buttons.RIGHT) { //
			if(dy < 0) ur.add(up.y, -up.x, 0); // look down
			if(dy > 0) ur.add(-up.y, up.x, 0); // look up
			if(dx > 0) ur.add(0, 0, -1f); // look left
			if(dx < 0) ur.add(0, 0, 1f); // look right
			
			float angle = rotationSpeed / 10f;// * 5;// * (Math.abs(dx) + Math.abs(dx)); // * delta;
			cam.rotateAround(target, ur, angle);
		}
		
		/*if(cam.position.z <= 0) {
			cam.position.z = 1;
		}*/
		cam.update();
		
		previousX = x;
		previousY = y;
		ut.set(0, 0, 0);
		ur.set(0, 0, 0);
		return true;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// System.out.println("key down " + keycode);

		if(keycode == Keys.SPACE) { //Gdx.input.isKeyPressed(Keys.SPACE)) {
			//cam.position.set(0, 0, 60);
			//cam.direction.set(0, 0, -1);
			//cam.up.set(0, 1, 0);
			MapEditorGame.screen.resetCamera();
			//MapEditorGame.screen.hud.dispose();
			//MapEditorGame.screen.hud = new EditorScreenHud();
		}
		if(keycode == Keys.P) {
			Log.debug("Controls3d point at [%s, %s]", Gdx.input.getX(), Gdx.input.getY());
		}
		
		return super.keyDown(keycode); // super.keyDown(event, keycode);
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// System.out.println("mouse Moved");
		
		Vector3 t = cam.unproject(new Vector3(x, y, 0));
		t.x = (float) Math.floor(t.x);
		t.y = (float) Math.floor(t.y);
		t.z = 0; //(float) Math.floor(t.z);
		
		Ray r = cam.getPickRay(x, y);
		
		Vector3 v = r.direction.cpy().scl(1).add(r.origin);
		float near = 1;
		float far = 1000;
		for(float scl = near; ; scl += 0.1f){
			v.set(r.direction).scl(scl).add(r.origin);
			if(v.z == 0 || Math.abs(v.z - 0) <= 0.05f) {
				v.x = (float) Math.floor(v.x);
				v.y = (float) Math.floor(v.y);
				v.z = 0; //(float) Math.floor(t.z);
			//	System.out.println(v);
			}
			if(scl > far) {
				return super.mouseMoved(x, y);
			}
		}
		
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// System.out.println("touch Down " + event.getKeyCode() + " / " +
		
		previousX = x;
		previousY = y;
		return true; // super.touchDown(event, x, y, pointer, button);
	}
	
	/*
	@Override
	public boolean scrolled(int amount) {
		// System.out.println("scrolled " + event.getScrollAmount() + " = " + amount);
		
		Vector3 p = cam.direction.cpy().scl(amount * -zoomSpeed);
		cam.position.add(p);
		cam.update();
		return true; // super.scrolled(event, x, y, amount);
	}
	*/
	

	@Override
	public boolean scrolled(int amount) {
		scrolledFloat(amount);
		//if(!activateBaseCamControl) return true;
		//return super.scrolled(amount);
		return true;
	}
	private void scrolledFloat(float amount) {
		//var viewport = SapphireGame.gfx.getViewport();
		var cam = MapEditorGame.screen.getCamera();
//		var ratio = viewport.getWorldWidth() / viewport.getWorldHeight();
//		viewport.setWorldWidth(viewport.getWorldWidth() + amount);
//		viewport.setWorldHeight( viewport.getWorldWidth() / ratio);
//		Log.info("viewport 1 : " + viewport.getWorldWidth() + "; " + viewport.getWorldHeight());
//		Log.info("cam 1 : " + cam.viewportWidth + "; " + cam.viewportHeight);

		var ratio = cam.viewportWidth / cam.viewportHeight;
		cam.viewportWidth += amount;
		cam.viewportHeight = cam.viewportWidth / ratio;
		if (autoUpdate)
			camera.update();
		
//		Log.info("viewport 2 : " + viewport.getWorldWidth() + "; " + viewport.getWorldHeight());
//		Log.info("cam 2 : " + cam.viewportWidth + "; " + cam.viewportHeight);

		// resize le viewport de la shadowlight pour sa shadowmap
		if(MapEditorGame.screen.getShadowLight() != null)
			MapEditorGame.screen.getShadowLight().zoom(cam.viewportWidth, cam.viewportHeight);
	}
	
	
}
