package com.souchy.randd.tools.mapeditor.shaderimpl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
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
		if(r.material.has(DissolveAttributes.DissolveIntensityType)) {
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
		u_dissolveTexture = s.register(new Uniform("u_dissolveTexture", DissolveAttributes.DissolveTextureType));
		u_dissolveUVTransform = s.register(new Uniform("u_dissolveUVTransform", DissolveAttributes.DissolveTextureType));
		u_dissolveIntensity = s.register(new Uniform("u_dissolveIntensity", DissolveAttributes.DissolveIntensityType));
		u_dissolveBorderColor = s.register(new Uniform("u_dissolveBorderColor", DissolveAttributes.DissolveBorderColorType));
		u_dissolveBorderWidth = s.register(new Uniform("u_dissolveBorderWidth", DissolveAttributes.DissolveBorderWidthType));
	}
	
	@Override 
	public void bind(BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
		if(attributes.has(DissolveAttributes.DissolveTextureType)) {
			var attr = (TextureAttribute) attributes.get(DissolveAttributes.DissolveTextureType);
//			attr.scaleV = 0.5f;
			//attr.offsetU = new Random().nextFloat();
			s.set(u_dissolveTexture, attr.textureDescription);
			s.set(u_dissolveUVTransform, attr.offsetU, attr.offsetV, attr.scaleU, attr.scaleV);
			//Log.info("Set dissolve texture " + attr.color);
		}
		if(attributes.has(DissolveAttributes.DissolveIntensityType)) {
			var attr = (FloatAttribute) attributes.get(DissolveAttributes.DissolveIntensityType);
			s.set(u_dissolveIntensity, attr.value);
			//Log.info("Set dissolve intensity " + attr.value + ", " + renderable.userData + ", " + renderable.hashCode());
		}
		if(attributes.has(DissolveAttributes.DissolveBorderColorType)) {
			var attr = (ColorAttribute) attributes.get(DissolveAttributes.DissolveBorderColorType);
			s.set(u_dissolveBorderColor, attr.color);
			//Log.info("Set dissolve color " + attr.color);
		}

		if(attributes.has(DissolveAttributes.DissolveBorderWidthType)) {
			var attr = (FloatAttribute) attributes.get(DissolveAttributes.DissolveBorderWidthType);
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
		public DissolveMaterial() {
			this(null, Color.BLUE, 0.03f, 0.3f);
		}
		public DissolveMaterial(Color borderColor, float borderWidth, float intensity) {
			this(null, borderColor, borderWidth, intensity);
		}
		public DissolveMaterial(Texture texture, Color borderColor, float borderWidth, float intensity) {
			this.set(DissolveAttributes.newBorderWidth(borderWidth), DissolveAttributes.newBorderColor(borderColor), 
					DissolveAttributes.newIntensity(intensity), DissolveAttributes.newTexture(texture));
		}
		public static void removeFrom(Attributes attributes) {
			attributes.remove(DissolveAttributes.DissolveTextureType);
			attributes.remove(DissolveAttributes.DissolveIntensityType);
			attributes.remove(DissolveAttributes.DissolveBorderColorType);
			attributes.remove(DissolveAttributes.DissolveBorderWidthType);
		}
	}
	

	public static abstract class DissolveAttributes extends Attribute {
		private DissolveAttributes(long type) {
			super(type);
		}
		public static final Texture defaultDissolveTexture;
		public static final String DissolveBorderWidthAlias = "a_DissolveBorderWidth";
		public static final long DissolveBorderWidthType = register(DissolveBorderWidthAlias);
		public static final String DissolveBorderColorAlias = "a_DissolveBorderColor";
		public static final long DissolveBorderColorType = register(DissolveBorderColorAlias);
		public static final String DissolveIntensityAlias = "a_DissolveIntensity";
		public static final long DissolveIntensityType = register(DissolveIntensityAlias);
		public static final String DissolveTextureAlias = "a_DissolveTexture";
		public static final long DissolveTextureType = register(DissolveTextureAlias);

		static {
			TextureAttribute.registerMask(DissolveTextureType);
			ColorAttribute.registerMask(DissolveBorderColorType);
			defaultDissolveTexture = LapisAssets.oneShot("res/textures/fx/Distortion01.png", Texture.class);
			defaultDissolveTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			defaultDissolveTexture.setAnisotropicFilter(8f);
		}
		/** just to trigger static initialization */
		public static void register() { }
		public static FloatAttribute newIntensity(float val) {
			return new FloatAttribute(DissolveIntensityType, val);
		}
		public static TextureAttribute newTexture(Texture val) {
			if(val == null) val = defaultDissolveTexture;
			return new TextureAttribute(DissolveTextureType, val);
		}
		public static FloatAttribute newBorderWidth(float val) {
			return new FloatAttribute(DissolveBorderWidthType, val);
		}
		public static ColorAttribute newBorderColor(Color val) {
			return new ColorAttribute(DissolveBorderColorType, val);
		}
	}
	
	
}
