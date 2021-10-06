package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class DepthShader extends ExtendableShader {
	
	public static class DepthShaderProvider extends MockShaderProvider {
		@Override
		protected Shader createShader(Renderable renderable) {
			//Log.info("DepthShaderProvider create");
			return new DepthShader(renderable, new Config(vert, frag));
		}
		@Override
		public void readFiles() {
			vert = Gdx.files.internal("shaders/ssao/depth.v.glsl").readString();
			frag = Gdx.files.internal("shaders/ssao/depth.f.glsl").readString();
		}
		@Override
		public Shader getShader(Renderable renderable) {
//			Shader suggestedShader = renderable.shader;
//			if (suggestedShader != null && suggestedShader.canRender(renderable)) return suggestedShader;
			if(shaders.size != 0)
				for (Shader shader : shaders) {
					//if (shader.canRender(renderable)) 
						//Log.info("DepthShaderProvider get(r) " + shader);
						return shader;
				}
			final Shader shader = createShader(renderable);
			shader.init();
			shaders.add(shader);
			return shader;
		}
	}
	
	
	public DepthShader(Renderable renderable, Config config) {
		super(renderable, config);
		Log.info("DepthShader");
	}
	
	protected boolean global() {
		return true;
	}
	protected boolean object() {
		return true;
	}
	protected boolean material() {
		return false;
	}
	protected boolean lighting() {
		return false;
	}
	
}
