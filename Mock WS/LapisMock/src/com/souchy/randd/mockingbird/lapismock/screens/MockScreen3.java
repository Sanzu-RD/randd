package com.souchy.randd.mockingbird.lapismock.screens;

import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.utils.*;
import com.bitfire.utils.ShaderLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.ModelInstanceParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Angular;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.PolarAcceleration;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.FaceDirection;
import com.badlogic.gdx.graphics.g3d.particles.influencers.Influencer;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.parser.impl.DefaultLmlParser;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.util.LmlUtilities;
import com.github.czyzby.lml.vis.util.VisLml;
import com.google.common.collect.Lists;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.mockingbird.lapismock.BaseScreen;
import com.souchy.randd.mockingbird.lapismock.CustomGreedyMesh;
import com.souchy.randd.mockingbird.lapismock.GreedyMeshMaker;
import com.souchy.randd.mockingbird.lapismock.MockCore;
import com.souchy.randd.mockingbird.lapismock.MockCore.MockGame;
import com.souchy.randd.mockingbird.lapismock.World;
import com.souchy.randd.mockingbird.lapismock.lwjgl1.LShader;
import com.souchy.randd.mockingbird.lapismock.shaders.PostProcessingFBO;

@SuppressWarnings("deprecation")
public class MockScreen3 extends BaseScreen {
	
	DirectionalShadowLight shadowLight;
	ModelBatch shadowBatch;
	static final boolean activateShadows = true;
	
	PostProcessingFBO postProcessor;
	LineDrawing lining;
	
	// highlights
	ModelInstance cellHighlighterInst;
	ModelInstance cellHighlighterInst2;
	
	// characters and animations
	AnimationController spiderController;
	List<ModelInstance> characters = new ArrayList<>();
	
	// ParticleSystem is a singleton class, we get the instance instead of creating
	// a new object:
	static ParticleSystem particleSystem = ParticleSystem.get();
	static ParticleEffect laserFX;
	static ParticleEffect kunaiFX;
	static Matrix4 matrix = new Matrix4();
	static Matrix4 kunaiTransform = new Matrix4();
	
