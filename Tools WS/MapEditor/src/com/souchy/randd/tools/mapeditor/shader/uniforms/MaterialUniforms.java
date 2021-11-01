package com.souchy.randd.tools.mapeditor.shader.uniforms;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.souchy.randd.tools.mapeditor.shader.Shader2;
import com.souchy.randd.tools.mapeditor.shader.Shader2Config;
import com.souchy.randd.tools.mapeditor.shader.ShaderUtils;
import static com.souchy.randd.tools.mapeditor.shader.ShaderUtils.*;

@SuppressWarnings("unused")
public class MaterialUniforms implements UniformsModule {
	
	// Material uniforms addresses
	private int u_shininess;
	private int u_opacity;
	private int u_diffuseColor;
	private int u_diffuseTexture;
	private int u_diffuseUVTransform;
	private int u_specularColor;
	private int u_specularTexture;
	private int u_specularUVTransform;
	private int u_emissiveColor;
	private int u_emissiveTexture;
	private int u_emissiveUVTransform;
	private int u_reflectionColor;
	private int u_reflectionTexture;
	private int u_reflectionUVTransform;
	private int u_normalTexture;
	private int u_normalUVTransform;
	private int u_ambientTexture;
	private int u_ambientUVTransform;
	private int u_alphaTest;

	@Override
	public String prefixFlags(Renderable r, Config c) {
		String prefix = "";
		final Attributes attributes = ShaderUtils.combineAttributes(r);
		final long attributesMask = attributes.getMask();
		final long vertexMask = r.meshPart.mesh.getVertexAttributes().getMask();

		if ((attributesMask & BlendingAttribute.Type) == BlendingAttribute.Type)
			prefix += "#define " + BlendingAttribute.Alias + "Flag\n";
		if ((attributesMask & TextureAttribute.Diffuse) == TextureAttribute.Diffuse) {
			prefix += "#define " + TextureAttribute.DiffuseAlias + "Flag\n";
			prefix += "#define " + TextureAttribute.DiffuseAlias + "Coord texCoord0\n"; // FIXME implement UV mapping
		}
		if ((attributesMask & TextureAttribute.Specular) == TextureAttribute.Specular) {
			prefix += "#define " + TextureAttribute.SpecularAlias + "Flag\n";
			prefix += "#define " + TextureAttribute.SpecularAlias + "Coord texCoord0\n"; // FIXME implement UV mapping
		}
		if ((attributesMask & TextureAttribute.Normal) == TextureAttribute.Normal) {
			prefix += "#define " + TextureAttribute.NormalAlias + "Flag\n";
			prefix += "#define " + TextureAttribute.NormalAlias + "Coord texCoord0\n"; // FIXME implement UV mapping
		}
		if ((attributesMask & TextureAttribute.Emissive) == TextureAttribute.Emissive) {
			prefix += "#define " + TextureAttribute.EmissiveAlias + "Flag\n";
			prefix += "#define " + TextureAttribute.EmissiveAlias + "Coord texCoord0\n"; // FIXME implement UV mapping
		}
		if ((attributesMask & TextureAttribute.Reflection) == TextureAttribute.Reflection) {
			prefix += "#define " + TextureAttribute.ReflectionAlias + "Flag\n";
			prefix += "#define " + TextureAttribute.ReflectionAlias + "Coord texCoord0\n"; // FIXME implement UV mapping
		}
		if ((attributesMask & TextureAttribute.Ambient) == TextureAttribute.Ambient) {
			prefix += "#define " + TextureAttribute.AmbientAlias + "Flag\n";
			prefix += "#define " + TextureAttribute.AmbientAlias + "Coord texCoord0\n"; // FIXME implement UV mapping
		}
		if ((attributesMask & ColorAttribute.Diffuse) == ColorAttribute.Diffuse)
			prefix += "#define " + ColorAttribute.DiffuseAlias + "Flag\n";
		if ((attributesMask & ColorAttribute.Specular) == ColorAttribute.Specular)
			prefix += "#define " + ColorAttribute.SpecularAlias + "Flag\n";
		if ((attributesMask & ColorAttribute.Emissive) == ColorAttribute.Emissive)
			prefix += "#define " + ColorAttribute.EmissiveAlias + "Flag\n";
		if ((attributesMask & ColorAttribute.Reflection) == ColorAttribute.Reflection)
			prefix += "#define " + ColorAttribute.ReflectionAlias + "Flag\n";
		if ((attributesMask & FloatAttribute.Shininess) == FloatAttribute.Shininess)
			prefix += "#define " + FloatAttribute.ShininessAlias + "Flag\n";
		if ((attributesMask & FloatAttribute.AlphaTest) == FloatAttribute.AlphaTest)
			prefix += "#define " + FloatAttribute.AlphaTestAlias + "Flag\n";
		
		return prefix;
	}
	
