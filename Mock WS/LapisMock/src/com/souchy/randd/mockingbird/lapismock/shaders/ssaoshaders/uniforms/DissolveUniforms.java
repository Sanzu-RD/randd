package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.LocalSetter;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;

public class DissolveUniforms implements UniformsModule {

	// -------------------
	
	// Data
	protected TextureDescriptor<Texture> dissolveTexture;
	public float dissolveIntensity = 0.5f;
	public float dissolveBorderWidth = 0.05f;
	public Color dissolveBorderColor = Color.BLUE;
	
	// -------------------
	
	
	// Dissolve uniforms
	public int u_dissolveTexture;
	//public final int u_diffuseColor;
	public int u_dissolveUVTransform;

	/** ex : 0.5 = half dissolved */
	public int u_dissolveIntensity;
	/** width of the border around the dissolved area */
	public int u_dissolveBorderWidth;
	/** color of the border */
	public int u_dissolveBorderColor;

	// -------------------
	
//	public String shaderFragment;
//	public String shaderVertex;
	
	public static final Texture texture = LapisAssets.oneShot("res/textures/fx/Distortion01.png", Texture.class);
	static {
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		texture.setAnisotropicFilter(8f);
	}
	
	public DissolveUniforms() {
		
		
		dissolveTexture = new TextureDescriptor<Texture>();
		dissolveTexture.minFilter = dissolveTexture.magFilter = Texture.TextureFilter.MipMapLinearNearest; //.MipMapLinearLinear;
		dissolveTexture.uWrap = dissolveTexture.vWrap = Texture.TextureWrap.Repeat; 
		dissolveTexture.texture = texture;
		
//		shaderFragment = Gdx.files.internal("res/shaders/components/dissolve.f.glsl").readString(); 
//		shaderVertex = Gdx.files.internal("res/shaders/components/dissolve.v.glsl").readString(); 
		//LapisAssets.get("res/shaders/dissolve.frag.glsl"); 
		//var shader = LapisAssets.get("", ShaderProgram.class);
		//shader.getVertexShaderSource();
	}

	@Override
	public void register(BaseShader s) {
		u_dissolveTexture = s.register(new Uniform("u_dissolveTexture", DissolveTextureAttribute.DissolveTextureType));
		u_dissolveUVTransform = s.register(new Uniform("u_dissolveUVTransform", DissolveTextureAttribute.DissolveTextureType));
		u_dissolveIntensity = s.register(new Uniform("u_dissolveIntensity"));
		u_dissolveBorderWidth = s.register(new Uniform("u_dissolveBorderWidth"));
		u_dissolveBorderColor = s.register(new Uniform("u_dissolveBorderColor"));
	}
	
	
	
