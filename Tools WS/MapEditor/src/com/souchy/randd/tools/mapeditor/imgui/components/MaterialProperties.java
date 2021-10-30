package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.utils.Array;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.components.AssetExplorer.AssetDialog;

import imgui.ImGui;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiDataType;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiPopupFlags;
import imgui.flag.ImGuiSliderFlags;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiTableRowFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class MaterialProperties implements ImGuiComponent {
	
	public Array<Material> mats;
	private boolean setColW = true;
	
	public MaterialProperties() {
	}

	@Override
	public void render(float delta) {
		if(mats == null) return;
		
//		if(ImGui.combo("new attribute", new ImInt(), "Blending\0Diffuse\0Emissive\0Ambient\0Specular\0Reflection\0Fog")) {
//			//ImGui.beginCombo("", "preview");
//		}
		
//		if(ImGui.beginMenu("AddAttribute")) {
//			if(ImGui.menuItem("Blending")) {
//			}
//			ImGui.separator();
//			if(ImGui.menuItem("Diffuse")) {
//			}
//			if(ImGui.menuItem("Specular")) {
//			}
//			if(ImGui.menuItem("Emissive")) {
//			}
//			if(ImGui.menuItem("Reflection")) {
//			}
//			if(ImGui.menuItem("Ambient")) {
//			}
//			if(ImGui.menuItem("Fog")) {
//			}
//			ImGui.endMenu();
//		}
		
		if(ImGui.button("New Material")) {
			mats.add(new Material("Material # " + this.mats.size));
		}

		ImGui.separator();
		
		int matid = 0;
		for(var mat : mats) {
			String matName = "Material # " + (matid++);
			
			
//			if(ImGui.beginChild("##" +matName + "-2")) {
				ImGui.textColored(0, 0.6f, 0.6f, 1, matName);
				
				renderAddAttribute(mat, matName);
				
				int height = 0;
				if(matid == mats.size - 1) height = -1;
				renderMaterial(mat, matName, height);
				
				if(matid < mats.size)
					ImGui.separator();
//			}
//			ImGui.endChild();
			
		}
	}
	
	public void renderAddAttribute(Material mat, String matName) {
		if(ImGui.button("Add Attribute##"+matName)) {
		}

		if(ImGui.beginPopupContextItem("AddAttributeBtnPopup"+matName, ImGuiPopupFlags.MouseButtonLeft)) {
			if(ImGui.button("Blending")) {
				mat.set(new BlendingAttribute(1));
				ImGui.closeCurrentPopup();
			}
			if(ImGui.button("Diffuse")) {
				mat.set(ColorAttribute.createDiffuse(1, 1, 1, 1));
				ImGui.closeCurrentPopup();
			}
			if(ImGui.button("Specular")) {
				mat.set(ColorAttribute.createSpecular(1, 1, 1, 1));
				ImGui.closeCurrentPopup();
			}
			if(ImGui.button("Emissive")) {
				mat.set(ColorAttribute.createEmissive(1, 1, 1, 1));
				ImGui.closeCurrentPopup();
			}
			ImGui.separator();
			if(ImGui.button("Dissolve")) {
				mat.set(new DissolveUniforms.DissolveMaterial());
				ImGui.closeCurrentPopup();
			}
			ImGui.endPopup();
		}
	}
	
	public void renderMaterial(Material mat, String matName, int height) {
		boolean ended = false;
		int columns = 2;
		
		ImGui.beginGroup();
//		if(ImGui.beginChild("##" +matName, -1, height, true, ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize)) { // ImGui.beginChild("##" +matName)) { 
		if(ImGui.beginTable(matName, columns, ImGuiTableFlags.SizingStretchProp | ImGuiTableFlags.Resizable)) { //, ImGuiTableFlags.BordersInnerV)) {
//			ImGui.columns(2, "mycolumns " + matName);
//			var dsa = ImGui.tableGetColumnFlags(0);
//			Log.info("dsa " + dsa);
//			ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthFixed);
//			ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthFixed);
			
//			if(setColW) {
//				ImGui.setColumnWidth(0, 180);
//				setColW = false;
//			}
			//ImGui.setColumnWidth(1, 1000);
			//Log.info("mat col 1 width " + ImGui.getColumnWidth(1));
//			ImGui.beginTable(matName, columns);
			//ImGui.tableSetupColumn("Name", ImGuiTableColumnFlags.None, 0.2f, 0);
			//ImGui.tableSetupColumn("Value", ImGuiTableColumnFlags.None, 0.8f, 1);
//			ImGui.columns(2);
//			ImGui.setColumnWidth(0, 80);
			int attrid = 0;
			for (Attribute a : mat) {
				attrid++;
				String name = Attribute.getAttributeAlias(a.type);
				 ImGui.tableNextRow();
				
//				ImGui.tableSetColumnIndex(clm++);
//				ImGui.text("" + a.getClass().getSimpleName());
				
				ImGui.tableNextColumn(); 
				//ImGui.nextColumn();
				if(ImGui.button("x##" + matName + name)) {
					mat.remove(a.type);
					continue;
				}
				ImGui.sameLine();
				ImGui.text("" + name);
				
				ImGui.tableNextColumn(); 
//				ImGui.nextColumn();

				ImGui.pushItemWidth(-5);
				renderAttribute(a);

				ImGui.popItemWidth();
//				ImGui.nextColumn();
				
				if(attrid < mat.size())
					ImGui.separator();
			}
			ImGui.endTable();
//			ImGui.endChild();
//			ended = true;
		}
//		if(!ended) ImGui.endChild();
		ImGui.endGroup();
	}
	
	public void renderAttribute(Attribute a) {
		if(a instanceof ColorAttribute c)
			renderColor(c);
		if(a instanceof TextureAttribute c)
			renderTexture(c);
		if(a instanceof FloatAttribute c)
			renderFloat(c);
		if(a instanceof IntAttribute c)
			renderInt(c);
		if(a instanceof BlendingAttribute c)
			renderBlend(c);
	}

	public void renderTexture(TextureAttribute a) {
		// texture popup
		AssetDialog.texturePicker.prepare(() -> {
			String asset = AssetDialog.texturePicker.pick();
			if(asset != null) {
				a.textureDescription.texture = LapisAssets.get(asset, Texture.class);
			}
		});
		
		// button to select a new texture
		if(ImGui.button("Set")) {
			AssetDialog.texturePicker.show();
		}
		ImGui.sameLine();
		// current texture
		ImGui.image(a.textureDescription.texture.getTextureObjectHandle(), 75, 75);
		// texture transforms
		ImFloat imv = Properties.poolFloat.obtain();
		imv.set(a.offsetU);
		if(ImGui.sliderScalar("U", ImGuiDataType.Float, imv, -1, 100)) {
			a.offsetU = imv.get();
		}
		imv.set(a.offsetV);
		if(ImGui.sliderScalar("V", ImGuiDataType.Float, imv, -1, 100)) {
			a.offsetV = imv.get();
		}
		imv.set(a.scaleU);
		if(ImGui.sliderScalar("W", ImGuiDataType.Float, imv, -1, 100)) {
			a.scaleU = imv.get();
		}
		imv.set(a.scaleV);
		if(ImGui.sliderScalar("H", ImGuiDataType.Float, imv, -1, 100)) {
			a.scaleV = imv.get();
		}
		Properties.poolFloat.free(imv);
	}
	
	public void renderColor(ColorAttribute a) {
		var col = new float[] { a.color.r, a.color.g, a.color.b, a.color.a }; 
		if(ImGui.colorEdit4("##" + Attribute.getAttributeAlias(a.type), col, ImGuiColorEditFlags.AlphaBar)) { //, ImGuiColorEditFlags.PickerHueWheel)) {
				//ImGui.colorPicker4("##" + Attribute.getAttributeAlias(a.type), col)) { //, ImGuiColorEditFlags.PickerHueWheel)) { 
			//ImGui.colorEdit4("##" + Attribute.getAttributeAlias(a.type), col, ImGuiColorEditFlags.PickerHueWheel)) {
			a.color.set(col[0], col[1], col[2], col[3]);
			
		}
	}
	
	public void renderFloat(FloatAttribute a) {
		ImFloat imv = Properties.poolFloat.obtain();
		imv.set(a.value);
//		ImGuiSliderFlags.Logarithmic
		if(ImGui.sliderScalar("##" + Attribute.getAttributeAlias(a.type), ImGuiDataType.Float, imv, -1, 50)) {
			a.value = imv.get();
		}
//		if(ImGui.inputFloat("##" + Attribute.getAttributeAlias(a.type), imv, 0.5f, 1f, "%.4g")) { //, ImGuiInputTextFlags.EnterReturnsTrue)) {
//			a.value = imv.get();
//		}
		// sliderfloat, dragfloat
			
		Properties.poolFloat.free(imv);
	}
	
	public void renderInt(IntAttribute a) {
		ImInt imv = Properties.poolInt.obtain();
		imv.set(a.value);
		if(ImGui.sliderScalar("##" + Attribute.getAttributeAlias(a.type), ImGuiDataType.S32, imv, -10, 100)) {
			a.value = imv.get();
		}
		Properties.poolInt.free(imv);
	}
	
	public void renderBlend(BlendingAttribute a) {
		ImGui.text("Blended"); 
		ImGui.sameLine();
		if(ImGui.checkbox("##Blended", a.blended)) {
			a.blended = !a.blended;
		}
		ImGui.sameLine();
		ImGui.text("src " + a.sourceFunction);
		ImGui.sameLine();
		ImGui.text("dest " + a.destFunction);
//		ImGui.text("fnc " + a.sourceFunction + "->" + a.destFunction);
//		ImGui.sameLine();
		
		var imv = Properties.poolFloat.obtain();
		imv.set(a.opacity);
		ImGui.text("Alpha"); ImGui.sameLine();
		//ImGui.pushItemWidth(75);
		if(ImGui.sliderScalar("##Alpha", ImGuiDataType.Float, imv, 0, 1)) {
			a.opacity = imv.get();
		}
		//ImGui.popItemWidth();
	}
	
	
	
}
