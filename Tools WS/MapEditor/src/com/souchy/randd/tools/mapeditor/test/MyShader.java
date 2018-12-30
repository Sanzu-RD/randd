package com.souchy.randd.tools.mapeditor.test;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MyShader extends DefaultShader {

	public MyShader(final Renderable renderable) {
		this(renderable, new Config());
	}
	
	public MyShader(final Renderable renderable, final Config config) {
		this(renderable, config, createPrefix(renderable, config));
	}
	
	public MyShader(final Renderable renderable, final Config config, final String prefix) {
		this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(),
				config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
	}
	
	public MyShader(final Renderable renderable, final Config config, final String prefix, final String vertexShader, final String fragmentShader) {
		this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
	}
	
	public MyShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
		super(renderable, config, shaderProgram);
	}
	
}
