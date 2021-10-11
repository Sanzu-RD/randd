package com.souchy.randd.ebishoal.sapphire.ux.components.sheets;

import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.main.SapphireAssets;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;

public class CreatureHoverDebug extends SapphireComponent {

	public Creature creature = null;
		
		
	@Override
	protected void onInit() {
		// TODO Auto-generated method stub
		var labelid = new VisLabel("" + creature.id);
		this.add(labelid);
		this.add(labelid);
	}

	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTemplateId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private I18NBundle getI18n() {
		return LapisAssets.get("res/i18n/creatures/bundle", I18NBundle.class);
	}

	@LmlAction("getCreatureId")
	public int getCreatureId() {
		if(creature == null) return 0;
		return creature.id;
	}

	@LmlAction("getCreatureName")
	public String getCreatureName() {
		if(creature == null) return "null";
		var name = getI18n().get("creature." + creature.modelid + ".name");
		return name;
	}
	
	@LmlAction("getDescription")
	public String getDescription() {
		if(creature == null) return "null";
		return getI18n().get("creature." + creature.modelid + ".description");
	}
	
	public String getCreatureModelIcon() {
		if(creature == null) return "";
		var icon = AssetData.creatures.get(creature.modelid).icon + "_round";
		icon = SapphireAssets.getCreatureIconPath(icon);
		return icon;
	}
	
}
