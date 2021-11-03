package com.souchy.randd.tools.mapeditor.imgui.components;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiUtil;
import com.souchy.randd.tools.mapeditor.main.EditorEntities;

import imgui.ImGui;
import imgui.flag.ImGuiTableFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

public class InstanceProperties implements ImGuiComponent {

	// Target
	private ModelInstance inst;
	private AnimationController controller;
	private Vector3 tempPos = new Vector3();
	private Quaternion tempRot = new Quaternion();
	private Vector3 tempRot2 = new Vector3();
	private Vector3 tempScl = new Vector3();
	
	// Widgets
	private MaterialProperties mats = new MaterialProperties();

	// 
	private ImBoolean snap = new ImBoolean(true);
	private ImBoolean rosette = new ImBoolean(true);
	private ImInt primitive = new ImInt(0);
	private ImInt animationImv = new ImInt(0);
	

	public ModelInstance getInst() {
		return inst;
	}

	public void setInst(ModelInstance inst) {
		this.inst = inst;
		if(inst != null) mats.mats = inst.materials;
		else mats.mats = null;
	}
	
	@Override
	public void render(float delta) {
		if(inst == null) return;
		ImGui.textColored(IGStyle.colorAccent, "ModelInstance");
		//
		if(ImGui.button("Delete")) {
			//MapWorld.world.instances.remove(inst);
			EditorEntities.removeAdaptor(inst);
			setInst(null);
			return;
		}
		ImGui.sameLine();
		ImGui.checkbox("Snap", snap);
		ImGui.sameLine();
		ImGui.checkbox("Rosette", rosette);
		ImGui.sameLine();
		if(ImGui.combo("Primitive", primitive, new String[] { "Triangles", "Wire" })) {
			switch(primitive.get()) {
				case 0 -> GL20.glPolygonMode(GL20.GL_FRONT_AND_BACK, GL20.GL_FILL);
				case 1 -> GL20.glPolygonMode(GL20.GL_FRONT_AND_BACK, GL20.GL_LINE); 
			}
		}
		
		ImGui.spacing();
		ImGui.separator();
		ImGui.spacing();
		
		//
		if(ImGui.treeNode("Transform")) {
			renderTransform();
			ImGui.treePop();
		}
		
		//
		ImGui.spacing();
		ImGui.separator();
		ImGui.spacing();
		if(ImGui.treeNode("Materials")) {
			mats.render(delta);
			ImGui.treePop();
		}

		//
		ImGui.spacing();
		ImGui.separator();
		ImGui.spacing();
		if(ImGui.treeNode("Animations")) {
			try {
				renderAnimation();
			} catch(Exception e) {
				Log.error("", e);
			}
			ImGui.treePop();
		}
	}
	

	private void renderAnimation() {
		var anime = EditorEntities.getAnime(inst); 
		if(anime.current != null) 
			animationImv.set(inst.animations.indexOf(anime.current.animation, true));
		else
			animationImv.set(0);
		
		String playpause = anime.paused ? "Play" : "Pause";
		if(ImGui.button(playpause)) {
			anime.paused = !anime.paused;
			if(!anime.paused && anime.current == null) {
				anime.setAnimation(inst.animations.get(animationImv.get()).id, -1);
			}
		}
		ImGui.sameLine();
		if(ImGui.button("Stop")) {
			try {
				anime.paused = false;
				anime.setAnimation(null);
				anime.update(0);
				anime.paused = true;
			} catch(Exception e) {
				Log.info("", e);
			}
		}
		var anims = new String[inst.animations.size];
		for(int i = 0; i < inst.animations.size; i++) {
			anims[i] = inst.animations.get(i).id;
		}
		if(ImGui.combo("##animationSelect", animationImv, anims)) { // inst.animations.toString("\0"))) {
			//Log.info("animation select %s, %s", animationImv.get(), inst.animations.get(animationImv.get()).id);
			anime.paused = false;
			anime.setAnimation(inst.animations.get(animationImv.get()).id, -1);
		}
		inst.animations.toArray();

	}

	private void renderTransform() {
		float cellhalf = 0.5f;
		
		inst.transform.getTranslation(tempPos);
		inst.transform.getScale(tempScl);
		inst.transform.getRotation(tempRot);
//		tempRot2.x = tempRot.getAngleAround(Vector3.X); //.getAngleAround(tempPos.y+0.5f, 0, tempPos.z);
//		tempRot2.y = tempRot.getAngleAround(Vector3.Y); //0, tempPos.x, tempPos.z);
//		tempRot2.z = tempRot.getAngleAround(Vector3.Z); //tempPos.x, tempPos.y, 0);
		
		if(ImGui.beginTable("##transform table", 2, ImGuiTableFlags.SizingStretchProp)) { //, ImGuiTableFlags.SizingStretchProp)) {
			ImGui.tableNextRow();
			ImGuiUtil.renderVector3("Translation", tempPos, 0 + cellhalf, 19 + cellhalf);
			ImGui.tableNextRow();
			//renderQuaternion("Rotation", tempRot, -1, 1);
			ImGuiUtil.renderVector3("Rotation", tempRot2, 5, -180f, 180f);
			ImGui.tableNextRow();
			ImGuiUtil.renderVector3("Scale", tempScl, 0 + cellhalf, 19 + cellhalf);
			ImGui.endTable();
		}
		//Log.info("Properties rotation %s", tempRot2);
		//tempRot.set(Vector3.X, 90);
		//tempRot.setFromAxis(Vector3.X, tempRot2.x);
		//tempRot.setFromAxis(Vector3.Y, tempRot2.y);
		//tempRot.setFromAxis(Vector3.Z, tempRot2.z);
		
		//Log.info("Properties renderTransform rot yaw %s", tempRot.getYaw());
		//inst.transform.set(tempPos, tempRot, tempScl);
		inst.transform //.idt()
		.setToTranslation(tempPos)
		.scl(tempScl)
		//.rotate(tempRot)
		.rotate(Vector3.X, tempRot2.x)
		.rotate(Vector3.Y, tempRot2.y)
		.rotate(Vector3.Z, tempRot2.z)
//		.rotate(tempPos.y + 0.5f, 0, tempPos.z, tempRot2.x)
//		.rotate(0, tempPos.x, tempPos.z, tempRot2.y)
//		.rotate(tempPos.x, tempPos.y, 0, tempRot2.z)
		;
		
	}

	
}
