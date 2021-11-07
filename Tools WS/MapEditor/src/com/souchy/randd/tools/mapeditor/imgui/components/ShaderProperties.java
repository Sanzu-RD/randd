package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Validator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.shader.Shader2;

import imgui.ImGui;

public class ShaderProperties implements ImGuiComponent {

	private Shader2 s;
	
	public void setShader(Shader2 s) {
		this.s = s;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(float delta) {
		if(s == null) return;
		ImGui.textColored(IGStyle.colorAccent, "Shader # " + s.hashCode());
		try {
			
			{
				long attributesMask = (long) s.getClass().getDeclaredField("attributesMask").get(s);
				ImGui.text("Attribute mask " + attributesMask);
			}
			{
				long vertexMask = (long) s.getClass().getDeclaredField("vertexMask").get(s);
				ImGui.text("Vertex mask " + vertexMask);
			}
			
			if(ImGui.treeNode("Uniforms")) {
				var uniforms = (Array<Uniform>) s.getClass().getDeclaredField("validators").get(s);
				for (var u : uniforms) {
					if(ImGui.treeNode(u.alias)) {
						//ImGui.text("Alias " + u.alias);
						ImGui.text("Mask " + u.overallMask);
						ImGui.text("Mask material " + u.materialMask);
						ImGui.text("Mask environment " + u.environmentMask);
						ImGui.treePop();
					}
				}
				ImGui.treePop();
			}

			if(ImGui.treeNode("Vertexc Attributes")) {
				var attributes = (IntIntMap) s.getClass().getDeclaredField("attributes").get(s);
				for (var u : attributes) {
					ImGui.text("key " + u.key + ", value " + u.value);
//					s.program.getAttributes()
//					if(ImGui.treeNode(u.)) {
//						//ImGui.text("Alias " + u.alias);
//						ImGui.text("Mask " + u.overallMask);
//						ImGui.text("Mask material " + u.materialMask);
//						ImGui.text("Mask environment " + u.environmentMask);
//						ImGui.treePop();
//					}
				}
				ImGui.treePop();
			}
			
			
		} catch(Exception e){
			
		}
	}
	
}
