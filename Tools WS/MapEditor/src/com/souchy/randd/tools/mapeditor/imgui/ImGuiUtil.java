package com.souchy.randd.tools.mapeditor.imgui;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

import imgui.ImGui;
import imgui.flag.ImGuiDataType;
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
	
	
}