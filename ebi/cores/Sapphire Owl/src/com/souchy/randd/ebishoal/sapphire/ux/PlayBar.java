package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class PlayBar extends SapphireWidget {

//	public PlayBar(Skin skin) {
//		super(skin);
//	}

	@LmlActor("life")
	public Label life;

	@LmlActor("pageUp")
	public Button pageUp;

	@LmlActor("pageUpImage")
	public Image pageUpImage;

	@LmlActor("pageDownImage")
	public Image pageDownImage;
	
	@Override
	public String getTemplateId() {
		return "playbar";
	}

	@Override
	protected void init() {
		createListeners();
//		if(this.getX() == -1) 
//			this.setX(this.getStage().getWidth() / 2 - this.getWidth() / 2);
//		if(this.getY() == -1)
//			this.setY(15);
	}

	private void createListeners() {
		pageUp.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				Log.info("enter");
				var light = 1.3f;
				//view.pageUpImage.setColor(light, light, light, view.pageUpImage.getColor().a);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Log.info("exit");
				pageUpImage.setColor(1, 1, 1, pageUpImage.getColor().a);
				super.exit(event, x, y, pointer, toActor);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch down");
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("down"));
				var shade = 0.7f;
				pageUpImage.setColor(shade, shade, shade, pageUpImage.getColor().a);
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch up");
				var light = 1.0f;
				pageUpImage.setColor(light, light, light, pageUpImage.getColor().a);
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("up"));
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	
}