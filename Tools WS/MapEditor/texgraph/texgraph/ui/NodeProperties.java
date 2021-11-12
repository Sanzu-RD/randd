package texgraph.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiUtil;

import imgui.ImGui;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiDataType;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import texgraph.model.Property;

public class NodeProperties implements ImGuiComponent {

	private Node node;
	private ImString imv;
	private ImString imf;
	
	
	public NodeProperties setNode(Node selected) {
		this.node = selected;
		imv = new ImString(node.shader.getVertexShaderSource().length() + 1000);
		imf = new ImString(node.shader.getFragmentShaderSource().length() + 1000);
		imv.set(node.shader.getVertexShaderSource());
		imf.set(node.shader.getFragmentShaderSource());
		Log.info("NodeProperties set " + node.id + " " + node.name + " " + node.properties.get(0).value.hashCode());
		return this;
	}
	
	@Override
	public void render(float delta) {
		if(node == null) return;
		
		// properties
		ImGui.textColored(IGStyle.colorAccent, "Properties");
//		if(ImGui.treeNode("Properties")) {
			for (var p : node.properties) {
				//ImGui.textColored(IGStyle.colorAccent, p.name);
				ImGui.text("#" + p.hashCode() + " " + p.name);
				switch (p.type) {
					case Bool -> renderBool(delta, p);
					case Color -> renderColor(delta, p);
					case Float -> renderFloat(delta, p);
					case Integer -> renderInteger(delta, p);
					case Texture -> renderTexture(delta, p);
					default -> throw new IllegalArgumentException("Unexpected value: " + p.type);
				}
			}
//			ImGui.treePop();
//		}
		ImGui.separator();

		ImGui.textColored(IGStyle.colorAccent, "Pins In");
		//ImGui.text(node.mainIn.id + " Main In " + "" + node.mainIn.name + " Texhash " + node.mainIn.getObject().hashCode() + " Texhandle " + node.getTexIn().getTextureObjectHandle());
		ImGui.image(node.getTexIn().getTextureObjectHandle(), 100, 100);
		for(var pin : node.pinsIn) {
			ImGui.text(pin.id + " " + pin.name + " " + pin.type.getSimpleName() + " " + pin.ptype + " " + pin.getObject());
		}
		ImGui.separator();

		ImGui.textColored(IGStyle.colorAccent, "Pins Out");
		//ImGui.text(node.mainOut.id + " Main Out " + "" + node.mainOut.name + " Texhash " + node.mainOut.getObject().hashCode() + " Texhandle " + node.getTexOut().getTextureObjectHandle());
		ImGui.image(node.getTexOut().getTextureObjectHandle(), 100, 100);
		for(var pin : node.pinsOut) {
			ImGui.text(pin.id + " " + pin.name + " " + pin.type.getSimpleName() + " " + pin.ptype + " " + pin.getObject());
		}
		ImGui.separator();
		
		// shader
		renderShader(delta);
	}
	
	public void renderShader(float delta) {
		ImGui.beginGroup();
		
		float width = ImGui.getWindowWidth() - 20;
		float height = 300;
		
		ImGui.textColored(IGStyle.colorAccent, "Shader");
		ImGui.sameLine();
		if(ImGui.button("Compile")) {
			try {
				node.shader.dispose();
				var compile = new ShaderProgram(imv.get(), imf.get());
				node.shader = compile;
			} catch(Exception e) {
				
			}
	    	//Log.info("frag " + imf.get());
		}
//		ImGui.text("Vertex"); ImGui.sameLine();
		if(ImGui.inputTextMultiline("##vertex", imv, width, 210, ImGuiInputTextFlags.AllowTabInput)) {
		}
//		ImGui.text("Fragment"); ImGui.sameLine();
		if(ImGui.inputTextMultiline("##fragment", imf, width, height, ImGuiInputTextFlags.AllowTabInput)) {
		}
		
		ImGui.endGroup();
	}
	
	public void renderBool(float delta, Property p) {
		boolean val = (boolean) p.value;
		if(ImGui.checkbox("##" + p.hashCode(), val)) {
			p.value = !val;
			node.update(p);
		}
	}
	public void renderColor(float delta, Property p) {
		Color color = (Color) p.value;
		var col = new float[] { color.r, color.g, color.b, color.a }; 
		if(ImGui.colorEdit4("##" + p.hashCode(), col, ImGuiColorEditFlags.AlphaBar)) {
			color.set(col[0], col[1], col[2], col[3]);
			node.update(p);
		}
	}
	public void renderFloat(float delta, Property p) {
		float val = (float) p.value;
		var imv = ImGuiUtil.poolFloat.obtain();
		imv.set(val);
		if(ImGui.dragScalar("##" + p.hashCode(), ImGuiDataType.Float, imv, p.step, p.min, p.max)) {
			p.value = imv.get();
			node.update(p);
		}
		ImGuiUtil.poolFloat.free(imv);
	}
	public void renderInteger(float delta, Property p) {
		var val = (int) p.value;
		var imv = ImGuiUtil.poolInt.obtain();
		imv.set(val);
		if(ImGui.dragScalar("##" + p.hashCode(), ImGuiDataType.Float, imv, p.step, (int) p.min, (int) p.max)) {
			p.value = imv.get();
			node.update(p);
		}
		ImGuiUtil.poolInt.free(imv);
	}
	public void renderTexture(float delta, Property p) {
		var val = (TextureAttribute) p.value;
		ImBoolean imv = new ImBoolean(false);
		imv.set(val.textureDescription.uWrap == TextureWrap.Repeat);
		if(ImGuiUtil.renderTexture(val, imv))
			node.update(p);
	}
	
	
	
}
