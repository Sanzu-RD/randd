package com.souchy.randd.tools.mapeditor.imgui.windowsgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.google.gson.Gson;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.SpellModel;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.imgui.FontAwesomeIcons;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.imgui.components.AutoTable;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.type.ImString;

/**
 * 
 * 
 * @author Blank
 * @date 28 nov. 2021
 */
public class SpellEditor extends Container {
	
	private static SpellEditor singleton = new SpellEditor();
	public static Container get() {
		return singleton;
	}

	public Json js = new Json();
	public FileHandle file = Gdx.files.local("spellmodels.json");
	{
		js.setOutputType(OutputType.json);
		try {
			file.file().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static class SpellCache {
		public ArrayList<SpellModel> models = new ArrayList<>();
		public SpellCache() {}
		public SpellCache(ArrayList<SpellModel> models) {
			this.models = models;
		}
	}
	
	public ArrayList<SpellModel> models = new ArrayList<>();
	public SpellModel selected = null;
	private ImString name = new ImString("", 30);
	private AutoTable autotable;
	
	
	public SpellEditor() {
		super();
		this.title = "SpellEditor";
	}

	@Override
	protected void applyDefaults() {
		this.size = new int[] { 800, 500 };
		this.position = new int[] { (Gdx.graphics.getWidth() - size[0]) / 2, 30 };
	}

	public void setSelected(SpellModel model) {
		this.selected = model;
		if(model == null) return;
		name.set(model.placeholderName);
		autotable = new AutoTable(model);
	}
	
	@Override
	public void renderContent(float delta) {
		if(ImGui.beginTable("##spelleditorlayout", 2, ImGuiTableFlags.SizingStretchProp | ImGuiTableFlags.BordersInnerV)) {
			ImGui.tableNextRow();
			ImGui.tableNextColumn(); 
			renderOptions();
			renderList();
			ImGui.tableNextColumn(); 
			renderProperties(delta);
			
			ImGui.endTable();
		}
	}
	
	private int nextid = 0;
	
	@SuppressWarnings("unchecked")
	public void renderOptions() {
		// options
		ImGui.beginGroup();
		{
			if(ImGui.button(FontAwesomeIcons.Plus)) {
				models.add(new SpellModel(nextid++));
			}
			ImGui.sameLine();
			if(ImGui.button(FontAwesomeIcons.Minus)) {
				models.remove(selected);
				setSelected(null);
			}
			ImGui.sameLine();
			if(ImGui.button(FontAwesomeIcons.FileExport)) {
				//Log.info("SpellEditor save " + file.file().getAbsolutePath());
				
				js.setElementType(SpellCache.class, "models", SpellModel.class);

				js.setUsePrototypes(false);
				js.setOutputType(OutputType.json);
				
				var json = js.prettyPrint(new SpellCache(models));
				
//				Log.info("spellmodel 0 json \n %s", js.prettyPrint(models.get(0)));
				
				file.writeString(json, false);
				
				
				
				
//				file.writeString(new Gson().toJson(models), false);
			}
			ImGui.sameLine();
			if(ImGui.button(FontAwesomeIcons.FileImport)) {
				js.setElementType(SpellCache.class, "models", SpellModel.class);
				SpellCache asd = js.fromJson(SpellCache.class, file);
//				var asd = js.fromJson(ArrayList.class, file);
//				var asd = new Gson().fromJson(file.readString(), models.getClass());
				models.clear();
//				models.addAll(asd);
				for(var model : asd.models)
					models.add(model);
				
				
			}
		}
		ImGui.endGroup();
	}

	public void renderList() {
		// list
		//ImGui.beginGroup();
		{
			for(var model : models) {
				//ImGui.text(model.id + " " + model.placeholderName);
				ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
				if(ImGui.button(FontAwesomeIcons.Cubes + " " + model.id + " " + model.placeholderName + "##" + model.hashCode())) {
					setSelected(model);
				}
				ImGui.popStyleColor();
			}
		}
		//ImGui.endGroup();
	}
	
	public void renderProperties(float delta) {
		if(selected == null) {
			ImGui.text("SpellModel Properties");
			return;
		}
		if(ImGui.inputText("##name", name)) {
			selected.placeholderName = name.get();
		}
		
		//selected.stats.cooldown.baseflat
		
		autotable.render(delta);
	}
	
	
}
