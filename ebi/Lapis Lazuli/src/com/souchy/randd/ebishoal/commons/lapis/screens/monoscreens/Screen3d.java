package com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.screens.Cameras;
import com.souchy.randd.ebishoal.commons.lapis.screens.Viewports;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

public class Screen3d extends BaseScreen {


    /**
     * 3D world of objects
     */
    private World world;
	private ModelBatch batch;
	private Environment env;
    //private Box2DDebugRenderer debug; // hmmmmm dont really use box2d
	
    public Screen3d() {
    	
    }

    /**
     * Creates the :
     * <ul>
     * <li> Camera
     * <li> Viewport 
     * <li> Model Batch
     * <li> Environment
     * <li> Basic lightning
     * <ul>
     */
	@Override
	protected void createHook() {
    	batch = new ModelBatch();
    	env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	@Override
	protected Camera createCam() {
		Vector3 camPos = new Vector3(-3, -3, 5);
		float fieldOfView = 2/3f; // � mettre dans les settings
		float near = 1f; 		  // � mettre dans les settings
		float far = 200f; 		  // � mettre dans les settings
		Camera cam = Cameras.perspective(camPos, Vector3.Zero, fieldOfView, near, far);
		cam.update();
		return cam;
	}

	@Override
	protected Viewport createView(Camera cam) {
		float aspectRatio = 16/9f;	// ratio � mettre dans les settings public
		float minWorldY = 50; 		// hauteur min � mettre ds settings priv�s
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contr�ller how much du monde qu'on voit
		// cela est ensuite scal� pour s'adapter � la grandeur de la fen�tre
		Viewport view = Viewports.extend(minWorldX, minWorldY,  cam);
		view.apply();
		return view;
	}
	
	@Override
	protected void renderHook(float delta) {
		batch.begin(getCam());
		batch.render(world.cache, env);
		batch.end();
	}

	
	@Override public void dispose(){ 
		batch.dispose();
	}
	
	
	
}
