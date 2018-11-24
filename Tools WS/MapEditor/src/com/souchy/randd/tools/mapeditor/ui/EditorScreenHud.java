package com.souchy.randd.tools.mapeditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kotcrab.vis.ui.widget.VisTable;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.tools.mapeditor.ui.components.BottomBar;
import com.souchy.randd.tools.mapeditor.ui.components.Component;
import com.souchy.randd.tools.mapeditor.ui.components.PropertiesPanel;
import com.souchy.randd.tools.mapeditor.ui.components.TopBar;

public class EditorScreenHud extends Screen2d {
	

	public PropertiesPanel properties;
	//private EditorLayoutPanel layout;

	public TopBar top;
	public BottomBar bottom;
	
	
	@Override
	protected void createHook() {
		super.createHook();

		//getStage().setDebugAll(true);
		
		Texture t = new Texture(Gdx.files.absolute("F:\\Users\\Souchy\\Desktop\\Robyn\\Git\\res\\assets\\textures\\1370834-galaxy.jpg"));
		Image a = new Image(t);
		//getDrawingSpace();
		a.setBounds(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
		
		getStage().addActor(a);
		
		top = new TopBar();
		bottom = new BottomBar();
		properties = new PropertiesPanel();
		getStage().addActor(top.getTable());
		getStage().addActor(bottom);
		//getStage().addActor(properties);
		getStage().addActor(properties);
		//getStage().addActor(actor);
		
		
		/*
		VisTable root = new VisTable();
		getStage().addActor(root);
		root.add(top.getTable());
		root.row();
		root.add(properties);
		root.row();*/
	}
	
	@Override
	public void renderHook(float delta) {
		super.renderHook(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		top.resize(width, height); // la menubar est spéciale ...
		getStage().getActors()
			.select(a -> a instanceof Component)
			.forEach(a -> ((Component)a).resize(width, height));
	}
	

	public Rectangle getDrawingSpace() {
		int x = 0;
		int y = (int) (bottom.getHeight() + bottom.getY());
		int spaceWidth = (int) (getViewport().getScreenWidth() - properties.getChildren().get(0).getWidth()) - 4;
		int spaceHeight = (int) (getViewport().getScreenHeight() - bottom.getHeight() - top.getTable().getHeight()); //(int) (top.getTable().getY() - top.getTable().getHeight()) - y; //.getHeight();
		//int[] space = new int[] {x + 5, y + 5, spaceWidth - 10, spaceHeight - 10};
		//System.out.println(spaceHeight);
		return new Rectangle(x + 5, y + 5, spaceWidth - 10, spaceHeight - 10);
	}
	
}
