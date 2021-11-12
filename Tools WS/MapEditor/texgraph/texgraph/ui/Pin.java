package texgraph.ui;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;

import imgui.extension.imnodes.ImNodes;
import imgui.extension.imnodes.flag.ImNodesPinShape;
import imgui.internal.ImGui;
import texgraph.model.PropertyType;

/**
 * 
 * 
 * @author Blank
 * @date 10 nov. 2021
 */
public class Pin implements ImGuiComponent {
	
	public static enum Way {
		In, Out;
	}

    public static AtomicInteger nextPinId = new AtomicInteger(1);
	public final int id = nextPinId.getAndIncrement();
	
	
	
	public Way way;
	public String name;
	
	public Class<?> type;
	public PropertyType ptype;
	
	public Link link;
	public Object object;
	
	public Pin(Way way, String name, Class<?> type) {
		this.way = way;
		this.name = name;
		this.type = type;
		ptype = PropertyType.getType(type);
		object = ptype.getDefaultValue();
		if(ptype == PropertyType.Texture) {
			var attr = (TextureAttribute) ptype.getDefaultValue(); //TextureAttribute.createDiffuse(new Texture(new Pixmap(100, 100, Format.RGBA8888)));
//			attr.offsetU = new Random().nextFloat();
			object = attr;
			Log.info("Pin [%s %s] new texture [%s %s]", name, this.hashCode(), object.hashCode(), attr.textureDescription.texture.getTextureObjectHandle());
		}
		if(ptype == PropertyType.Color) {
			object = Color.WHITE.cpy();
		}
	}
	
	public void render(float delta) {
		if(way == Way.In) {
	        ImNodes.beginInputAttribute(id, ImNodesPinShape.Quad);
	        ImGui.text(name);
	        ImNodes.endInputAttribute();
		} 
		if(way == Way.Out) {
			//Log.info("Pin render " + id);
	        ImNodes.beginOutputAttribute(id);
	        ImGui.text(name);
	        ImNodes.endOutputAttribute();
		}
	}
	
	public String toUniformString() {
		return "uniform " + ptype.toUniformTypeString() + " " + name + ";\n";
	}
	
	public void bind(ShaderProgram shader) {
		//Log.info("Pin bind shader vert \n" + shader.getVertexShaderSource());
		//Log.info("Pin bind shader frag \n" + shader.getFragmentShaderSource());
		//Log.info("Pin [" + name + "] bind ptype [" + ptype.name() + "] = " + getObject());
		
		// only bind uniforms that actually exist and are used at this moment
		if(!shader.hasUniform(name)) return;
		if(getObject() instanceof Color c) {
//			Log.info("Pin bind color %s = %s", this.name, c);
			shader.setUniformf(this.name, c);
		} else 
		if(getObject() instanceof Float c) {
			shader.setUniformf(this.name, c);
		} else
		if(getObject() instanceof Integer c) {
			shader.setUniformi(this.name, c);
		} else
		if(getObject() instanceof Texture c) {
			//Log.info("Pin bind object texture = " + c.getTextureObjectHandle());
			shader.setUniformi(this.name, c.getTextureObjectHandle());
		} else
		if(getObject() instanceof TextureAttribute c) {
//			Log.info("Pin bind object textureattribute = " + c.textureDescription.texture.getTextureObjectHandle());
//			c.textureDescription.texture.bind(300 + id);
			shader.setUniformi(this.name, 0); // c.textureDescription.texture.glTarget // c.textureDescription.texture.getTextureObjectHandle());
		} else 
		if(getObject() instanceof Boolean c) {
			shader.setUniformi(this.name, c ? 1 : 0);
		}
	}

	public Object getObject() {
		if(way == Way.In && link != null) 
			return link.sourcePin.getObject();
		return object;
	}
	
	public void dispose() {
		if(link != null) 
			link.dispose();
		link = null;
	}
	

}
