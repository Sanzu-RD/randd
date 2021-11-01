package com.souchy.randd.tools.mapeditor.shader.uniforms;

import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.souchy.randd.tools.mapeditor.shader.ShaderPart;

/**
 * All modules add their glsl code to the parent shader. <br>
 * 
 * They are activated by a prefix like "#define dissolveFlag" created only if the renderable needs it (ex: has a Dissolve attribute) <br>
 * 
 * That define flag then activates the rest of the code inside tags like "#if dissolveFlag" "#endif" <br>
 * 
 * Therefore, shaders only contain that module's code if the renderable needs it <br>
 * 
 * 
 * @author Blank
 * @date 1 nov. 2021
 */
public interface UniformsModule {
	
	/**
	 * Register uniforms
	 */
	public default void register(BaseShader s, Renderable r, Config config) {
		// do nothing
	}
	
	/**
	 * Init shader based on a renderable
	 */
	public default void init(BaseShader s) {
		// do nothing
	}
	
	/**
	 * 
	 */
	public default void begin(BaseShader s) {
		// do nothing
	}
	
	/**
	 * Bind uniforms every frame
	 */
	public default void bind(BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
		// do nothing
	}
	
	/**
	 * 
	 */
	public default boolean canRender(Renderable r) {
		return true;
	}

	/**
	 * Return a prefix to add to the shader if required by the renderable. <br>
	 * Example : "#define dissolveFlag"
	 */
	public default String prefixFlags(Renderable renderable, Config config) {
		return "";
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
	
	/**
	 * 
	 */
//	public default ShaderPart shader() {
//		return null;
//	}
	
	
}
