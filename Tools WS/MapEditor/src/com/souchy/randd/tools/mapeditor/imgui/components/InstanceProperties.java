package com.souchy.randd.tools.mapeditor.imgui.components;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiUtil;

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
	private AutoTable autoTable;

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
		if(inst != null) {
			mats.mats = inst.materials;
			autoTable = new AutoTable(this.inst);
		}
		else {
			mats.mats = null;
		}
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
			ImGuiUtil.renderTransform(inst.transform, tempPos, tempScl, tempRot2);
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
				for(var a : inst.animations) {
					renderAnimation(delta, a);
				}
			} catch(Exception e) {
				Log.error("", e);
			}
			ImGui.treePop();
		}

		
		//
		ImGui.spacing();
		ImGui.separator();
		ImGui.spacing();
		if(ImGui.treeNode("Nodes")) {
			try {
				renderNodes(delta);
			} catch(Exception e) {
				Log.error("", e);
			}
			ImGui.treePop();
		}

		
		//
		ImGui.spacing();
		ImGui.separator();
		ImGui.spacing();
		if(ImGui.treeNode("Model")) {
			if(ImGui.treeNode("Meshes")) {
				try {
					for (var mesh : inst.model.meshes) {
						renderMesh(delta, mesh);
					}
				} catch(Exception e) {
					Log.error("", e);
				}
				ImGui.treePop();
			}
			if(ImGui.treeNode("MeshParts")) {
				try {
					for (var meshPart : inst.model.meshParts) {
						renderMeshPart(delta, meshPart);
					}
				} catch(Exception e) {
					Log.error("", e);
				}
				ImGui.treePop();
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
	}

	private void renderNodes(float delta) {
		for(var node : inst.nodes) {
			renderNode(delta, node);
		}
	}
	
	private void renderNode(float delta, Node node) {
		if(ImGui.treeNode("Node # " + node.id+"##"+node.hashCode())) {
			//ImGuiUtil.renderVector3("Translation", node.translation, -20, 20);
			//ImGuiUtil.renderQuaternion("Rotation", node.rotation, -180, 180);
			//ImGuiUtil.renderVector3("Scale", node.scale, 0, 20);
			//ImGui.text("localTransform");
			//ImGuiUtil.renderTransform(node.localTransform, tempPos, tempScl, tempRot2);
			ImGui.text("globalTransform");
			ImGuiUtil.renderTransform(node.globalTransform, tempPos, tempScl, tempRot2);
			
			if(ImGui.treeNode("NodeParts##" + node.hashCode())) {
				for(var part : node.parts) {
					renderNodePart(delta, part);
				}
				ImGui.treePop();
			}
			
			if(ImGui.treeNode("Children##"+node.hashCode())) {
				for(var child : node.getChildren()) {
					renderNode(delta, child);
				}
				ImGui.treePop();
			}
			
			ImGui.treePop();
		}
	}

	private void renderNodePart(float delta, NodePart part) {
		if(ImGui.treeNode("NodePart # " + part.hashCode())) {
			//ImGui.text("Material : " + part.material.id);
			ImGui.text("MeshPart : " + part.meshPart.id);
			
			var imv = new ImInt(0);
			var items = new String[inst.materials.size];
			for(int i = 0; i < items.length; i++) {
				items[i] = inst.materials.get(i).id;
				if(items[i].equals(part.material.id)) {
					imv.set(i);
				}
			}
			
			ImGui.text("Material : ");
			ImGui.sameLine();
			if(ImGui.combo("##Material "+part.meshPart.id+part.hashCode(), imv, items)) {
				part.material = inst.materials.get(imv.get());
			}
			
			ImGui.treePop();
		}
	}
	
	private void renderMesh(float delta, Mesh mesh) {
		if(ImGui.treeNode("Mesh # " + mesh.hashCode())) {
			
			ImGui.treePop();
		}
	}
	
	private void renderMeshPart(float delta, MeshPart part) {
		if(ImGui.treeNode("MeshPart # " + part.id + "##" + part.hashCode())) {
			
			ImGui.treePop();
		}
	}
	
	private void renderAnimation(float delta, Animation a) {
		if(ImGui.treeNode("Animation # " + a.id + "##" + a.hashCode())) {
			ImGui.text("Duration : " + a.duration);
			// render node animations > node key frames
			ImGui.treePop();
		}
	}
	
	
	
	
	
}