	public MockScreen3() {
		super();
		String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
		String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();
		modelBatch = new ModelBatch(vert, frag);
		
		// FBO
		postProcessor = new PostProcessingFBO(cam);
		Gdx.gl.glEnable(GL20.GL_LINEAR);
		
		// GRID
		lining = new LineDrawing(cam, null);
		lining.createGrid(1, (int) worldBB.getWidth(), (int) worldBB.getHeight(), true);
		
		// HIGHTLIGHT MODEL TEST
		cellHighlighterModelTest();
		
		// SPIDER MODEL CREATION :
		// List<FileHandle> files =
		// MockCore.core.getGame().modelDiscoverer.explore("models");
		// MockCore.core.getGame().modelManager.loadSync(files);
		Model spider1 = MockCore.core.getGame().modelManager.loadSync("models/Wasp.g3dj");
		System.out.println("spider 1 = " + spider1);
		Model spider = MockCore.core.getGame().modelManager.get("Wasp");
		System.out.println("spider 2 = " + spider);
		
		var names = MockCore.core.getGame().modelManager.getAssetNames();
		System.out.println("Names : ");
		names.forEach(s -> System.out.println(s));
		System.out.println("Names END");
		
		var spiderInst = new ModelInstance(spider);
		spiderInst.transform.scl(1f / 100f);
		spiderInst.transform.setTranslation(5, 5, 1);
		spiderInst.transform.rotate(1, 0, 0, 90);
		characters.add(spiderInst);
		
		spiderController = new AnimationController(spiderInst);
	//	spiderController.setAnimation("HumanArmature|Spider_Walk", -1);
		
		String blender = "H:/Assets/myblender/";
		// KUNAI MODEL :
		MockCore.core.getGame().modelManager.loadSync(blender + "kunai.g3dj");
		Model kunai = MockCore.core.getGame().modelManager.get("kunai");
		var kunaiInst = new ModelInstance(kunai);
		kunaiInst.transform.scl(1f / 100f);
		kunaiInst.transform.setTranslation(0, 0, 1);
		kunaiInst.transform.rotate(1, 0, 0, 90);
		// characters.add(kunaiInst);
		
		// GUI :
		LmlApplicationListener a;
		// GlobalLML.lmlParser.parseTemplate("ui/test1.lml");
		// GlobalLML.init();
		GlobalLML.getLmlParser().createView(view, view.getTemplateFile());
		
		// Generating DTD:
		// GlobalLML.lml().saveDtdSchema(Gdx.files.local("lml.dtd"));
		
		// PARTICLE EFFECTS :
		var pfxTestPath = "H:/Assets/pfx/test/";
		var fxPath = "g3d/anims_fx_etc/laser1.pfx";
		var kunaifxPath = pfxTestPath + "unlimitedKunaiWorks.pfx"; // "kunaiNova8.pfx";
		// var fx = Gdx.files.internal(fxPath);
		// System.out.println("fx : " + fx);
		
		// Since our particle effects are PointSprites, we create a
		// PointSpriteParticleBatch
		PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
		pointSpriteBatch.setCamera(cam);
		particleSystem.add(pointSpriteBatch);
		// Since our particle effects are PointSprites, we create a
		// PointSpriteParticleBatch
		ModelInstanceParticleBatch modelParticleBatch = new ModelInstanceParticleBatch();
		particleSystem.add(modelParticleBatch);
		
		ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
		MockCore.core.getGame().assets.load(fxPath, ParticleEffect.class, loadParam);
		MockCore.core.getGame().assets.load(kunaifxPath, ParticleEffect.class, loadParam);
		MockCore.core.getGame().assets.finishLoading();
		
		// laser effect
		ParticleEffect laserFXo = MockCore.core.getGame().assets.get(fxPath);
		// we cannot use the originalEffect, we must make a copy each time we create new
		// particle effect
		laserFX = laserFXo.copy();
		laserFX.translate(new Vector3(-2, -2, 2));
		laserFX.rotate(new Vector3(0, 1, 0), 90);
		laserFX.init();
		laserFX.start(); // optional: particle will begin playing immediately
		particleSystem.add(laserFX);
		
		// kunai effect
		ParticleEffect kunaiFXo = MockCore.core.getGame().assets.get(kunaifxPath);
		// we cannot use the originalEffect, we must make a copy each time we create new
		// particle effect
		kunaiFX = kunaiFXo.copy();
		kunaiTransform.idt();
		kunaiTransform.setToTranslation(new Vector3(10, -10, 5));
		kunaiTransform.rotate(new Vector3(1, 1, 1), 90);
		kunaiFX.setTransform(kunaiTransform);
		ParticleController co = kunaiFX.getControllers().get(0);
		for (int i = 1; i < 8; i++) {
			var controller = co.copy();
			ColorInfluencer ci = controller.findInfluencer(ColorInfluencer.class);
			DynamicsInfluencer di = controller.findInfluencer(DynamicsInfluencer.class); // (DynamicsInfluencer) controller.influencers.select(v -> v instanceof
																							// DynamicsInfluencer).iterator().next();
			PolarAcceleration pa = (PolarAcceleration) di.velocities.select(v -> v instanceof PolarAcceleration).iterator().next();
			pa.thetaValue.setHigh(0);
			pa.phiValue.setHigh(i * 45);
			// pa.strengthValue.setHigh(10);
			di.velocities.clear();
			di.velocities.add(pa);
			di.velocities.add(new FaceDirection());
			kunaiFX.getControllers().add(controller);
		}
		kunaiFX.getControllers().removeIndex(0);
		kunaiFX.init();
		kunaiFX.start(); // optional: particle will begin playing immediately
		particleSystem.add(kunaiFX);
		
	}
	
	private FirstView view = new FirstView();
	
	public static class FirstView extends AbstractLmlView {
		@LmlActor("random")
		private Label result;
		
		public FirstView() {
			super(new Stage());
		}
		
		@Override
		public FileHandle getTemplateFile() {
			return Gdx.files.internal("ui/test1.lml");
		}
		
		@Override
		public String getViewId() {
			return "test1";
		}
		
		@LmlAction("roll")
		public void rollNumber() {
			laserFX.init();
			laserFX.start();
			result.setText(String.valueOf((int) (MathUtils.random() * 1000)));
		}
	}
	
	public static class GlobalLML extends LmlApplicationListener {
		private static GlobalLML singleton;
		
		public static GlobalLML lml() {
			if(singleton == null) singleton = new GlobalLML();
			return singleton;
		}
		
