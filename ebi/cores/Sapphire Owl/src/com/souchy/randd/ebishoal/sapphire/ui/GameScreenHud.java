package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.souchy.randd.ebishoal.commons.lapis.screens.Viewports;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

public class GameScreenHud extends Screen2d {

	LabelStyle hongkong = new LabelStyle();
	VisLabel fpslbl;

    private SpriteBatch spriteBatch;
	Sprite s;
    Texture texture;
    Pixmap pixmap;

    
	
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
		/*
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
		getStage().addActor(a);*/

		/*//Font font = new Font("Arial", 100);
		BitmapFont font = new BitmapFont();
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		LabelStyle s = new LabelStyle();
		s.font = font;*/

		fpslbl = new VisLabel("FPS : " + Gdx.graphics.getFramesPerSecond(), hongkong);
		fpslbl.setFontScale(40 / 100f);
		fpslbl.setPosition(30, 0);
		getStage().addActor(fpslbl);
		
        spriteBatch = new SpriteBatch();
        pixmap = new Pixmap(0, 0, Format.RGBA8888);
		pixmap.setColor(Color.GOLD);
		pixmap.fill();
		texture = new Texture(pixmap);
       // s = new Sprite(texture);
       // s.setColor(Color.GOLD);
       // s.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
	}
	
	@Override
	protected Viewport createView(Camera cam) {
		// TODO Auto-generated method stub
		return super.createView(cam);
		/*float aspectRatio = 16/9f;	// ratio à mettre dans les settings public
		float minWorldY = 50; 		// hauteur min à mettre ds settings privés
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		//Viewport view = Viewports.extend(minWorldX, minWorldY,  cam);
		Viewport view = Viewports.scaling(Scaling.none, minWorldX, minWorldY,  cam);
		view.apply();
		return view;*/
	}
	
	@Override
	public void renderHook(float delta) {

        /*spriteBatch.setProjectionMatrix(getCam().combined);
        spriteBatch.begin();
       // s.draw(spriteBatch);
        spriteBatch.draw(texture,0,0);
        spriteBatch.end();*/

		fpslbl.setText("FPS : " + Gdx.graphics.getFramesPerSecond());

		super.renderHook(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
/*

        pixmap = new Pixmap((int)getViewport().getWorldWidth(), (int)getViewport().getWorldHeight(), Format.RGBA8888);
		pixmap.setColor(Color.GOLD);
		pixmap.fill();
		texture = new Texture(pixmap);

		getCam().position.set(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()/2, 60);
		getCam().direction.set(0, 0, -1);
		getCam().up.set(0, 1, 0);
        getCam().update();*/
	}
	
}
