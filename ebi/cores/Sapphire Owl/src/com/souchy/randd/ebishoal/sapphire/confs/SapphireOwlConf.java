package com.souchy.randd.ebishoal.sapphire.confs;

import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;

/**
 * 
 * @author Blank
 * @date 15 août 2020
 */
public class SapphireOwlConf extends JsonConfig {

	public GeneralConfig general = new GeneralConfig();
	public FunctionalityConfig functionality = new FunctionalityConfig();
	public ShortcutConfig shortcut = new ShortcutConfig();
	public UIConfig ui = new UIConfig();
	public GfxConfig gfx = new GfxConfig();
	public SoundConfig sound = new SoundConfig();
	

	public static class GeneralConfig extends JsonConfig {
		public String locale = "fr";
	}

	public static class FunctionalityConfig extends JsonConfig {
		public boolean centerline = true;
		public boolean grid = true;
		public Color gridColor = Color.GRAY;
		public boolean ruler = true;
		public boolean lineofsight = true; 
	}

	/**
	 *  have to process {@link Keys} and {@link Buttons}
	 */
	public static class ShortcutConfig extends JsonConfig {
		public String[] refreshui = new String[] { "SPACE" };
		
	}
	
	public static class UIConfig extends JsonConfig {
		public double scale = 100; // percent
	}
	
	public static class GfxConfig extends JsonConfig {
		public boolean shadows = true;
		
		public boolean background = true;
		public String backgroundnPath = "";
		public Color backgroundColor = Color.BLACK;
		
		public boolean postProcess = true;
		public boolean stencil = true;
		public boolean blur = true;
	}
	
	public static class SoundConfig extends JsonConfig {
		public int general;
		public int music;
		public int ambiance;
		public int fx;
		public int ui;
		//public boolean play = true; pause at sound = 0
	}
	


	public void setPref(String key, Object val) {
		setPref(this, key, val);
	}
	public void setPref(Object container, String key, Object val) {
		try {
			key = key.replace(".value", "");
			var clazz = container.getClass();
			var keys = key.split("\\.");
			//Log.info("config set container (" + clazz + ") key [" + key + "] vs keys {" + keys.length + "} vs 0 = " + keys[0]);
			var field = clazz.getField(keys[0]);
			if (keys.length > 1) {
				setPref(field.get(container), key.substring(key.indexOf(".") + 1), val);
			} else {
				field.set(container, val);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	
	public static void loopPrefs(Map<String, Object> map, Object container, String prefix) {
		for (var field : container.getClass().getFields()) {
			try {
				var name = prefix == null ? field.getName() : String.join(".", prefix, field.getName());
				var val = field.get(container);
				//Log.info("field ("+field.getType()+") " + name + " = " + val);
				if (JsonConfig.class.isAssignableFrom(field.getType())) {
					loopPrefs(map, val, name);
				} else {
					map.put(name, val);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
