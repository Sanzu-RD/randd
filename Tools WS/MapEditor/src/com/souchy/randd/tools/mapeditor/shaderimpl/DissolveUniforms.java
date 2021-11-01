package com.souchy.randd.tools.mapeditor.shaderimpl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.shader.Shader2;
import com.souchy.randd.tools.mapeditor.shader.Shader2Config;
import com.souchy.randd.tools.mapeditor.shader.ShaderPart;
import com.souchy.randd.tools.mapeditor.shader.uniforms.UniformsModule;

public class DissolveUniforms implements UniformsModule {

	// ------------------- Uniform addresses
	
	private int u_dissolveTexture;
	//public final int u_diffuseColor;
	private int u_dissolveUVTransform;
	/** ex : 0.5 = half dissolved */
	private int u_dissolveIntensity;
	/** color of the border */
	private int u_dissolveBorderColor;
	/** width of the border around the dissolved area */
	private int u_dissolveBorderWidth;
	
	
	@Override
	public String prefixFlags(Renderable r, Config c) {
		String prefix = "";
		if(r.material.has(DissolveIntensityAttribute.DissolveIntensityType)) {
			prefix += "#define dissolveFlag\n";
		}
		return prefix;
	}
	
	@Override
	public String vertex() {
		return "res/shaders/components/dissolve.v.glsl";
	}
	
	@Override
	public String fragment() {
		return "res/shaders/components/dissolve.f.glsl";
	}
	
	
	@Override
	public void register(BaseShader s, Renderable r, Config config) {
		u_dissolveTexture = s.register(new Uniform("u_dissolveTexture", DissolveTextureAttribute.DissolveTextureType));
		u_dissolveUVTransform = s.register(new Uniform("u_dissolveUVTransform", DissolveTextureAttribute.DissolveTextureType));
		u_dissolveIntensity = s.register(new Uniform("u_dissolveIntensity", DissolveIntensityAttribute.DissolveIntensityType));
		u_dissolveBorderColor = s.register(new Uniform("u_dissolveBorderColor", DissolveBorderColorAttribute.DissolveBorderColorType));
		u_dissolveBorderWidth = s.register(new Uniform("u_dissolveBorderWidth", DissolveBorderWidthAttribute.DissolveBorderWidthType));
	}
	
	@Override 
	public void bind(BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
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
	}

	
	
	
	/**
	 * Dissolve material to easily add/remove all Attributes needed for the dissolve effect
	 * 
	 * @author Blank
	 * @date 1 nov. 2021
	 */
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
			if(texture == null) texture = DissolveTextureAttribute.defaultDissolveTexture;
			this.texture = new DissolveTextureAttribute(texture);
			this.intensity = new DissolveIntensityAttribute(intensity);
			this.borderColor = new DissolveBorderColorAttribute(borderColor);
			this.borderWidth = new DissolveBorderWidthAttribute(borderWidth);
			this.set(this.texture, this.intensity, this.borderColor, this.borderWidth);
		}
		public static void removeFrom(Attributes attributes) {
			attributes.remove(DissolveTextureAttribute.DissolveTextureType);
			attributes.remove(DissolveIntensityAttribute.DissolveIntensityType);
			attributes.remove(DissolveBorderColorAttribute.DissolveBorderColorType);
			attributes.remove(DissolveBorderWidthAttribute.DissolveBorderWidthType);
		}
	}

	/**
	 * texture and transform
	 */
	public static class DissolveTextureAttribute extends TextureAttribute {
		public static final Texture defaultDissolveTexture;
//		public static final TextureDescriptor<Texture> defaultDissolveTextureDesc;
		
		public static final String DissolveTextureAlias = "a_DissolveTexture";
		public static final long DissolveTextureType = register(DissolveTextureAlias);
		
		static {
			DissolveTextureAttribute.Mask |= DissolveTextureType;
			
			defaultDissolveTexture = LapisAssets.oneShot("res/textures/fx/Distortion01.png", Texture.class);
			defaultDissolveTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			defaultDissolveTexture.setAnisotropicFilter(8f);
//			defaultDissolveTextureDesc = new TextureDescriptor<Texture>();
//			defaultDissolveTextureDesc.minFilter = defaultDissolveTextureDesc.magFilter = Texture.TextureFilter.MipMapLinearNearest; //.MipMapLinearLinear;
//			defaultDissolveTextureDesc.uWrap = defaultDissolveTextureDesc.vWrap = Texture.TextureWrap.Repeat; 
//			defaultDissolveTextureDesc.texture = defaultDissolveTexture;
			
		}
		public DissolveTextureAttribute() {
			super(DissolveTextureType, defaultDissolveTexture);
		}
		public DissolveTextureAttribute(Texture value) {
			super(DissolveTextureType, value);
		}
	}
	/**
	 * Intensity
	 */
	public static class DissolveIntensityAttribute extends FloatAttribute {
		public static final String DissolveIntensityAlias = "a_DissolveIntensity";
		public static final long DissolveIntensityType = register(DissolveIntensityAlias);
		public DissolveIntensityAttribute(float value) {
			super(DissolveIntensityType, value);
		}
	}
	/**
	 * Border Color
	 */
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
	/**
	 * Border width
	 */
	public static class DissolveBorderWidthAttribute extends FloatAttribute {
		public static final String DissolveBorderWidthAlias = "a_DissolveBorderWidth";
		public static final long DissolveBorderWidthType = register(DissolveBorderWidthAlias);
		public DissolveBorderWidthAttribute(float value) {
			super(DissolveBorderWidthType, value);
		}
	}
	
	
}
