package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shaders.LapisShader;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.ExtendableShader.ShadeProvider;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveAttributes;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.GlobalUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.LightingUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.MaterialUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.NodeUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.UniformsModule;

public class ExtendableShader extends BaseShader {
	/**
	 * 
	 * 
	 * @author Blank
	 * @date 1 nov. 2021
	 */
	public static class ShadeProvider extends BaseShaderProvider {
		private Function<Renderable, Shader> builder;
		public ShadeProvider(Function<Renderable, Shader> builder) {
			this.builder = builder;
		}
		public void reset() {
			this.shaders.clear();
		}
		@Override
		protected Shader createShader(Renderable renderable) {
			return builder.apply(renderable); // new ExtendableShader(renderable);
		}
		@Override
		public Shader getShader(Renderable renderable) {
			//Log.info("provider getShader");
			return super.getShader(renderable);
		}
	}
	
	/**
	 * Example : 
	 * 
	 * new ModelBatch(provider = new ShadeProvider(r -> {
			var conf = new ExtendableShader.ExtendableConfig();
			conf.add(new DissolveUniforms());
			conf.compile("base");
			return new ExtendableShader(r, conf);
		}));
	 * 
	 * @author Blank
	 * @date 1 nov. 2021
	 */
	public static class ExtendableConfig extends Config {
		List<UniformsModule> modules = new ArrayList<>();
		public void add(UniformsModule module) {
			modules.add(module);
		}
		public void compile(String shaderName) {
			var modulesVerts = modules.stream().map(m -> m.vertex()).toArray(String[]::new); //.toArray(new String[0]);
			var modulesFrags = modules.stream().map(m -> m.fragment()).toArray(String[]::new);
			this.vertexShader = LapisShader.getVertexShader(shaderName, modulesVerts);
			this.fragmentShader = LapisShader.getFragmentShader(shaderName, modulesFrags);
		}
	}
	
//	protected static long implementedFlags = BlendingAttribute.Type | TextureAttribute.Diffuse | ColorAttribute.Diffuse
//		| ColorAttribute.Specular | FloatAttribute.Shininess;
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
//	public GlobalUniforms uglobal;
//	public NodeUniforms uobject;
//	public LightingUniforms ulighting;
//	public MaterialUniforms umaterial;
	// --------------
	/** The renderable used to create this shader, invalid after the call to init */
	private Renderable renderable;
	// --------------
	/** Ctor */
//	public ExtendableShader (final Renderable renderable) {
//		this(renderable, new Config());
//	}
	public ExtendableShader (final Renderable renderable, final Config config) {
		this(renderable, config, DefaultShader.createPrefix(renderable, config) + createPrefix(renderable, config));
	}
	public ExtendableShader (final Renderable renderable, final Config config, final String prefix) {
		this(renderable, config, prefix, 
				config.vertexShader != null ? config.vertexShader : DefaultShader.getDefaultVertexShader(),
				config.fragmentShader != null ? config.fragmentShader : DefaultShader.getDefaultFragmentShader()
			);
	}
	public ExtendableShader (final Renderable renderable, final Config config, final String prefix, final String vertexShader,
		final String fragmentShader) {
		this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
	}
	public ExtendableShader(Renderable renderable, Config config, final ShaderProgram shaderProgram) {
		this.config = config;
		this.program = shaderProgram;
		this.renderable = renderable;
		// material/environment attributes mask
		final Attributes attributes = combineAttributes(renderable);
		attributesMask = attributes.getMask() | optionalAttributes;
		// vertex attributes mask
		vertexMask = renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked();
		
		
		//if(global()) 
			modules.add(/* uglobal = */ new GlobalUniforms(this));
		//if(object()) 
			modules.add(/* uobject = */ new NodeUniforms(this, renderable, config));
		//if(material()) 
			modules.add(/* umaterial = */ new MaterialUniforms(this));
		//if(lighting()) 
			modules.add(/* ulighting = */ new LightingUniforms(this, renderable, config, attributes));
		
		if(config instanceof ExtendableConfig ext) 
			modules.addAll(ext.modules);

		for(var module : modules) module.register(this);
	}
	// --------------
	protected boolean global() {
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
	// -------------
	/** add a module and return this */
//	public ExtendableShader add(UniformsModule module) {
//		modules.add(module);
//		program = new ShaderProgram(
//			module.vertex() + this.program.getVertexShaderSource(), 
//			module.fragment() + this.program.getFragmentShaderSource()
//		);
//		//Log.info("Extendable shader add vertex : " + module.vertex());
//		//Log.info("Extendable shader add fragment : " + module.fragment());
//		module.register(this);
//		return this;
//	}
	// --------------
	@Override
	public void init() {
		final ShaderProgram program = this.program; 
		this.program = null;
		//Log.info("modules : " + modules.stream().map(m -> m.getClass().getSimpleName()).reduce((a, b) -> a + ", " + b));
		//Log.info("Fragment : " + program.getFragmentShaderSource());
//		Log.info("Vertex : " + program.getVertexShaderSource());
		init(program, renderable);
		renderable = null;
		
		//var diss = (DissolveUniforms) modules.get(modules.size()-1);
//		Log.info("ExtendableShader dissolvecolor alias " + this.getUniformAlias(diss.u_dissolveIntensity)); 

		//Log.info("ExShader has " + this.has(diss.u_dissolveIntensity));
		//Log.info("ExShader has " + this.has((int) DissolveUniforms.DissolveIntensityAttribute.DissolveIntensityType));
		//Log.info("ExShader loc " + this.program.fetchUniformLocation("u_dissolveIntensity", false));
		
		
		//if(lighting()) ulighting.init(this);
		for(var module : modules) module.init(this);
	}
	@Override
	public void init(ShaderProgram program, Renderable renderable) {
		// TODO Auto-generated method stub
		super.init(program, renderable);
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
	public int compareTo(Shader other) {
		if (other == null) return -1;
		if (other == this) return 0;
		return 0; // FIXME compare shaders on their impact on performance
	}
	
	@Override
	public boolean canRender(Renderable renderable) {
		if (renderable.bones != null && renderable.bones.length > config.numBones) return false;
		final long renderableMask = combineAttributeMasks(renderable);
		
		// test check the renderable's mask vs this shader's mask
		{
			//Log.info("canRender " + renderable);
			var rhas = (renderableMask & DissolveAttributes.DissolveIntensityType) > 0;
			var shas = (attributesMask & DissolveAttributes.DissolveIntensityType) > 0;
			if(rhas) {
				//Log.info("canRender has dissolve intensity " + renderable.hashCode());
			} else {
				//Log.info("canRender has not dissolve intensity " + renderable.hashCode());
			}
			if(shas) {
				//Log.info("shader has dissolve intensity " + hashCode());
			} else {
				//Log.info("shader has not dissolve intensity " + hashCode());
			}
			if(rhas && shas) {
				//Log.info("both have dissolve intensity " + renderable.hashCode());
			} else 
			if (rhas && !shas) {
				//Log.info("only renderable has dissolve intensity" + hashCode() + ", " + this.modules.stream().anyMatch(m -> m instanceof DissolveUniforms));
			}
		}
		
		return (attributesMask == (renderableMask | optionalAttributes))
			&& (vertexMask == renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked()) 
			&& (renderable.environment != null) == lighting();
	}

//	@Override
//	public boolean canRender (final Renderable renderable) {
//		if (renderable.bones != null && renderable.bones.length > config.numBones) return false;
//		final long renderableMask = combineAttributeMasks(renderable);
//		return (attributesMask == (renderableMask | optionalAttributes))
//			&& (vertexMask == renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked()) 
//			&& (renderable.environment != null) == lighting;
//	}

	
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
	
	
	private static final String createPrefix(Renderable r, Config c) {
		String str = "";
		if(r.material.has(DissolveAttributes.DissolveIntensityType)) {
			str += "#define dissolveFlag\n";
			Log.warning("Define dissolve flag prefix ! ");
		}
		return str;
	}
	
}