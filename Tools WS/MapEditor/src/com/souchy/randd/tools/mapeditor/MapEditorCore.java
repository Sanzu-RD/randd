package com.souchy.randd.tools.mapeditor;

import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;

public class MapEditorCore extends LapisCore {
	
	public static final MapEditorCore core = new MapEditorCore();
	
	public static void main(String[] args) throws Exception {  
	     launch(core); 
	 }
	
	@Override
	protected LapisGame createGame() {
		return new MapEditorGame();
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.tools.mapeditor" };
	}

	
}
