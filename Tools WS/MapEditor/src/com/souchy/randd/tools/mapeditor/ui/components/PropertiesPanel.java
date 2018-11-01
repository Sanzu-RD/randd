package com.souchy.randd.tools.mapeditor.ui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSplitPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.tools.mapeditor.MapEditorCore;
import com.souchy.randd.tools.mapeditor.MapEditorGame;

public class PropertiesPanel extends VisSplitPane  implements Component {
	
	//LineDrawing lines;

	private static VisTable table;
	
	public PropertiesPanel() {
		super(null, table = new VisTable(), false);
		
	//	lines = new LineDrawing(null, null);
		// lines.createGrid(50, 500, 500);
		
		//Color charcoal = new Color(47 / 255f, 47 / 255f, 47 / 255f, 1);
		
		
		Skin skin = new Skin(Gdx.files.internal("res/uiskin.json"));
		
		Texture honkongTex = new Texture(Gdx.files.internal("res/hongkonghustle-hiero-100_00.png"), true); // true enables mipmaps
		honkongTex.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image
		BitmapFont hongkongFont = new BitmapFont(Gdx.files.internal("res/hongkonghustle-hiero-100.fnt"), new TextureRegion(honkongTex), false);
		LabelStyle hongkong = new LabelStyle();
		hongkong.font = hongkongFont;
		
		
		VisLabel ta = new VisLabel("Properties");
		ta.setFontScale(1.5f);
		ta.pack();
		table.add(ta).height(50);
		table.row();
		table.add(new VisLabel("Width")).width(125);
		table.add(new NumberEntryField("40")).width(125);
		table.row();
		table.add(new VisLabel("Height"));
		table.add(new NumberEntryField("40"));
		table.row();
		table.add(new VisLabel("Show grid"));
		table.add(new VisCheckBox("", false));
		table.row();
		table.add(new VisLabel("Title"));
		table.add(new VisTextField("enter title") {
			{
				this.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						var text = ((VisTextField)actor).getText();
						System.out.println("changed text field : " + text);
						MapEditorCore.core.getProperties().title.set(text);
						System.out.println("prop.title : " + MapEditorCore.core.getProperties().title.get());
					}
				});
			}
		});
		table.row();
		
		table.getCells().forEach(c -> c.align(Align.left));
		table.background(skin.newDrawable("white"));
		table.setColor(Color.DARK_GRAY);
		table.align(Align.topLeft);
	//	table.pack();
		
		//this.pack();
		this.setWidth(250);
		this.setSplitAmount(0);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);

		this.setPosition(getStage().getWidth(), 0, Align.bottomRight);
		this.setHeight(getStage().getHeight());
	}

	@Override
	public void resize(float width, float height) {
		//pack();
		//this.setPosition(width, 0, Align.bottomRight);
		//this.setHeight(height);
	}
	
	
	
	/*
	 * @Override public void resize(int width, int height) { //super.resize(width,
	 * height); getViewport().update(width, height, true);
	 * 
	 * //((ScreenViewport)getViewport()).setUnitsPerPixel(1);
	 * //getViewport().setWorldSize(100, 400); /*getViewport().update(100, 400);
	 * //getViewport().setScreenBounds(50, 50, 100, 400); /
	 * //getViewport().setScreenPosition(50, 50); //getViewport().update(1000, 800);
	 * //getViewport().setScreenPosition(50, 50); //
	 * getViewport().setScreenBounds(50, 50, 100, 400);
	 * //getViewport().setWorldSize(900, 400); //getViewport().apply();
	 * 
	 * 
	 * table.setPosition(getStage().getWidth() -50, getStage().getHeight() -50,
	 * Align.right); lines.resize(); //updateDebug(); float top =
	 * getViewport().getTopGutterY(); float bot =
	 * getViewport().getBottomGutterHeight(); float lef =
	 * getViewport().getLeftGutterWidth(); float rig =
	 * getViewport().getWorldWidth(); //getViewport().getRightGutterX();
	 * lines.get(Color.RED).clear(); lines.addLine(new Line(Color.RED, new
	 * Vector3(lef + 2, bot + 2, 0), new Vector3(rig - 2, bot + 2, 0), null));
	 * lines.addLine(new Line(Color.RED, new Vector3(lef + 2, bot + 2, 0), new
	 * Vector3(lef + 2, top - 3, 0), null)); lines.addLine(new Line(Color.RED, new
	 * Vector3(lef + 2, top - 3, 0), new Vector3(rig - 2, top - 3, 0), null));
	 * lines.addLine(new Line(Color.RED, new Vector3(rig - 2, bot + 2, 0), new
	 * Vector3(rig - 2, top - 3, 0), null));
	 * 
	 * getCam().update(); //table.setBounds(500, 500, 100, getStage().getHeight());
	 * 
	 * System.out.println("table : "+ table.getX() + " @ " + table.getWidth()
	 * +" x "+ table.getHeight() + ", resize : " + width); //", stage : " +
	 * getStage().getWidth() + ", screen : " + getViewport().getScreenWidth() +
	 * ", world : " + getViewport().getWorldWidth());
	 * System.out.println("stage.W : " + getStage().getWidth() + ", world.W : " +
	 * getViewport().getWorldWidth() + ", screen.W : " +
	 * getViewport().getScreenWidth() + ", screen.X " + getViewport().getScreenX());
	 * }
	 */
	
}
