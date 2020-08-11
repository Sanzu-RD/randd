package nodepmock.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

import nodepmock.huds.MockHudPrefs;
import nodepmock.screens.MockScreen;

public class MockApp {

	private LwjglApplicationConfiguration config;
	
	public static void main(String[] args) throws Exception {
		new MockApp().start();
	}

	public void start() {
		config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
	    new LwjglApplication(new MockGame(), config);
	}
	
	
	public static class MockGame extends Game {
		public static MockConfig config;
		static {
			config = JsonConfig.readExternal(MockConfig.class, "./");		
		}
		@Override
		public void create() {
			MockScreen scene = new MockScreen();
			MockHudPrefs hud = new MockHudPrefs();
			scene.view = hud;
			
			screen = scene;
			screen.show();
		}
		
	}
	
}
