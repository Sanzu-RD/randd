package com.souchy.randd.tools.uieditor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.Draggable;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.DragAndResizeListener;

public class ActorWrapper<T extends Actor> extends Container<T> {
	
	private static ShapeRenderer shapeRenderer = new ShapeRenderer();
	static private boolean projectionMatrixSet = false;
	
	public ActorWrapper(T actor) {
		super(actor);
		
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Log.info("click actor wrapper " + ActorWrapper.this.getActor());
				PropertiesTab.set(getActor());
			}
		});
//		this.addListener(new DragAndResizeListener(this));
		
		this.addListener(new Draggable());
		
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		if(!projectionMatrixSet) {
//			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		}
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		shapeRenderer.end();
		batch.begin();
		
//		this.getActor().draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	
	
	
	
}
