package com.souchy.randd.ebishoal.sapphire.controls;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;

public class Commands {
	
	
	public static void process(String text) {
		Log.info("Command process : " + text);
		
		if(text.contains("clear")) {
			SapphireGame.gfx.hud.chat.clearChat();
		}
	}
	
}
