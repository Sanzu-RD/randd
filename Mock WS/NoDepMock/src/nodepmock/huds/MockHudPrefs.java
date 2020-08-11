package nodepmock.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.randd.commons.tealwaters.logging.Log;

import nodepmock.main.MockLmlParser;
import nodepmock.main.MockApp.MockGame;

public class MockHudPrefs extends AbstractLmlView {
	
	@LmlActor("parameters")
	public Table parameters;
	
	@LmlActor("scrollpane")
	public ScrollPane scrollpane;
	
	@LmlActor("general")
	public Table general;
	
	@LmlActor("ui")
	public Table ui;
	
	@LmlActor("gfx")
	public Table gfx;
	
	@LmlActor("sound")
	public Table sound;
	
	@LmlActor("general.locale.value")
	public SelectBox<String> localeSelectBox;

	public MockHudPrefs() {
		super(new Stage());
		init();
	}
	
	private void init() {
		Log.info("hud init");
		this.getStage().clear();
		MockLmlParser.parser().createView(this, getTemplateFile());
		
		parameters.getCells().get(0).prefWidth(this.getStage().getWidth() * 0.5f);
		parameters.getCells().get(0).prefHeight(this.getStage().getHeight() * 0.8f);
		
		var locale = MockGame.config.general.locale; // localeSelectBox.getSelected(); //
//		Log.info("hud init selected " + locale);
		localeSelectBox.setItems("fr", "en");
		localeSelectBox.setSelected(locale);
		localeSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MockLmlParser.init();
				init();
			}
		});
	}
	
	
	public String getViewId() {
		return "parameters";
	}
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal(getViewId() + ".lml");
	}
	
	public FileHandle getSkinFile() {
		return Gdx.files.internal(getViewId() + ".json");
	}
	

}
