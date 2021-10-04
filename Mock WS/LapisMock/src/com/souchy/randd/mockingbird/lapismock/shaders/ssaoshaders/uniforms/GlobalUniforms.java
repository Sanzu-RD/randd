package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;

public class GlobalUniforms implements UniformsModule {
	// Global uniforms
	public final int u_projTrans;
	public final int u_viewTrans;
	public final int u_projViewTrans;
	public final int u_cameraPosition;
	public final int u_cameraDirection;
	public final int u_cameraUp;
	public final int u_cameraNearFar;
	public final int u_time;
	
	private float time;
	
	public GlobalUniforms(BaseShader s) {
		// Global uniforms
		u_projTrans = s.register(Inputs.projTrans, Setters.projTrans);
		u_viewTrans = s.register(Inputs.viewTrans, Setters.viewTrans);
		u_projViewTrans = s.register(Inputs.projViewTrans, Setters.projViewTrans);
		u_cameraPosition = s.register(Inputs.cameraPosition, Setters.cameraPosition);
		u_cameraDirection = s.register(Inputs.cameraDirection, Setters.cameraDirection);
		u_cameraUp = s.register(Inputs.cameraUp, Setters.cameraUp);
		u_cameraNearFar = s.register(Inputs.cameraNearFar, Setters.cameraNearFar);
		u_time = s.register(new Uniform("u_time"));
	}
	
	@Override
	public void begin(BaseShader s) {
		if (s.has(u_time)) s.set(u_time, time += Gdx.graphics.getDeltaTime());
	}
	
}