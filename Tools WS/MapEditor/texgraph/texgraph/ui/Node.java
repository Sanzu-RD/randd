package texgraph.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.shader.Shader2;

import imgui.ImGui;
import imgui.extension.imnodes.ImNodes;
import imgui.extension.imnodes.flag.ImNodesColorStyle;
import imgui.flag.ImGuiCol;
import texgraph.Texgraph;
import texgraph.model.NodeModel;
import texgraph.model.Property;
import texgraph.model.PropertyType;
import texgraph.ui.Pin.Way;

/**
 * 
 * 
 * @author Blank
 * @date 10 nov. 2021
 */
public class Node implements ImGuiComponent {
	
	public static AtomicInteger nextNodeId = new AtomicInteger(1);
    public final int id = nextNodeId.getAndIncrement();
    
    public static Texture defaultTexture;
    static {
    	defaultTexture = LapisAssets.defaultTexture;
    }
    
	public FrameBuffer fbo;
	public ShaderProgram shader;
//    public TextureAttribute texin = TextureAttribute.createDiffuse(LapisAssets.defaultTexture);
//    public TextureAttribute texout = TextureAttribute.createDiffuse(LapisAssets.defaultTexture);
//	public Texture texin = defaultTexture;
//	public Texture texout = defaultTexture;
    public Pin mainIn = null;
    public Pin mainOut = null;
	
	public String name;
    public List<Pin> pinsIn = new ArrayList<>();
    public List<Pin> pinsOut = new ArrayList<>();
    public List<Property> properties = new ArrayList<>();
    
    public NodeModel model;
    
    public Node() {
    	//fbo = new FrameBuffer(Format.RGBA8888, texin.textureDescription.texture.getWidth(), texin.textureDescription.texture.getHeight(), false);
	}
    
    public void init(NodeModel model){
    	this.model = model;
    	this.name = model.name;
    	for(var p : model.properties) {
    		this.properties.add(p); // .copy()
    		//update(p);
    	}
    	for(var pin : model.in) {
    		var p = new Pin(Way.In, pin.name, pin.type);
    		if(pin.name.equals("TextureIn")) {
    			mainIn = p;
    		}
    		pinsIn.add(p);
    	}
    	for(var pin : model.out) {
    		var p = new Pin(Way.Out, pin.name, pin.type);
    		if(pin.name.equals("TextureOut")) {
    			mainOut = p;
    		}
    		pinsOut.add(p);
    	}
    	for(var in : this.pinsIn) {
    		if(!in.name.contains("In")) continue;
    	    for(var out : this.pinsOut) {
        		if(!out.name.contains("Out")) continue;
    			var namein = in.name.substring(0, in.name.lastIndexOf("In"));
    			var nameout = out.name.substring(0, out.name.lastIndexOf("Out"));
    			if(namein.equals(nameout)) {
    				
    			}
    		}
    	}
    	this.shader = new ShaderProgram(model.shaderVert, model.shaderFrag);
    	update();
    }

    /**
     * Bind properties to pins and refresh fbo
     */
    public void update() {
    	for(var p : properties) {
			for(var pin : this.pinsIn)
				if(pin.name.equals(p.name) && pin.link == null) {
					Log.info("update pin in bind to property %s = %s. / %s, %s", p.name, p.value, p.type, p.value.getClass());
					pin.object = p.value;
				}
			for(var pin : this.pinsOut)
				if(pin.name.equals(p.name) && pin.link == null) {
					Log.info("update pin out bind to property %s = %s. / %s, %s", p.name, p.value, p.type, p.value.getClass());
					pin.object = p.value;
				}
    	}
    	
    	//Log.info("Node update check main pin in %s = %s. / %s, %s", mainIn.name, mainIn.getObject(), mainIn.ptype, mainIn.getObject() == null ? null : mainIn.getObject().getClass());
    	//Log.info("Node update check main pin out %s = %s. / %s, %s", mainOut.name, mainOut.getObject(), mainOut.ptype, mainOut.getObject() == null ? null : mainOut.getObject().getClass());
    	
    	createFbo();
    }
	
