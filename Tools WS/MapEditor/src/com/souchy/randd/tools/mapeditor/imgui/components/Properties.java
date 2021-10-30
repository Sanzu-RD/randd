package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Pool;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;

import imgui.ImGui;
import imgui.flag.ImGuiDataType;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class Properties implements ImGuiComponent {
	
	static final Pool<ImInt> poolInt;
	static final Pool<ImFloat> poolFloat;
	static {
		poolInt = new com.badlogic.gdx.utils.Pool<ImInt>(30) {
			@Override
			protected ImInt newObject() {
				return new ImInt();
			}
		};
		poolFloat = new com.badlogic.gdx.utils.Pool<ImFloat>(30) {
			@Override
			protected ImFloat newObject() {
				return new ImFloat();
			}
		};
	
	}
	
	private ModelInstance inst;
	private MaterialProperties mats = new MaterialProperties();

	
	private ImBoolean snap = new ImBoolean(true);
	
	public Properties() {}
	

	@Override
	public void render(float delta) {
		if(inst == null) return;
		ImGui.checkbox("Snap", snap);
		renderTransform();
		mats.render(delta);
	}

	private void renderTransform() {
		int type = ImGuiDataType.Float;
		var imv = poolFloat.obtain();

//		inst.transform
		
		if(ImGui.sliderScalarN("trans3", type, 3, imv, -10, 10)) {
			
		}
		
		if(ImGui.sliderScalar("trans1", type, imv, -100, 100)) {
			
		}
		
		poolFloat.free(imv);
	}
	
	
	public ModelInstance getInst() {
		return inst;
	}

	public void setInst(ModelInstance inst) {
		this.inst = inst;
		mats.mats = inst.materials;
	}
	
	
}
