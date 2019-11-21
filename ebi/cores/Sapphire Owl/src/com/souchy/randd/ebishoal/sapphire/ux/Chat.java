package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;

public class Chat extends SapphireWidget {

	@LmlActor("area")
	public TextArea area;

	@LmlActor("field")
	public TextField field;

	
	@Override
	public String getTemplateId() {
		return "chat";
	}
    
	@LmlAction("sendMsg")
	public void sendMsg(String str) {
		
	}
	
	
}
