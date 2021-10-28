package com.souchy.randd.ebishoal.commons.lapis.gfx.shaders;

import com.badlogic.gdx.Gdx;

public class LapisShader {

	/**
	 * Where we define flags, uniforms, attributes..
	 */
	public static final String defineShader = "define.glsl";
	
	/**
	 * Concatenates the text of all files under a vertex folder
	 * @param shader shader folder name 
	 */
	public static String getVertexShader(String shader, String... modules) {
		String str = compileShader(shader + "/vertex", modules);
		return str;
	}
	/**
	 * Concatenates the text of all files under a fragment folder
	 * @param shader shader folder name 
	 */
	public static String getFragmentShader(String shader, String... modules) {
		String str = compileShader(shader + "/fragment", modules);
		return str;
	}
	
	/**
	 * concatenates the text of all files inside the folder <res/shaders/"shaderFolderName">
	 */
	public static String compileShader(String shaderFolderName, String... modules) {
		String str = "";
		// if there is a define file, add it first
		if(Gdx.files.internal("res/shaders/" + shaderFolderName + "/" + defineShader).exists())
			str += Gdx.files.internal("res/shaders/" + shaderFolderName + "/" + defineShader).readString();
		
		// then add all modules
		for (var module : modules) {
			str += Gdx.files.internal("res/shaders/" + module).readString();
		}
		
		// then add the rest of the chosen shader
		for (var file : Gdx.files.internal("res/shaders/" + shaderFolderName).list()) {
			if(file.name().equals(defineShader) == false) 
				str += file.readString();
		}
		return str;
	}
	
	
}
