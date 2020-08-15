package nodepmock.b2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.souchy.randd.commons.tealwaters.logging.Log;

import nodepmock.main.MockLmlParser;
import nodepmock.main.SapphireComponent;
import nodepmock.main.MockApp.MockGame;
import nodepmock.screens.MockScreen;

public class ParamComponent extends SapphireComponent { // Table implements ActionContainer {
	
	/*
	 * On avait fait les sapphirewidget comme ça pcq on voulait que la classe component extend Table
	 * pour que ça soit le root / que la classe soit vraiment un actor plutôt que juste un wrapper 
	 * donc forcément, quand on parse le template, ça créé la root table 
	 * 
	 * 
	 * 
	 * Enjeux : 
	 * - classe composant est un actor/table ou non
	 * - 
	 * 
	 * 
	 * createView(this, getTemplateFile()); - fonctionne pour les acteurs à l'intérieur, mais ne set pas this comme étant le premier acteur (root)
	 * this reste une table vide
	 * 
	 * 
	 */

//	@LmlActor("parameters")
//	public Table parameters;
	
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
	
	public ParamComponent() {
//		LmlParser parser = MockLmlParser.parser();
//		parser.createView(this, getTemplateFile());
//		parser.parseTemplate(getTemplateFile());
		this.setStage(MockScreen.view.getStage());
		init();
	}

//	public void parse() {
//		MockLmlParser.parser().createView(this, getTemplateFile());
//		init();
//	}
	
	public void init() {
		Log.info("params init");
		
		var stage = this.getStage();
		stage.clear();
		this.clear();
		MockLmlParser.parser().createView(this, getTemplateFile());
		stage.addActor(this);
		
//		this.add(parameters);
		Log.info("params = " + this);
//		
		this.getCells().get(0).prefWidth(stage.getWidth() * 0.5f); // parameters.
		this.getCells().get(0).prefHeight(stage.getHeight() * 0.8f); // parameters.
		
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
	
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal(getTemplateId() + ".lml");
	}
	
	public FileHandle getSkinFile() {
		return Gdx.files.internal(getTemplateId() + ".json");
	}

	@Override
	public String getTemplateId() {
		return "parameters";
	}
	
	
}
