package nodepmock.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

import nodepmock.b2.ParamComponent;
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
//			MockHudPrefs hud = new MockHudPrefs();
//			scene.view = hud;

			scene.view = new AbstractLmlView(new Stage()) {
				@Override
				public String getViewId() {
					return "main";
				}
			};
//			var com = new ParamComponent();
//			scene.view.getStage().addActor(com);
//			com.init();
			
//			var a = MockLmlParser.parser().parseTemplate(new ParamComponent().getTemplateFile());
//			scene.view.getStage().addActor(a.get(0));
			
			scene.view.getStage().addActor(new ParamComponent());
			
			
			screen = scene;
			screen.show();
		}
		
	}
	
}
