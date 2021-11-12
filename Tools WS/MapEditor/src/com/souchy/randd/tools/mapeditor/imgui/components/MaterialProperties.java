package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
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
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiUtil;
import com.souchy.randd.tools.mapeditor.ui.mapeditor.EditorImGuiHud;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiDataType;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiPopupFlags;
import imgui.flag.ImGuiSliderFlags;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiTableRowFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class MaterialProperties implements ImGuiComponent {
	
	public Array<Material> mats;
	private boolean setColW = true;
	
	private ImBoolean repeatTexture = new ImBoolean();
	
	public MaterialProperties() {
	}

	@Override
	public void render(float delta) {
		if(mats == null) return;
		
		if(ImGui.button("New Material")) {
			mats.add(new Material("Material # " + this.mats.size));
		}

		ImGui.separator();
		
		int matid = 0;
		for(var mat : mats) {
			String matName = "#" + (matid++) + " " + mat.id; //"Material # " + (matid++);

			if(ImGui.treeNode(matName)) {
				// button add attribute
				renderAddAttribute(mat, matName);
				
				int height = 0;
				if(matid == mats.size - 1) height = -1;
				// material table
				renderMaterial(mat, matName, height);
				
				ImGui.treePop();
			}
			
		}
	}
	
	public void renderAddAttribute(Material mat, String matName) {
		if(ImGui.button("Add Attribute##"+matName)) {
		}
		ImGui.pushStyleColor(ImGuiCol.Border, Color.WHITE.toIntBits());
		ImGui.pushItemWidth(250);
		if(ImGui.beginPopupContextItem("AddAttributeBtnPopup"+matName, ImGuiPopupFlags.MouseButtonLeft)) {
			ImGui.text("Color");
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
			ImGui.text("Texture");
			if(ImGui.button("Diffuse")) {
				TextureAttribute.createDiffuse(LapisAssets.defaultTexture);
				ImGui.closeCurrentPopup();
			}
			ImGui.separator();
			ImGui.text("Custom");
			if(ImGui.button("Dissolve")) {
				mat.set(new DissolveUniforms.DissolveMaterial());
				ImGui.closeCurrentPopup();
			}
			ImGui.endPopup();
		}
		ImGui.popItemWidth();
		ImGui.popStyleColor();
	}
	
	public void renderMaterial(Material mat, String matName, int height) {
		int columns = 2;
		
		ImGui.beginGroup();
//		if(ImGui.beginChild("##" +matName, -1, height, true, ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize)) { // ImGui.beginChild("##" +matName)) { 
		if(ImGui.beginTable(matName, columns, ImGuiTableFlags.SizingStretchProp)) { // | ImGuiTableFlags.Resizable)) { //, ImGuiTableFlags.BordersInnerV)) {
			int attrid = 0;
			for (Attribute a : mat) {
				attrid++;
				String name = Attribute.getAttributeAlias(a.type);
				ImGui.tableNextRow();
				
				ImGui.tableNextColumn(); 
				if(ImGui.button("x##" + matName + name)) {
					mat.remove(a.type);
					continue;
				}
				ImGui.sameLine();
				ImGui.text("" + name);
				
				ImGui.tableNextColumn(); 

				ImGui.pushItemWidth(-5);
				renderAttribute(a);
				ImGui.popItemWidth();
				
				if(attrid < mat.size())
					ImGui.separator();
			}
			ImGui.endTable();
		}
		ImGui.endGroup();
	}
	
	public void renderAttribute(Attribute a) {
		if(a instanceof ColorAttribute c)
			renderColor(c);
		if(a instanceof TextureAttribute c)
			ImGuiUtil.renderTexture(c, repeatTexture);
		if(a instanceof FloatAttribute c)
			renderFloat(c);
		if(a instanceof IntAttribute c)
			renderInt(c);
		if(a instanceof BlendingAttribute c)
			renderBlend(c);
	}

	
	public void renderColor(ColorAttribute a) {
		var col = new float[] { a.color.r, a.color.g, a.color.b, a.color.a }; 
		if(ImGui.colorEdit4("##" + Attribute.getAttributeAlias(a.type), col, ImGuiColorEditFlags.AlphaBar)) {
			a.color.set(col[0], col[1], col[2], col[3]);
		}
	}
	
	public void renderFloat(FloatAttribute a) {
		ImFloat imv = ImGuiUtil.poolFloat.obtain();
		imv.set(a.value);
		if(ImGui.dragScalar("##" + Attribute.getAttributeAlias(a.type), ImGuiDataType.Float, imv, -1, 50)) {
			a.value = imv.get();
		}
		ImGuiUtil.poolFloat.free(imv);
	}
	
	public void renderInt(IntAttribute a) {
		ImInt imv = ImGuiUtil.poolInt.obtain();
		imv.set(a.value);
		if(ImGui.sliderScalar("##" + Attribute.getAttributeAlias(a.type), ImGuiDataType.S32, imv, -10, 100)) {
			a.value = imv.get();
		}
		ImGuiUtil.poolInt.free(imv);
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
		
		var imv = ImGuiUtil.poolFloat.obtain();
		imv.set(a.opacity);
		ImGui.text("Alpha"); ImGui.sameLine();
		//ImGui.pushItemWidth(75);
		if(ImGui.sliderScalar("##Alpha", ImGuiDataType.Float, imv, 0, 1)) {
			a.opacity = imv.get();
		}
		//ImGui.popItemWidth();
	}
	
	
	
}
