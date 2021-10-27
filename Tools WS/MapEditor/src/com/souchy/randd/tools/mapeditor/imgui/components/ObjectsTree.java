package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FlushablePool;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

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
		this.size = new int[] { 200, 400 };
		this.position = new int[] { 20, 100 };
	}
	
	@Override
	public void renderContent(float delta) {
		int i = 0;
		
		ImGui.text("Cache");
		MapWorld.world.cache.getRenderables(renderables, renderablesPool);
		for (var inst : renderables) {
			if(ImGui.treeNode((i++) + " " + inst.meshPart.id)) {
				ImGui.treePop();
			}
		}
		renderables.clear();
		ImGui.text("Instances");
		for (var inst : MapWorld.world.instances) {
			if(ImGui.treeNode((i++) + " " + inst.nodes.get(0).id)) { // inst.model.meshParts.get(0).id
				ImGui.treePop();
			}
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
