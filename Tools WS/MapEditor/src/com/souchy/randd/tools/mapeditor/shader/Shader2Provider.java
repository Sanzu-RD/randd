package com.souchy.randd.tools.mapeditor.shader;

import java.util.function.Function;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;

/**
 * Shader provider based on a lambda Function for the createShader(Renderable) method
 * 
 * @author Blank
 * @date 1 nov. 2021
 */
public class Shader2Provider extends BaseShaderProvider {
	/**
	 * Builder to create a new shader instance based on a renderable. <br>
	 * Usually just <code>(renderable) -> new Shader(renderable, config);</code>
	 */
	private Function<Renderable, Shader> builder;
	
	/**
	 * Shader provider creating a Shader2 with the default config
	 */
	public Shader2Provider() {
		builder = (r) -> new Shader2(r, Shader2Config.defaultConfig());
	}
	
	/**
	 * Shader provider using a custom builder to create a custom new shader with a custom config
	 * <br>
	 * Examples : 
	 * <pre>(renderable) -> new Shader(renderable, config);</pre>
	 * or
	 * <pre>
	 * new ModelBatch(provider = new ShadeProvider(r -> { 
	 * 	var conf = new ExtendableShader.ExtendableConfig();
	 *	conf.add(new DissolveUniforms()); 
	 *	conf.compile("base"); 
	 *	return new ExtendableShader(r, conf);
	 * }));
	 * </pre>
	 * @param builder
	 */
	public Shader2Provider(Function<Renderable, Shader> builder) {
		this.builder = builder;
	}
	
	/**
	 * Dispose all cached shaders and clear the cached list
	 */
	public void reset() {
		for(var s : shaders)
			s.dispose();
		this.shaders.clear();
	}
	
	/**
	 * Calls the builder to create a shader instance
	 */
	@Override
	protected Shader createShader(Renderable renderable) {
		return builder.apply(renderable);
	}
	
	/**
	 * Get a shader that has canRender(r) true. Creates and caches a new one if needed.
	 */
	@Override
	public Shader getShader(Renderable renderable) {
		return super.getShader(renderable);
	}
	
}

