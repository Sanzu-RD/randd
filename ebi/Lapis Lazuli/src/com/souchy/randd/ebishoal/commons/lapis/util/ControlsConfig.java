package com.souchy.randd.ebishoal.commons.lapis.util;

import java.lang.reflect.Field;
import java.util.Map;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.Exclude;
import com.souchy.randd.ebishoal.commons.lapis.util.KeyCombination.KeyCombinationArray;

public class ControlsConfig extends JsonConfig {

	@Exclude
	public EventBus bus = new EventBus();
	
	
	public int buttonAction = Buttons.LEFT;
	public int buttonInfo = Buttons.RIGHT;
	public KeyCombinationArray cancel = new KeyCombinationArray(new KeyCombination(Keys.ESCAPE));
	public KeyCombinationArray musicToggle = new KeyCombinationArray(new KeyCombination(Keys.M));

	public Controls3dConfig controls3d = new Controls3dConfig();
	
	public static class Controls3dConfig extends JsonConfig {
		/** whether we can move the camera with the mouse or not */
		public boolean controlCameraWithMouse = true;
		public int buttonDrag = Buttons.LEFT;
		public int buttonRotate = Buttons.RIGHT;
		
		public KeyCombinationArray camReset = new KeyCombinationArray(new KeyCombination(Keys.SPACE));
		public KeyCombinationArray camTopView = new KeyCombinationArray(new KeyCombination(Keys.T));
		
		public KeyCombinationArray rotateUpFree = new KeyCombinationArray(new KeyCombination(Keys.W));
		public KeyCombinationArray rotateDownFree = new KeyCombinationArray(new KeyCombination(Keys.S));
		public KeyCombinationArray rotateLeftFree = new KeyCombinationArray(new KeyCombination(Keys.A));
		public KeyCombinationArray rotateRightFree = new KeyCombinationArray(new KeyCombination(Keys.D));
		
		public KeyCombinationArray rotateUpX = new KeyCombinationArray(new KeyCombination(Keys.UP));
		public KeyCombinationArray rotateDownX = new KeyCombinationArray(new KeyCombination(Keys.DOWN));
		public KeyCombinationArray rotateLeftX = new KeyCombinationArray(new KeyCombination(Keys.LEFT));
		public KeyCombinationArray rotateRightX = new KeyCombinationArray(new KeyCombination(Keys.RIGHT));
		
		public KeyCombinationArray translateUpFree = new KeyCombinationArray(new KeyCombination(Keys.W, Keys.ALT_LEFT));
		public KeyCombinationArray translateDownFree = new KeyCombinationArray(new KeyCombination(Keys.S, Keys.ALT_LEFT));
		public KeyCombinationArray translateLeftFree = new KeyCombinationArray(new KeyCombination(Keys.A, Keys.ALT_LEFT));
		public KeyCombinationArray translateRightFree = new KeyCombinationArray(new KeyCombination(Keys.D, Keys.ALT_LEFT));
		
		public KeyCombinationArray translateUpX = new KeyCombinationArray(new KeyCombination(Keys.UP, Keys.ALT_LEFT));
		public KeyCombinationArray translateDownX = new KeyCombinationArray(new KeyCombination(Keys.DOWN, Keys.ALT_LEFT));
		public KeyCombinationArray translateLeftX = new KeyCombinationArray(new KeyCombination(Keys.LEFT, Keys.ALT_LEFT));
		public KeyCombinationArray translateRightX = new KeyCombinationArray(new KeyCombination(Keys.RIGHT, Keys.ALT_LEFT));
		
		public KeyCombinationArray zoomInFree = new KeyCombinationArray(new KeyCombination(Keys.Q)); //, new KeyCombination(Keys.CONTROL_LEFT));
		public KeyCombinationArray zoomOutFree = new KeyCombinationArray(new KeyCombination(Keys.E)); //, new KeyCombination(Keys.SHIFT_LEFT));
		
		public KeyCombinationArray zoomInX = new KeyCombinationArray();
		public KeyCombinationArray zoomOutX = new KeyCombinationArray();
	}


	
	public static class ConfigEvent {
		public Field f;
		public Object oldValue;
		public Object newValue;
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
				bus.post(new ConfigEvent() {
					{
						this.f = field;
						this.oldValue = field.get(container);
						this.newValue = val;
					}
				});
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
