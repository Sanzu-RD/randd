package nodes;

import com.badlogic.gdx.graphics.Color;

import texgraph.model.NodeModel;
import texgraph.model.PinModel;
import texgraph.model.Property;
import texgraph.model.PropertyType;

public class MultiplyNode extends NodeModel {
	
	public MultiplyNode() {
		super();
		this.name = "Multiply";
	}
	
	@Override
	public void createProperties() {
		super.createProperties();
		// props
		var p = new Property();
		PropertyType.Color.setup(p);
		p.name = "ColorMultiply";
		this.properties.add(p);
	}
	@Override
	public void createPins() {
		super.createPins();
		// pins
		var pin = new PinModel();
		pin.name = "ColorMultiply";
		pin.type = Color.class;
		in.add(pin);
	}
	
	@Override
	public String compileActions() {
		return super.compileActions() + "gl_FragColor *= " + in.get(1).name + ";\n";
	}
	
	
}
