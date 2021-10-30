package com.souchy.randd.tools.mapeditor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.mapio.MapData;
import com.souchy.randd.commons.tealwaters.commons.Bean;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.configs.EditorProperties;
import com.souchy.randd.tools.mapeditor.controls.Commands;
import com.souchy.randd.tools.mapeditor.ui.mapeditor.EditorScreen;

public class MapEditorGame extends LapisGame {
	
	public static MapEditorGame game;
	
	public static Bean<FileHandle> currentFile = new Bean<FileHandle>();
	public static Bean<MapData> currentMap = new Bean<MapData>();
	//public static MapCache mapCache;
	
	public static EditorScreen screen;
	public static Skin skin;
	//public static float cellSize = 5;
	
	public static EditorProperties properties;

	public MapEditorGame() {
		game = this;
	}
	
	@Override
	public void init() {
		LapisAssets.blocking = false;
		properties = new EditorProperties();
		properties.load();
		
		Commands.initCommands();
		
		VisUI.load();
		skin = VisUI.getSkin(); // Skin(); // new Skin(Gdx.files.internal("ui/common/uiskin.json"));
		//mapCache = new MapCache(); //"data/maps/", FileType.Internal);
		//System.out.println("on create :" + mapCache.getRoot());
		
		screen = new EditorScreen();
		screen.init();
		//screen.create();
		
		currentFile.set(Gdx.files.internal(properties.lastMap.get())); //"res/maps/goulta7b.map")); //"data/maps/goulta7.map"));
		screen.world.gen();
		screen.resetCamera();
	}
	
	
	@Override
	public Screen getStartScreen() {
		return screen;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		properties.save(); // save on exit
		if(!MapEditorCore.conf.reset) {
			MapEditorCore.conf.gfx.width = Gdx.graphics.getWidth();
			MapEditorCore.conf.gfx.height = Gdx.graphics.getHeight();
			MapEditorCore.conf.gfx.x = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getPositionX();
			MapEditorCore.conf.gfx.y = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getPositionY();
			MapEditorCore.conf.save(); // save on exit
		}
	}
	
}
