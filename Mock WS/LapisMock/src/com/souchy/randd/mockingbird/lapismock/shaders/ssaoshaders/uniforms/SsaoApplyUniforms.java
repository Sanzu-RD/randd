package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;

/**
 * SSAO rendering uniforms (ssao result texture)
 * 
 * @author Blank
 * @date 4 oct. 2021
 */
@SuppressWarnings("rawtypes")
public class SsaoApplyUniforms implements UniformsModule {
	// --------------
	public TextureDescriptor ssaoTex;
	// --------------
	protected final int u_ssaoMapProjViewTrans;
	protected final int u_ssaoTexture;
	protected final int u_ssaoPCFOffset;
	// --------------
	public SsaoApplyUniforms(BaseShader s) {
		ssaoTex = new TextureDescriptor();
		ssaoTex.minFilter = ssaoTex.magFilter = Texture.TextureFilter.Nearest;
		ssaoTex.uWrap = ssaoTex.vWrap = Texture.TextureWrap.ClampToEdge;
		
		u_ssaoMapProjViewTrans = s.register(new Uniform("u_ssaoMapProjViewTrans"));
		u_ssaoTexture = s.register(new Uniform("u_ssaoTexture"));
		u_ssaoPCFOffset = s.register(new Uniform("u_ssaoPCFOffset"));
	}
	public void bind(BaseShader s) {
		//if (lights != null && lights.shadowMap != null) {
			//set(u_ssaoMapProjViewTrans, lights.shadowMap.getProjViewTrans());
			//set(u_ssaoTexture, lights.shadowMap.getDepthMap());
			//set(u_ssaoPCFOffset, 1.f / (2f * lights.shadowMap.getDepthMap().texture.getWidth()));
		//}
		s.set(u_ssaoTexture, ssaoTex);
//		s.set(u_ssaoMapProjViewTrans, ssaoTex);
//		s.set(u_ssaoPCFOffset, ssaoTex);
	}
}