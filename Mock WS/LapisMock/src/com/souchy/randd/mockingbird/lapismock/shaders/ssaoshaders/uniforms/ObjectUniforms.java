package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;

public class ObjectUniforms implements UniformsModule {
	
	// Object uniforms
	public final int u_worldTrans;
	public final int u_viewWorldTrans;
	public final int u_projViewWorldTrans;
	public final int u_normalMatrix;
	public final int u_bones;
	
	public ObjectUniforms(BaseShader s, Renderable renderable, Config config) {
		// Object uniforms
		u_worldTrans = s.register(Inputs.worldTrans, Setters.worldTrans);
		u_viewWorldTrans = s.register(Inputs.viewWorldTrans, Setters.viewWorldTrans);
		u_projViewWorldTrans = s.register(Inputs.projViewWorldTrans, Setters.projViewWorldTrans);
		u_normalMatrix = s.register(Inputs.normalMatrix, Setters.normalMatrix);
		u_bones = -1;
		if(renderable.bones != null && config.numBones > 0)
			s.register(Inputs.bones, new Setters.Bones(config.numBones));
	}
	
}