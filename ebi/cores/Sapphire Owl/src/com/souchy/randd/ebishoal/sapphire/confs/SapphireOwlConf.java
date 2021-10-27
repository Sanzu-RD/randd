package com.souchy.randd.ebishoal.sapphire.confs;

import java.lang.reflect.Field;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.Exclude;
import com.souchy.randd.ebishoal.commons.lapis.util.KeyCombination;
import com.souchy.randd.ebishoal.commons.lapis.util.KeyCombination.KeyCombinationArray;

/**
 * 
 * @author Blank
 * @date 15 août 2020
 */
public class SapphireOwlConf extends JsonConfig {

	@Exclude
	public EventBus bus = new EventBus();
	
	
	public GeneralConfig general = new GeneralConfig();
	public FunctionalityConfig functionality = new FunctionalityConfig();
	public ShortcutConfig shortcut = new ShortcutConfig();
	public UIConfig ui = new UIConfig();
	public GfxConfig gfx = new GfxConfig();
	public SoundConfig sound = new SoundConfig();
	

	public static class GeneralConfig extends JsonConfig {
		public String locale = "fr";
		public int maxChatMessages = 100;
	}

	public static class FunctionalityConfig extends JsonConfig {
		public Color gridColor = Color.GRAY;
		public boolean grid = true;
		public boolean centerline = true;
		public boolean ruler = true;
		public boolean lineofsight = true; 
		public boolean showCursorPos = false;
	}

	/**
	 *  have to process {@link Keys} and {@link Buttons}
	 */
	public static class ShortcutConfig extends JsonConfig {
		public int buttonAction = Buttons.LEFT;
		public int buttonInfo = Buttons.RIGHT;
		public KeyCombination targetCellModifier = new KeyCombination(Keys.CONTROL_LEFT);
		
		
		public KeyCombinationArray refreshui = new KeyCombinationArray(new KeyCombination(Keys.SPACE));
		public KeyCombinationArray cancel = new KeyCombinationArray(new KeyCombination(Keys.ESCAPE));
		public KeyCombinationArray passTurn = new KeyCombinationArray(new KeyCombination(Keys.C));
		public KeyCombinationArray camReset = new KeyCombinationArray(new KeyCombination(Keys.R));
		public KeyCombinationArray camTopView = new KeyCombinationArray(new KeyCombination(Keys.T));
		public KeyCombinationArray musicToggle = new KeyCombinationArray(new KeyCombination(Keys.M));
		public KeyCombinationArray debugUI = new KeyCombinationArray(new KeyCombination(Keys.F3));
		
		public KeyCombinationArray testGainLife = new KeyCombinationArray(new KeyCombination(Keys.V));
		public KeyCombinationArray testMove = new KeyCombinationArray(new KeyCombination(Keys.X));

		public KeyCombinationArray highlightCells = new KeyCombinationArray(new KeyCombination(Keys.J));

		public KeyCombinationArray renderCache = new KeyCombinationArray(new KeyCombination(Keys.K));
		public KeyCombinationArray renderBackground = new KeyCombinationArray(new KeyCombination(Keys.B));
		public KeyCombinationArray renderLines = new KeyCombinationArray(new KeyCombination(Keys.L));
		
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
		
		public KeyCombinationArray translateUpX = new KeyCombinationArray();
		public KeyCombinationArray translateDownX = new KeyCombinationArray();
		public KeyCombinationArray translateLeftX = new KeyCombinationArray();
		public KeyCombinationArray translateRightX = new KeyCombinationArray();
		
		public KeyCombinationArray zoomInFree = new KeyCombinationArray(new KeyCombination(Keys.Q), new KeyCombination(Keys.CONTROL_LEFT));
		public KeyCombinationArray zoomOutFree = new KeyCombinationArray(new KeyCombination(Keys.E), new KeyCombination(Keys.SHIFT_LEFT));
		
		public KeyCombinationArray zoomInX = new KeyCombinationArray();
		public KeyCombinationArray zoomOutX = new KeyCombinationArray();

		public KeyCombinationArray spell0 = new KeyCombinationArray(new KeyCombination(Keys.NUM_1));
		public KeyCombinationArray spell1 = new KeyCombinationArray(new KeyCombination(Keys.NUM_2));
		public KeyCombinationArray spell2 = new KeyCombinationArray(new KeyCombination(Keys.NUM_3));
		public KeyCombinationArray spell3 = new KeyCombinationArray(new KeyCombination(Keys.NUM_4));
		public KeyCombinationArray spell4 = new KeyCombinationArray(new KeyCombination(Keys.NUM_5));
		public KeyCombinationArray spell5 = new KeyCombinationArray(new KeyCombination(Keys.NUM_6));
		public KeyCombinationArray spell6 = new KeyCombinationArray(new KeyCombination(Keys.NUM_7));
		public KeyCombinationArray spell7 = new KeyCombinationArray(new KeyCombination(Keys.NUM_8));
		public KeyCombinationArray spell8 = new KeyCombinationArray(new KeyCombination(Keys.NUM_9));
		public KeyCombinationArray spell9 = new KeyCombinationArray(new KeyCombination(Keys.NUM_0));
		
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
