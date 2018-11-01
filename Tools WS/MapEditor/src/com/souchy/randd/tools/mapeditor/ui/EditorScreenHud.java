package com.souchy.randd.tools.mapeditor.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisTable;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.tools.mapeditor.ui.components.BottomBar;
import com.souchy.randd.tools.mapeditor.ui.components.Component;
import com.souchy.randd.tools.mapeditor.ui.components.PropertiesPanel;
import com.souchy.randd.tools.mapeditor.ui.components.TopBar;

public class EditorScreenHud extends Screen2d {
	

	private PropertiesPanel properties;
	//private EditorLayoutPanel layout;

	private TopBar top;
	private BottomBar bottom;
	
	
	@Override
	protected void createHook() {
		super.createHook();

		//getStage().setDebugAll(true);
		
		VisTable root = new VisTable();
		getStage().addActor(root);
		
		top = new TopBar();
		bottom = new BottomBar();
		properties = new PropertiesPanel();
		getStage().addActor(top.getTable());
		getStage().addActor(bottom);
		//getStage().addActor(properties);
		getStage().addActor(properties);
		//getStage().addActor(actor);
		
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
	
	
}