		public static LmlParser getLmlParser() {
			return lml().getParser();
		}
		
		public GlobalLML() {
			create();
		}
		
		@Override
		protected LmlParser createParser() {
			return VisLml.parser()
					// Registering global action container:
					.actions("global", GlobalLMLActions.class)
					// Adding localization support:
					.i18nBundle(I18NBundle.createBundle(Gdx.files.internal("i18n/bundle")))
					// Add custom skin
					.skin(new Skin(Gdx.files.internal("uiskin.json"))).build();
		}
	}
	
	public static class GlobalLMLActions implements ActionContainer {
		// private final MockGame core = (MockGame) Gdx.app.getApplicationListener();
		// private final GlobalLML lml = GlobalLML.lml();
		@LmlAction("setLocale")
		public void setLocale(final Actor actor) {
			final String localeId = LmlUtilities.getActorId(actor);
			final I18NBundle currentBundle = GlobalLML.lml().getParser().getData().getDefaultI18nBundle();
			if(currentBundle.getLocale().getLanguage().equalsIgnoreCase(localeId)) {
				// Same language.
				return;
			}
			GlobalLML.lml().getParser().getData().setDefaultI18nBundle(I18NBundle.createBundle(Gdx.files.internal("i18n/bundle"), new Locale(localeId)));
			GlobalLML.lml().reloadViews();
		}
	}
	
	/**
	 * test de model pour highlighter des cellules. p.ex. des pièges, glyphes,
	 * prévisualisation de portée et d'aoe, etc. le but est de créer un mesh avec
	 * une certain transparence.
	 */
	private void cellHighlighterModelTest() {
		float r = 1, g = 0, b = 0;
		var areaColor = new Color(r, g, b, 0.15f);
		var borderColor = new Color(r, g, b, 1f);
		Material mat = new Material(ColorAttribute.createDiffuse(areaColor), new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
		Model highlight = new Model();
		Vector3 pos = new Vector3(0, 0, 1f);
		Vector3 size = new Vector3(15, 15, 0);
		Vector3 normal = new Vector3(0, 0, 1);
		
		int vertexPropCount = 6; // nombre de propriété par vertex (pos.xyz + norm.xyz = 6)
		
		var vertices = new float[] { pos.x, pos.y, pos.z, normal.x, normal.y, normal.z, pos.x + size.x, pos.y, pos.z, normal.x, normal.y, normal.z,
				pos.x + size.x, pos.y + size.y, pos.z, normal.x, normal.y, normal.z, pos.x, pos.y + size.y, pos.z, normal.x, normal.y, normal.z, };
		var indices = new short[] { 0, 1, 2, 2, 3, 0 };
		Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		
		String id = "highlight";
		int renderType = GL20.GL_TRIANGLES;
		// if(mat == null) mat = mat3;
		MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, vertices.length, renderType);
		Node node = new Node();
		node.id = "node_" + id;
		node.parts.add(new NodePart(meshPart, mat));
		
		highlight.meshes.add(mesh);
		highlight.meshParts.add(meshPart);
		highlight.nodes.add(node);
		
		float borderRadius = 0.03f;
		Material borderMat = new Material(ColorAttribute.createDiffuse(borderColor));
		var c = vertices.length - vertexPropCount;
		for (int i = 0; i < c + 1; i += vertexPropCount) {
			int i2 = (i != c) ? i + vertexPropCount : 0;
			Vector3 v1 = new Vector3(vertices[i], vertices[i + 1], vertices[i + 2]);
			Vector3 v2 = new Vector3(vertices[i2], vertices[i2 + 1], vertices[i2 + 2]);
			
			// direction du vecteur 1 au vecteur 2
			Vector3 dir = new Vector3(v2.x - v1.x, v2.y - v1.y, 0).nor();
			Vector3 pDir = dir.cpy().crs(0, 0, 1); // vecteur perpendiculaire à la direction
			
			// vu qu'on va du v1 au v2, on sait dans quel sens offset les points pour créer
			// la bordure
			var b1 = new Vector3(v1.x, v1.y, v1.z);
			b1.sub(dir.cpy().scl(borderRadius));
			b1.add(pDir.cpy().scl(borderRadius));
			var b2 = new Vector3(v1.x, v1.y, v1.z);
			b2.sub(dir.cpy().scl(borderRadius));
			b2.sub(pDir.cpy().scl(borderRadius));
			var b3 = new Vector3(v2.x, v2.y, v2.z);
			b3.add(dir.cpy().scl(borderRadius));
			b3.add(pDir.cpy().scl(borderRadius));
			var b4 = new Vector3(v2.x, v2.y, v2.z);
			b4.add(dir.cpy().scl(borderRadius));
			b4.sub(pDir.cpy().scl(borderRadius));
			
			var verticesBorder = new float[] { b2.x, b2.y, b2.z, normal.x, normal.y, normal.z, b1.x, b1.y, b1.z, normal.x, normal.y, normal.z, b3.x, b3.y, b3.z,
					normal.x, normal.y, normal.z, b4.x, b4.y, b4.z, normal.x, normal.y, normal.z, };
			var indicesBorder = new short[] { 0, 1, 2, 2, 3, 0 };
			
			Mesh meshBorder = new Mesh(true, verticesBorder.length, indicesBorder.length, VertexAttribute.Position(), VertexAttribute.Normal());
			meshBorder.setVertices(verticesBorder);
			meshBorder.setIndices(indicesBorder);
			
			String idBorder = "border" + i + "_" + i2;
			MeshPart meshPartBorder = new MeshPart("meshpart_" + idBorder, meshBorder, 0, vertices.length, renderType);
			Node nodeBorder = new Node();
			nodeBorder.id = "node_" + idBorder;
			nodeBorder.parts.add(new NodePart(meshPartBorder, borderMat));
			
			highlight.meshes.add(meshBorder);
			highlight.meshParts.add(meshPartBorder);
			highlight.nodes.add(nodeBorder);
		}
		
		cellHighlighterInst = new ModelInstance(highlight);
	}
	
