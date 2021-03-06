package com.souchy.randd.tools.mapeditor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
	public void dispose() {
		properties.save(); // save on exit
		screen.dispose();
		super.dispose();
	}
	
	@Override
	public Screen getStartScreen() {
		return screen;
	}

	
	
}
