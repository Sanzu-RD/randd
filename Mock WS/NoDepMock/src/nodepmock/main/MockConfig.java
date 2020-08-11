package nodepmock.main;

import java.util.Map;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class MockConfig extends JsonConfig {
	
	public GeneralConfig general = new GeneralConfig();
	public UIConfig ui = new UIConfig();
	public GfxConfig gfx = new GfxConfig();
	public SoundConfig sound = new SoundConfig();
	

	public static class GeneralConfig extends JsonConfig {
		public String locale = "fr";
	}

	public static class UIConfig extends JsonConfig {
		public boolean grid = true;
	}
	
	public static class GfxConfig extends JsonConfig {
		public boolean postProcess = true;
	}
	
	public static class SoundConfig extends JsonConfig {
		public int general;
		public int music;
		public int ambiance;
		public int fx;
		public int ui;
		public boolean play = true;
	}
	


	public static void setPref(Object container, String key, Object val) {
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
