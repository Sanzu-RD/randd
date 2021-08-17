package com.souchy.randd.ebishoal.sapphire.controls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.ux.components.sheets.CreatureSheet;
import com.souchy.randd.moonstone.commons.packets.ICM;
import com.souchy.randd.moonstone.white.Moonstone;

public class Commands {
	
	private static Map<String, Consumer<String>> commands = new HashMap<>();
	private static final String channel_command = "Console";
	private static I18NBundle i18n;
	
	public static void init() {
//		var bundle = Gdx.files.internal("res/i18n/ux/bundle");
//		var locale = new Locale(SapphireOwl.conf.general.locale);
//		i18n = I18NBundle.createBundle(bundle, locale, "UTF-8");
		i18n = LapisAssets.assets.get("res/i18n/ux/bundle", I18NBundle.class);
		I18NBundle i18nCreatures = LapisAssets.assets.get("res/i18n/creatures/bundle", I18NBundle.class);
		// LapisAssets.get("res/i18n/ux/bundle")

		commands.put("missingCommand", (t) -> chat(i18n.get("chat.missingCommand")));
		commands.put("clear", (t) -> SapphireGame.gfx.hud.chat.clearChat());
		
		commands.put("learn", (t) -> {
			try {

				int creatureID = Moonstone.fight.timeline.current();
				Creature creature = Moonstone.fight.creatures.get(creatureID);
				int spellModelID = Integer.parseInt(t.split(" ")[1]);
				Spell spell = DiamondModels.spells.get(spellModelID).copy(Moonstone.fight);
				creature.spellbook.add(spell.id);
				
				CreatureSheet.updateSheet(creature);
				
			} catch (Exception e) {
				chat(i18n.get("chat.missingCommand"));
			}
		});
		
		
		commands.put("creatures", (t) -> chat("creatures : %s", Moonstone.fight.creatures.map(c -> c.id + " " + i18nCreatures.get("creature." + c.modelid + ".name"))));
	}
	
	private static void chat(String text, Object... args) {
		Moonstone.bus.post(new ICM(channel_command, "System", String.format(text, args)));
	}
	
	public static void process(String text) {
		Log.info("Command process : " + text);
		
		text = text.substring(1);
		
		if(commands.containsKey(text))
			commands.get(text).accept(text);
		else
			commands.get("missingCommand").accept(text);
	}
	
}
