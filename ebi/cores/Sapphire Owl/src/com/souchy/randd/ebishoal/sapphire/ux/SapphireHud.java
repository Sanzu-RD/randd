package com.souchy.randd.ebishoal.sapphire.ux;

import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.annotation.LmlAction;
import com.google.common.eventbus.Subscribe;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.souchy.randd.commons.diamond.effects.resources.ResourceGainLoss;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler.HandlerType;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.diamond.statusevents.resource.ResourceGainLossEvent;
import com.souchy.randd.commons.diamond.statusevents.resource.ResourceGainLossEvent.OnResourceGainLossHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.LapisShader;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireBatch;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
//import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.LmlWidgets;
//import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.SapphireWidgetTagProvider;
import com.souchy.randd.ebishoal.sapphire.ux.components.Chat;
import com.souchy.randd.ebishoal.sapphire.ux.components.Parameters;
import com.souchy.randd.ebishoal.sapphire.ux.components.PlayBar;
import com.souchy.randd.ebishoal.sapphire.ux.components.QuickOptions;
import com.souchy.randd.ebishoal.sapphire.ux.components.Timeline;
import com.souchy.randd.moonstone.white.Moonstone;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent.DisposeUIEvent;
import com.souchy.randd.ebishoal.sapphire.ux.actions.ResourceDecalAction;
import com.souchy.randd.ebishoal.sapphire.ux.components.CreatureSheet;

public class SapphireHud extends LapisHud implements OnResourceGainLossHandler {

	public Chat chat;
	public PlayBar playbar;
	public Timeline timeline;
	public Parameters parameters;
	
	public boolean isLoaded = false;
	
	
	private final LabelStyle styleDmgLife;
	private final LabelStyle styleDmgComposite;
	private final LabelStyle styleDmgShield;
	private final LabelStyle styleDmgMana;
	private final LabelStyle styleDmgMove;
	private final LabelStyle styleDmgSpecial;
	
	public SapphireHud() {
		// SpriteBatch, Viewport, Shader, Stage
		var batch = new SapphireBatch();
		var viewport = new ScreenViewport();
		var shader = new ShaderProgram(LapisShader.getVertexShader("ui"), LapisShader.getFragmentShader("ui"));
		batch.setShader(shader);
		this.setStage(new Stage(viewport, batch));

		BitmapFont damageFont = LapisAssets.get("gen_damage.ttf");
		styleDmgLife = new LabelStyle(damageFont, Color.WHITE);
		styleDmgComposite = new LabelStyle(damageFont, Color.PURPLE);
		styleDmgShield = new LabelStyle(damageFont, Color.BLUE);
		
		styleDmgMana = new LabelStyle(damageFont, Color.CYAN);
		styleDmgMove = new LabelStyle(damageFont, Color.GREEN);
		styleDmgSpecial = new LabelStyle(damageFont, Color.ORANGE);
		
		SapphireGame.fight.statusbus.reactors.register(this);
	}
	
	public void reload() {
		SapphireOwl.core.bus.post(new DisposeUIEvent());
		this.getStage().clear();
		if(!isLoaded) SapphireLmlParser.parser.createView(this, getTemplateFile());

		try {
			SapphireHudSkin.play(SapphireGame.fight.creatures.first());
		} catch (Exception e) { }
		
		chat = new Chat();
		playbar = new PlayBar();
		timeline = new Timeline();
		new QuickOptions();
		
		parameters = new Parameters();
		parameters.setVisible(false);

		isLoaded = true;
	}
	
	
	@Override
	public void resize(int width, int height, boolean centerCamera) {
		super.resize(width, height, centerCamera);
		this.getStage().getActors().forEach(a -> {
			if(a instanceof SapphireComponent) {
				((SapphireComponent) a).resizeScreen(width, height, centerCamera);
			}
		});
	}
	
	@Override
	public String getViewId() {
		return "main";
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getViewId() + ".lml");
	}
	
	@Override
	public FileHandle getSkinFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getViewId() + ".json");
	}
	

	@Override
	public void onResourceGainLoss(ResourceGainLossEvent e) {
//		todo : damage font on a billboard?
		
		LabelStyle style = styleDmgLife;
//		style = switch(e.res) {
//			case life -> switch(e.composite) {
//				case composite -> styleDmgComposite;
//				case onlyShield -> styleDmgShield;
//				case noShield -> styleDmgLife;
//			};
//			case mana -> styleDmgMana;
//			case move -> styleDmgMove;
//			case special -> styleDmgSpecial;
//			default -> styleDmgLife;
//		};
		
		
		var ef = (ResourceGainLoss) e.effect;
		if(ef.shields.size() > 0 && ef.resources.size() > 0) {
			if(ef.resources.containsKey(Resource.life)) style = styleDmgComposite;
			else if(ef.resources.containsKey(Resource.mana)) style = styleDmgLife;
			else if(ef.resources.containsKey(Resource.move)) style = styleDmgMove;
			else if(ef.resources.containsKey(Resource.special)) style = styleDmgSpecial;
		} else if(ef.shields.size() > 0) {
			if(ef.shields.containsKey(Resource.life)) style = styleDmgShield;
			else if(ef.shields.containsKey(Resource.mana)) style = styleDmgMana;
			else if(ef.shields.containsKey(Resource.move)) style = styleDmgMove;
			else if(ef.shields.containsKey(Resource.special)) style = styleDmgSpecial;
		} else if(ef.resources.size() > 0) {
			if(ef.resources.containsKey(Resource.life)) style = styleDmgLife;
			else if(ef.resources.containsKey(Resource.mana)) style = styleDmgMana;
			else if(ef.resources.containsKey(Resource.move)) style = styleDmgMove;
			else if(ef.resources.containsKey(Resource.special)) style = styleDmgSpecial;
		}
		
		var total = 0;
		for (var v : ef.shields.values())
			total += v;
		for (var v : ef.resources.values())
			total += v;
		
		// label + moving action
		var lbl = new Label("" + total, style);
		var action = new ResourceDecalAction(() -> {
			var pos2d = e.target.pos; 
			return new Vector3((float) pos2d.x, (float) pos2d.y, 1);
		});
		lbl.addAction(action);
		this.getStage().addActor(lbl);
		
		// update sheet
		CreatureSheet.updateSheet(e.getCreatureTarget());
	}
	
	@Override
	public HandlerType type() {
		return HandlerType.Reactor;
	}
	
}
