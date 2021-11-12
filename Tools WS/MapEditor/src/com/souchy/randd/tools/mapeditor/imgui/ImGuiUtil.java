package com.souchy.randd.tools.mapeditor.imgui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.tools.mapeditor.imgui.components.AssetDialog;

import imgui.ImGui;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiDataType;
import imgui.flag.ImGuiTableFlags;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class ImGuiUtil {

	public static final Pool<ImInt> poolInt;
	public static final Pool<ImFloat> poolFloat;
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

	
	public static boolean renderTexture(TextureAttribute a, ImBoolean repeatTexture) {
		ImBoolean changed = new ImBoolean(false);
		// texture popup
		AssetDialog.texturePicker.prepare(() -> {
			String asset = AssetDialog.texturePicker.pick();
			if(asset != null) {
				a.textureDescription.texture = LapisAssets.get(asset, Texture.class);
				changed.set(true);
			}
		});
		
		ImGui.beginGroup();
		// button to select a new texture
		if(ImGui.button("Set")) {
			AssetDialog.texturePicker.show();
		}
		if(ImGui.checkbox("Repeat", repeatTexture)) {
			if(repeatTexture.get()) {
				a.textureDescription.uWrap = TextureWrap.Repeat;
				a.textureDescription.vWrap = TextureWrap.Repeat;
			} else {
				a.textureDescription.uWrap = TextureWrap.ClampToEdge;
				a.textureDescription.vWrap = TextureWrap.ClampToEdge;
			}
		}
		ImGui.endGroup();
		ImGui.sameLine();
		// current texture
		ImGui.image(a.textureDescription.texture.getTextureObjectHandle(), 75, 75);
		// texture transforms
		ImFloat imv = ImGuiUtil.poolFloat.obtain();
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
		ImGuiUtil.poolFloat.free(imv);
		
		return changed.get();
	}

	
	public static void renderVector3(String name, Vector3 vc, float min, float max) {
		renderVector3(name, vc, 0.5f, min, max);
	}
	public static void renderVector3(String name, Vector3 vc, float speed, float min, float max) {
		var imvX = poolFloat.obtain();
		var imvY = poolFloat.obtain();
		var imvZ = poolFloat.obtain();
		
		ImGui.tableNextColumn();
		ImGui.text(name);
		//ImGui.sameLine();
		
		
		ImGui.tableNextColumn();
		float width = ImGui.getColumnWidth();
		float w = width / 3.0f;
		
			ImGui.beginGroup();
			ImGui.pushID(name);
			{
				ImGui.pushItemWidth(w);
				imvX.set(vc.x);
				if(ImGui.dragScalar("##"+name+" x", ImGuiDataType.Float, imvX, speed, min, max)) { //ImGui.sliderScalar("##"+name+" x", ImGuiDataType.Float, imvX, min, max)) {
					vc.x = imvX.get();
					//if(snap.get()) vc.x = Math.round(vc.x - cellhalf) + cellhalf;
				}
				ImGui.sameLine(0, ImGui.getStyle().getItemInnerSpacingX());
				imvY.set(vc.y);
				if(ImGui.dragScalar("##"+name+" y", ImGuiDataType.Float, imvY, speed, min, max)) { //ImGui.sliderScalar("##"+name+" y", ImGuiDataType.Float, imvY, min, max)) { 
					vc.y = imvY.get();
					//if(snap.get()) vc.y = Math.round(vc.y - cellhalf) + cellhalf;
				}
				ImGui.sameLine(0, ImGui.getStyle().getItemInnerSpacingX());
				imvZ.set(vc.z);
				if(ImGui.dragScalar("##"+name+" z", ImGuiDataType.Float, imvZ, speed, min, max)) {
					vc.z = imvZ.get();
					//if(snap.get()) vc.z = Math.round(vc.z - cellhalf) + cellhalf;
				}
				ImGui.popItemWidth();
			}
			ImGui.popID();
			ImGui.endGroup();
		
		poolFloat.free(imvX);
		poolFloat.free(imvY);
		poolFloat.free(imvZ);
	}
	
	public static void renderQuaternion(String name, Quaternion vc, float min, float max) {
		var imvX = poolFloat.obtain();
		var imvY = poolFloat.obtain();
		var imvZ = poolFloat.obtain();
		var imvW = poolFloat.obtain();
		ImGui.tableNextColumn();
		ImGui.text(name);
		
		ImGui.tableNextColumn();
		float width = ImGui.getColumnWidth();
		float w = width / 4.0f;
		float spacing = ImGui.getStyle().getItemInnerSpacingX();
		ImGui.beginGroup();
		ImGui.pushID(name);
		{
			float speed = 0.01f;
			ImGui.pushItemWidth(w);
			imvX.set(vc.x);
			if(ImGui.dragScalar("##"+name+" x", ImGuiDataType.Float, imvX, speed, min, max)) {
				vc.x = imvX.get();
			}
			ImGui.sameLine(0, spacing);
			imvY.set(vc.y);
			if(ImGui.dragScalar("##"+name+" y", ImGuiDataType.Float, imvY, speed, min, max)) { 
				vc.y = imvY.get();
			}
			ImGui.sameLine(0, spacing);
			imvZ.set(vc.z);
			if(ImGui.dragScalar("##"+name+" z", ImGuiDataType.Float, imvZ, speed, min, max)) {
				vc.z = imvZ.get();
			}
			ImGui.sameLine(0, spacing);
			imvW.set(vc.w);
			if(ImGui.dragScalar("##"+name+" W", ImGuiDataType.Float, imvW, speed, min, max)) {
				vc.w = imvW.get();
			}
			ImGui.popItemWidth();
		}
		ImGui.popID();
		ImGui.endGroup();
		poolFloat.free(imvX);
		poolFloat.free(imvY);
		poolFloat.free(imvZ);
		poolFloat.free(imvW);
	}
	
	
	/**
	 * 
	 * @param transform
	 * @param tempPos
	 * @param tempScl
	 * @param tempRot
	 */
	public static void renderTransform(Matrix4 transform, Vector3 tempPos, Vector3 tempScl, Vector3 tempRot) {
		float cellhalf = Constants.cellHalf;
		
		transform.getTranslation(tempPos);
		transform.getScale(tempScl);
		//transform.getRotation(tempRot);
		//var q = transform.getRotation(new Quaternion());
		//tempRot.x = q.getAngleAround(Vector3.X);
		//tempRot.y = q.getAngleAround(Vector3.Y);
		//tempRot.z = q.getAngleAround(Vector3.Z);
		
		if(ImGui.beginTable("##transform table", 2, ImGuiTableFlags.SizingStretchProp)) { //, ImGuiTableFlags.SizingStretchProp)) {
			ImGui.tableNextRow();
			ImGuiUtil.renderVector3("Translation", tempPos, -19 - cellhalf, 19 + cellhalf);
			ImGui.tableNextRow();
			//renderQuaternion("Rotation", tempRot, -1, 1);
			ImGuiUtil.renderVector3("Rotation", tempRot, 5, -180f, 180f);
			ImGui.tableNextRow();
			ImGuiUtil.renderVector3("Scale", tempScl, -19 - cellhalf, 19 + cellhalf);
			ImGui.endTable();
		}
		
		transform //.idt()
		.setToTranslation(tempPos)
		.scl(tempScl)
		.rotate(Vector3.X, tempRot.x)
		.rotate(Vector3.Y, tempRot.y)
		.rotate(Vector3.Z, tempRot.z)
		;
		
	}
	
}
