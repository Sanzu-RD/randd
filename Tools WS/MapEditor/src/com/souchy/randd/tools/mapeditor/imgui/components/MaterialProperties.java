package com.souchy.randd.tools.mapeditor.imgui.components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.InstantAdapter;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.ZonedDateTimeAdapter;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.imgui.EditorImGuiHud;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiUtil;
import com.souchy.randd.tools.mapeditor.imgui.windows.AssetExplorer.AssetDialog;
import com.souchy.randd.tools.mapeditor.io.MaterialJson;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.shaderimpl.DissolveUniforms.DissolveMaterial;

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
	
	
	public static final Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeHierarchyAdapter(Instant.class, new InstantAdapter())
			.registerTypeHierarchyAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
			.setExclusionStrategies(JsonHelpers.exclusionStrategy)
			.create();
	
	public static class ClassTypeAdapter extends TypeAdapter<Class<?>> {
		// The type adapter does not hold state, so it can be easily made singleton (+
			// making the constructor private)
		private static final TypeAdapter<Class<?>> instance = new ClassTypeAdapter()
				// This is a convenient method that can do trivial null-checks in
				// write(...)/read(...) itself
				.nullSafe();
		
		private ClassTypeAdapter() {
		}
		
		static TypeAdapter<Class<?>> get() {
			return instance;
		}
		
		@Override
		public void write(final JsonWriter out, final Class<?> value) throws IOException {
			// value is never a null here
			out.value(value.getName());
		}
		
		@Override
		public Class<?> read(final JsonReader in) throws IOException {
			try {
				// This will never be a null since nullSafe() is used above
				final String className = in.nextString();
				return Class.forName(className);
			} catch (final ClassNotFoundException ex) {
				// No need to duplicate the message generated in ClassNotFoundException
				throw new JsonParseException(ex);
			}
		}
		
	}
	
	
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
				AssetDialog.materialPicker.prepare(() -> {
					String asset = AssetDialog.materialPicker.pick();
					if(asset != null) {
						var newmat = LapisAssets.get(asset, Material.class);
						mat.set(newmat.copy());
					}
				});
				if(ImGui.button("Set")) {
					AssetDialog.materialPicker.show();
				}
				ImGui.sameLine();
				if(ImGui.button("Export")) {
					//JsonConfig.save(mat, Paths.get("data/shaders/"+mat.id));
					//if(true) return;
					try {
						Json js = MaterialJson.js;
						String json = js.prettyPrint(mat);
						MapEditorGame.screen.hud.saveAs(Environment.fromRes("materials/"), ".mat", (files) -> {
							if(files.size == 0) return;
							files.get(0).writeString(json, false);
						});
						
					} catch (Exception e) {
						Log.info("", e);
					}
				}
				ImGui.sameLine();
				if(ImGui.button("Import")) {
					try {
						Json js = MaterialJson.js;
						MapEditorGame.screen.hud.open(Environment.fromRes("materials/"), ".mat", (files) -> {
							if(files.size == 0) return;
							String json = files.get(0).readString();
							Material mm = js.fromJson(Material.class, json); //Files.readString());
							mat.set(mm);
						});
					} catch (Exception e) {
						Log.info("", e);
					}
				}
				ImGui.sameLine();
				if(ImGui.button("Empty")) {
					mat.clear();
				}
				ImGui.sameLine();
				if(ImGui.button("Delete")) {
					mats.removeValue(mat, true);
				}
				
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
				mat.set(new DissolveMaterial());
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
				try {
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
				} catch(Exception e) {
					ImGui.textColored(Color.RED.toIntBits(), "" + attrid + " " + name);
//					Log.error("", e);
				}
			}
			ImGui.endTable();
		}
		ImGui.endGroup();
	}
	
	public void renderAttribute(Attribute a) {
		if(a instanceof ColorAttribute c)
			renderColor(c);
		if(a instanceof TextureAttribute c)
			renderTexture(c);
		if(a instanceof FloatAttribute c)
			renderFloat(c);
		if(a instanceof IntAttribute c)
			renderInt(c);
		if(a instanceof BlendingAttribute c)
			renderBlend(c);
	}

	public void renderTexture(TextureAttribute a) {
		// texture popup
		AssetDialog.texturePicker.prepare(() -> {
			String asset = AssetDialog.texturePicker.pick();
			if(asset != null) {
				a.textureDescription.texture = LapisAssets.get(asset, Texture.class);
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
		ImGui.text("U"); ImGui.sameLine();
		if(ImGui.dragScalar("##U", ImGuiDataType.Float, imv, 0, 1)) {
			a.offsetU = imv.get();
		}
		imv.set(a.offsetV);
		ImGui.text("V"); ImGui.sameLine();
		if(ImGui.dragScalar("##V", ImGuiDataType.Float, imv, 0, 1)) {
			a.offsetV = imv.get();
		}
		imv.set(a.scaleU);
		ImGui.text("W"); ImGui.sameLine();
		if(ImGui.dragScalar("##W", ImGuiDataType.Float, imv, 0, 20)) {
			a.scaleU = imv.get();
		}
		imv.set(a.scaleV);
		ImGui.text("H"); ImGui.sameLine();
		if(ImGui.dragScalar("##H", ImGuiDataType.Float, imv, 0, 20)) {
			a.scaleV = imv.get();
		}
		ImGuiUtil.poolFloat.free(imv);
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
//		ImGuiSliderFlags.Logarithmic
		if(ImGui.dragScalar("##" + Attribute.getAttributeAlias(a.type), ImGuiDataType.Float, imv, -1, 50)) {
			a.value = imv.get();
		}
//		if(ImGui.inputFloat("##" + Attribute.getAttributeAlias(a.type), imv, 0.5f, 1f, "%.4g")) { //, ImGuiInputTextFlags.EnterReturnsTrue)) {
//			a.value = imv.get();
//		}
		// sliderfloat, dragfloat
			
		ImGuiUtil.poolFloat.free(imv);
	}
	
	public void renderInt(IntAttribute a) {
		ImInt imv = ImGuiUtil.poolInt.obtain();
		imv.set(a.value);
		if(ImGui.dragScalar("##" + Attribute.getAttributeAlias(a.type), ImGuiDataType.S32, imv, -10, 100)) {
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
