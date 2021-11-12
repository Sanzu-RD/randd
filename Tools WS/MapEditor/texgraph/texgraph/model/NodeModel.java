package texgraph.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

import texgraph.Texgraph;

public class NodeModel {
	
	public String name = "defaultName";
	public String category = "defaultCategory";
	
	
	public List<Property> properties = new ArrayList<>();
	public List<PinModel> in = new ArrayList<>();
	public List<PinModel> out = new ArrayList<>();
	
	// publid NodeModel copy() ? 

	public String shaderVert = Texgraph.defaultShaderVertex;
	public String shaderFrag = Texgraph.defaultShaderFragment;

	
	public NodeModel() {
		createProperties();
		createPins();
		compile();
	}
	
	public void createProperties() {
		// props
		var p = new Property();
		PropertyType.Texture.setup(p);
		p.name = "TextureIn";
		this.properties.add(p);
	}
	
	public void createPins() {
		// pins
		var pinin = new PinModel();
		pinin.name = "TextureIn";
		pinin.type = TextureAttribute.class;
		in.add(pinin);
		
		// pins
		var pinout = new PinModel();
		pinout.name = "TextureOut";
		pinout.type = TextureAttribute.class;
		out.add(pinout);
	}
	
	public void compile() {
		String vars = this.compileUniforms(); // on devrait use les pins in à la place des props
		String actions = compileActions();
		this.shaderFrag = Texgraph.frag(vars, actions);
	}
	
	public String compileUniforms() {
		String vars = "";
		for(var prop : properties) {
			vars += prop.toUniformString();
		}
		return vars;
	}
	
	public String compileActions() {
		return "gl_FragColor = v_color * texture2D("+this.in.get(0).name+", v_texCoords);\n";
	}
	
}
