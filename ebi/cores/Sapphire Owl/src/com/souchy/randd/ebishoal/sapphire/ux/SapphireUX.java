package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.vis.util.VisLml;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.GlobalLML.GlobalLMLActions;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireBatch;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHudSkin;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImageLmlTagProvider;

public class SapphireUX extends LmlApplicationListener {

	public SapphireBatch batch;
	public Viewport viewport;
	public Stage stage;
	public Skin skin;
	
	public SapphireUX() {
		batch = new SapphireBatch();
		viewport = new ScreenViewport();
		stage = new Stage(viewport, batch);
		skin = new SapphireHudSkin(Gdx.files.internal("res/ux/sapphire/main.json"));
		
		var chat = new Chat();
		var actors1 = this.getParser().createView(chat, chat.getTemplateFile());
		var actors2 = this.getParser().parseTemplate(chat.getTemplateFile());
		
		
		Chat c = this.getParser().createView(Chat.class, "res/ux/sapphire/chat.lml");
		

		var main = this.getParser().createView(AbstractLmlView.class, "res/ux/sapphire/main.lml");
		this.setView(main);
		
		
		stage.addActor(c);
		
	}
	
	@Override
	protected LmlParser createParser() {
		return VisLml.parser()
				// Registering global action container:
				.actions("global", GlobalLMLActions.class)
				// Adding localization support:
				//.i18nBundle(i18n)
				// Set default skin
				.skin(skin) 
				// Tags
				.tag(new RoundImageLmlTagProvider(), "roundImage")
				.build();
	}
	
	/*
	 * private static class SapphireView extends AbstractLmlView { public
	 * SapphireView(Stage stage) { super(stage); }
	 * 
	 * @Override public String getViewId() { return "main"; }
	 * 
	 * }
	 */
	
}
