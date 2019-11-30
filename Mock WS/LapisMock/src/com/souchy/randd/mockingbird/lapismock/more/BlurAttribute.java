package com.souchy.randd.mockingbird.lapismock.more;

import com.badlogic.gdx.graphics.g3d.Attribute;

public class BlurAttribute extends Attribute {

    public final static String MyDouble1Alias = "myDouble1";
    public final static long MyDouble1 = register(MyDouble1Alias);
    public double value;
    
	protected BlurAttribute(long type) {
		super(type);
	}

	@Override
	public int compareTo(Attribute o) {
        if (type != o.type) return type < o.type ? -1 : 1;
        double otherValue = ((BlurAttribute) o).value;
        return value == otherValue ? 0 : (value < otherValue ? -1 : 1);
	}

	@Override
	public Attribute copy() {
		var copy = new BlurAttribute(type);
		copy.value = value;
		return copy;
	}
	
	
	
}