	/**
	 * Binds pins inputs to shader uniforms
	 */
	public void bind() {
		if(shader == null) return;
		for(var pin : this.pinsIn) {
			//if(!pin.name.equals("TextureIn"))
			pin.bind(shader);
		}
		//shader.setUniformf(0, 200);
		//shader.setUniformf("test", 200);
		for(var u : shader.getUniforms()) {
			if(shader.getUniformType(u) == GL20.GL_FLOAT) {
				shader.setUniformf(u, 200);
			}
		}
		ShaderProgram.pedantic = false;
		
//		String uni = "ColorAdd"; // test
//		Log.info("bind uniform "+uni+" : %s, %s, %s, %s", shader.hasUniform(uni), shader.getUniformLocation(uni), 
//				shader.getUniformType(uni), shader.getUniformSize(uni));
	}
	
	public void render(float delta) {
		// RENDER FBO
		renderFbo(delta);
		
    	// RENDER TO IMGUI
//        ImNodes.pushColorStyle(ImNodesColorStyle.NodeBackground, Color.BLUE.toIntBits());
        ImNodes.beginNode(id);
        {
	        // title
	        ImNodes.beginNodeTitleBar();
	        ImGui.text(id + " " + name);
	        ImNodes.endNodeTitleBar();
	        
	        // pins in
	        ImGui.beginGroup();
	        	for(var pin : pinsIn) pin.render(delta);
	        ImGui.endGroup();
	        
	        // content
	        //ImGui.pushStyleColor(ImGuiCol.WindowBg, Color.RED.toIntBits());
	        ImGui.beginGroup();
		        ImGui.text("in");
				ImGui.image(getTexIn().getTextureObjectHandle(), 100, 100);
		        ImGui.text("out");
				ImGui.image(getTexOut().getTextureObjectHandle(), 100, 100);
	        ImGui.endGroup();
			//ImGui.popStyleColor();
	        
	        // pins out
	        ImGui.beginGroup();
	        	for(var pin : pinsOut) pin.render(delta);
	        ImGui.endGroup();
        }
        // end
        ImNodes.endNode();
//        ImNodes.popColorStyle();
	}

