package com.souchy.randd.ebishoal.sapphire.controls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.ux.components.PlayBar;
import com.souchy.randd.ebishoal.sapphire.ux.components.sheets.CreatureSheet;
import com.souchy.randd.moonstone.commons.packets.ICM;
import com.souchy.randd.moonstone.white.Moonstone;

public class Commands {
	
	private static Map<String, Consumer<Object[]>> commands = new HashMap<>();
	private static final String channel_command = "Console";
	private static I18NBundle i18n;
	private static I18NBundle i18nCreatures;
	private static I18NBundle i18nSpells;
	private static I18NBundle i18nStatus;
	
	public static void init() {
//		var bundle = Gdx.files.internal("res/i18n/ux/bundle");
//		var locale = new Locale(SapphireOwl.conf.general.locale);
//		i18n = I18NBundle.createBundle(bundle, locale, "UTF-8");
		i18n = LapisAssets.get("res/i18n/ux/bundle", I18NBundle.class);
		i18nCreatures = LapisAssets.get("res/i18n/creatures/bundle", I18NBundle.class);
		i18nSpells = LapisAssets.get("res/i18n/spells/bundle", I18NBundle.class);
		i18nStatus = LapisAssets.get("res/i18n/status/bundle", I18NBundle.class);
		

		commands.put("missingCommand", Commands::missingCommand);
		commands.put("clear", (t) -> SapphireGame.gfx.hud.chat.clearChat());
		commands.put("debug", Commands::debug);

		commands.put("creature", Commands::creature);
		commands.put("spell", Commands::spell);
		commands.put("status", Commands::status);
		commands.put("cell", Commands::cell);
		
		commands.put("creatures", Commands::creatures);
		commands.put("spells", Commands::spells);
		commands.put("statuses", Commands::statuses);
		
		commands.put("learn", Commands::learn);
		
		commands.put("reloadAssetModels", Commands::reloadAssetModels);
		commands.put("reloadAssetTextures", Commands::reloadAssetTextures);
		commands.put("reloadAssetData", Commands::reloadAssetData);
		commands.put("reloadRenderables", Commands::reloadRenderables);
		commands.put("rram", Commands::reloadAssetModels);
		commands.put("rrat", Commands::reloadAssetTextures);
		commands.put("rrad", Commands::reloadAssetData);
		commands.put("rrr", Commands::reloadRenderables);

		// to lowercase
		var keys = commands.keySet().toArray();
		var vals = commands.values().toArray();
		for(int i = 0; i < commands.size(); i++) {
			commands.remove(keys[i].toString());
			commands.put(keys[i].toString().toLowerCase(), (Consumer<Object[]>) vals[i]);
		}
	}
	
	private static void missingCommand(Object... args) {
		chat(i18n.get("chat.missingCommand"));
	}
	
	private static void chat(String text, Object... args) {
		Moonstone.bus.post(new ICM(channel_command, "System", String.format(text, args)));
	}
	
	public static void process(String text) {
		try {
			Log.verbose("Command process : " + text);
			
			if(text.startsWith("/"))
				text = text.substring(1);
			text = text.toLowerCase();
			
			var args = text.split(" ");
			
			if(commands.containsKey(args[0]))
				commands.get(args[0]).accept(args);
			else
				process("missingCommand");
		} catch(Exception e) {
			Log.info("", e);
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static void debug(Object... args) {
		SapphireGame.gfx.hud.debug();
	}
	
	public static void creature(Object... args) {
		var id = Integer.parseInt(args[1].toString());
		var c = SapphireGame.fight.creatures.get(id);
		chat("info creature: %s (%s)# %s | spells %s | statuses %s", i18nCreatures.get("creature." + c.modelid + ".name"), c.modelid, c.id, c.spellbook, c.statuses);
	}
	public static void spell(Object... args) {
		var id = Integer.parseInt(args[1].toString());
		var c = SapphireGame.fight.spells.get(id);
		chat("info spell: %s (%s) #%s | %s", i18nSpells.get("spell." + c.modelid() + ".name"), c.modelid(), c.id, "");
	}
	public static void status(Object... args) {
		var id = Integer.parseInt(args[1].toString());
		var c = SapphireGame.fight.status.get(id);
		chat("info status: %s (%s) #%s | stacks %s, duration %s, source %s, target %s", i18nStatus.get("status." + c.modelid() + ".name"), c.modelid(), c.id, c.stacks, c.duration, c.sourceEntityId, c.targetEntityId);
	}
	
	public static void cell(Object... args) {
		var id = Integer.parseInt(args[1].toString());
		var c = SapphireGame.fight.cells.get(id);
		if(c != null) chat("info cell: (%s) %s | statuses %s", c.id, c.pos, c.statuses);
		else chat("info cell %s null", id);
	}
	
	
	public static void creatures(Object... args) {
		chat("all creatures : %s", SapphireGame.fight.creatures.map(c -> c.id + " " + i18nCreatures.get("creature." + c.modelid + ".name")));
	}

	public static void spells(Object... args) {
		chat("all spells : %s", SapphireGame.fight.spells.map(c -> c.id + " " + i18nSpells.get("spell." + c.modelid() + ".name")));
	}

	public static void statuses(Object... args) {
		chat("all statuses : %s", SapphireGame.fight.status.map(c -> c.id + " " + i18nStatus.get("status." + c.modelid() + ".name")));
	}
	
	
	
	public static void learn(Object... args) {
		int creatureID = Moonstone.fight.timeline.current();
		Creature creature = Moonstone.fight.creatures.get(creatureID);
		int spellModelID = Integer.parseInt(args[1].toString());
		Spell spell = DiamondModels.spells.get(spellModelID).copy(Moonstone.fight);
		creature.spellbook.add(spell.id);
		CreatureSheet.updateSheet(creature);
	}
	
	public static void reloadAssetModels(Object... args) {
		LapisAssets.loadModels(Gdx.files.internal("res/models/"));
	}
	public static void reloadAssetTextures(Object... args) {
		LapisAssets.loadTextures(Gdx.files.internal("res/textures/"));
	}
	
	public static void reloadAssetData(Object... args) {
		AssetData.loadResources();
	}

	public static void reloadRenderables(Object... args) {
		reloadAssetModels();
		reloadAssetData();
		SapphireGame.renderableEntitySystem.foreach(SapphireGame.renderableEntitySystem::makeRenderable);
	}
	
}
