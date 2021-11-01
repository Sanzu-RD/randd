package com.souchy.randd.tools.mapeditor.shader.uniforms;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.souchy.randd.tools.mapeditor.shader.Shader2;
import com.souchy.randd.tools.mapeditor.shader.Shader2Config;
import com.souchy.randd.tools.mapeditor.shader.ShaderUtils;
import static com.souchy.randd.tools.mapeditor.shader.ShaderUtils.*;

public class LightingUniforms implements UniformsModule {
	
	// Lighting uniforms addresses are final
	protected int u_ambientCubemap;
	protected int u_environmentCubemap;
	protected int u_dirLights0color;
	protected int u_dirLights0direction;
	protected int u_dirLights1color;
	protected int u_pointLights0color;
	protected int u_pointLights0position;
	protected int u_pointLights0intensity;
	protected int u_pointLights1color;
	protected int u_spotLights0color;
	protected int u_spotLights0position;
	protected int u_spotLights0intensity;
	protected int u_spotLights0direction;
	protected int u_spotLights0cutoffAngle;
	protected int u_spotLights0exponent;
	protected int u_spotLights1color;
	protected int u_fogColor;
	protected int u_shadowMapProjViewTrans;
	protected int u_shadowTexture;
	protected int u_shadowPCFOffset;
	// FIXME Cache vertex attribute locations...
	
	
	// 
	protected int dirLightsLoc;
	protected int dirLightsColorOffset;
	protected int dirLightsDirectionOffset;
	protected int dirLightsSize;
	protected int pointLightsLoc;
	protected int pointLightsColorOffset;
	protected int pointLightsPositionOffset;
	protected int pointLightsIntensityOffset;
	protected int pointLightsSize;
	protected int spotLightsLoc;
	protected int spotLightsColorOffset;
	protected int spotLightsPositionOffset;
	protected int spotLightsDirectionOffset;
	protected int spotLightsIntensityOffset;
	protected int spotLightsCutoffAngleOffset;
	protected int spotLightsExponentOffset;
	protected int spotLightsSize;

	// these used to be final
	protected boolean lighting;
	protected boolean environmentCubemap;
	protected boolean shadowMap;
	protected AmbientCubemap ambientCubemap = new AmbientCubemap();
	protected DirectionalLight directionalLights[];
	protected PointLight pointLights[];
	protected SpotLight spotLights[];

	//
	private boolean lightsSet;

	@Override
	public String prefixFlags(Renderable renderable, Config config) {
		String prefix = "";
		final Attributes attributes = combineAttributes(renderable);
		//final long attributesMask = attributes.getMask();
		final long vertexMask = renderable.meshPart.mesh.getVertexAttributes().getMask();
		
		if (and(vertexMask, Usage.Normal) || and(vertexMask, Usage.Tangent | Usage.BiNormal)) {
			if (renderable.environment != null) {
				prefix += "#define lightingFlag\n";
				prefix += "#define ambientCubemapFlag\n";
				prefix += "#define numDirectionalLights " + config.numDirectionalLights + "\n";
				prefix += "#define numPointLights " + config.numPointLights + "\n";
				prefix += "#define numSpotLights " + config.numSpotLights + "\n";
				if (attributes.has(ColorAttribute.Fog)) {
					prefix += "#define fogFlag\n";
				}
				if (renderable.environment.shadowMap != null) prefix += "#define shadowMapFlag\n";
				if (attributes.has(CubemapAttribute.EnvironmentMap)) prefix += "#define environmentCubemapFlag\n";
			}
		}
		return prefix;
	}
	
	@Override
	public boolean canRender(Renderable r) {
		return (r.environment != null) == lighting;
	}
	
