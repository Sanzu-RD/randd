package com.souchy.randd.mockingbird.lapismock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public abstract class BaseScreen implements Screen {

	public Camera cam;
    public Environment env;
    public ModelBatch modelBatch;
    public CameraInputController camController;
    public World world;

	public BoundingBox worldBB;
	public Vector3 worldCenter;
    
    public BaseScreen() {
        modelBatch = new ModelBatch();

        createEnvironment();
        world = new World(env);
        worldBB = world.getBoundingBox();
        worldCenter = worldBB.getCenter(new Vector3());
        
        createCam();
        
        setupShader();

	    /*
	   	renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		shader = new TestShader();
		shader.init();
		*/
	}
    
    public boolean useOrtho() {
    	return false;
    }
    
    public void createCam() {
    	if(useOrtho()) {
    		//cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    		float viewportSize = 30;
    		cam = new OrthographicCamera(viewportSize*16/9, viewportSize);
            //cam.position.set(worldCenter.x, worldCenter.y*0.6f, worldCenter.x*1.5f);
            //cam.lookAt(worldCenter.x, worldCenter.y*0.9f, 0);
            cam.direction.set(-1, 1, -1f);
            cam.up.set(-1, 1, 1f);
            cam.position.set(14, 14, 0);
            cam.near = -30f;
            cam.far = 300f;
            cam.update();
    	} else {
    		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            cam.position.set(worldCenter.x, worldCenter.y*0.6f, worldCenter.x*1.8f);
            cam.lookAt(worldCenter.x, worldCenter.y*0.9f, 0);
            cam.near = 1f;
            cam.far = 300f;
            cam.update();
    	}
    }
    
    public void createEnvironment() {
        env = new Environment();
        var ambiant = 0.6f;
        var dir = 0.6f;
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 1f));
        env.add(new DirectionalLight().set(dir, dir, dir, -1f, -0.8f, -0.2f));
    }
    
    
    public abstract void setupShader();
	
	
	@Override
	public void show() {
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
	}
	
	public void clearScreen() {
		Color color = getBackgroundColor();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
	}
	
	public Color getBackgroundColor() {
		return Color.SKY;//Color.BLACK;
	}
	
	@Override
	public void render(float delta) {
        camController.update();

        clearScreen();
        
        /*modelBatch.begin(cam);
        modelBatch.render(cache, shader);
        //for (ModelInstance instance : instances)
        //	modelBatch.render(instance, shader);
        //renderables.forEach(r -> modelBatch.render(r, shader));
        //modelBatch.render(cache, environment);
        modelBatch.end();*/

        renderWorld(world);
        
        /*
        renderContext.begin();
        shader.begin(cam, renderContext);
        renderables.forEach(r -> shader.render(r));
        //shader.render(renderable);
        shader.end();
        renderContext.end();
         */
        
        Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
	}
	
	public abstract void renderWorld(World world);

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void dispose () {
        modelBatch.dispose();
        world.dispose();
    }
	
	
}
