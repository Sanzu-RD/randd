package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.lml.util.Lml;
import com.github.czyzby.lml.vis.util.VisLml;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.screen.GlobalLML.GlobalLMLActions;
import com.souchy.randd.ebishoal.commons.lapis.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.ebishoal.sapphire.ui.roundImage.RoundImageLmlTagProvider;
import com.souchy.randd.ebishoal.sapphire.ui.roundImage.RoundTextureRegion;

public class SapphireScreen extends LapisScreen {
	
	// light cycle
	private float time = 0; // current time
	private float period = 120; // period time in seconds
	private double radius = 2; // circle radius
	
	@Override
	public LineDrawing createLining(Camera cam, BoundingBox worldBB) {
//		var center = getWorldCenter();
//		cam = new OrthographicCamera(17f * 16f/9f, 17f);
//		cam.direction.set(1, 1, -1.5f);
//		cam.up.set(1, 1, 1f);
//		cam.position.set(center.x, center.y, center.z); 
//		cam.near = -30f;
//		cam.far = 120f;
//		cam.update();
		return null;// super.createLining(cam, worldBB);
	}
	
	@Override
	public LapisHud createUI() {
		var view = new SapphireHud();
		var parser = VisLml.parser()
				// Registering global action container:
				.actions("global", GlobalLMLActions.class)
				// Adding localization support:
				// .i18nBundle(I18NBundle.createBundle(i18n))
				// Add global custom skin
				// .skin(new Skin(Gdx.files.internal("uiskin.json")))
				.build();
		parser.getSyntax().addTagProvider(new RoundImageLmlTagProvider(), "roundImage");
		var skin = new Skin(view.getStyleFile());

		//parser.getData().addSkin(view.getViewId(), skin);
		parser.getData().setDefaultSkin(skin);
		
		var tex1 = new Texture(Gdx.files.absolute("G:/Assets/pack/kenney/kenney_emotespack/PNG/Pixel/Style 1/emote_alert.png"), true);
		var tex2 = new Texture(Gdx.files.internal("gdx/ui/res/borders/ring_frame.PNG"), true);
		var tex3 = new Texture(Gdx.files.internal("gdx/ui/res/buttons/slider_02_03.png"), true);
		var tex4 = new Texture(Gdx.files.internal("gdx/ui/res/buttons/slider_02_04.png"), true);
		var ava1 = new Texture(Gdx.files.internal("avatars/Tex_AnimeAva_15.png"), true);
		var ava2 = new Texture(Gdx.files.internal("avatars/Tex_AnimeAva_30.png"), true);
		
		tex1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		tex2.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		tex3.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		tex4.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		ava1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		ava2.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		parser.getData().getDefaultSkin().add("emote_alert", new TextureRegionDrawable(new RoundTextureRegion(tex1)));
		parser.getData().getDefaultSkin().add("ring_frame", tex2);
		parser.getData().getDefaultSkin().add("up", tex3);
		parser.getData().getDefaultSkin().add("down", tex4);
		parser.getData().getDefaultSkin().add("ava1", new TextureRegionDrawable(new RoundTextureRegion(ava1)));
		parser.getData().getDefaultSkin().add("ava2", new TextureRegionDrawable(new RoundTextureRegion(ava2)));
		
		int i = 0;
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_07.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_19.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_27.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_29.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_34.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_55.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_56.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_59.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_72.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_74.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_79.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_81.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_82.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_83.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_87.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_90.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_91.png"), true))));
		parser.getData().getDefaultSkin().add("spell" + (i++), new TextureRegionDrawable(new RoundTextureRegion(new Texture(Gdx.files.internal("sungjin/SpellBook01_98.png"), true))));
		
		parser.getData().getDefaultSkin().getAll(TextureRegionDrawable.class).forEach(d -> {
			d.value.getRegion().getTexture().setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		});
		
		
		// add skin and style sheets
		//skin.add("0000ffff", Color.GOLD);
		//parser.parseStyleSheet(Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/ui/sheet.lss"));
		
		//parser.getData().getDefaultSkin().add("default", skin.get(LabelStyle.class));
		//parser.getData().getDefaultSkin().add("0000ffff", Color.GOLD);
		

		Log.info("skin lbl style : " + skin.get(LabelStyle.class).fontColor);
		
		parser.createView(view, view.getTemplateFile());
		
		view.pageUp.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				Log.info("enter");
				var light = 1.3f;
				//view.pageUpImage.setColor(light, light, light, view.pageUpImage.getColor().a);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Log.info("exit");
				view.pageUpImage.setColor(1, 1, 1, view.pageUpImage.getColor().a);
				super.exit(event, x, y, pointer, toActor);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch down");
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("down"));
				var shade = 0.7f;
				view.pageUpImage.setColor(shade, shade, shade, view.pageUpImage.getColor().a);
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch up");
				var light = 1.0f;
				view.pageUpImage.setColor(light, light, light, view.pageUpImage.getColor().a);
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("up"));
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		Log.info("life : " + view.life.getColor() + ", " + view.life.getStyle().fontColor);
		return view;
	}
	
	public InputProcessor createInputProcessor() {
		var multi = new InputMultiplexer();
		multi.addProcessor(getView().getStage());
		multi.addProcessor(new SapphireController(getCamera()));
		return multi;
	}
	
	@Override
	public Camera createCam(boolean useOrtho) {
//		float viewportSize = 17; // acts as a zoom (lower number is closer zoom)
//		var cam = new OrthographicCamera(viewportSize * 16 / 9, viewportSize);
		return new OrthographicCamera();
	}
	
	@Override
	public Viewport createViewport(Camera cam) {
		float viewportSize = 17; // getWorldCenter().x * 2; // acts as a zoom (lower number is closer zoom)
		var viewport = new ExtendViewport(viewportSize * 16/9, viewportSize, cam);
		//var viewport = new SapphireViewport(viewportSize * 16 / 9, viewportSize, cam);
		//var viewport = new ScreenViewport(cam);
		//viewport.setUnitsPerPixel(0.02f);
		return viewport;
	}
	
	@Override
	protected void updateLight(float delta) {
		time += delta;
		if(time >= period) time = 0;
		double radian = ((period - time) / period) * 2 * Math.PI;
		getShadowLight().direction.x = (float) (Math.sin(radian) / radius);
		getShadowLight().direction.y = (float) (Math.cos(radian) / radius);
	}
	
	@Override
	public World createWorld() {
		return new SapphireWorld();
	}

	/**
	 * Get the color to clear the screen with
	 */
//	public Color getBackgroundColor() {
//		return Color.PINK;
//	}
//	
//	@Override
//	public Texture createBackground() {
//		return null; //super.createBackground();
//	}

}
