package com.souchy.randd.tools.mapeditor.shader;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shaders.LapisShader;
import com.souchy.randd.tools.mapeditor.shader.uniforms.GlobalUniforms;
import com.souchy.randd.tools.mapeditor.shader.uniforms.LightingUniforms;
import com.souchy.randd.tools.mapeditor.shader.uniforms.MaterialUniforms;
import com.souchy.randd.tools.mapeditor.shader.uniforms.NodeUniforms;
import com.souchy.randd.tools.mapeditor.shader.uniforms.UniformsModule;

/**
 * 
 * @author Blank
 * @date 1 nov. 2021
 */
public class Shader2Config extends Config {
	
	List<UniformsModule> modules = new ArrayList<>();
	
	public void add(UniformsModule module) {
		modules.add(module);
	}
	
	public void compile(String shaderName) {
		var modulesVerts = modules.stream().map(m -> m.vertex()).toArray(String[]::new); //.toArray(new String[0]);
		var modulesFrags = modules.stream().map(m -> m.fragment()).toArray(String[]::new);
		this.vertexShader = LapisShader.getVertexShader(shaderName, modulesVerts);
		this.fragmentShader = LapisShader.getFragmentShader(shaderName, modulesFrags);
		
	}
	
	public void compile() {
		String path = "res/shaders/base/";
		
		var vertex = new ShaderPart().load(path + "vertex/");
		var fragment = new ShaderPart().load(path + "fragment/");
		
		for(var module : modules) {
			vertex.children.add(new ShaderPart().load(module.vertex())); //module.shader());
			fragment.children.add(new ShaderPart().load(module.fragment()));  //fragment.children.add(module.shader());
		}
		this.vertexShader = vertex.compile();
		this.fragmentShader = fragment.compile();
	}
	
	
	public static Shader2Config defaultConfig() {
		var conf = new Shader2Config();
		conf.add(new GlobalUniforms());
		conf.add(new NodeUniforms());
		conf.add(new MaterialUniforms());
		conf.add(new LightingUniforms());
		return conf;
	}
	
}