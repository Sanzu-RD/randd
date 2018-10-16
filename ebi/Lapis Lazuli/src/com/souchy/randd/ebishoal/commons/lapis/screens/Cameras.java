package com.souchy.randd.ebishoal.commons.lapis.screens;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * Perspective projection is based on distance. Cubes become trapezoids with the farther ridges/sides appearing smaller than the ones closer even though they might be the same size.
 * 
 * Orthographic means there's no perspective based on distance. A cube stays a cube.
 * 
 * Isometric view is based on a rotated Orthogonal cam
 * 
 * @author Souchy
 */
public class Cameras {
	
	/**
	 * 
	 * //@param viewport - use  {@link Viewport#apply} with a viewport from {@link Viewports} instead  
	 * @param pos
	 * @param target
	 * @param fov
	 * @param near
	 * @param far
	 * @return
	 */
	public static Camera perspective(Vector3 pos, Vector3 target, float fov, float near, float far) {
		//PerspectiveCamera cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		PerspectiveCamera cam = new PerspectiveCamera();
		set(cam, pos, target, near, far);
		cam.fieldOfView = fov;
		return cam;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Camera orthogonal(Vector3 pos, Vector3 target, float zoom, float near, float far) {
		OrthographicCamera cam = new OrthographicCamera(1600, 900);		
		//set(cam, pos, target, near, far);
		//cam.zoom = zoom;
		return cam;
	}
	
	/**
	 * Just an orthographic cam that looks in the direction vector3 (1, 1, -1)
	 * @return
	 */
	public static Camera isometric(Vector3 pos, float zoom, float near, float far) {
		Camera cam = orthogonal(pos, Vector3.Zero, zoom, near, far);
		cam.direction.set(1, 1, -1);
		return cam;
	}
	
	private static void set(Camera cam, Vector3 pos, Vector3 target, float near, float far) {
		cam.position.set(pos);
		cam.lookAt(target);
		cam.near = near;
		cam.far = far;
		cam.update();
		
	}
	
}
