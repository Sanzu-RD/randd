package com.souchy.randd.ebishoal.commons.lapis.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.tealwaters.commons.Lambda;

public class LapisUtil {
	
	/**
	 * Correctly align a table on the stage by using its x/y attributes and alignment
	 */
	public static void align(Table table) {
		float x = table.getX();
		float y = table.getY();
		// y
		if((table.getAlign() & Align.top) != 0) y = table.getStage().getHeight() - table.getY(); // top
		else if((table.getAlign() & Align.bottom) != 0) y = table.getY(); // bottom
		else y = table.getStage().getHeight() / 2f - table.getY(); // center
		// x
		if((table.getAlign() & Align.right) != 0) x = table.getStage().getWidth() - table.getX(); // right
		else if((table.getAlign() & Align.left) != 0) x = table.getX(); // left
		else x = table.getStage().getWidth() / 2f - table.getX(); // center
		
		table.setPosition(x, y, table.getAlign());
	}
	
	/**
	 * Add a lambda click listener to an actor
	 */
	public static void onClick(Actor actor, Lambda lambda) {
		actor.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
            	lambda.call();
            	super.clicked(event, x, y);
            }
        });
	}
	
	public static void onType(Actor actor, Lambda lambda) {
		actor.addListener(new ClickListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				// TODO Auto-generated method stub
				return super.keyTyped(event, character);
			}
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				return super.keyDown(event, keycode);
			}
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
            	lambda.call();
            	super.clicked(event, x, y);
            }
        });
	}

	/**
	 * Add an hover listener to an actor with a lambda for entering and another for exiting
	 */
	public static void onHover(Actor actor, Lambda in, Lambda out) {
		actor.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				in.call();
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				out.call();
				super.exit(event, x, y, pointer, toActor);
			}
		});
	}

	/**
	 * Add an hover listener to an actor with a hover color for enter and default white for exit
	 */
	public static void onHover(Actor actor, Color in) {
		onHover(actor, in, Color.WHITE);
	}

	/**
	 * Add an hover listener to an actor with a hover color for enter and exit states
	 */
	public static void onHover(Actor actor, Color in, Color out) {
		
		actor.addListener(new HoverColorListener(actor, in, out));
//		new ClickListener() {
//			@Override
//			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//				setColor(actor, in);
//				super.enter(event, x, y, pointer, fromActor);
//			}
//			@Override
//			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//				setColor(actor, out);
//				super.exit(event, x, y, pointer, toActor);
//			}
//		});
	}
	
	/**
	 * Set the color of an actor/group and all its children recursively
	 */
	public static void setColor(Actor actor, Color c) {
		setColor(actor, c.r, c.g, c.b, c.a);
	}
	
	/**
	 * Set the color of an actor/group and all its children recursively
	 */
	public static void setColor(Actor actor, float r, float g, float b, float a) {
		actor.setColor(r, g, b, a); //actor.getColor().a);
		if(actor instanceof Group) {
			var group = (Group) actor;
			group.getChildren().forEach(child -> {
				child.setColor(r, g, b, a); //child.getColor().a);
			});
		}
	}


	public static Drawable getImage(String key) {
		return VisUI.getSkin().getDrawable(key);
	}
	public static Image setImage(Image img, String imgid) {
		if(img == null) return img;
		var drawable = getImage(imgid);
		img.setDrawable(drawable);
		return img;
	}
	
	public static void setText(Label lbl, String text) {
		lbl.setText(text); 
	}
	

	private static class HoverColorListener extends ClickListener {
		private final Color original;
		private final Actor actor;
		private final Color in, out;
		public HoverColorListener(Actor a, Color in, Color out) {
			this.original = a.getColor();
			this.actor = a;
			this.in = in; this.out = out;
			//Log.info("HoverColorListener original : " + original + ", in : " + in + ", out : " + out);
		}
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			//Log.info("HoverColorListener enter : " + in.mul(original));
			//setColor(actor, in.r * original.r, in.g * original.g, in.b * original.b, in.a * original.a);
			setColor(actor, in);
			super.enter(event, x, y, pointer, fromActor);
		}
		@Override
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			//Log.info("HoverColorListener exit : " + out.mul(original));
			//setColor(actor, out.r * original.r, out.g * original.g, out.b * original.b, out.a * original.a);
			setColor(actor, out);
			super.exit(event, x, y, pointer, toActor);
		}
	}
}
