package com.souchy.randd.mockingbird.lapismock.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.mockingbird.lapismock.shaders.TestShader;

public class MockScreen1 implements Screen {

	public Camera cam;
    public Environment environment;
    public ModelBatch modelBatch;
    public List<ModelInstance> instances;
    public List<Renderable> renderables;
    public CameraInputController camController;
    public ModelCache cache;
    public ModelBuilder modelBuilder = new ModelBuilder();
    
    public RenderContext renderContext;
    public Shader shader;
    
	public MockScreen1() {
        modelBatch = new ModelBatch();
        instances = new ArrayList<>();
        renderables = new ArrayList<>();
        cache = new ModelCache();
        
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(15f, -15f, 30f);
        cam.lookAt(15,15,0);
        //cam.direction.set(0, 0, -1);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        var cellSize = 1f;
    	Model model = modelBuilder.createBox(
    			cellSize, cellSize, cellSize, 
    			new Material(ColorAttribute.createDiffuse(Color.GREEN)), 
    			Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		for(int x = 0; x < 30; x++) {
			for(int y = 0; y < 30; y++) {
				Vector3 pos = new Vector3(cellSize * x + cellSize/2, cellSize * y + cellSize/2, 0);//z * cellSize - cellSize/2));
				addBlock(model, pos);
			}
		}
		addBlock(model, new Vector3(5, 5, 3));

		cache.begin();
		cache.add(instances);
		cache.end();

	    renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.LRU, 1));
		shader = new TestShader();
		shader.init();
	}
	
	private void addBlock(Model model, Vector3 pos) {
		var instance = createBlock(model, pos);
		var renderable = createRenderable(instance);
		instances.add(instance);
		renderables.add(renderable);
	}
	
	private Renderable createRenderable(ModelInstance instance) {
		var renderable = new Renderable();
		instance.getRenderable(renderable);
		renderable.environment = environment;
		//renderable.worldTransform.idt();
		return renderable;
	}
	
	private ModelInstance createBlock(Model model, Vector3 pos) {
    	ModelInstance instance = new ModelInstance(model);
    	instance.transform.setToTranslation(pos); 
        instance.calculateTransforms();
        return instance;
	}
	
	
	@Override
	public void show() {
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void render(float delta) {
        camController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);// | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        
        /*
        modelBatch.begin(cam);
        modelBatch.render(cache, shader);
//        for (ModelInstance instance : instances)
//        	modelBatch.render(instance, shader);
//        renderables.forEach(r -> modelBatch.render(r));
//        modelBatch.render(cache, environment);
        modelBatch.end();
        */

        renderContext.begin();
        shader.begin(cam, renderContext);
        renderables.forEach(r -> shader.render(r));
//        shader.render(renderable);
        shader.end();
        renderContext.end();

        Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
	}

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
        shader.dispose();
        modelBatch.dispose();
        cache.dispose();
        instances.forEach(i -> i.model.dispose());
    }
	
}