	@Override 
	public void bind(BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
		//s.set(u_dissolveTexture, dissolveTexture);
		//s.set(u_dissolveIntensity, dissolveIntensity);
		//s.set(u_dissolveBorderWidth, dissolveBorderWidth);
		//s.set(u_dissolveBorderColor, dissolveBorderColor);

		if(attributes.has(DissolveTextureAttribute.DissolveTextureType)) {
			var attr = (TextureAttribute) attributes.get(DissolveTextureAttribute.DissolveTextureType);
//			attr.scaleV = 0.5f;
			//attr.offsetU = new Random().nextFloat();
			s.set(u_dissolveTexture, attr.textureDescription);
			s.set(u_dissolveUVTransform, attr.offsetU, attr.offsetV, attr.scaleU, attr.scaleV);
			//Log.info("Set dissolve texture " + attr.color);
		}
		if(attributes.has(DissolveIntensityAttribute.DissolveIntensityType)) {
			var attr = (FloatAttribute) attributes.get(DissolveIntensityAttribute.DissolveIntensityType);
			s.set(u_dissolveIntensity, attr.value);
			//Log.info("Set dissolve intensity " + attr.value + ", " + renderable.userData + ", " + renderable.hashCode());
		}
		if(attributes.has(DissolveBorderColorAttribute.DissolveBorderColorType)) {
			var attr = (ColorAttribute) attributes.get(DissolveBorderColorAttribute.DissolveBorderColorType);
			s.set(u_dissolveBorderColor, attr.color);
			//Log.info("Set dissolve color " + attr.color);
		}

		if(attributes.has(DissolveBorderWidthAttribute.DissolveBorderWidthType)) {
			var attr = (FloatAttribute) attributes.get(DissolveBorderWidthAttribute.DissolveBorderWidthType);
			s.set(u_dissolveBorderWidth, attr.value);
			//Log.info("Set dissolve width " + attr.value);
		}
		//Log.info("bind dissolve : %s, %s, %s, %s", dissolveTexture != null, dissolveIntensity, dissolveBorderWidth, dissolveBorderColor);
		//Log.info("bind dissolve : %s, %s, %s, %s", u_dissolveTexture, u_dissolveIntensity, u_dissolveBorderWidth, u_dissolveBorderColor);
		//Log.info("bind dissolve : %s, %s, %s", DissolveIntensityAttribute.DissolveIntensityType, DissolveBorderWidthAttribute.DissolveBorderWidthType, DissolveBorderColorAttribute.DissolveBorderColorType);
	}

	
	@Override
	public String vertex() {
		return "components/dissolve.v.glsl";
	}
	
	@Override
	public String fragment() {
		return "components/dissolve.f.glsl";
	}


	
	public static class DissolveMaterial extends Material {
		public DissolveTextureAttribute texture;
		public DissolveIntensityAttribute intensity;
		public DissolveBorderColorAttribute borderColor;
		public DissolveBorderWidthAttribute borderWidth;
		public DissolveMaterial() {
			this(null, Color.BLUE, 0.03f, 0.3f);
		}
		public DissolveMaterial(Color borderColor, float borderWidth, float intensity) {
			this(null, borderColor, borderWidth, intensity);
		}
		public DissolveMaterial(Texture texture, Color borderColor, float borderWidth, float intensity) {
			if(texture == null) texture = DissolveUniforms.texture;
			this.texture = new DissolveTextureAttribute(texture);
			this.intensity = new DissolveIntensityAttribute(intensity);
			this.borderColor = new DissolveBorderColorAttribute(borderColor);
			this.borderWidth = new DissolveBorderWidthAttribute(borderWidth);
			this.set(this.texture, this.intensity, this.borderColor, this.borderWidth);
		}
	}

	/**
	 * texture and transform
	 */
	public static class DissolveTextureAttribute extends TextureAttribute {
		public static final String DissolveTextureAlias = "a_DissolveTexture";
		public static final long DissolveTextureType = register(DissolveTextureAlias);
		static {
			DissolveTextureAttribute.Mask |= DissolveTextureType;
		}
		public DissolveTextureAttribute(Texture value) {
			super(DissolveTextureType, value);
		}
	}
	public static class DissolveIntensityAttribute extends FloatAttribute {
		public static final String DissolveIntensityAlias = "a_DissolveIntensity";
		public static final long DissolveIntensityType = register(DissolveIntensityAlias);
		public DissolveIntensityAttribute(float value) {
			super(DissolveIntensityType, value);
		}
	}
	public static class DissolveBorderColorAttribute extends ColorAttribute {
		public static final String DissolveBorderColorAlias = "a_DissolveBorderColor";
		public static final long DissolveBorderColorType = register(DissolveBorderColorAlias);
		static {
			ColorAttribute.Mask |= DissolveBorderColorType;
		}
		public DissolveBorderColorAttribute(Color value) {
			super(DissolveBorderColorType, value);
		}
	}
	public static class DissolveBorderWidthAttribute extends FloatAttribute {
		public static final String DissolveBorderWidthAlias = "a_DissolveBorderWidth";
		public static final long DissolveBorderWidthType = register(DissolveBorderWidthAlias);
		public DissolveBorderWidthAttribute(float value) {
			super(DissolveBorderWidthType, value);
		}
	}
	
	
}
