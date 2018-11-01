package com.souchy.randd.ebishoal.commons.lapis.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class UIBehaviour {
	
	
	public static InputListener hoverEffect(Color original, Color hover) {
		return new InputListener() {
			@Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				//if(hoverEffectEnabled)
					event.getListenerActor().setColor(hover);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				event.getListenerActor().setColor(original);
				super.exit(event, x, y, pointer, toActor);
			}
		};
	}
	
}
