package com.souchy.randd.tools.mapeditor.ui.components;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSplitPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.properties.Property;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.ui.Component;

public class PropertiesPanel extends VisSplitPane  implements Component {
	
	//LineDrawing lines;

	private static VisTable table;
	
	public PropertiesPanel() {
		super(null, table = new VisTable(), false);
		
	//	lines = new LineDrawing(null, null);
		// lines.createGrid(50, 500, 500);
		
		//Color charcoal = new Color(47 / 255f, 47 / 255f, 47 / 255f, 1);
		
		Skin skin = VisUI.getSkin(); // MapEditorGame.skin;//new Skin(Gdx.files.internal("res/uiskin.json"));
//		table.setSkin(skin);
		

		//VisUI.getSkin().get(Label.LabelStyle.class).font = font; // -> lui marche
		VisLabel ta = new VisLabel("Properties");
	//	ta.getStyle().font = romance;
	//	ta.setFontScale(1.5f);
	//	ta.pack();
		table.add(ta).height(50);
		table.row();

		var titleEntry = new VisTextField();
		titleEntry.setName("title");
		//linkStringProperty(titleEntry, MapEditorCore.core.getProperties().title);
		table.add(new VisLabel("Title"));
		table.add(titleEntry);
		table.row();
		
		table.add(new VisLabel("Width")); //.width(125); // new Label("Width", skin)
		table.add(new NumberEntryField("40")); //.width(125);
		table.row();
		
		table.add(new VisLabel("Height"));
		table.add(new NumberEntryField("40"));
		table.row();

		var showGridEntry = new VisCheckBox("", false); 
		showGridEntry.setName("showGrid");
		linkBoolProperty(showGridEntry, MapEditorGame.properties.showGrid);
		table.add(new VisLabel("Show grid"));
		table.add(showGridEntry);
		table.row();
		
		var ambiantEntry = new NumberEntryField();
		ambiantEntry.setName("ambiantBrightness");
		linkFloatProperty(ambiantEntry, MapEditorGame.properties.ambiantBrightness);
		MapEditorGame.properties.ambiantBrightness.addListener((e) -> {
			MapEditorGame.screen.getEnvironment().clear();
			MapEditorGame.screen.createEnvironment();
		});
		table.add(new VisLabel("Ambiant Brightness"));
		table.add(ambiantEntry);
		table.row();

		var dirBrightEntry = new NumberEntryField();
		dirBrightEntry.setName("directionalBrightness");
		linkFloatProperty(dirBrightEntry, MapEditorGame.properties.directionalBrightness);
		MapEditorGame.properties.directionalBrightness.addListener((e) -> {
			MapEditorGame.screen.getEnvironment().clear();
			MapEditorGame.screen.createEnvironment();
		});
		table.add(new VisLabel("Dir Brightness"));
		table.add(dirBrightEntry);
		table.row();

		var dirCountEntry = new NumberEntryField();
		dirCountEntry.setName("directionalLightCount");
		linkIntProperty(dirCountEntry, MapEditorGame.properties.directionalLightCount);
		MapEditorGame.properties.directionalLightCount.addListener((e) -> {
			MapEditorGame.screen.getEnvironment().clear();
			MapEditorGame.screen.createEnvironment();
		});
		table.add(new VisLabel("Dir Light Count"));
		table.add(dirCountEntry);
		table.row();
		
		table.getCells().forEach(c -> c.align(Align.left));
		table.background(skin.newDrawable("white"));
		table.setColor(Color.DARK_GRAY);
		table.align(Align.topLeft);
		table.pack();
		
		//this.pack();
		this.setWidth(300);
		this.setSplitAmount(0);
	}
	

	private void linkStringProperty(VisTextField entry, Property<String> p) {
		linkProperty(entry, p, entry::setText, entry::getText);
	}
	private void linkBoolProperty(VisCheckBox entry, Property<Boolean> p) {
		linkProperty(entry, p, entry::setChecked, entry::isChecked);
	}
	private void linkIntProperty(NumberEntryField entry, Property<Integer> p) {
		linkProperty(entry, p, entry::setText, entry::getText, Integer::parseInt, i -> i.toString());
	}
	private void linkFloatProperty(NumberEntryField entry, Property<Float> p) {
		linkProperty(entry, p, entry::setText, entry::getText, Float::parseFloat, f -> f.toString());
	}

	@SuppressWarnings("unchecked")
	private <T,E> void linkProperty(Actor entry, Property<T> p, Consumer<E> actorSetter, Supplier<E> actorGetter) {
		linkProperty(entry, p, actorSetter, actorGetter, v -> (T)v, v -> (E)v);
	}
	private <T,E> void linkProperty(Actor entry, Property<T> p, Consumer<E> actorSetter, Supplier<E> actorGetter, Function<E, T> convert1, Function<T, E> convert2) {
		actorSetter.accept(convert2.apply(p.get()));
		entry.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				var actorValue = actorGetter.get();//  ((VisTextField) actor).getText();
				try {
					p.set(convert1.apply(actorValue));
				} catch(Exception e) { }
				System.out.println("Changed prop ["+entry.getName()+"] : " + p.get());
			}
		});
	}
	
	
	
	@Override
	public void act(float delta) {
		super.act(delta);

		this.setPosition(getStage().getWidth(), 0, Align.bottomRight);
		this.setHeight(getStage().getHeight() - 31);
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
