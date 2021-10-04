package com.souchy.randd.mockingbird.lapismock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.souchy.randd.mockingbird.lapismock.worlds.World;

public class MockScreen2 extends BaseScreen {

    public RenderContext renderContext;
    public Shader shader;
    
	public MockScreen2() {
		super();
        String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();
        modelBatch = new ModelBatch(vert, frag);
	}

	@Override
	public void setupShader() {

		/*shader = new TestShader();
		shader.init();*/
		
		/*Gdx.gl.glEnable(GL20.GL_POLYGON_OFFSET_FILL);
		Gdx.gl20.glPolygonOffset(0.3f, 0.3f);
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LEQUAL); // GL_LESS is default
		*/
	}

	
	@Override
	public void renderWorld(World world) {
	    modelBatch.begin(cam);

        
	    //for (ModelInstance instance : world.instances)
	    //    modelBatch.render(instance, env);
	    
	    modelBatch.render(world.cache, env);
	  //  modelBatch.render(world.cache, shader);
	    /*world.renderables.forEach(r -> {
	    	//r.environment = env;
	    	//r.shader = shader;
	    	modelBatch.render(r);
	    });*/
	    
	    modelBatch.end();

	}

	@Override
	protected void updateLight(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void act(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderShadows(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderParticleEffects() {
		// TODO Auto-generated method stub
		
	}
	
	
}