	@Override
	public void register(BaseShader s, Renderable r, Config config) {
		final Attributes attributes = combineAttributes(r);
		boolean hasCube = attributes.has(CubemapAttribute.EnvironmentMap); // ShaderUtils.hasAttribute(s.attributesMask, CubemapAttribute.EnvironmentMap);
		
		lighting = r.environment != null;
		environmentCubemap = 
				hasCube // attributes.has(CubemapAttribute.EnvironmentMap)
				|| (lighting && hasCube //attributes.has(CubemapAttribute.EnvironmentMap)
					);
		shadowMap = lighting && r.environment.shadowMap != null;
		
		
		u_dirLights0color = s.register(new Uniform("u_dirLights[0].color"));
		u_dirLights0direction = s.register(new Uniform("u_dirLights[0].direction"));
		u_dirLights1color = s.register(new Uniform("u_dirLights[1].color"));
		u_pointLights0color = s.register(new Uniform("u_pointLights[0].color"));
		u_pointLights0position = s.register(new Uniform("u_pointLights[0].position"));
		u_pointLights0intensity = s.register(new Uniform("u_pointLights[0].intensity"));
		u_pointLights1color = s.register(new Uniform("u_pointLights[1].color"));
		u_spotLights0color = s.register(new Uniform("u_spotLights[0].color"));
		u_spotLights0position = s.register(new Uniform("u_spotLights[0].position"));
		u_spotLights0intensity = s.register(new Uniform("u_spotLights[0].intensity"));
		u_spotLights0direction = s.register(new Uniform("u_spotLights[0].direction"));
		u_spotLights0cutoffAngle = s.register(new Uniform("u_spotLights[0].cutoffAngle"));
		u_spotLights0exponent = s.register(new Uniform("u_spotLights[0].exponent"));
		u_spotLights1color = s.register(new Uniform("u_spotLights[1].color"));
		u_fogColor = s.register(new Uniform("u_fogColor"));
		u_shadowMapProjViewTrans = s.register(new Uniform("u_shadowMapProjViewTrans"));
		u_shadowTexture = s.register(new Uniform("u_shadowTexture"));
		u_shadowPCFOffset = s.register(new Uniform("u_shadowPCFOffset"));
		
		u_ambientCubemap = lighting ? s.register(Inputs.ambientCube, new Setters.ACubemap(config.numDirectionalLights,
			config.numPointLights)) : -1;
		u_environmentCubemap = environmentCubemap ? s.register(Inputs.environmentCubemap, Setters.environmentCubemap) : -1;

		
		this.directionalLights = new DirectionalLight[lighting && config.numDirectionalLights > 0 ? config.numDirectionalLights : 0];
		for (int i = 0; i < directionalLights.length; i++)
			directionalLights[i] = new DirectionalLight();
		this.pointLights = new PointLight[lighting && config.numPointLights > 0 ? config.numPointLights : 0];
		for (int i = 0; i < pointLights.length; i++)
			pointLights[i] = new PointLight();
		this.spotLights = new SpotLight[lighting && config.numSpotLights > 0 ? config.numSpotLights : 0];
		for (int i = 0; i < spotLights.length; i++)
			spotLights[i] = new SpotLight();
	}
	
	@Override
	public void init(BaseShader s) {
		dirLightsLoc = s.loc(u_dirLights0color);
		dirLightsColorOffset = s.loc(u_dirLights0color) - dirLightsLoc;
		dirLightsDirectionOffset = s.loc(u_dirLights0direction) - dirLightsLoc;
		dirLightsSize = s.loc(u_dirLights1color) - dirLightsLoc;
		if (dirLightsSize < 0) dirLightsSize = 0;

		pointLightsLoc = s.loc(u_pointLights0color);
		pointLightsColorOffset = s.loc(u_pointLights0color) - pointLightsLoc;
		pointLightsPositionOffset = s.loc(u_pointLights0position) - pointLightsLoc;
		pointLightsIntensityOffset = s.has(u_pointLights0intensity) ? s.loc(u_pointLights0intensity) - pointLightsLoc : -1;
		pointLightsSize = s.loc(u_pointLights1color) - pointLightsLoc;
		if (pointLightsSize < 0) pointLightsSize = 0;

		spotLightsLoc = s.loc(u_spotLights0color);
		spotLightsColorOffset = s.loc(u_spotLights0color) - spotLightsLoc;
		spotLightsPositionOffset = s.loc(u_spotLights0position) - spotLightsLoc;
		spotLightsDirectionOffset = s.loc(u_spotLights0direction) - spotLightsLoc;
		spotLightsIntensityOffset = s.has(u_spotLights0intensity) ? s.loc(u_spotLights0intensity) - spotLightsLoc : -1;
		spotLightsCutoffAngleOffset = s.loc(u_spotLights0cutoffAngle) - spotLightsLoc;
		spotLightsExponentOffset = s.loc(u_spotLights0exponent) - spotLightsLoc;
		spotLightsSize = s.loc(u_spotLights1color) - spotLightsLoc;
		if (spotLightsSize < 0) spotLightsSize = 0;
	}

	@Override
	public void begin(BaseShader s) {
		for (final DirectionalLight dirLight : directionalLights)
			dirLight.set(0, 0, 0, 0, -1, 0);
		for (final PointLight pointLight : pointLights)
			pointLight.set(0, 0, 0, 0, 0, 0, 0);
		for (final SpotLight spotLight : spotLights)
			spotLight.set(0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, 0);
		lightsSet = false;
	}

