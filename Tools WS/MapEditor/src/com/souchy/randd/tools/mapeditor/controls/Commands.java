package com.souchy.randd.tools.mapeditor.controls;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;
import com.souchy.randd.tools.mapeditor.actions.Actions;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;

public class Commands {
	
	public static class LapisCommands {
		private static Map<String, Consumer<Object[]>> commands = new HashMap<>();
		private static final String channel_command = "Console";
		private static I18NBundle i18n;
		private static String missingCommandKey = "chat.missingCommand";
		
		static {
			LapisAssets.blocking = true;
			LapisAssets.loadI18NBundles(Gdx.files.internal("res/i18n/ux/bundle"));
			LapisAssets.blocking = false;
			i18n = LapisAssets.get("res/i18n/ux/bundle", I18NBundle.class);
			

			//commands.put("missingCommand", LapisCommands::missingCommand);
			commands.put("clear", (t) -> { }); // SapphireGame.gfx.hud.chat.clearChat());
			commands.put("chat", (t) -> { }); // Moonstone.bus.post(new ICM(channel_command, "System", String.format(text, args)));
			//commands.put("debug", Commands::debug);
			//initCommands();
			
			// to lowercase
			var keys = commands.keySet().toArray();
			var vals = commands.values().toArray();
			for(int i = 0; i < commands.size(); i++) {
				commands.remove(keys[i].toString());
				commands.put(keys[i].toString().toLowerCase(), (Consumer<Object[]>) vals[i]);
			}
		}
		private static void chat(String text, Object... args) {
			process("/chat " + String.format(text, args));
		}
		private static void missingCommand(Object... args) {
			String str = i18n == null ? missingCommandKey : i18n.get(missingCommandKey);
			for(var s : args) str += " " + s;
			chat(str);
		}
		public static void setChat(Consumer<Object[]> action) {
			commands.put("chat", action);
		}
		public static void setClearChat(Consumer<Object[]> action) {
			commands.put("clear", action);
		}
		public static void process(String text) {
			try {
				Log.verbose("Command process : " + text);
				
				// remove the "/" if present, but not needed
				if(text.startsWith("/"))
					text = text.substring(1);
				text = text.toLowerCase();
				
				// special case for chat because we dont want to split all our text
				if(text.startsWith("chat ")) {
					commands.get("chat").accept(text.split(" ", 2));
					return;
				}
				
				// split all arguments
				var args = text.split(" ");
				// call command if present
				if(commands.containsKey(args[0]))
					commands.get(args[0]).accept(args);
				else {
					//Log.info("process cant find command");
					missingCommand();
				}
			} catch(Exception e) {
				Log.warning("", e);
			}
		}
		
		public static void add(String name, Consumer<Object[]> action) {
			commands.put(name, action);
		}
		
	}

	public static void initCommands() {
		LapisCommands.setClearChat(o -> {
			MapEditorGame.screen.imgui.console.messages.clear();
			MapEditorGame.screen.imgui.console.messages.add("");
			Log.info("clear x");
		});
		LapisCommands.setChat(o -> {
			MapEditorGame.screen.imgui.console.messages.add((String) o[1]);
			Log.info("chat x");
		});
		LapisCommands.add("ra", o -> Actions.reloadAssets());
		LapisCommands.add("ram", o -> Actions.reloadAssetModels());
		LapisCommands.add("rat", o -> Actions.reloadAssetTextures());
		LapisCommands.add("rad", Actions::reloadAssetData);
		LapisCommands.add("load", Commands::load);
		LapisCommands.add("r", Commands::resetShaders);
		LapisCommands.add("rs", o -> Actions.resetConf());
	}
	
	
	/**
	 * Load an asset (model.g3dj, music.mp3, texture.png.jpg.jpeg.bmp...)
	 */
	public static void load(Object... args) {
		String filepath = (String) args[1];
		var handle = Gdx.files.internal(filepath);

		LapisAssets.loadAuto(handle);
	}

	
	public static void resetShaders(Object... args) {
		MapEditorGame.screen.provider.reset();
	}
	
}
