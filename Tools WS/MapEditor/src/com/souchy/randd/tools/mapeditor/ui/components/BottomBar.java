package com.souchy.randd.tools.mapeditor.ui.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.souchy.randd.tools.mapeditor.MapEditorGame;

public class BottomBar extends VisTable implements Component {

	VisLabel mapName = new VisLabel("(No map)");
	VisLabel fps = new VisLabel("(Fps)");
	VisLabel pos = new VisLabel("(CamPos)");
	VisLabel dir = new VisLabel("(CamDir)");
	
	public BottomBar() {
		this.align(Align.left);
		this.padTop(3);
		this.padBottom(3);
		this.padLeft(5);
		
		this.add(mapName);
		this.addSeparator(true);

		this.add(fps);
		this.addSeparator(true);

		this.add(pos);
		this.addSeparator(true);

		this.add(dir);
		this.addSeparator(true);
		
		// Update le nom de la map quand on en charge une
		MapEditorGame.currentFile.addListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(MapEditorGame.currentFile.get() == null) mapName.setText("(New map)");
				else mapName.setText(MapEditorGame.currentFile.get().name());
			}
		});
		
		this.getCells().forEach(c -> c.padRight(10));
		this.background(MapEditorGame.skin.newDrawable("white"));		
		this.setColor(Color.BLACK);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		fps.setText(Gdx.graphics.getFramesPerSecond() + "");
		pos.setText(MapEditorGame.screen.getCam().position + "");
		dir.setText(MapEditorGame.screen.getCam().direction + "");

		pack();
		this.setPosition(0, 0);
		this.setWidth(getStage().getWidth());
	}

	@Override
	public void resize(float width, float height) {
		//pack();
		//this.setPosition(0, 0);
		//this.setWidth(getStage().getWidth());
	}
	
	
}
