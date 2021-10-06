package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.utils.Array;

public class MockShaderProvider extends BaseShaderProvider {
	
	public String vertPath;
	public String fragPath;
	
	public String vert; 
	public String frag;
	
	public Class<? extends Shader> shaderclass;
	
	public MockShaderProvider() {
		readFiles();
	}
	public MockShaderProvider(String vert, String frag) {
		this.vert = vert;
		this.frag = frag;
	}

	public MockShaderProvider(String vert, String frag, Class<? extends Shader> shaderclass) {
		this.vertPath = vert;
		this.fragPath = frag;
	}
	
	@Override
	protected Shader createShader (final Renderable renderable) {
		try {
			return shaderclass.getConstructor(Renderable.class, Config.class).newInstance(renderable, new Config(vert, frag));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		//return createShader(renderable, new Config(vert, frag)); // new MockShaderProvider(renderable, new Config(vert, frag));
	}

	@Override
	public Shader getShader (Renderable renderable) {
//		Shader suggestedShader = renderable.shader;
//		if (suggestedShader != null && suggestedShader.canRender(renderable)) return suggestedShader;
		for (Shader shader : shaders) {
			if (shader.canRender(renderable)) return shader;
		}
		final Shader shader = createShader(renderable);
		shader.init();
		shaders.add(shader);
		return shader;
	}
	
	/**
	 * read shader files. currently only supports default files. could use lambdas for custom loading
	 */
	public void readFiles() {
		vert = Gdx.files.internal(vertPath).readString();
		frag = Gdx.files.internal(fragPath).readString();
	}
	
	/**
	 * reload shaders
	 */
	public void clear() {
		readFiles();
		this.shaders.clear();
	}
	
	public Array<Shader> get() {
		return shaders;
	}
}
