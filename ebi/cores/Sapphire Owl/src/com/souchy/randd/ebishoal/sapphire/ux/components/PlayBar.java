package com.souchy.randd.ebishoal.sapphire.ux.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;

public class PlayBar extends SapphireComponent {

	@LmlActor("life")
	public Label life;

	@LmlActor("pageUp")
	public Button pageUp;

	@LmlActor("pageDown")
	public Button pageDown;

	@LmlActor("pageUpImage")
	public Image pageUpImage;

	@LmlActor("pageDownImage")
	public Image pageDownImage;
	
	public PlayBar(Stage stage) {
		super(stage);
	}

	@Override
	protected void onInit() {
		createListeners();
//		if(this.getX() == -1) 
//			this.setX(this.getStage().getWidth() / 2 - this.getWidth() / 2);
//		if(this.getY() == -1)
//			this.setY(15);
	}

	@Override
	public String getTemplateId() {
		return "playbar";
	}
	
	private void createListeners() {
		var btnShaderListener = new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				Log.info("enter");
				var light = 1.3f;
				var actor = event.getTarget();
				actor.setColor(light, light, light, actor.getColor().a);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Log.info("exit");
				var actor = event.getTarget();
				actor.setColor(1, 1, 1, actor.getColor().a);
				super.exit(event, x, y, pointer, toActor);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch down");
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("down"));
				var shade = 0.7f;
				var actor = event.getTarget();
				actor.setColor(shade, shade, shade, actor.getColor().a);
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch up");
				var light = 1.3f;
				var actor = event.getTarget();
				actor.setColor(light, light, light, actor.getColor().a);
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("up"));
				super.touchUp(event, x, y, pointer, button);
			}
		};
		
		pageUp.addListener(btnShaderListener);
		pageDown.addListener(btnShaderListener);
	}

	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}
	
	
}
