package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.UniformsModule;

public class DeferredShader extends ExtendableShader {

	
	public DeferredUniforms udeffered;
	
	public DeferredShader(Renderable renderable, Config config) {
		super(renderable, config);
		this.modules.add(udeffered = new DeferredUniforms());
	}
	
	
	public static class DeferredFBO {
		protected FrameBuffer fbo;
		protected Camera cam;
		public DeferredShader shader;
		public DeferredFBO(Camera cam) {
			this.cam = cam;
			createFbo(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		public void resize(int screenW, int screenH) {
			createFbo(screenW, screenH);
		}
		public void createFbo(int width, int height) {
			fbo = new FrameBuffer(Format.RGBA8888, width, height, true, true);
		}
		public void begin() {
			
		}
		public void render(RenderableProvider r) {
			
		}
	}


	public static class DeferredUniforms implements UniformsModule {
		// maps to camera view space
		
		// output position map
		// output normal map
		// output albedo map (diffuse?)
		// output specular map
		@Override
		public void init(BaseShader s) {
			
		}
		@Override
		public void bind(BaseShader s, Renderable renderable, Config config, Attributes attributes) {
			
		}
	}
	
	
}
