package texgraph.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

public class Property {
	
	public String name;
	public PropertyType type;
	
	
	public Object value;
	public Object defaultValue;
	
	public float min, max, step;

	
	public Property copy() {
		var p  = new Property();
		p.name = name;
		p.type = type;
		p.value = value;
		p.defaultValue = defaultValue;
		if(value instanceof TextureAttribute t)
			p.value = TextureAttribute.createDiffuse(t.textureDescription.texture);
		if(defaultValue instanceof TextureAttribute t)
			p.defaultValue = TextureAttribute.createDiffuse(t.textureDescription.texture);
		if(value instanceof Color c)
			p.value = c.cpy();
		if(defaultValue instanceof Color c)
			p.defaultValue = c.cpy();
		p.min = min;
		p.max = max;
		p.step = step;
		return p;
	}
	
	public String toUniformString() {
		return "uniform " + type.toUniformTypeString() + " " + name + ";\n";
	}
	
}
