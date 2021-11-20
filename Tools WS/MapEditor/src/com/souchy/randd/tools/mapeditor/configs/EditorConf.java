package com.souchy.randd.tools.mapeditor.configs;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;

public class EditorConf extends JsonConfig {
	
	/** this will disable save on exit for the current session and will be set to false when the application starts again */
	public boolean reset = true;
	
	public String lastFolder = "res/"; //Environment.res.toAbsolutePath().toString();
	
	public GraphicsConf gfx = new GraphicsConf();
	public HudConfig hud = new HudConfig();
	// public ShortcutConf shortcuts 
	
	
	
	public static class GraphicsConf {
		public boolean vsync = false;
		public int fps = 200;
		public int backgroundFps = 60;
		public int x = -1;
		public int y = -1;
		public int width = 1600;
		public int height = 900;
	}
	
	public static class HudConfig extends JsonConfig {
		/** String - full class name with package */
		public Map<String, ComponentConfig> components = new HashMap<>();
		public static class ComponentConfig {
			public boolean visible;
			public int[] size;
			public int[] pos;
			public ComponentConfig() { }
			public ComponentConfig(boolean visible, int[] pos, int[] size) {
				this.visible = visible;
				this.pos = pos;
				this.size = size;
			}
		}
	}

	
}
