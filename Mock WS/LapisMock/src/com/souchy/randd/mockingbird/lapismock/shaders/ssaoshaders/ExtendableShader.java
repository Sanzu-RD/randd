package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import java.util.ArrayList;
import java.util.List;

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
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.GlobalUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.LightingUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.MaterialUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.ObjectUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.UniformsModule;

public class ExtendableShader extends BaseShader {
	// --------------
	/** The attributes that this shader supports */
	protected final long attributesMask;
	private final long vertexMask;
	protected final Config config;
	/** Attributes which are not required but always supported. */
	private final static long optionalAttributes = IntAttribute.CullFace | DepthTestAttribute.Type;
	// -------------- hey modular system
	public List<UniformsModule> modules = new ArrayList<>();
	// -------------- technically we dont need those anymore
	public GlobalUniforms uglobal;
	public ObjectUniforms uobject;
	public LightingUniforms ulighting;
	public MaterialUniforms umaterial;
	// --------------
	/** The renderable used to create this shader, invalid after the call to init */
	private Renderable renderable;
	// --------------
	/** Ctor */
	public ExtendableShader (final Renderable renderable) {
		this(renderable, new Config());
	}
	public ExtendableShader (final Renderable renderable, final Config config) {
		this(renderable, config, DefaultShader.createPrefix(renderable, config));
	}
	public ExtendableShader (final Renderable renderable, final Config config, final String prefix) {
		this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : DefaultShader.getDefaultVertexShader(),
			config.fragmentShader != null ? config.fragmentShader : DefaultShader.getDefaultFragmentShader());
	}
	public ExtendableShader (final Renderable renderable, final Config config, final String prefix, final String vertexShader,
		final String fragmentShader) {
		this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
	}
	public ExtendableShader(Renderable renderable, Config config, final ShaderProgram shaderProgram) {
		final Attributes attributes = combineAttributes(renderable);
		this.config = config;
		this.program = shaderProgram;
		
		this.renderable = renderable;
		attributesMask = attributes.getMask() | optionalAttributes;
		vertexMask = renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked();
		
		if(global()) modules.add(uglobal = new GlobalUniforms(this));
		if(object()) modules.add(uobject = new ObjectUniforms(this, renderable, config));
		if(material()) modules.add(umaterial = new MaterialUniforms(this));
		if(lighting()) modules.add(ulighting = new LightingUniforms(this, renderable, config, attributes));
	}
	// --------------
	private boolean global() {
		return true;
	}
	protected boolean object() {
		return true;
	}
	protected boolean material() {
		return true;
	}
	protected boolean lighting() {
		return true;
	}
	// --------------
	@Override
	public void init() {
		final ShaderProgram program = this.program; 
		this.program = null;
		init(program, renderable);
		renderable = null;
		
		//if(lighting()) ulighting.init(this);
		for(var module : modules) module.init(this);
	}
	
	@Override
	public void begin(Camera camera, RenderContext context) {
		super.begin(camera, context);
		//if(lighting()) ulighting.begin(this);
		//if(global()) uglobal.begin(this);
		for(var module : modules) module.begin(this);
	}
	
	@Override
	public void render (Renderable renderable, Attributes combinedAttributes) {
		if (!combinedAttributes.has(BlendingAttribute.Type))
			context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//if(material()) umaterial.bind(this, renderable, config, combinedAttributes);
		//if(lighting()) ulighting.bind(this, renderable, config, combinedAttributes);
		for(var module : modules) module.bind(this, renderable, config, combinedAttributes);
		super.render(renderable, combinedAttributes);
	}
	
	@Override
	public void end() {
		super.end();
	}

	@Override
	public void dispose () {
		program.dispose();
		super.dispose();
	}

	@Override
	public int compareTo(Shader other) {
		if (other == null) return -1;
		if (other == this) return 0;
		return 0; // FIXME compare shaders on their impact on performance
	}
	
	@Override
	public boolean canRender(Renderable instance) {
		final long renderableMask = combineAttributeMasks(renderable);
		return (attributesMask == (renderableMask | optionalAttributes))
			&& (vertexMask == renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked()) 
			&& (renderable.environment != null) == lighting();
	}

	private final static Attributes tmpAttributes = new Attributes();
	// TODO: Perhaps move responsibility for combining attributes to RenderableProvider?
	private static final Attributes combineAttributes (final Renderable renderable) {
		tmpAttributes.clear();
		if (renderable.environment != null) tmpAttributes.set(renderable.environment);
		if (renderable.material != null) tmpAttributes.set(renderable.material);
		return tmpAttributes;
	}
	private static final long combineAttributeMasks (final Renderable renderable) {
		long mask = 0;
		if (renderable.environment != null) mask |= renderable.environment.getMask();
		if (renderable.material != null) mask |= renderable.material.getMask();
		return mask;
	}
	
	
}