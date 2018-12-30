package com.souchy.randd.mockingbird.lapismock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.souchy.randd.mockingbird.lapismock.BaseScreen;
import com.souchy.randd.mockingbird.lapismock.World;
import com.souchy.randd.mockingbird.lapismock.shaders.PostProcessingFBO;

@SuppressWarnings("deprecation")
public class MockScreen3 extends BaseScreen {

	DirectionalShadowLight shadowLight;
	ModelBatch shadowBatch;
	Environment shadowEnv;
	
	PostProcessingFBO noise;
	
	public MockScreen3() {
		super();
        String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();
        modelBatch = new ModelBatch(vert, frag);
        
        noise = new PostProcessingFBO(cam);
		Gdx.gl.glEnable(GL20.GL_LINEAR);
	}
	
	@Override
	public void createEnvironment() {
		//super.createEnvironment();
		
		//(shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f, 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f);
		(shadowLight = new DirectionalShadowLight(1024*2*2, 1024*2*2, 50, 50, 1f, 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.5f);

		var ambiant = 0.6f;
		
		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 1f));
		 env.add(shadowLight);
		 env.shadowMap = shadowLight;
	//	env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f));
		// env.add(new PointLight().set(0.8f, 0.8f, 0.8f, 18, 18, 1, 2f));
		// env.set(new ColorAttribute(ColorAttribute.Specular, .5f, .5f, .5f, 1f));
		
		shadowEnv = new Environment();
		shadowEnv.set(new ColorAttribute(ColorAttribute.AmbientLight, .5f, .5f, .5f, 1f));
		shadowEnv.add(shadowLight);
		shadowEnv.shadowMap = shadowLight;
	}
	
	@Override
	public void setupShader() {
		// TODO Auto-generated method stub
        String vert = Gdx.files.internal("shaders/depth.vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/depth.fragment.glsl").readString();
		shadowBatch = new ModelBatch(new DepthShaderProvider(vert, frag));
	}

	FrameBuffer fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	
	@Override
	public void renderWorld(World world) {

	//	Gdx.gl.glEnable(GL20.GL_LINEAR);
//		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		var v = new Vector3();
		shadowLight.begin(worldCenter, cam.direction);
		shadowBatch.begin(shadowLight.getCamera());
		world.instances.forEach(inst -> {
			var pos = inst.transform.getTranslation(v);
			//if(pos.z > 0) {
				//shadowBatch.render(inst, shadowEnv);
				shadowBatch.render(inst);
			//}
		});
		//shadowBatch.render(world.cache, shadowEnv);
		shadowBatch.end();
		shadowLight.end();
		
//		noise.begin();
//		noise.render(world.cache);
//		noise.end();

		
		modelBatch.begin(cam);
		world.instances.forEach(inst -> {
			var pos = inst.transform.getTranslation(v);
			if(pos.z > 0) {
				//modelBatch.render(inst, shadowEnv);
				modelBatch.render(inst, env);
			} else {
				modelBatch.render(inst, env);
			}
		});
		//modelBatch.render(world.cache, env);
		modelBatch.end();
		
		
	}
	
	
	
	
}
