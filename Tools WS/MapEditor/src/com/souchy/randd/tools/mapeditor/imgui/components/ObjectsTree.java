package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FlushablePool;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.imgui.FontAwesomeIcons;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.main.EditorEntities;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;
import com.souchy.randd.tools.mapeditor.ui.mapeditor.EditorImGuiHud;

import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;

public class ObjectsTree extends Container { // implements ImGuiComponent 
	
//	private int lastListSize = 0;
	
	protected final Array<Renderable> renderables = new Array<Renderable>();
	protected final RenderablePool renderablesPool = new RenderablePool();
	
	
	public ObjectsTree() {
		super();
		this.title = "Object Tree";
	}
	
	@Override
	protected void applyDefaults() {
		this.size = new int[] { 150, 300 };
		this.position = new int[] { 10, 250 };
	}
	
	@Override
	public void renderContent(float delta) {
		int i = 0;
		
		ImGui.textColored(IGStyle.colorAccent, "Cache");
		MapWorld.world.cache.getRenderables(renderables, renderablesPool);
		for (var inst : renderables) {
			//if(ImGui.treeNode((i++) + " " + inst.meshPart.id)) {
			//	ImGui.treePop();
			//}
			ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
			if(ImGui.button(FontAwesomeIcons.Cubes + " "+ (i++) + " " + inst.meshPart.id)) {
				//MapEditorGame.screen.imgui.properties.setInst(inst);
			}
			ImGui.popStyleColor();
		}
		renderables.clear();
		ImGui.textColored(IGStyle.colorAccent, "Instances");
		for (var inst : EditorEntities.getInstances()) {
			/*
			if(ImGui.treeNode((i++) + " " + inst.nodes.get(0).id)) { // inst.model.meshParts.get(0).id
				Log.info("open node " + inst.nodes.get(0).id);
				//MapEditorGame.screen.imgui.properties.setInst(inst);
				ImGui.treePop();
			}
			*/
			ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
			if(ImGui.button(FontAwesomeIcons.Cube + " "+ (i++) + " " + inst.nodes.get(0).id)) {
				MapEditorGame.screen.imgui.properties.setObj(inst);
			}
			ImGui.popStyleColor();
		}
		
		
	}
	
	

	protected static class RenderablePool extends FlushablePool<Renderable> {
		@Override
		protected Renderable newObject () {
			return new Renderable();
		}

		@Override
		public Renderable obtain () {
			Renderable renderable = super.obtain();
			renderable.environment = null;
			renderable.material = null;
			renderable.meshPart.set("", null, 0, 0, 0);
			renderable.shader = null;
			renderable.userData = null;
			return renderable;
		}
	}
	
}
