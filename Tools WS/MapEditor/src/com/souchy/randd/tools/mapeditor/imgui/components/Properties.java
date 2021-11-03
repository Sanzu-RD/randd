package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.souchy.jeffekseer.Effect;
import com.souchy.jeffekseer.Jeffekseer;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;

public class Properties extends Container { // implements ImGuiComponent {
	
	private Object target;
	
	private InstanceProperties propInst = new InstanceProperties();
	
	public Properties() {
		super();
		this.title = "Object Properties";
	}
	
	@Override
	protected void applyDefaults() {
		this.size = new int[] { 300, 700 };
		this.position = new int[] { Gdx.graphics.getWidth() - 20 - size[0], 50 };
	}
	

	@Override
	public void renderContent(float delta) {
		propInst.render(delta);
	}
	
	public void setObj(Object o) {
		target = o;
		if(o == null) {
			propInst.setInst(null);
			return;
		}
		if(o instanceof ModelInstance i) {
			propInst.setInst(i);
		}
		if(o instanceof Effect e) {
			//propEffect.setInst(e);
		}
	}
	
	
}
