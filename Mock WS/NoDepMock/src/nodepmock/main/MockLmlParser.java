package nodepmock.main;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.vis.util.VisLml;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

import nodepmock.main.MockApp.MockGame;

public class MockLmlParser {

	public static LmlParser parser() {
		return parser;
	}
	
	private static LmlParser parser;
	
	static {
		init();
	}
	
	public static void init() {
		if(!VisUI.isLoaded()) VisUI.load();
		MockGame.config = JsonConfig.readExternal(MockConfig.class, "./");		
		
		Log.info("MockLmlParser locale " + MockGame.config.general.locale);

		var skin = VisUI.getSkin();
		var prefs = loadPrefs();
		
		var bundle = Gdx.files.internal("bundle");
		Locale locale = new Locale(MockGame.config.general.locale);
		var i18n = I18NBundle.createBundle(bundle, locale, "UTF-8"); //LapisAssets.assets.get("res/i18n/ux/bundle", I18NBundle.class);
		Log.info("MockLmlParser test sound.general : " + i18n.get("sound.general") + " = " + prefs.getInteger("sound.general"));
		

		parser = new SapphireLmlParserBuilder() // VisLml.parser()
				.tag(new RootTagProvider(), "root")
				.actions("", new MockLmlGlobalActions())
				.preferences("prefs", prefs)
				.i18nBundle("i18n", i18n)
				.skin(skin)
				.build();
		parser.setStrict(false);
	}

	private static Preferences loadPrefs() {
		var prefs = Gdx.app.getPreferences("test");
		prefs.clear();
		// read conf and put all fields into a map, then into prefs
		Map<String, Object> map = new HashMap<String, Object>();
		MockConfig.loopPrefs(map, MockGame.config, null);
		prefs.put(map);
		prefs.flush();
		return prefs;
	}


	public static class MockLmlGlobalActions implements ActionContainer {
		/**
		 * On any registered change value event
		 */
		@LmlAction("onChangeVal")
		public void onChangeVal(Object obj) {
			var actor = (Actor) obj;
			if(actor instanceof Slider) {
				var slider = (Slider) actor;
				Log.info("onChange Slider " + slider.getName() + " = " + (int) slider.getValue());
				MockConfig.setPref(MockGame.config, slider.getName(), (int) slider.getValue());
			} else 
			if (actor instanceof VisCheckBox) {
				var field = (VisCheckBox) actor;
				MockConfig.setPref(MockGame.config, field.getName(), field.isChecked());
				Log.info("onChange CheckBox " + field.getName() + " = " + field.isChecked());
			} else 
			if (actor instanceof TextField) {
				var field = (TextField) actor;
				MockConfig.setPref(MockGame.config, field.getName(), field.getText()); 
				Log.info("onChange TextField " + field.getName() + " = " + field.getText());
			} else 
			if (actor instanceof SelectBox) {
					var field = (SelectBox) actor;
					MockConfig.setPref(MockGame.config, field.getName(), field.getSelected()); 
					Log.info("onChange SelectBox " + field.getName() + " = " + field.getSelected());
			} else {
				Log.info("onChange : " + actor + " is " + actor.getClass());
			}
			MockGame.config.save();
		}
	}
	
}
