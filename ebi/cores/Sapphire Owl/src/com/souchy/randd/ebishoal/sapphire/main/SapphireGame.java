package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.sapphire.ui.SapphireScreen;

import gamemechanics.models.Fight;

public class SapphireGame extends LapisGame {
	
	public Skin skin;
	
	public SapphireScreen gfx;
	//public FightContext context;
	public Fight context;
	
	@Override
	public void onCreateHook() {

		skin = new Skin(Gdx.files.internal("res/gdx/ui/uiskin.json"));
		System.out.println("skin =" + skin);
		//labelStyles.add(new LabelStyle());
		
		// All graphics
		gfx = new SapphireScreen(); //gfx = new GameScreen();
		//gfx.create();
		
		
		// Might use or not 
		//Injector injector = Guice.createInjector(new SapphireModule());
		
		/*
		// Context board and World model cache
		FightContext context = new FightContext();
		IBoard board = context.board;
		
		
		// Loading assets / models
		AssetManager manager = new AssetManager();
		List<FileHandle> modelFiles = modelDiscoverer.explore("data/cellModels");
		modelFiles.forEach(f -> {
			manager.load(f.name(), Model.class);
		});
		
		// testGenerateMapFile();
		
		// Loading a map
		MapCache cache = new MapCache();
		String path = Gdx.files.internal("data/maps/map1.json").file().getAbsolutePath();
		MapData data = cache.get(path); //"data/maps/map1.json");
		gfx.getWorld().cache.begin();
		for(int i = 0; i < data.cells.length; i++) {
			for(int j = 0; j < data.cells[i].length; j++) {
				int modelID = data.getModel(i, j);
				int height = data.getElevation(i, j);
				boolean walk = data.isWalkable(i, j);
				boolean los = data.isLos(i, j);
				
				if(modelID != MatrixFlags.PositionningFlags.NoFlag.getID()) {
					//Model model = manager.get("data/cellModels/" + modelID + ".g3dj");
					ModelInstance instance = testModelGenerator(modelID); //new ModelInstance(model);
					
					EbiCell c = new EbiCell(i, j, height, walk, los, instance);
					instance.transform.set(new Vector3(5 * i, 5 * j, height * 5), new Quaternion(0, 0, 0, 0));
					
					board.getCells().put(i, j, c);
					gfx.getWorld().cache.add(c.model);
				}
			}
		}
		gfx.getWorld().cache.end();*/
	}
	
	public static ModelInstance testModelGenerator(int id) {
		ModelBuilder builder = new ModelBuilder();
		long attributes = Usage.Position | Usage.Normal | Usage.ColorPacked;
		Model model = builder.createBox(5f, 5f, 5f, getMat(id), attributes);
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
			    ColorAttribute.createDiffuse(Color.GREEN));
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


	@Override
	public Screen getStartScreen() {
		return gfx;
	}
	
}
