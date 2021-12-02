package com.souchy.randd.tools.mapeditor.imgui.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FlushablePool;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;
import com.souchy.randd.tools.mapeditor.imgui.EditorImGuiHud;
import com.souchy.randd.tools.mapeditor.imgui.FontAwesomeIcons;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

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
			ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
			if(ImGui.button(FontAwesomeIcons.Cubes + " "+ (i++) + " " + inst.meshPart.id)) {
				//MapEditorGame.screen.imgui.properties.setInst(inst);
			}
			ImGui.popStyleColor();
		}
		renderables.clear();
		i = 0;
		ImGui.textColored(IGStyle.colorAccent, "Instances");
		for (var inst : EditorEntities.getInstances()) {
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
