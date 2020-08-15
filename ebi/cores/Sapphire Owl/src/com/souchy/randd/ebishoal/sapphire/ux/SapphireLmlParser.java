package com.souchy.randd.ebishoal.sapphire.ux;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.parser.LmlData;
import com.github.czyzby.lml.parser.LmlSyntax;
import com.github.czyzby.lml.parser.impl.DefaultLmlParser;
import com.github.czyzby.lml.vis.parser.impl.VisLmlSyntax;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireOwlConf;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImageLmlTagProvider;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.ux.components.CreatureSheet;
import com.souchy.randd.ebishoal.sapphire.ux.components.Timeline;

public class SapphireLmlParser extends DefaultLmlParser {
	
	public static SapphireLmlParser parser;
	

	public static SapphireComponent currentView = null;
	
	SapphireLmlParser(LmlData data, LmlSyntax syntax) {
		super(data, syntax);
		var skin = new SapphireHudSkin(Gdx.files.internal("res/ux/sapphire/main.json")); // SapphireGame.gfx.hud.getSkinFile());
		VisUI.dispose(true);
		VisUI.load(skin);
	}
	
	public static void init() {
		var config = JsonConfig.readExternal(SapphireOwlConf.class, "./");		
		
		var prefs = loadPrefs();
		
//		var ii = LapisAssets.assets.get("res/i18n/ux/bundle", I18NBundle.class);
		var bundle = Gdx.files.internal("res/i18n/ux/bundle");
		Locale locale = new Locale(config.general.locale);
		var i18n = I18NBundle.createBundle(bundle, locale, "UTF-8"); //LapisAssets.assets.get("res/i18n/ux/bundle", I18NBundle.class);
//		Log.info("MockLmlParser test sound.general : " + i18n.get("sound.general") + " = " + prefs.getInteger("sound.general"));
		

		parser = (SapphireLmlParser) new SapphireLmlParserBuilder() // VisLml.parser()
				// Preferences
				.preferences("prefs", prefs)
				// Registering global action container:
//				.actions("", SapphireGame.gfx.hud)
				// Adding localization support:
				.i18nBundle("i18n", i18n)
				// Set default skin
				.skin(VisUI.getSkin())
				.tag(new RoundImageLmlTagProvider(), "roundImage")
				.tag(new RootTagProvider(), "root")
				.tag(new RootTagProvider(), "chat")
				.tag(new RootTagProvider(), "playbar")
				.tag(new RootTagProvider(), "statusicon")  //keep the previous sapphiretag for classes when inside main.lml ? refreshing should prob not imply reloading the lml
				.tag(new RootTagProvider(), "statusbar")
				.tag(new RootTagProvider(), "timeline")
				.tag(new RootTagProvider(), "creaturesheet")
				.tag(new RootTagProvider(), "quickoptions")
				.tag(new RootTagProvider(), "parameters")
//				.actions("sheet", CreatureSheet.class)
//				.actions("timeline", Timeline.class)
				.build();
		parser.setStrict(false);
	}

	private static Preferences loadPrefs() {
		var prefs = Gdx.app.getPreferences("test");
		prefs.clear();
		// read conf and put all fields into a map, then into prefs
		Map<String, Object> map = new HashMap<String, Object>();
		SapphireOwlConf.loopPrefs(map, SapphireOwl.conf, null);
		prefs.put(map);
		prefs.flush();
		return prefs;
	}
	
	
	
	@Override
	public <View> Array<Actor> createView(View view, FileHandle lmlTemplateFile) {
		if(view instanceof Actor) currentView = (SapphireComponent) view;
		var result = super.createView(view, lmlTemplateFile);
		currentView = null;
		return result;
	}
	
}

