package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

public class GameScreenHud extends Screen2d {

	LabelStyle hongkong = new LabelStyle();
	
	public GameScreenHud() {
		Texture honkongTex = new Texture(Gdx.files.internal("res/hongkonghustle-hiero-100_00.png"), true); // true enables mipmaps
		honkongTex.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image
		BitmapFont hongkongFont = new BitmapFont(Gdx.files.internal("res/hongkonghustle-hiero-100.fnt"), new TextureRegion(honkongTex), false);
		hongkong.font = hongkongFont;
	}
	

	@Override
	protected void createHook() {
		super.createHook();
		
		// add actors
		
		var game = SapphireOwl.core.getGame();
		

		Skin skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		Color charcoal = new Color(47/255f, 47/255f, 47/255f, 1);;
		Color bgcolor = charcoal;
		Color fontcolor = Color.WHITE;
		
		LabelStyle style = new LabelStyle();
		style.background = skin.newDrawable("white", fontcolor);
		style.font = new BitmapFont();
		style.fontColor = fontcolor;
		
		Label l = new Label("kjashdbfkjhasdbjhfbdsa test", game.skin);
		l.setFontScale(40 / 100f);
		l.setPosition(300, 300);
		getStage().addActor(l);
		
		var area = new TextArea("l;koaisjdnfjinds", game.skin);
		area.setPosition(100, 100);
		area.setSize(1000, 1000);
		getStage().addActor(area);
		
		VisLabel a = new VisLabel("HELLO T", hongkong);
		a.setFontScale(50 / 100f);
		a.setPosition(30, 165);
		getStage().addActor(a);
	}
	
	
	
}
