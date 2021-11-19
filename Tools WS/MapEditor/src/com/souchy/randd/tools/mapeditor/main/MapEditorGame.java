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
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.ebi.ammolite.util.LightningDistort;
import com.souchy.randd.ebi.ammolite.util.LightningDistort.LightningNode;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.configs.EditorProperties;
import com.souchy.randd.tools.mapeditor.controls.Commands;
import com.souchy.randd.tools.mapeditor.entities.DissolveAction;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;
import com.souchy.randd.tools.mapeditor.io.MaterialJson.MaterialLoader;
import com.souchy.randd.tools.mapeditor.ui.mapeditor.EditorScreen;

import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class MapEditorGame extends LapisGame {
	
	public static Engine engine;
	public static EditorEntities entities;
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
		// crazy we have to call this here and not in MapEditorCore, 
		// otherwise Gdx initializes as Lwjgl instead of Lwjgl3 because of lapis imports dependencies
		LapisCore.arguments(MapEditorCore.args);
		
		LapisAssets.hack().setLoader(Material.class, new MaterialLoader(LapisAssets.hack().getFileHandleResolver()));
		LapisAssets.blocking = false;
		properties = new EditorProperties();
		properties.load();
		
		engine = new Engine();
		entities = new EditorEntities(engine);
		entities.actions.add(new DissolveAction());
		
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

		//LapisAssets.loadModels(Gdx.files.internal("res/models/"));
		LapisAssets.loadMaterials(Gdx.files.internal("res/materials/"));
		
	}
	
	@Override
	public void render() {
		entities.update(Gdx.graphics.getDeltaTime());
		super.render();
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
