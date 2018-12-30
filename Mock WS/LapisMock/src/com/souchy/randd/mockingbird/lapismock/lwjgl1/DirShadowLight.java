package com.souchy.randd.mockingbird.lapismock.lwjgl1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.mockingbird.lapismock.MockCore;

public class DirShadowLight {

	public Camera cam;
	public Vector3 dir;
	public Color c;
	
	public DirShadowLight(Vector3 dir, Color c) {
		this.dir = dir;
		this.c = c;
		createCam();
	}
	
	
	
	public void createCam() {
		//cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		var world = MockCore.core.getGame().getStartScreen().world;
		var bb = world.getBoundingBox();
		var center = bb.max.cpy().add(bb.min).scl(0.5f);
		bb.getCenter(new Vector3());
		
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		
		cam.position.set(values)
        cam.position.set(15f, -15f, 30f);
        cam.lookAt(15,15,0);
        //cam.direction.set(0, 0, -1);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
	}
	
	
}
