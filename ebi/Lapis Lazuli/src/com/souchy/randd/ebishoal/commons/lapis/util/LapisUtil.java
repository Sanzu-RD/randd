package com.souchy.randd.ebishoal.commons.lapis.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.github.czyzby.lml.util.LmlUtilities;
import com.souchy.randd.commons.tealwaters.commons.Lambda;

public class LapisUtil {
	
	public static void align(Table a) {
		float x = a.getX();
		float y = a.getY();
		// y
		if((a.getAlign() & Align.top) != 0) y = a.getStage().getHeight() - a.getY(); // top
		else if((a.getAlign() & Align.bottom) != 0) y = a.getY(); // bottom
		else y = a.getStage().getHeight() / 2f - a.getY(); // center
		// x
		if((a.getAlign() & Align.right) != 0) x = a.getStage().getWidth() - a.getX(); // right
		else if((a.getAlign() & Align.left) != 0) x = a.getX(); // left
		else x = a.getStage().getWidth() / 2f - a.getX(); // center
		
		a.setPosition(x, y, a.getAlign());
	}
	
	public static void onClick(Actor actor, Lambda lambda) {
		actor.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
            	lambda.call();
            	super.clicked(event, x, y);
            }
        });
	}

	public static void onHover(Actor actor, Color in) {
		onHover(actor, in, Color.WHITE);
	}
	public static void onHover(Actor actor, Color in, Color out) {
		actor.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				setColor(actor, in);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				setColor(actor, out);
				super.exit(event, x, y, pointer, toActor);
			}
		});
	}
	
	public static void setColor(Actor actor, Color c) {
		setColor(actor, c.r, c.g, c.b, c.a);
	}
	public static void setColor(Actor actor, float r, float g, float b, float a) {
		actor.setColor(r, g, b, a); //actor.getColor().a);
		if(actor instanceof Group) {
			var group = (Group) actor;
			group.getChildren().forEach(child -> {
				child.setColor(r, g, b, a); //child.getColor().a);
			});
		}
	}
//	public static void setAlpha(Actor actor, float alpha) {
//		actor.setColor(actor.getColor().r, actor.getColor().g, actor.getColor().b, alpha);
//		if(actor instanceof Group) {
//			var group = (Group) actor;
//			group.getChildren().forEach(child -> {
//				child.setColor(child.getColor().r, child.getColor().g, child.getColor().b, alpha);
//			});
//		}
//	}

//	public static int percentConverter(String val, float stageWidth) {
//		int result = 0;
//		if(val.contains("%")) {
//			val = val.substring(0, val.length() - 1);
//			result = Integer.parseInt(val);
//			result = (int) (stageWidth * result / 100f);
//		} else {
//			result = Integer.parseInt(val);
//		}
//		return result;
//	}
	
}
