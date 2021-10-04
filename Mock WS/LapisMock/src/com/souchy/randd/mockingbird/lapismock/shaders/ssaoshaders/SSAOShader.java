package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.SsaoGenUniforms;

public class SSAOShader extends ExtendableShader {
	public static class SSAOShaderProvider extends BaseShaderProvider {
		@Override
		protected SSAOShader createShader (final Renderable renderable) {
			//Log.critical("SSAOShaderProvider create");
			return new SSAOShader(renderable);
		}
		public SSAOShader getShader() {
			//Log.critical("SSAOShaderProvider get");
			if(shaders.isEmpty()) {
				return null;
			}
			return (SSAOShader) this.shaders.first();
		}
		@Override
		public Shader getShader (Renderable renderable) {
//			Shader suggestedShader = null; //renderable.shader;
//			if (suggestedShader != null && suggestedShader.canRender(renderable)) return suggestedShader;
//			for (Shader shader : shaders) {
//				if (shader.canRender(renderable)) return shader;
//			}
//			final Shader shader = createShader(renderable);
//			shader.init();
//			shaders.add(shader);
			var shader = getShader();
			if(shader == null) {
				shader = createShader(renderable);
				shader.init();
				this.shaders.add(shader);
			}
			return shader;
		}

	}
	

	public SsaoGenUniforms ussao;
	public SSAOShader(final Renderable renderable) {
		super(renderable, new Config(
				Gdx.files.internal("shaders/ssao/testSSAO.v.glsl").readString(), 
				Gdx.files.internal("shaders/ssao/testSSAO.f.glsl").readString())
		);
		Log.critical("SSAOShader");
		modules.add(ussao = new SsaoGenUniforms(this));
	}

	@Override
	protected boolean lighting() {
		return false;
	}

}
