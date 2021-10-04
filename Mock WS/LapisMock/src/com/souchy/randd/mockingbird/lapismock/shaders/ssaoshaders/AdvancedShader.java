package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
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
		@Override
		protected AdvancedShader createShader (final Renderable renderable) {
			//Log.critical("AdvancedShaderProvider create");
			return new AdvancedShader(renderable);
		}
		public AdvancedShader getShader() {
			//Log.critical("AdvancedShaderProvider get");
			if(shaders.isEmpty()) {
				return null;
			}
			return (AdvancedShader) this.shaders.first();
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
	
	public SsaoApplyUniforms ussao;
	public AdvancedShader (final Renderable renderable) {
		super(renderable, new Config(
				Gdx.files.internal("shaders/ssao/default.vertex.glsl").readString(), 
				Gdx.files.internal("shaders/ssao/default.fragment.glsl").readString())
		);
		Log.critical("Advanced Shader");
		this.modules.add(ussao = new SsaoApplyUniforms(this));
	}
	
}
