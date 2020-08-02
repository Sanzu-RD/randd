package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.github.czyzby.lml.annotation.LmlAction;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;

public class Parameters extends SapphireWidget {

	@Override
	public String getTemplateId() {
		return "paramters";
	}

	@Override
	protected void init() {
	}
	
	@LmlAction("concede")
	public void concede() {
		
	}
	
	
	public void toggleVisibility() {
		setVisible(!isVisible());
	}
	
}
