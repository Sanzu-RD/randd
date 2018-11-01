package com.souchy.randd.tools.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.souchy.randd.beans.Bean;
import com.souchy.randd.commons.cache.map.MapCache;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.sapphire.models.EbiCell;
import com.souchy.randd.situationtest.matrixes.MatrixFlags;
import com.souchy.randd.situationtest.models.map.MapData;
import com.souchy.randd.tools.mapeditor.configs.EditorProperties;
import com.souchy.randd.tools.mapeditor.ui.EditorScreen;

public class MapEditorGame extends LapisGame {
	
	public static Bean<FileHandle> currentFile = new Bean<FileHandle>();
	public static Bean<MapData> currentMap = new Bean<MapData>();
	public static MapCache cache;
	
	public static EditorScreen screen;
	public static Skin skin;
	public static float cellSize = 5;
	
	public static EditorProperties properties;

	@Override
	public void onCreateHook() {
		properties = new EditorProperties();
		properties.load();
		
		skin = new Skin(Gdx.files.internal("res/uiskin.json"));
		cache = new MapCache(); //"data/maps/", FileType.Internal);
		System.out.println("on create :" + cache.getRoot());
		screen = new EditorScreen();
		screen.create();
		
		currentFile.set(Gdx.files.internal("data/maps/map1.map"));
		loadMap();
	}
	
	@Override
	public Screen getStartScreen() {
		return screen;
	}

	public static void loadMap() {
		//MapCache cache = new MapCache();

		screen.getWorld().cache.begin();
		
		if(currentFile.get() != null) {
			String path = currentFile.get().file().getAbsolutePath();
			MapData data = MapEditorGame.cache.get(path);
			MapEditorGame.currentMap.set(data);

			//MapData data = currentMap.get(); //cache.get("data/maps/map1.map");
			for(int i = 0; i < data.models.length; i++) {
				for(int j = 0; j < data.models[i].length; j++) {
					int modelID = data.getModel(i, j);
					int height = data.getElevation(i, j);
					boolean walk = data.isWalkable(i, j);
					boolean los = data.isLos(i, j);
					
					if(modelID != MatrixFlags.PositionningFlags.NoFlag.getID()) {
						//Model model = manager.get("data/cellModels/" + modelID + ".g3dj");
						ModelInstance instance = testModelGenerator(modelID); //new ModelInstance(model);
						
						EbiCell c = new EbiCell(i, j, height, walk, los, instance);
						instance.transform.setTranslation(new Vector3(cellSize * i + cellSize/2, cellSize * j + cellSize/2, height * cellSize - cellSize/2));
						
						//board.getCells().put(i, j, c);
						screen.getWorld().cache.add(c.model);
					}
				}
			}
		}
		screen.resetCam();

		screen.getWorld().cache.end();
	}
	
	private static ModelInstance testModelGenerator(int id) {
		ModelBuilder builder = new ModelBuilder();
		long attributes = Usage.Position | Usage.Normal | Usage.ColorPacked;
		Model model = builder.createBox(cellSize, cellSize, cellSize, getMat(id), attributes);
		ModelInstance instance = new ModelInstance(model);
		//instance.transform.set(new Vector3(5 * i + 1f, 5 * j + 1f, 0), new Quaternion(0, 0, 0, 0));
		//getWorld().cache.add(instance);
		return instance;
	}
	
	private static Material getMat(int id) {
		Material mat1 = new Material(
			  //  IntAttribute.createCullFace(GL20.GL_FRONT),//For some reason, libgdx ModelBuilder makes boxes with faces wound in reverse, so cull FRONT
			  //  new BlendingAttribute(1f), //opaque since multiplied by vertex color
			    new DepthTestAttribute(true), //don't want depth mask or rear cubes might not show through
			    ColorAttribute.createDiffuse(Color.WHITE));
		Material mat2 = mat1.copy();
		Material mat3 = mat1.copy();
		Material mat4 = mat1.copy();
		Material mat5 = mat1.copy();
		mat2.set(ColorAttribute.createDiffuse(Color.PINK));
		mat3.set(ColorAttribute.createDiffuse(Color.BROWN));
		mat4.set(ColorAttribute.createDiffuse(Color.WHITE));
		mat5.set(ColorAttribute.createDiffuse(Color.BLACK));
		switch(id) {
			case 1: return mat1;
			case 2: return mat2;
			case 3: return mat3;
			case 4: return mat4;
			default: return mat1;
		}
	}
	
}
