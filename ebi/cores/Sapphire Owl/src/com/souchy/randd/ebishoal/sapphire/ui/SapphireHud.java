package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.screen.LapisHud;
import com.souchy.randd.ebishoal.sapphire.ui.roundImage.RoundImage;

public class SapphireHud extends LapisHud {

	@LmlActor("life")
	public Label life;

	@LmlActor("pageUp")
	public Button pageUp;

	@LmlActor("pageUpImage")
	public Image pageUpImage;

	@LmlActor("pageDownImage")
	public Image pageDownImage;
	
	public SapphireHud() {
		super(new Stage(
				new ScreenViewport(), 
				//new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), //new ScalingViewport(Scaling.none, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), //new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()),
				new SapphireBatch()
		));
		var vert = Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/shaders/ui.vertex.glsl"); //Gdx.files.internal("res/gdx/shaders/postProcess.vertex.glsl");
		var frag = Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/shaders/ui.fragment.glsl"); //Gdx.files.internal("res/gdx/shaders/postProcess.fragment.glsl");
		var shader = new ShaderProgram(vert, frag);
		getStage().getBatch().setShader(shader);
	}
	
	
	
	@Override
	public String getViewId() {
		return "sheet";
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/ui/" + getViewId() + ".lml");
	}
	
	@Override
	public FileHandle getStyleFile() {
		return Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/ui/" + getViewId() + ".json");
	}
	
	public void sadfgffd() {
		var assets = new AssetManager();
		
		// round borders
		assets.load("res/gdx/ui/res/borders/ring_frame.PNG", Texture.class);
		assets.load("res/gdx/ui/res/borders/map_01_01.png", Texture.class);
		// round backgrounds
		assets.load("res/gdx/ui/res/backgrounds/map_01_02.png", Texture.class);
		assets.load("res/gdx/ui/res/backgrounds/map_01_03.png", Texture.class);
		assets.load("res/gdx/ui/res/backgrounds/map_01_04.png", Texture.class);
		assets.load("res/gdx/ui/res/backgrounds/scale_01_03.png", Texture.class);
		// buttons
		assets.load("res/gdx/ui/res/buttons/slider_02_03.png", Texture.class);
		
		new Image(new Texture(""));
	}
	
}
