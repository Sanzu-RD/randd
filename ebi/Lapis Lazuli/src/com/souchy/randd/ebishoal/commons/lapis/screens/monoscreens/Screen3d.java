package com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.screens.Cameras;
import com.souchy.randd.ebishoal.commons.lapis.screens.Viewports;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

@SuppressWarnings("deprecation")
public abstract class Screen3d extends BaseScreen {


	private ModelBatch batch;
    private Environment env;
    protected DirectionalShadowLight shadowLight;
    private ModelBatch shadowBatch;

    /** 3D world of objects */
	private final World world;

    public Screen3d() {
    	world = new World();
    }

	@Override
	protected void createHook() {
		//super.createHook();
    	batch = new ModelBatch();//new DepthShaderProvider());
    	env = new Environment();
    	createEnvironment();
		shadowBatch = new ModelBatch(new DepthShaderProvider());
	}
	
	protected void createEnvironment() {
		/*
    	//float diffuseIntensity = 0.8f;
    	//float directionalIntensity = 1f;
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness() * 0.75f, 1f));
		//env.set(new ColorAttribute(ColorAttribute.Diffuse, diffuseIntensity, diffuseIntensity, diffuseIntensity, 1f));
	//	env.add(new DirectionalLight().set(getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness(), 0.2f, 0.2f, -1f));
		//env.add((shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f, 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f));
	//	env.add((shadowLight = new DirectionalShadowLight(1024, 1024, 60f, 60f, .1f, 50f)).set(1f, 1f, 1f, 0.2f, 0.2f, -1f));   
	//	env.shadowMap = shadowLight;
		
	//	com.badlogic.gdx.graphics.g3d.environment.BaseLight<BaseLight<T>>
		
		/*PointLight pl = new PointLight().setPosition(-10, -10, 1000)
				.setColor(Color.WHITE)
				.setIntensity(1000000);
		env.add(pl);
		//new SpotLight().setPosition(50, 50, 50);
		 * /
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, .6f, 1f));
		env.add((shadowLight = new DirectionalShadowLight(1024, 1024, 60f, 60f, .1f, 50f))                  
	                .set(1f, 1f, 1f, 40.0f, -35f, -35f));   
		env.shadowMap = shadowLight; 
		
		//new SpotLightsAttribute().lights.add();
		*/
	}
	
	protected float getAmbiantBrightness() {
		return 0.8f;
	}

	@Override
	protected Camera createCam() {
		Vector3 camPos = new Vector3(-3, -3, 5);
		float fieldOfView = 67; // �+ mettre dans les settings
		float near = 0.1f; 		  // + mettre dans les settings
		float far = 200f; 		  // + mettre dans les settings
		Camera cam = Cameras.perspective(camPos, Vector3.Zero, fieldOfView, near, far);
		cam.update();
		return cam;
	}

	@Override
	protected Viewport createView(Camera cam) {
		float aspectRatio = 16/9f;	// ratio à mettre dans les settings public
		float minWorldY = 50; 		// hauteur min à mettre ds settings priv�s
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contr�ller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter � la grandeur de la fen�tre
		Viewport view = Viewports.extend(minWorldX, minWorldY,  cam);
		view.apply();
		return view;
	}
	
	
	@Override
	public void render(float delta) {
		world.buildCache();
		renderShadows(delta);
		// clearScreen + renderHook
		super.render(delta);
	}

	protected void renderShadows(float delta) {
		// render les shadows avant de clearScreen
		shadowLight.begin(Vector3.Zero, getCam().direction);
			shadowBatch.begin(shadowLight.getCamera());
				shadowBatch.render(world.cache, env);
				world.tempModels.forEach(m -> shadowBatch.render(m, env));
			shadowBatch.end();
		shadowLight.end();
	}
	
	protected void renderWorld(float delta) {
		getBatch().begin(getCam());
			// render the cached models
			getBatch().render(getWorld().cache, getEnvironment());
			// render the temp models
			getWorld().tempModels.forEach(m -> getBatch().render(m, getEnvironment()));
		getBatch().end();
	}
	
	@Override
	protected void renderHook(float delta) {
		renderWorld(delta);
	}

	
	@Override public void dispose(){ 
		batch.dispose();
		world.dispose(); //.cache.dispose();
	}

    public World getWorld() {
		return world;
	}

    public ModelBatch getBatch() {
		return batch;
	}

    public Environment getEnvironment() {
		return env;
	}
    
    /**
     * Dont recenter the screen for 3D as we keep the custom camera position.
     * Maybe the bad result on recentering is a result of a bad viewport setting, but keep the custom position seems more efficient
     */
    @Override
    public void resize(int width, int height) {
		getViewport().update(width, height, false);
    }
    
}