	public void renderFbo(float delta) {
		
		
		// FBO RENDER
		var batch = Texgraph.batch;
		boolean shading = false;
		try {
			//shader.bind();
			//bind();
			batch.setShader(shader);
//			update();
			// pass the textureIn through the shader
			fbo.begin();
			{
				// doesn't matter what color I make the background now that I disable the blending of it
				clearScreen(0, 0, 0, 0);
//				clearScreen(1, 1, 1, 0);
//				clearScreen(0, 0, 0, 1);
//				clearScreen(1, 1, 1, 1);
//				Gdx.gl.glClearColor(0, 0, 0, 0);
//				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				shading = true;

				// blending
				{
					// original
//					batch.enableBlending();
//					batch.disableBlending();
//					batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					
					// tries
					///batch.setBlendFunction(GL20.GL_FUNC_ADD, GL20.GL_FUNC_ADD);
//					batch.setBlendFunction(GL20.GL_SRC_COLOR, GL20.GL_SRC_ALPHA);
//					batch.setBlendFunctionSeparate(GL20.GL_ONE, GL20.GL_ONE, GL20.GL_ONE, GL20.GL_ONE);
					batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO); // essentially the same as disabling blending btw
//					batch.setBlendFunctionSeparate(GL20.GL_ONE, GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_ZERO);
//					batch.setBlendFunctionSeparate(GL20.GL_SRC_COLOR, GL20.GL_ZERO, GL20.GL_SRC_ALPHA, GL20.GL_ZERO);
					
					// forum
					//Gdx.gl.glEnable(GL20.GL_BLEND);
//					batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
//					batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
//					batch.enableBlending();
				}
				
				// filtering
				{
					
					TextureFilter filterMin = TextureFilter.Nearest;
					if(getTexIn().getTextureData().useMipMaps()) filterMin = TextureFilter.MipMapLinearLinear;
					TextureFilter filterMag = TextureFilter.Linear; 
					//if(getTexIn().getTextureData().useMipMaps()) filterMag = TextureFilter.MipMapLinearLinear;
					getTexIn().setFilter(filterMin, filterMag);
					
					getTexIn().setAnisotropicFilter(1);
				}
				
				// batch draw
				batch.begin();
				{
					bind();
					TextureRegion textureRegion = new TextureRegion(getTexIn());
					textureRegion.flip(false, true);
//					batch.draw(textureRegion, 0, 0);// texin.textureDescription.texture // LapisAssets.defaultTexture
//					batch.draw(getTexIn(), 0, 0);// texin.textureDescription.texture // LapisAssets.defaultTexture
					//textureRegion.setU(new Random().nextFloat() / 8);
					batch.draw(textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
					
				}
				batch.end();
			}
			fbo.end();
			shading = false;
			setTexOut(fbo.getColorBufferTexture());
			
			// filter out
			{
				getTexOut().bind();
				Gdx.gl.glGenerateMipmap(GL20.GL_TEXTURE_2D);
				TextureFilter filterOutMin = TextureFilter.MipMapLinearLinear;
				//if(getTexOut().getTextureData().useMipMaps()) filterOutMin = TextureFilter.MipMapLinearLinear;
				TextureFilter filterOutMag = TextureFilter.Linear;
				if(getTexOut().getWidth() <= 10) filterOutMag = TextureFilter.Nearest;
				getTexOut().setFilter(filterOutMin, filterOutMag);
			}
			
			batch.setShader(null);
			
//			if(false) {
//				batch.begin();
//					batch.draw(getTexIn(), 100, 0);// texin.textureDescription.texture // LapisAssets.defaultTexture
//				batch.end();
//				
//				batch.begin();
//					batch.draw(getTexOut(), 500, 0);// texin.textureDescription.texture // LapisAssets.defaultTexture
//				batch.end();
//			}
		} catch(Exception e) {
			if(batch.isDrawing()) batch.end();
			if(shading) fbo.end();
			Log.error("", e);
		}
//		Log.info("[%s, %s] [%s, %s] [%s, %s]", getTexIn().getWidth(), getTexIn().getHeight(),
//				getTexOut().getWidth(), getTexOut().getHeight(),
//				fbo.getWidth(), fbo.getHeight());
	}
	
	
	
	
	public void clearScreen(float r, float g, float b, float a) {
		//Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(r, g, b, a);
		Gdx.gl.glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
	}
	
	@Override
	public void resizeScreen(int screenW, int screenH) {
//		Log.info("asdfljnasdf");
		//createFbo();
		update();
	}

	public Pin getPin(long id) {
		for(var p : pinsIn)
			if(p.id == id)
				return p;
		for(var p : pinsOut)
			if(p.id == id)
				return p;
		return null;
	}

    public void update(Property p) {
		if(p.type == PropertyType.Texture) {
			//texin = ((TextureAttribute) p.value);
			createFbo();
		}
		update();
    }
    
	public String getName() {
		return name;
	}
	
    private void createFbo() {
//    	fbo = new FrameBuffer(Format.RGBA8888, 1600, 900, true, false);
    	fbo = new FrameBuffer(Format.RGBA8888, getTexIn().getWidth(), getTexIn().getHeight(), true, true);
//    	fbo = new FrameBuffer(Format.RGBA8888, 1920, 1080, true, true);
//    	fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, true);
//    	fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true, true);
    }
    
    public Texture getTexIn() {
    	return ((TextureAttribute) mainIn.getObject()).textureDescription.texture;
    }
    
    public Texture getTexOut() {
    	//if(mainOut.getObject() == null) return null;
    	return ((TextureAttribute) mainOut.getObject()).textureDescription.texture;
    }
    
    public void setTexOut(Texture t) {
    	try {
    		//Log.info("Set MainTexOut %s = %s. / %s, %s", mainOut.name, mainOut.getObject(), mainOut.ptype, mainOut.getObject() == null ? null : mainOut.getObject().getClass());
        	var attr = (TextureAttribute) mainOut.getObject();
        	(attr).textureDescription.texture = t;
    	} catch(Exception e) {
    		Log.error("", e);
    	}
    }

	
}
