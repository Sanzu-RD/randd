package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MaterialUniforms implements UniformsModule {
	
	// Material uniforms
	public final int u_shininess;
	public final int u_opacity;
	public final int u_diffuseColor;
	public final int u_diffuseTexture;
	public final int u_diffuseUVTransform;
	public final int u_specularColor;
	public final int u_specularTexture;
	public final int u_specularUVTransform;
	public final int u_emissiveColor;
	public final int u_emissiveTexture;
	public final int u_emissiveUVTransform;
	public final int u_reflectionColor;
	public final int u_reflectionTexture;
	public final int u_reflectionUVTransform;
	public final int u_normalTexture;
	public final int u_normalUVTransform;
	public final int u_ambientTexture;
	public final int u_ambientUVTransform;
	public final int u_alphaTest;
	
	public MaterialUniforms(BaseShader s) {
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
				s.context.setBlending(true, ((BlendingAttribute)attr).sourceFunction, ((BlendingAttribute)attr).destFunction);
				s.set(u_opacity, ((BlendingAttribute)attr).opacity);
			} else if ((t & IntAttribute.CullFace) == IntAttribute.CullFace)
				cullFace = ((IntAttribute)attr).value;
			else if ((t & FloatAttribute.AlphaTest) == FloatAttribute.AlphaTest)
				s.set(u_alphaTest, ((FloatAttribute)attr).value);
			else if ((t & DepthTestAttribute.Type) == DepthTestAttribute.Type) {
				DepthTestAttribute dta = (DepthTestAttribute)attr;
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