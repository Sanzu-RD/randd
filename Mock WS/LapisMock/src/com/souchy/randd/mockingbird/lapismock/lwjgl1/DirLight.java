package com.souchy.randd.mockingbird.lapismock.lwjgl1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class DirLight {
	
	
	public Camera cam;
	
	public DirLight(Vector3 dir, Color c) {
		
	}
	
	
	
	public void createCam() {
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(15f, -15f, 30f);
        cam.lookAt(15,15,0);
        //cam.direction.set(0, 0, -1);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
	}
	
	
}
