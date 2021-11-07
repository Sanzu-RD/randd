package com.souchy.randd.tools.mapeditor.shader;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shader2 extends BaseShader {

	/** The material attributes that this shader instance supports */
	private final long attributesMask;
	/** The vertex attributes that this shader instance supports */
	private final long vertexMask;
	private final Shader2Config config; 
	/** Attributes which are not required but always supported. */
	private final static long optionalAttributes = IntAttribute.CullFace | DepthTestAttribute.Type;
	/** 
	 * The renderable used to create this shader, invalid after the call to init 
	 */
	private Renderable renderable;
	
	/**
	 * Removed the other ctors as this should cover every case with the extended config and provider. <br>
	 * Just use a good config.
	 */
	public Shader2 (final Renderable renderable, final Shader2Config config) {
		var prefix = ShaderUtils.createPrefix(renderable, config);
		var shaderProgram = new ShaderProgram(prefix + config.vertexShader, prefix + config.fragmentShader);
		
		
		
		this.config = config;
		this.program = shaderProgram;
		this.renderable = renderable;
		// material/environment attributes mask
		final Attributes attributes = ShaderUtils.combineAttributes(renderable);
		attributesMask = attributes.getMask() | optionalAttributes;
		// vertex attributes mask
		vertexMask = renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked();
		
		// register modules
		for(var module : config.modules) 
			module.register(this, renderable, config);
	}

	@Override
	public void init() {
		final ShaderProgram program = this.program; 
		this.program = null;
		init(program, renderable);
		renderable = null;
		
		for(var module : config.modules) 
			module.init(this);
	}
	
	@Override
	public void begin(Camera camera, RenderContext context) {
		super.begin(camera, context);
		for(var module : config.modules) 
			module.begin(this);
	}
	
	@Override
	public void render (Renderable renderable, Attributes combinedAttributes) {
		if (!combinedAttributes.has(BlendingAttribute.Type))
			context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		for(var module : config.modules) 
			module.bind(this, renderable, config, combinedAttributes);
		super.render(renderable, combinedAttributes);
	}
	
	@Override
	public int compareTo(Shader other) {
		if (other == null) return -1;
		if (other == this) return 0;
		return 0; // FIXME compare shaders on their impact on performance
	}

	@Override
	public boolean canRender (final Renderable renderable) {
		if (renderable.bones != null && renderable.bones.length > config.numBones) return false;
		final long renderableMask = ShaderUtils.combineAttributeMasks(renderable) | optionalAttributes;
		final long renderableVertMask = renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked();
		
		boolean canRender = true;
		canRender &= (attributesMask == renderableMask);
		canRender &= (vertexMask == renderableVertMask);
		//canRender &= ((attributesMask & renderableMask) == renderableMask);
		//canRender &= ((vertexMask & renderableVertMask) == renderableVertMask);
		//&& (renderable.environment != null) == lighting // put this in LightingUniforms.canRender()
		for(var m : config.modules)
			canRender &= m.canRender(renderable);
		
		return canRender;
	}
	
	
	
}
