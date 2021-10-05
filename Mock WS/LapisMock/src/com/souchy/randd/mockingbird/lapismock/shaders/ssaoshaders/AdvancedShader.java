package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.SsaoApplyUniforms;

/**
 * A default shader with the ability to render an SSAO map
 * 
 * @author Blank
 * @date 4 oct. 2021
 */
public class AdvancedShader extends ExtendableShader {
	public static class AdvancedShaderProvider extends BaseShaderProvider {
		public String vert; 
		public String frag;
		
		public AdvancedShaderProvider() {
			vert = Gdx.files.internal("shaders/ssao/default.vertex.glsl").readString();
			frag = Gdx.files.internal("shaders/ssao/default.fragment.glsl").readString();
		}
		public AdvancedShaderProvider(String vert, String frag) {
			this.vert = vert;
			this.frag = frag;
		}
		@Override
		protected AdvancedShader createShader (final Renderable renderable) {
			return new AdvancedShader(renderable, new Config(vert, frag));
		}
		public void setSSAO(Texture ssaoTex) {
			for(var shader : shaders) ((AdvancedShader)shader).ussao.ssaoTex.texture = ssaoTex;
		}
		public void clear() {
			this.shaders.clear();
		}
	}
	
	
	public SsaoApplyUniforms ussao;
	public AdvancedShader (final Renderable renderable, Config config) {
		super(renderable, config);
		Log.critical("Advanced Shader");
		this.modules.add(ussao = new SsaoApplyUniforms(this));
	}
	
	
	
}
