package com.souchy.randd.tools.mapeditor.shader.uniforms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;
import com.souchy.randd.tools.mapeditor.shader.Shader2;
import com.souchy.randd.tools.mapeditor.shader.Shader2Config;

/**
 * 
 * 
 * @author Blank
 * @date 1 nov. 2021
 */
@SuppressWarnings("unused")
public class GlobalUniforms implements UniformsModule {
	
	// Global uniforms addresses
	private int u_projTrans; // camera.projection
	private int u_viewTrans; // camera.view
	private int u_projViewTrans; // camera.combined
	private int u_cameraPosition;
	private int u_cameraDirection;
	private int u_cameraUp;
	private int u_cameraNearFar;
	private int u_time;
	
	private float time;
	
	
	@Override
	public void register(BaseShader s, Renderable r, Config config) {
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
		if (s.has(u_time)) 
			s.set(u_time, time += Gdx.graphics.getDeltaTime());
	}
	
}