	@Override
	public void register(BaseShader s, Renderable r, Config config) {
		u_shininess = s.register(Inputs.shininess, Setters.shininess);
		u_opacity = s.register(Inputs.opacity);
		u_diffuseColor = s.register(Inputs.diffuseColor, Setters.diffuseColor);
		u_diffuseTexture = s.register(Inputs.diffuseTexture, Setters.diffuseTexture);
		u_diffuseUVTransform = s.register(Inputs.diffuseUVTransform, Setters.diffuseUVTransform);
		u_specularColor = s.register(Inputs.specularColor, Setters.specularColor);
		u_specularTexture = s.register(Inputs.specularTexture, Setters.specularTexture);
		u_specularUVTransform = s.register(Inputs.specularUVTransform, Setters.specularUVTransform);
		u_emissiveColor = s.register(Inputs.emissiveColor, Setters.emissiveColor);
		u_emissiveTexture = s.register(Inputs.emissiveTexture, Setters.emissiveTexture);
		u_emissiveUVTransform = s.register(Inputs.emissiveUVTransform, Setters.emissiveUVTransform);
		u_reflectionColor = s.register(Inputs.reflectionColor, Setters.reflectionColor);
		u_reflectionTexture = s.register(Inputs.reflectionTexture, Setters.reflectionTexture);
		u_reflectionUVTransform = s.register(Inputs.reflectionUVTransform, Setters.reflectionUVTransform);
		u_normalTexture = s.register(Inputs.normalTexture, Setters.normalTexture);
		u_normalUVTransform = s.register(Inputs.normalUVTransform, Setters.normalUVTransform);
		u_ambientTexture = s.register(Inputs.ambientTexture, Setters.ambientTexture);
		u_ambientUVTransform = s.register(Inputs.ambientUVTransform, Setters.ambientUVTransform);
		u_alphaTest = s.register(Inputs.alphaTest);
	}
	
	@Override
	public void bind(BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
		int cullFace = config.defaultCullFace == -1 ? DefaultShader.defaultCullFace : config.defaultCullFace;
		int depthFunc = config.defaultDepthFunc == -1 ? DefaultShader.defaultDepthFunc : config.defaultDepthFunc;
		float depthRangeNear = 0f;
		float depthRangeFar = 1f;
		boolean depthMask = true;

		for (final Attribute attr : attributes) {
			final long t = attr.type;
			if (BlendingAttribute.is(t)) {
				s.context.setBlending(true, ((BlendingAttribute) attr).sourceFunction, ((BlendingAttribute) attr).destFunction);
				s.set(u_opacity, ((BlendingAttribute) attr).opacity);
			} else if ((t & IntAttribute.CullFace) == IntAttribute.CullFace)
				cullFace = ((IntAttribute) attr).value;
			else if ((t & FloatAttribute.AlphaTest) == FloatAttribute.AlphaTest)
				s.set(u_alphaTest, ((FloatAttribute) attr).value);
			else if ((t & DepthTestAttribute.Type) == DepthTestAttribute.Type) {
				DepthTestAttribute dta = (DepthTestAttribute) attr;
				depthFunc = dta.depthFunc;
				depthRangeNear = dta.depthRangeNear;
				depthRangeFar = dta.depthRangeFar;
				depthMask = dta.depthMask;
			} else if (!config.ignoreUnimplemented) throw new GdxRuntimeException("Unknown material attribute: " + attr.toString());
		}

		s.context.setCullFace(cullFace);
		s.context.setDepthTest(depthFunc, depthRangeNear, depthRangeFar);
		s.context.setDepthMask(depthMask);
	}
	
	
	
}