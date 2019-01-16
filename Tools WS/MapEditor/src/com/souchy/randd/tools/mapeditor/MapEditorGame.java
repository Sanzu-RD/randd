package com.souchy.randd.tools.mapeditor;

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
	public static MapCache mapCache;
	
	public static EditorScreen screen;
	public static Skin skin;
	public static float cellSize = 5;
	
	public static EditorProperties properties;

	@Override
	public void onCreateHook() {
		properties = new EditorProperties();
		properties.load();

		
		skin = new Skin(Gdx.files.internal("ui/common/uiskin.json"));
		mapCache = new MapCache(); //"data/maps/", FileType.Internal);
		System.out.println("on create :" + mapCache.getRoot());
		screen = new EditorScreen();
		screen.create();
		
		currentFile.set(Gdx.files.internal("data/maps/map1.map"));
		loadMap();
	}
	
	@Override
	public void dispose() {
		properties.save(); // save on exit
		super.dispose();
	}
	
	@Override
	public Screen getStartScreen() {
		return screen;
	}

	public static void loadMap() {
		screen.getWorld().clearCache();
		//screen.getWorld().clearTemp();
		if(currentFile.get() != null) {
			String path = currentFile.get().file().getAbsolutePath();
			MapData data = MapEditorGame.mapCache.get(path);
			MapEditorGame.currentMap.set(data);

			//MapData data = currentMap.get(); //cache.get("data/maps/map1.map");
			for(int x = 0; x < data.models.length; x++) {
				for(int y = 0; y < data.models[x].length; y++) {
					int modelID = data.getModel(x, y);
					int z = data.getElevation(x, y);
					boolean walk = data.isWalkable(x, y);
					boolean los = data.isLos(x, y);
					
					if(modelID != MatrixFlags.PositionningFlags.NoFlag.getID()) {
						//Model model = manager.get("data/cellModels/" + modelID + ".g3dj");
						ModelInstance instance = testModelGenerator(modelID); //new ModelInstance(model);
						instance.transform.setTranslation(new Vector3(cellSize * x + cellSize/2, cellSize * y + cellSize/2, z * cellSize - cellSize/2));
						screen.getWorld().addToCache(instance);
						
						EbiCell c = new EbiCell(x, y, z, walk, los, instance);
						//board.getCells().put(i, j, c);
					}
				}
			}
		}
		screen.resetCam();
		Gdx.graphics.getClass();
	}
	
	private static ModelInstance testModelGenerator(int id) {
		ModelBuilder builder = new ModelBuilder();
		long attributes = Usage.Position | Usage.Normal; // | Usage.ColorPacked;
		Model model = builder.createBox(cellSize, cellSize, cellSize, getMat(id), attributes);
		ModelInstance instance = new ModelInstance(model);
		//instance.transform.set(new Vector3(5 * i + 1f, 5 * j + 1f, 0), new Quaternion(0, 0, 0, 0));
		//getWorld().cache.add(instance);
		return instance;
	}
	
	private static Material getMat(int id) {
		Material mat1 = new Material(
				//IntAttribute.createCullFace(GL20.GL_FALSE), //new IntAttribute(IntAttribute.CullFace, 0),
			    //IntAttribute.createCullFace(GL20.GL_BACK),//For some reason, libgdx ModelBuilder makes boxes with faces wound in reverse, so cull FRONT
			   // new BlendingAttribute(1f), //opaque since multiplied by vertex color
			   // new DepthTestAttribute(true), //don't want depth mask or rear cubes might not show through
			    ColorAttribute.createDiffuse(Color.valueOf("AEE897")));
		Material mat2 = mat1.copy();
		Material mat3 = mat1.copy();
		Material mat4 = mat1.copy();
		Material mat5 = mat1.copy();
		mat2.set(ColorAttribute.createDiffuse(Color.SKY));
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
