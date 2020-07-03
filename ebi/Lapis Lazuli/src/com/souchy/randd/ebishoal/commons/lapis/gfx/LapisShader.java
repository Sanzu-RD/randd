package com.souchy.randd.ebishoal.commons.lapis.gfx;

import com.badlogic.gdx.Gdx;

public class LapisShader {

	/**
	 * Concatenates the text of all files under a vertex folder
	 * @param shader shader folder name 
	 */
	public static String getVertexShader(String shader) {
		String vertex = "";
		for(var file : Gdx.files.internal("res/shaders/"+shader+"/vertex").list()) 
			vertex += file.readString();
		return vertex;
	}
	/**
	 * Concatenates the text of all files under a fragment folder
	 * @param shader shader folder name 
	 */
	public static String getFragmentShader(String shader) {
		String fragment = "";
		for(var file : Gdx.files.internal("res/shaders/"+shader+"/fragment").list()) 
			fragment += file.readString();
		return fragment;
	}
	
}
