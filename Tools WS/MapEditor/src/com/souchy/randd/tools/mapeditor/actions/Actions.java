package com.souchy.randd.tools.mapeditor.actions;

import com.souchy.randd.tools.mapeditor.configs.EditorConf;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;

public class Actions {

	
	public static void resetConf() {
		MapEditorCore.conf = new EditorConf();
		MapEditorCore.conf.save();
		// this wont work because imgui components we save the config on exit ?
	}
	
	
}
