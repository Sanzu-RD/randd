package com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
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
    private DirectionalShadowLight shadowLight;
    private ModelBatch shadowBatch;
    //private Box2DDebugRenderer debug; // hmmmmm dont really use box2d

    /** 3D world of objects */
	private final World world;

    public Screen3d() {
    	world = new World();
    }

	@Override
	protected void createHook() {
		//super.createHook();
    	batch = new ModelBatch();
    	createEnvironment();
		shadowBatch = new ModelBatch(new DepthShaderProvider());
	}
	
	protected void createEnvironment() {
    	env = new Environment();
    	//float diffuseIntensity = 0.8f;
    	//float directionalIntensity = 1f;
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness() * 0.75f, 1f));
		//env.set(new ColorAttribute(ColorAttribute.Diffuse, diffuseIntensity, diffuseIntensity, diffuseIntensity, 1f));
		//env.add(new DirectionalLight().set(directionalIntensity, directionalIntensity, directionalIntensity, -1f, -0.8f, -0.2f));
		//env.add((shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f, 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f));
		env.add((shadowLight = new DirectionalShadowLight(1024, 1024, 60f, 60f, .1f, 50f)).set(1f, 1f, 1f, 40.0f, -35f, -35f));   
		env.shadowMap = shadowLight;
	}
	
	protected float getAmbiantBrightness() {
		return 0.8f;
	}

	@Override
	protected Camera createCam() {
		Vector3 camPos = new Vector3(-3, -3, 5);
		float fieldOfView = 67; // à mettre dans les settings
		float near = 0.1f; 		  // à mettre dans les settings
		float far = 200f; 		  // à mettre dans les settings
		Camera cam = Cameras.perspective(camPos, Vector3.Zero, fieldOfView, near, far);
		cam.update();
		return cam;
	}

	@Override
	protected Viewport createView(Camera cam) {
		float aspectRatio = 16/9f;	// ratio à mettre dans les settings public
		float minWorldY = 50; 		// hauteur min à mettre ds settings privés
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		Viewport view = Viewports.extend(minWorldX, minWorldY,  cam);
		view.apply();
		return view;
	}
	
	@Override
	public void render(float delta) {
		shadowLight.begin(Vector3.Zero, getCam().direction);
		shadowBatch.begin(shadowLight.getCamera());
		shadowBatch.render(world.cache, env);
		shadowBatch.end();
		shadowLight.end();
		super.render(delta);
	}
	
	@Override
	protected void renderHook(float delta) {
		batch.begin(getCam());
		batch.render(world.cache, env);
		batch.end();
	}

	
	@Override public void dispose(){ 
		batch.dispose();
		world.cache.dispose();
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
