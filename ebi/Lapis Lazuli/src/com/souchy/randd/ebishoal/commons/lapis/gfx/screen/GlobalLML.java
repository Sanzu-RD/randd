package com.souchy.randd.ebishoal.commons.lapis.gfx.screen;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.util.LmlUtilities;
import com.github.czyzby.lml.vis.util.VisLml;

public class GlobalLML extends LmlApplicationListener {
	private static GlobalLML singleton;
	
	private static FileHandle i18n;
	
	public static GlobalLML lml() {
		if(singleton == null) singleton = new GlobalLML();
		return singleton;
	}
	
	public static LmlParser getLmlParser() {
		return lml().getParser();
	}
	
	public GlobalLML() {
		i18n = Gdx.files.internal("res/i18n/bundle");
		create();
	}
	
	@Override
	protected LmlParser createParser() {
		return VisLml.parser()
				// Registering global action container:
				.actions("global", GlobalLMLActions.class)
				// Adding localization support:
				.i18nBundle(I18NBundle.createBundle(i18n))
				// Add global custom skin
				.skin(new Skin(Gdx.files.internal("uiskin.json")))
				.build();
	}

	
	public static class GlobalLMLActions implements ActionContainer {
		// private final MockGame core = (MockGame) Gdx.app.getApplicationListener();
		// private final GlobalLML lml = GlobalLML.lml();
		@LmlAction("setLocale")
		public void setLocale(final Actor actor) {
			final String localeId = LmlUtilities.getActorId(actor);
			final I18NBundle currentBundle = GlobalLML.lml().getParser().getData().getDefaultI18nBundle();
			// If same language.
			if(currentBundle.getLocale().getLanguage().equalsIgnoreCase(localeId)) return;
			GlobalLML.lml().getParser().getData().setDefaultI18nBundle(I18NBundle.createBundle(i18n, new Locale(localeId)));
			GlobalLML.lml().reloadViews();
		}
		@LmlAction("reload")
		public void reload() {
			GlobalLML.lml().reloadViews();
		}
	}
}