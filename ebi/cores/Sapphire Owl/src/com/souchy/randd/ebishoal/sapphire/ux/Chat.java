package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.VisTextArea;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;

public class Chat extends SapphireWidget {

	@LmlActor("area")
	public VisTextArea area;

	@LmlActor("field")
	public VisTextField field;

	
	@Override
	public String getTemplateId() {
		return "chat";
	}
    
//	public Chat(Skin skin) {
//		super(skin);
//		Log.info("ctor chat");
//	}

	@Override
	protected void init() {
		//Log.info("init chat ");
		refresh();
	}
	
	public void refresh() {
		area.appendText("x : " + this.getX());
		area.appendText("\n");
		area.appendText("y : " + this.getY());
	}
	
//	@LmlAction("sendMsg")
//	public void sendMsg(String str) {
//		
//	}
	
	
}
