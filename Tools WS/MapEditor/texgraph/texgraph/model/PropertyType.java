package texgraph.model;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;

public enum PropertyType {
	
	Float() {
		@Override
		public String toUniformTypeString() {
			return "float";
		}
		@Override
		public Object getDefaultValue() {
			return 0;
		}
	},
	Integer() {
		@Override
		public String toUniformTypeString() {
			return "int";
		}
		@Override
		public Object getDefaultValue() {
			return 0;
		}
	},
	Bool() {
		@Override
		public String toUniformTypeString() {
			return "bool";
		}
		@Override
		public Object getDefaultValue() {
			return false;
		}
	},
	Color() {
		@Override
		public String toUniformTypeString() {
			return "vec4";
		}
		@Override
		public Object getDefaultValue() {
			return com.badlogic.gdx.graphics.Color.WHITE.cpy();
		}
	},
	Texture(){
		@Override
		public String toUniformTypeString() {
			return "sampler2D";
		}

		@Override
		public Object getDefaultValue() {
			var attr = TextureAttribute.createDiffuse(LapisAssets.defaultTexture);
			attr.textureDescription.magFilter = TextureFilter.MipMapLinearLinear;
			attr.textureDescription.minFilter = TextureFilter.MipMapLinearLinear;
			attr.textureDescription.uWrap = TextureWrap.ClampToEdge;
			attr.textureDescription.vWrap = TextureWrap.ClampToEdge;
			//Log.info("PROPERTY DEFAULT VALUE");
			return attr;
		}
	},
	Matrix(){
		@Override
		public String toUniformTypeString() {
			return "mat4";
		}
		@Override
		public Object getDefaultValue() {
			return new Matrix4();
		}
	}
	;
	
	
	public abstract Object getDefaultValue();
	public abstract String toUniformTypeString();
	// public abstract Class<?> getClassType(); // maybe i'll do this

	public void setup(Property p) {
		p.name = this.name();
		p.type = this;
		p.value = getDefaultValue();
		p.defaultValue = getDefaultValue();
	}
	
	
	public static PropertyType getType(Class<?> c) {
		if(c == java.lang.Boolean.class) {
			return Float;
		}
		if(c == java.lang.Float.class) {
			return Float;
		}
		if(c == java.lang.Integer.class) {
			return Float;
		}
		if(c == com.badlogic.gdx.graphics.Texture.class) {
			return Texture;
		}
		if(c == TextureAttribute.class) {
			return Texture;
		}
		if(c == com.badlogic.gdx.graphics.Color.class) {
			return Color;
		}
		if(c == com.badlogic.gdx.math.Matrix4.class) {
			return Matrix;
		}
		return null;
	}
	
}