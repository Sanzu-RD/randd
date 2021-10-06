package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.SsaoGenUniforms;

public class SSAOShader extends ExtendableShader {
	public static class SSAOShaderProvider extends MockShaderProvider {
		public TextureDescriptor shadowMap;
		public SSAOShaderProvider(TextureDescriptor shadowMap) {
			super();
			this.shadowMap = shadowMap;
		}
		@Override
		protected SSAOShader createShader (final Renderable renderable) {
			return new SSAOShader(renderable, new Config(vert, frag), shadowMap);
		}
		/**
		 * read shader files. currently only supports default files. could use lambdas for custom loading
		 */
		@Override
		public void readFiles() {
			vert = Gdx.files.internal("shaders/ssao/testSSAO.v.glsl").readString();
			frag = Gdx.files.internal("shaders/ssao/testSSAO.f.glsl").readString();
		}
	}

	public SsaoGenUniforms ussao;
	public SSAOShader(final Renderable renderable, Config config, TextureDescriptor depthMap) {
		super(renderable, config);
		//Log.critical("SSAOShader");
		modules.add(ussao = new SsaoGenUniforms(this, depthMap));
	}

//	@Override
//	protected boolean lighting() {
//		return true;
//	}

}
