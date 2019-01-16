package com.souchy.randd.mockingbird.lapismock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.mockingbird.lapismock.BaseScreen;
import com.souchy.randd.mockingbird.lapismock.CustomGreedyMesh;
import com.souchy.randd.mockingbird.lapismock.GreedyMeshMaker;
import com.souchy.randd.mockingbird.lapismock.World;
import com.souchy.randd.mockingbird.lapismock.shaders.PostProcessingFBO;

@SuppressWarnings("deprecation")
public class MockScreen3 extends BaseScreen {

	DirectionalShadowLight shadowLight;
	ModelBatch shadowBatch;
	
	PostProcessingFBO postProcessor;
	LineDrawing lining;
	
	public MockScreen3() {
		super();
        String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();
        modelBatch = new ModelBatch(vert, frag);
        
        postProcessor = new PostProcessingFBO(cam);
		Gdx.gl.glEnable(GL20.GL_LINEAR);
		

		lining = new LineDrawing(cam, null);
		lining.createGrid(1, (int) worldBB.getWidth(), (int) worldBB.getHeight(), true);
	}
	
	@Override
	public Color getBackgroundColor() {
		return Color.BLACK;
	}

	@Override
    public boolean useOrtho() {
    	return true;
    }
	
	@Override
	public void createEnvironment() {
		//super.createEnvironment();
		var ambiant = 0.7f;
		var dir = 0.8f;
		
		//(shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f, 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f);
		(shadowLight = new DirectionalShadowLight(1024*2*2, 1024*2*2, 50, 50, 1f, 100f)).set(dir, dir, dir, -1f, -1.0f, -.5f);

		
		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 1f));
		 env.add(shadowLight);
		 env.shadowMap = shadowLight;
	//	env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f));
		// env.add(new PointLight().set(0.8f, 0.8f, 0.8f, 18, 18, 1, 2f));
		// env.set(new ColorAttribute(ColorAttribute.Specular, .5f, .5f, .5f, 1f));
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

		if(false) {
			//	Gdx.gl.glEnable(GL20.GL_LINEAR);
//			Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
//			Gdx.gl.glClearColor(0, 0, 0, 1);
//			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

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
			
//			noise.begin();
//			noise.render(world.cache);
//			noise.end();

			
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
		

		if(true) {
			var inst = true ? greedyModel1 : greedyModel;
			shadowLight.begin(worldCenter, cam.direction);
			shadowBatch.begin(shadowLight.getCamera());
			shadowBatch.render(inst, env); //world.cache, shadowEnv);
			shadowBatch.end();
			shadowLight.end();
			
			modelBatch.begin(cam);
			modelBatch.render(inst, env); //world.cache, env);
			modelBatch.end();
		}

		//lining.renderLines();
	}

	GreedyMeshMaker g = new GreedyMeshMaker();
	ModelInstance greedyModel = new ModelInstance(g.rootNode);
	
	CustomGreedyMesh g1 = new CustomGreedyMesh();
	ModelInstance greedyModel1 = new ModelInstance(g1.rootNode);
	
	
	
}