	@Override
	public void bind (BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
		final Environment lights = renderable.environment;
		final DirectionalLightsAttribute dla = attributes.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
		final Array<DirectionalLight> dirs = dla == null ? null : dla.lights;
		final PointLightsAttribute pla = attributes.get(PointLightsAttribute.class, PointLightsAttribute.Type);
		final Array<PointLight> points = pla == null ? null : pla.lights;
		final SpotLightsAttribute sla = attributes.get(SpotLightsAttribute.class, SpotLightsAttribute.Type);
		final Array<SpotLight> spots = sla == null ? null : sla.lights;

		if (dirLightsLoc >= 0) {
			for (int i = 0; i < directionalLights.length; i++) {
				if (dirs == null || i >= dirs.size) {
					if (lightsSet && directionalLights[i].color.r == 0f && directionalLights[i].color.g == 0f
						&& directionalLights[i].color.b == 0f) continue;
					directionalLights[i].color.set(0, 0, 0, 1);
				} else if (lightsSet && directionalLights[i].equals(dirs.get(i)))
					continue;
				else
					directionalLights[i].set(dirs.get(i));

				int idx = dirLightsLoc + i * dirLightsSize;
				s.program.setUniformf(idx + dirLightsColorOffset, directionalLights[i].color.r, directionalLights[i].color.g,
					directionalLights[i].color.b);
				s.program.setUniformf(idx + dirLightsDirectionOffset, directionalLights[i].direction.x,
					directionalLights[i].direction.y, directionalLights[i].direction.z);
				if (dirLightsSize <= 0) break;
			}
		}

		if (pointLightsLoc >= 0) {
			for (int i = 0; i < pointLights.length; i++) {
				if (points == null || i >= points.size) {
					if (lightsSet && pointLights[i].intensity == 0f) continue;
					pointLights[i].intensity = 0f;
				} else if (lightsSet && pointLights[i].equals(points.get(i)))
					continue;
				else
					pointLights[i].set(points.get(i));

				int idx = pointLightsLoc + i * pointLightsSize;
				s.program.setUniformf(idx + pointLightsColorOffset, pointLights[i].color.r * pointLights[i].intensity,
					pointLights[i].color.g * pointLights[i].intensity, pointLights[i].color.b * pointLights[i].intensity);
				s.program.setUniformf(idx + pointLightsPositionOffset, pointLights[i].position.x, pointLights[i].position.y,
					pointLights[i].position.z);
				if (pointLightsIntensityOffset >= 0) s.program.setUniformf(idx + pointLightsIntensityOffset, pointLights[i].intensity);
				if (pointLightsSize <= 0) break;
			}
		}

		if (spotLightsLoc >= 0) {
			for (int i = 0; i < spotLights.length; i++) {
				if (spots == null || i >= spots.size) {
					if (lightsSet && spotLights[i].intensity == 0f) continue;
					spotLights[i].intensity = 0f;
				} else if (lightsSet && spotLights[i].equals(spots.get(i)))
					continue;
				else
					spotLights[i].set(spots.get(i));

				int idx = spotLightsLoc + i * spotLightsSize;
				s.program.setUniformf(idx + spotLightsColorOffset, spotLights[i].color.r * spotLights[i].intensity,
					spotLights[i].color.g * spotLights[i].intensity, spotLights[i].color.b * spotLights[i].intensity);
				s.program.setUniformf(idx + spotLightsPositionOffset, spotLights[i].position);
				s.program.setUniformf(idx + spotLightsDirectionOffset, spotLights[i].direction);
				s.program.setUniformf(idx + spotLightsCutoffAngleOffset, spotLights[i].cutoffAngle);
				s.program.setUniformf(idx + spotLightsExponentOffset, spotLights[i].exponent);
				if (spotLightsIntensityOffset >= 0)
					s.program.setUniformf(idx + spotLightsIntensityOffset, spotLights[i].intensity);
				if (spotLightsSize <= 0) break;
			}
		}

		if (attributes.has(ColorAttribute.Fog)) {
			s.set(u_fogColor, ((ColorAttribute)attributes.get(ColorAttribute.Fog)).color);
		}

		if (lights != null && lights.shadowMap != null) {
			s.set(u_shadowMapProjViewTrans, lights.shadowMap.getProjViewTrans());
			s.set(u_shadowTexture, lights.shadowMap.getDepthMap());
			s.set(u_shadowPCFOffset, 1.f / (2f * lights.shadowMap.getDepthMap().texture.getWidth()));
		}

		lightsSet = true;
	}

	
	
}