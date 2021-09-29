package com.souchy.randd.mockingbird.lapismock.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
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
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.FaceDirection;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.PolarAcceleration;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
//import com.bitfire.postprocessing.PostProcessor;
//import com.bitfire.postprocessing.effects.Bloom;
//import com.bitfire.utils.ShaderLoader;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.parser.impl.tag.AbstractGroupLmlTag;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.util.LmlUtilities;
import com.github.czyzby.lml.vis.util.VisLml;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.mockingbird.lapismock.BaseScreen;
import com.souchy.randd.mockingbird.lapismock.CustomGreedyMesh;
import com.souchy.randd.mockingbird.lapismock.GreedyMeshMaker;
import com.souchy.randd.mockingbird.lapismock.World;
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
	
	// ParticleSystem is a singleton class, we get the instance instead of creating a new object:
	static ParticleSystem particleSystem = ParticleSystem.get();
	static ParticleEffect laserFX;
	static ParticleEffect kunaiFX;
	static Matrix4 matrix = new Matrix4();
	static Matrix4 kunaiTransform = new Matrix4();

	ModelCache cache = new ModelCache();
	boolean greedyOrInstances = false;
	
	
	boolean particles = true;
	boolean creatures = true;


	static String internal = "bin/";
	
	public MockScreen3() {
		super();
		// Since our particle effects are PointSprites, we create a PointSpriteParticleBatch
		PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
		pointSpriteBatch.setCamera(cam);
		particleSystem.add(pointSpriteBatch);
		// Since our particle effects are PointSprites, we create a PointSpriteParticleBatch
		ModelInstanceParticleBatch modelParticleBatch = new ModelInstanceParticleBatch();
		particleSystem.add(modelParticleBatch);
		ParticleEffectLoader.ParticleEffectLoadParameter params = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
		
//		var dir = Gdx.files.internal("res/delete/");
//		Log.info("test : " + dir.path());
//		for(var f : dir.list())
//			Log.info("test : " + f.type() + ",  " + f.path());
		
		var blender = "G:/Assets/myblender/";
		var assettests = "G:/Assets/test/";
		var pfxTestPath = "G:/Assets/pfx/test/";
		LapisAssets.loadModels(Gdx.files.internal(internal+"delete/"));
		LapisAssets.loadModels(Gdx.files.internal(internal+"models/"));
		LapisAssets.loadTextures(Gdx.files.internal(internal+"textures/"));
		LapisAssets.loadI18NBundles(Gdx.files.internal(internal+"i18n/"));
//		LapisAssets.loadParticleEffects(Gdx.files.internal(internal+"fx/"), params);
//		LapisAssets.loadParticleEffects(Gdx.files.absolute(pfxTestPath), params);
		LapisAssets.loadModels(Gdx.files.absolute(blender));
		LapisAssets.loadTextures(Gdx.files.absolute(assettests));
		
		Log.info("assets : { " + String.join(", ", LapisAssets.assets.getAssetNames()) + " }");
		
		String vert = Gdx.files.internal("data/shaders/default.vertex.glsl").readString();
		String frag = Gdx.files.internal("data/shaders/default.fragment.glsl").readString();
		modelBatch = new ModelBatch(vert, frag);
		var asdf = new ModelBatch(new DefaultShaderProvider());
		var mod = new Model();
		var in = new ModelInstance(mod);
		in.userData = "data passed to shader used as parameters";
		
		
		// FBO
		postProcessor = new PostProcessingFBO(cam);
		Gdx.gl.glEnable(GL20.GL_LINEAR);
		
		// GRID
		lining = new LineDrawing(cam);
		lining.createGrid(1, (int) worldBB.getWidth(), (int) worldBB.getHeight(), true);
		
		// HIGHTLIGHT MODEL TEST
		cellHighlighterModelTest();
		
		// SPIDER MODEL CREATION :
		// List<FileHandle> files =
		// MockCore.core.getGame().modelDiscoverer.explore("models");
		// MockCore.core.getGame().modelManager.loadSync(files);
		Model spider = LapisAssets.get(internal+"models/Wasp.g3dj"); //LapisMock.core.getGame().modelManager.loadSync("models/Wasp.g3dj");
		System.out.println("spider 1 = " + spider);
//		Model spider = LapisResources.assets.get("res/delete/Wasp.g3dj", Model.class); //LapisMock.core.getGame().modelManager.get("Wasp");
//		System.out.println("spider 2 = " + spider);
		
//		var names = LapisMock.core.getGame().modelManager.getAssetNames();
//		System.out.println("Names : ");
//		names.forEach(s -> System.out.println(s));
//		System.out.println("Names END");
		
		var spiderInst = new ModelInstance(spider);
		spiderInst.transform.scl(1f / 100f);
		spiderInst.transform.setTranslation(5, 5, 1);
		spiderInst.transform.rotate(1, 0, 0, 90);
		characters.add(spiderInst);
		
		spiderController = new AnimationController(spiderInst);
		//spiderController.setAnimation("HumanArmature|Spider_Walk", -1);
		
		// KUNAI MODEL :
		//LapisMock.core.getGame().modelManager.loadSync(new FileHandle(blender + "kunai.g3dj"));
		
		Model kunai = LapisAssets.get(blender + "kunai.g3dj"); //LapisMock.core.getGame().modelManager.get("kunai");
		var kunaiInst = new ModelInstance(kunai);
		kunaiInst.transform.scl(1f / 100f);
		kunaiInst.transform.setTranslation(0, 0, 1);
		kunaiInst.transform.rotate(1, 0, 0, 90);
		 characters.add(kunaiInst);

		// CHIP MODEL :
		//LapisMock.core.getGame().modelManager.loadSync(new FileHandle(blender + "kunai.g3dj"));
		Model chip = LapisAssets.get(blender + "chip.g3dj"); //LapisMock.core.getGame().modelManager.get("kunai");
		var chipInst = new ModelInstance(chip);
		chipInst.transform.scl(1f / 100f);
		chipInst.transform.setTranslation(3, 3, 1);
		chipInst.transform.rotate(1, 0, 0, 90);
		
		// SOME TEXTURES :
//		LapisMock.core.getGame().assets.load("G:/Assets/test/grass137x137.png", Texture.class);
//		LapisMock.core.getGame().assets.load("G:/Assets/test/blackborder.png", Texture.class);
//		LapisMock.core.getGame().assets.finishLoading();
		GlobalLML.getLmlParser().getData().getDefaultSkin().add("bg", LapisAssets.get(assettests + "grass137x137.png")); //LapisMock.core.getGame().assets.get("G:/Assets/test/grass137x137.png"));
		GlobalLML.getLmlParser().getData().getDefaultSkin().add("border", LapisAssets.get(assettests + "blackborder.png")); //LapisMock.core.getGame().assets.get("G:/Assets/test/blackborder.png"));
		
		// GUI :
		//LmlApplicationListener a;
		// GlobalLML.lmlParser.parseTemplate("ui/test1.lml");
		// GlobalLML.init();
		GlobalLML.getLmlParser().createView(view, view.getTemplateFile());
		//var test2 = GlobalLML.getLmlParser().parseTemplate(Gdx.files.internal("ui/test2.lml")).first();
		Test2 test2 = LmlWidgets.createGroup(internal+"ui/test2.lml");
		test2.refresh("1");
		view.getStage().addActor(test2);
		
		
		// Generating DTD:
		// GlobalLML.lml().saveDtdSchema(Gdx.files.local("lml.dtd"));
		
		// PARTICLE EFFECTS :
		var fxPath = internal+"fx/laser1.pfx";
		var kunaifxPath = pfxTestPath + "unlimitedKunaiWorks.pfx"; // "kunaiNova8.pfx";
		// var fx = Gdx.files.internal(fxPath);
		// System.out.println("fx : " + fx);
		
		// laser effect
		ParticleEffect laserFXo = LapisAssets.get(fxPath);
		// we cannot use the originalEffect, we must make a copy each time we create new
		// particle effect
		laserFX = laserFXo.copy();
		laserFX.translate(new Vector3(-2, -2, 2));
		laserFX.rotate(new Vector3(0, 1, 0), 90);
		laserFX.init();
		laserFX.start(); // optional: particle will begin playing immediately
		particleSystem.add(laserFX);
		
		// kunai effect
		ParticleEffect kunaiFXo = LapisAssets.get(kunaifxPath);
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
		
		
//
//		cache.begin();
//		cache.add(inst);
//		cache.end();
	}
	
	public static class MockWidget extends Group {
		public void setImage(Image img, String imgid) {
			if(img == null) return;
			var drawable = GlobalLML.getLmlParser().getData().getDefaultSkin().getDrawable(imgid);
			img.setDrawable(drawable);
		}
		public void setText(Label lbl, String text) {
			lbl.setText(text);
		}
	}
	
	/*
	 * Peut avoir une config avec la position initiale de chacun des composants ex :
	 * {
	 * 		"chat": { "align":"bottomleft", "x":100, "y":100 },
	 * 		"playbar": { "align":"bottomcenter", "x":0, "y":100 },
	 * 		"timeline": { "align":"bottomright", "x":100, "y":100, "orientation":"vertical" },
	 * }
	 * ou pas besoin de ça quand t'as déjà le lml que les users peuvent éditer
	 */
	
	public static class Test2 extends MockWidget {
		@LmlActor("icon")
		public Image icon;
		@LmlActor("border")
		public Image border;
		@LmlActor("stacks")
		public Label stacks;
		@LmlActor("duration")
		public Label duration;
		@LmlActor("asdf")
		public Asdf asdf;
		public void refresh(String str) {
			this.setPosition(1000, 300);
			
			setImage(icon, "bg");
			setImage(border, "border");
			setText(stacks, "1");
			setText(duration, "2");
			
			setText(asdf.lbl, "okok");
		}
	}
	public static class Asdf extends MockWidget {
		@LmlActor("lbl")
		public Label lbl;
	}
	
	/**
	 * Injects LML fields 
	 */
	public static class LmlInjector {
		public static <T extends Group> void inject(T group) {
			for(var field : group.getClass().getFields()) {
				try {
					LmlActor ann = field.getAnnotation(LmlActor.class);
					var actorId = ann.value()[0];
					var value = group.findActor(actorId);
					field.set(group, value);
					
					// inject sub groups
					if(value instanceof Group) 
						inject((Group) value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class LmlWidgets {
		public static <T extends Group> T createGroup(String path) {
			var group = (T) GlobalLML.getLmlParser().parseTemplate(Gdx.files.internal(path)).first();
			LmlInjector.inject(group);
			return group;
		}
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
			return Gdx.files.internal(internal+"ui/test1.lml");
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
					.i18nBundle(I18NBundle.createBundle(Gdx.files.internal(internal+"i18n/bundle"))) // ""i18n/ux/bundle")))
					// custom tags
					.tag(new Test2TagProvider(), "test2")
					.tag(new AsdfTagProvider(), "asdf")
					// Add custom skin
					.skin(new Skin(Gdx.files.internal(internal+"ui/uiskin.json"))).build(); // "ux/sapphire/main.json"))).build();//+"ui/uiskin.json"))).build();
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
			GlobalLML.lml().getParser().getData().setDefaultI18nBundle(I18NBundle.createBundle(Gdx.files.internal(internal+"i18n/bundle"), new Locale(localeId)));
			GlobalLML.lml().reloadViews();
		}
	}
	
	public static class Test2TagProvider implements LmlTagProvider {
		@Override
		public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			Log.info("tag provider");
			return new Test2Tag(parser, parentTag, rawTagData);
		}
	}
	public static class Test2Tag extends AbstractGroupLmlTag {
		public Test2Tag(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			super(parser, parentTag, rawTagData);
			Log.info("tag 1");
		}
		@Override
		protected Test2 getNewInstanceOfGroup(LmlActorBuilder builder) {
			Log.info("tag 2");
			return new Test2();
		}
	}
	
	public static class AsdfTagProvider implements LmlTagProvider {
		@Override
		public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			return new AsdfTag(parser, parentTag, rawTagData);
		}
	}
	public static class AsdfTag extends AbstractGroupLmlTag {
		public AsdfTag(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			super(parser, parentTag, rawTagData);
		}
		@Override
		protected Asdf getNewInstanceOfGroup(LmlActorBuilder builder) {
			return new Asdf();
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
		
		var vertices = new float[] { 
				pos.x, pos.y, pos.z, 						normal.x, normal.y, normal.z, 
				pos.x + size.x, pos.y, pos.z, 				normal.x, normal.y, normal.z,
				pos.x + size.x, pos.y + size.y, pos.z, 		normal.x, normal.y, normal.z,
				pos.x, pos.y + size.y, pos.z, 				normal.x, normal.y, normal.z,
				};
		var indices = new short[] { 0, 1, 2, 2, 3, 0 };
		Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		
		String id = "highlight";
		int renderType = GL20.GL_TRIANGLES;
		// if(mat == null) mat = mat3;
		MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, indices.length, renderType);
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
			MeshPart meshPartBorder = new MeshPart("meshpart_" + idBorder, meshBorder, 0, indices.length, renderType);
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
		var shadow = 0.5f;
		
		// (shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f,
		// 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f);
		if(activateShadows)
			// (shadowLight = new DirectionalShadowLight(Gdx.graphics.getWidth(),
			// Gdx.graphics.getHeight(), 30, 30, 1f, 100f)).set(dir, dir, dir, -1f, -1.0f,
			// -.5f);
			(shadowLight = new DirectionalShadowLight(1024 * 2 * 2, 1024 * 2 * 2, 70, 70, 0.1f, 100f)).set(shadow, shadow, shadow, -1f, -1.0f, -.5f);
		// (shadowLight = new DirectionalShadowLight(1024*2*2, 1024*2*2, 50, 50, 1f,
		// 100f)).set(dir, dir, dir, -1f, -1.0f, -.5f);
		
		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 0.1f));
		if(activateShadows) {
			env.add(shadowLight);
			env.shadowMap = shadowLight;
		}
		else env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 1f, .8f, -.2f));
		// env.add(new PointLight().set(0.8f, 0.8f, 0.8f, 18, 18, 1, 2f));
		// env.set(new ColorAttribute(ColorAttribute.Specular, .5f, .5f, .5f, 1f));
	}
	
	@Override
	public void setupShader() {
		// TODO Auto-generated method stub
		String vert = Gdx.files.internal(internal+"shaders/depth.vertex.glsl").readString();
		String frag = Gdx.files.internal(internal+"shaders/depth.fragment.glsl").readString();
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
//	private PostProcessor pp;
	{
		fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, true);
		
//		ShaderLoader.BasePath = internal+"shaders/manuelbua_shaders/";
//		pp = new PostProcessor(false, false, true);
//		Bloom bloom = new Bloom((int) (Gdx.graphics.getWidth() * 0.25f), (int) (Gdx.graphics.getHeight() * 0.25f));
//		bloom.enableBlending(1, 1);
//		bloom.setBlurAmount(5);
//		pp.addEffect(bloom);
	}
	

	@Override
	public void renderShadows(World world) {
		if(activateShadows) {
			shadowLight.begin(worldCenter, cam.direction);
				shadowBatch.begin(shadowLight.getCamera());
				// render shadows on the world
				if(greedyOrInstances) 
					shadowBatch.render(inst, env); // world.cache, shadowEnv);
				else 
					shadowBatch.render(world.cache, env);
				// dont render shadows on highlight effects like traps, glyphs, etc
				// shadowBatch.render(cellHighlighterInst);
				if(creatures) shadowBatch.render(characters, env);
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
			
			//postProcessor.begin();
			
			//fbo.begin();
				modelBatch.begin(cam);
					// render world
					if(greedyOrInstances) 
						modelBatch.render(inst, env); // world.cache, env);
						//g1.meshes.forEach(m -> m.render(modelBatch.getShaderProvider().getShader(null), GL20.GL_TRIANGLES));
					else 
						modelBatch.render(world.cache, env);
					// render highlight effects like traps, glyphs, etc (might or might not render with the environment var)
					modelBatch.render(cellHighlighterInst); // , new LShader());
					// render characters
					if(creatures) modelBatch.render(characters, env);
				modelBatch.end();


			//postProcessor.end();
			//postProcessor.render();
			
			//fbo.end();

			//pp.captureEnd();
			//pp.render();
			
		}
		renderUI();
		// lining.renderLines();
	}
	
	public void renderUI() {
		// Render UI view
		view.render();
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
		if(particles) {
			pfxBatch.begin(cam);
			
			
			   matrix.idt(); 
			   Vector3 pos = new Vector3();
			  characters.get(0).transform.getTranslation(pos); pos.z = 2;
			  matrix.setTranslation(pos);
			  
			  laserFX.setTransform(matrix);
			 
			
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
	}
	
	@Override
	public String getTitleText() {
		int sumVertices = 0;
		int sumMeshes = 0;
		int parts = 0;
		if(greedyOrInstances) {
			for (var m : g1.rootNode.meshes) {
				sumMeshes++;
				sumVertices += m.getNumVertices();
				
			}
			parts += g1.rootNode.meshParts.size;
		} else {
			for(var i : world.instances) {
				for(var m : i.model.meshes) {
					sumMeshes++;
					sumVertices += m.getNumVertices();
				}
				parts += i.model.meshParts.size;
			}
		}
		return "FPS : " + Gdx.graphics.getFramesPerSecond() + "  Vertices : " + sumVertices + "  Meshes : " + sumMeshes+ "  Parts : " + parts;
	}
	
	
	GreedyMeshMaker g = new GreedyMeshMaker();
	ModelInstance greedyModel = new ModelInstance(g.rootNode);
	
	CustomGreedyMesh g1 = new CustomGreedyMesh();
	ModelInstance greedyModel1 = new ModelInstance(g1.rootNode);

	ModelInstance inst = true ? greedyModel1 : greedyModel;
	
}
