package com.souchy.randd.ebishoal.sapphire.ui;

import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;

@SuppressWarnings("deprecation")
public class SapphireScreen {
	
	// 3D
	DirectionalShadowLight shadowLight;
	ModelBatch shadowBatch;
	ModelBatch modelBatch;
	Environment env;
	
	// 2D

	Stage stage;
	
	// to go in world
	ModelCache objects;
	List<ModelInstance> characters; 
	ModelInstance terrain; 
	BoundingBox worldBB = new BoundingBox();
	Vector3 worldCenter = new Vector3();
	
	// Cameras
	Camera cam3d;
	Camera camui;
	
	
	
	public SapphireScreen() {
		// Create the terrain (will be voxel meshing in the world class)
		terrain = SapphireGame.testModelGenerator(0);
		// Get the World's BoundingBox (will be in world class)
		terrain.calculateBoundingBox(worldBB);
		// Get the World's center (will be in world class)
		worldBB.getCenter(worldCenter);
	}
	
	
	public void render(float delta) {

		// ----------------------------------------
		// Render voxels --------------------------
		// ----------------------------------------
		shadowLight.begin(worldCenter, cam3d.direction);
			shadowBatch.begin(shadowLight.getCamera());
				shadowBatch.render(terrain, env); 
				shadowBatch.render(objects, env);
			shadowBatch.end();
		shadowLight.end();
		
		modelBatch.begin(cam3d);
		modelBatch.render(terrain, env); //world.cache, env);
		//modelBatch.end();

		// ----------------------------------------
		// Render terrain effects (glyphs) --------
		// ----------------------------------------
		// they should be rendererd with the other models and creatures actually so that blocks appear on top

		// ----------------------------------------
		// Render grid highlights -----------------
		// ----------------------------------------
		

		// ----------------------------------------
		// Render objects & characters ------------
		// ----------------------------------------
		//modelBatch.begin(cam3d);
		modelBatch.render(objects, env); 
		modelBatch.render(characters, env); 
		modelBatch.end();

		// ----------------------------------------
		// Render VFX -----------------------------
		// ----------------------------------------
		

		// ----------------------------------------
		// Render Post-Process --------------------
		// ----------------------------------------
		// tout ce qu'on a render avant, envoie le dans un FBO à la place
		// puis envoie le fbo à opengl pour appliquer les shaders post-process
		
		// ----------------------------------------
		// Render UI ------------------------------
		// ----------------------------------------
		stage.act(delta);
		stage.draw();
	}
	
	
	
	
	
	
	
	
}
