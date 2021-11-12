package nodes;

import com.badlogic.gdx.graphics.Color;

import texgraph.model.NodeModel;
import texgraph.model.PinModel;
import texgraph.model.Property;
import texgraph.model.PropertyType;

public class AddNode extends NodeModel {
	
	public AddNode() {
		super();
		this.name = "Add";
	}
	
	@Override
	public void createProperties() {
		super.createProperties();
		// props
		var p = new Property();
		PropertyType.Color.setup(p);
		p.name = "ColorAdd";
		this.properties.add(p);
	}
	@Override
	public void createPins() {
		super.createPins();
		// pins
		var pin = new PinModel();
		pin.name = "ColorAdd";
		pin.type = Color.class;
		in.add(pin);
	}
	
	@Override
	public String compileActions() {
		return super.compileActions() + "gl_FragColor += " + in.get(1).name + ";\n";
	}
	
	
}
