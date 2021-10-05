package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.SsaoGenUniforms;

public class SSAOShader extends ExtendableShader {
	public static class SSAOShaderProvider extends BaseShaderProvider {
		public String vert; 
		public String frag;
		public SSAOShaderProvider() {
			vert = Gdx.files.internal("shaders/ssao/testSSAO.v.glsl").readString();
			frag = Gdx.files.internal("shaders/ssao/testSSAO.f.glsl").readString();
		}
		public SSAOShaderProvider(String vert, String frag) {
			this.vert = vert;
			this.frag = frag;
		}
		@Override
		protected SSAOShader createShader (final Renderable renderable) {
			return new SSAOShader(renderable, new Config(vert, frag));
		}
		public void clear() {
			this.shaders.clear();
		}
	}

	public SsaoGenUniforms ussao;
	public SSAOShader(final Renderable renderable, Config config) {
		super(renderable, config);
		Log.critical("SSAOShader");
		modules.add(ussao = new SsaoGenUniforms(this));
	}

//	@Override
//	protected boolean lighting() {
//		return true;
//	}

}
