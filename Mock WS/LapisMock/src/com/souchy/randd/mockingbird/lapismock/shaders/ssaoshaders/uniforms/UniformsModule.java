package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms;

import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;

public interface UniformsModule {
	
	public default void register(BaseShader s) {
		// do nothing
	}
	
	public default void init(BaseShader s) {
		// do nothing
	}
	
	public default void begin(BaseShader s) {
		// do nothing
	}
	
	public default void bind(BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
		// do nothing
	}

	
	/**
	 * fragment shader path to add it to a main shader
	 */
	public default String fragment() {
		return "";
	}
	
	/**
	 * vertex shader path to add it to a main shader
	 */
	public default String vertex() {
		return "";
	}
	
}