	@Override
	public Color getBackgroundColor() {
		return Color.BLACK;
	}
	
	@Override
	public boolean useOrtho() {
		return true;
	}
	
	@Override
	public void createEnvironment() {
		// super.createEnvironment();
		var ambiant = 0.7f;
		var dir = 0.8f;
		
		// (shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f,
		// 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f);
		if(activateShadows)
			// (shadowLight = new DirectionalShadowLight(Gdx.graphics.getWidth(),
			// Gdx.graphics.getHeight(), 30, 30, 1f, 100f)).set(dir, dir, dir, -1f, -1.0f,
			// -.5f);
			(shadowLight = new DirectionalShadowLight(1024 * 2 * 2, 1024 * 2 * 2, 70, 70, 1, 100f)).set(dir, dir, dir, -1f, -1.0f, -.5f);
		// (shadowLight = new DirectionalShadowLight(1024*2*2, 1024*2*2, 50, 50, 1f,
		// 100f)).set(dir, dir, dir, -1f, -1.0f, -.5f);
		
		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 0.1f));
		if(activateShadows) {
			env.add(shadowLight);
			env.shadowMap = shadowLight;
		}
		// env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f));
		// env.add(new PointLight().set(0.8f, 0.8f, 0.8f, 18, 18, 1, 2f));
		// env.set(new ColorAttribute(ColorAttribute.Specular, .5f, .5f, .5f, 1f));
	}
	
	@Override
	public void setupShader() {
		// TODO Auto-generated method stub
		String vert = Gdx.files.internal("shaders/depth.vertex.glsl").readString();
		String frag = Gdx.files.internal("shaders/depth.fragment.glsl").readString();
		shadowBatch = new ModelBatch(new DepthShaderProvider(vert, frag));
	}
	
	
	// light cycle
	private float time = 0; // current time
	private float period = 120; // period time in seconds
	private double radius = 2; // circle radius
	
	// walk cycle
	private float walkTime = 0; // current time
	private float walkPeriod = 15; // period time in seconds
	private double walkRadius = 7; // circle radius

	private FrameBuffer fbo;
	private PostProcessor pp;
	{
		fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, true);
		
		/*ShaderLoader.BasePath = "shaders/manuelbua_shaders/";
		pp = new PostProcessor(false, false, true);
		Bloom bloom = new Bloom((int) (Gdx.graphics.getWidth() * 0.25f), (int) (Gdx.graphics.getHeight() * 0.25f));
		bloom.enableBlending(1, 1);
		bloom.setBlurAmount(5);
		pp.addEffect(bloom);*/
	}
	

	@Override
	public void renderShadows(World world) {
		if(activateShadows) {
			shadowLight.begin(worldCenter, cam.direction);
				shadowBatch.begin(shadowLight.getCamera());
				// render shadows on the world
				shadowBatch.render(inst, env); // world.cache, shadowEnv);
				// dont render shadows on highlight effects like traps, glyphs, etc
				// shadowBatch.render(cellHighlighterInst);
				shadowBatch.render(characters, env);
				shadowBatch.end();
			shadowLight.end();
		}
	}
	ModelBatch pfxBatch = new ModelBatch();
	@Override
	public void renderWorld(World world) {
		
		// Gdx.input.setInputProcessor(view.getStage());
		
		// less performance in
		if(true) {
			//pp.capture();
			
		//	fbo.begin();
			modelBatch.begin(cam);
			// render world
			modelBatch.render(inst, env); // world.cache, env);
			// render highlight effects like traps, glyphs, etc (might or might not render
			// with the environment var)
			modelBatch.render(cellHighlighterInst); // , new LShader());
			// render characters
			modelBatch.render(characters, env);
			modelBatch.end();

		//	fbo.end();

			
			//pp.captureEnd();
			//pp.render();
			
			// Render UI view
			// view.render();
		}
		
		// lining.renderLines();
	}
	
	public void renderUI() {
		
	}
	
	/*
	 * For stuff like animations & effects ...
	 */
	@Override
	protected void act(float delta) {
		walkTime += delta;
		if(walkTime >= walkPeriod) walkTime = 0;
		double radian = ((walkPeriod - walkTime) / walkPeriod) * 2 * Math.PI; // rotation
		float y = (float) (Math.sin(radian) * walkRadius); // opp
		float x = (float) (Math.cos(radian) * walkRadius); // adj
		float z = 1;
		
		characters.get(0).transform.setTranslation(x + 15, y + 15, z);
		characters.get(0).transform.rotate(0, 1, 0, (float) ((walkPeriod - delta) / walkPeriod) * 360); // rotate en Y au lieu d'en Z à cause qu'on a tourné le
																										// model de 90° en X avant.
		
		spiderController.update(delta);
	}
	
	/*
	 * Update the lightning direction
	 */
	@Override
	protected void updateLight(float delta) {
		time += delta;
		if(time >= period) time = 0;
		double radian = ((period - time) / period) * 2 * Math.PI;
		if(activateShadows) {
			shadowLight.direction.x = (float) (Math.sin(radian) / radius);
			shadowLight.direction.y = (float) (Math.cos(radian) / radius);
		}
	}
	
	
	public void renderParticleEffects() {
		pfxBatch.begin(cam);
		
		/*
		 * // matrix.idt(); Vector3 pos = new Vector3();
		 * characters.get(0).transform.getTranslation(pos); pos.z = 2;
		 * matrix.setTranslation(pos);
		 * 
		 * laserFX.setTransform(matrix);
		 */
		
		// kunaiFX.reset();
		// kunaiFX.start();
		kunaiTransform.setToTranslation(25, 10, 10);
		kunaiTransform.scale(0.5f, 0.5f, 0.5f);
		kunaiFX.setTransform(kunaiTransform);
		
		particleSystem.update(); // technically not necessary for rendering
		particleSystem.begin();
		particleSystem.draw();
		particleSystem.end();
		pfxBatch.render(particleSystem);

		pfxBatch.end();
	}
	
	@Override
	public String getTitleText() {
		return "FPS : " + Gdx.graphics.getFramesPerSecond() + "  Vertices : " + getNumVertices() + "  Meshes : " + g1.rootNode.meshes.size;
	}
	
	private int getNumVertices() {
		int sum = 0;
		for (var m : g1.rootNode.meshes)
			sum += m.getNumVertices();
		return sum;
	}
	
	
	GreedyMeshMaker g = new GreedyMeshMaker();
	ModelInstance greedyModel = new ModelInstance(g.rootNode);
	
	CustomGreedyMesh g1 = new CustomGreedyMesh();
	ModelInstance greedyModel1 = new ModelInstance(g1.rootNode);

	ModelInstance inst = true ? greedyModel1 : greedyModel;
	
}
