package com.souchy.randd.tools.mapeditor.shader.uniforms;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters;
import com.souchy.randd.tools.mapeditor.shader.Shader2;
import com.souchy.randd.tools.mapeditor.shader.Shader2Config;
import static com.souchy.randd.tools.mapeditor.shader.ShaderUtils.*;

/**
 * 
 * 
 * @author Blank
 * @date 1 nov. 2021
 */
@SuppressWarnings("unused")
public class NodeUniforms implements UniformsModule {
	
	// Node uniforms addresses
	private int u_worldTrans; // renderable.worldTransform
	private int u_viewWorldTrans; // camera.view * renderable.worldTransform
	private int u_projViewWorldTrans; // camera.combined * renderable.worldTransform
	private int u_normalMatrix; // (renderable.worldTransform).inv().transpose()
	private int u_bones;

	@Override
	public String prefixFlags(Renderable renderable, Config config) {
		String prefix = "";
		//final Attributes attributes = ShaderUtils.combineAttributes(renderable);
		//final long attributesMask = attributes.getMask();
		final long vertexMask = renderable.meshPart.mesh.getVertexAttributes().getMask();

		if (and(vertexMask, Usage.Position)) prefix += "#define positionFlag\n";
		if (or(vertexMask, Usage.ColorUnpacked | Usage.ColorPacked)) prefix += "#define colorFlag\n";
		if (and(vertexMask, Usage.BiNormal)) prefix += "#define binormalFlag\n";
		if (and(vertexMask, Usage.Tangent)) prefix += "#define tangentFlag\n";
		if (and(vertexMask, Usage.Normal)) prefix += "#define normalFlag\n";
		
		final int n = renderable.meshPart.mesh.getVertexAttributes().size();
		for (int i = 0; i < n; i++) {
			final VertexAttribute attr = renderable.meshPart.mesh.getVertexAttributes().get(i);
			if (attr.usage == Usage.BoneWeight)
				prefix += "#define boneWeight" + attr.unit + "Flag\n";
			else 
			if (attr.usage == Usage.TextureCoordinates) 
				prefix += "#define texCoord" + attr.unit + "Flag\n";
		}
		
		if (renderable.bones != null && config.numBones > 0) 
			prefix += "#define numBones " + config.numBones + "\n";
		return prefix;
	}
	
	
	@Override
	public void register(BaseShader s, Renderable r, Config config) {
		// Object uniforms
		u_worldTrans = s.register(Inputs.worldTrans, Setters.worldTrans);
		u_viewWorldTrans = s.register(Inputs.viewWorldTrans, Setters.viewWorldTrans);
		u_projViewWorldTrans = s.register(Inputs.projViewWorldTrans, Setters.projViewWorldTrans);
		u_normalMatrix = s.register(Inputs.normalMatrix, Setters.normalMatrix);
		u_bones = -1;
		if(r.bones != null && config.numBones > 0)
			s.register(Inputs.bones, new Setters.Bones(config.numBones));
	}
	
}