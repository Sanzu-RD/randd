package com.souchy.randd.tools.mapeditor.main;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.souchy.randd.commons.mapio.CellData;
import com.souchy.randd.commons.mapio.MapData;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.Meshing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.jade.Constants;

public class MapWorld extends World {

	private static final int cellSize = (int) Constants.cellWidth;
	public static MapWorld world;
	
	public MapWorld() {
		world = this;
	}
	
	public void tree() {
		String name = "tree1";
		FileHandle file = Gdx.files.internal("g3d/object_models/" + name + ".g3dj");
		var f = file.file();
		var s = f.getAbsolutePath();
		System.out.println("---- " + file.file().exists());
		System.out.println("---- " + file.file().getAbsolutePath());
		LapisAssets.loadModels(file);

		var obj = LapisAssets.get(name, Model.class);
		System.out.println(obj);
		
		// obj.meshes.forEach(m -> m.scale(2, 2, 2));
		BoundingBox bb = obj.calculateBoundingBox(new BoundingBox());
		System.out.println(bb);
		
		ModelInstance tree = new ModelInstance(obj);
		//tree.nodes.forEach(n -> n.parts.forEach(np -> np.meshPart.););
		//getBatch().render(tree);
		
		tree.transform.translate(new Vector3(3, 7, 0)); //.scl(MapEditorGame.cellSize));
		tree.transform.rotateRad(Vector3.X, (float) (Math.PI/2));
		instances.add(tree);
	}

	/*
	public static void loadMap() {
		float cellSize = Constants.cellWidth;
		//MapEditorGame.screen.getWorld().clearCache();
		//screen.getWorld().clearTemp();
		if(MapEditorGame.currentFile.get() != null) {
			String path = MapEditorGame.currentFile.get().file().getAbsolutePath();
			//MapData data = MapEditorGame.mapCache.get(path);
			MapData data = MapData.read(path);
			MapEditorGame.currentMap.set(data);

			//MapData data = currentMap.get(); //cache.get("data/maps/map1.map");
			for(int x = 0; x < data.cells.length; x++) {
				for(int y = 0; y < data.cells[x].length; y++) {
//					int modelID = data.getModel(x, y);
//					int z = data.getElevation(x, y);
//					boolean walk = data.isWalkable(x, y);
//					boolean los = data.isLos(x, y);
					
					if(modelID != MatrixFlags.PositionningFlags.NoFlag.getID()) {
						//Model model = manager.get("data/cellModels/" + modelID + ".g3dj");
						ModelInstance instance = testModelGenerator(modelID); //new ModelInstance(model);
						instance.transform.setTranslation(new Vector3(cellSize * x + cellSize/2, cellSize * y + cellSize/2, z * cellSize - cellSize/2));
						
						MapEditorGame.screen.getWorld().addToCache(instance);
						
						//EbiCell c = new EbiCell(x, y, instance); //new EbiCell(x, y, z, walk, los, instance);
						//board.getCells().put(i, j, c);
					}
				}
			}
		}
		MapEditorGame.screen.resetCamera();
		Gdx.graphics.getClass();
	}
	*/
	
	public static int worldWidth() {
		if(MapEditorGame.currentMap.get() == null) return 25;
		return MapEditorGame.currentMap.get().cellModels[0].length;
	}
	public static int worldHeight() {
		if(MapEditorGame.currentMap.get() == null) return 25;
		return MapEditorGame.currentMap.get().cellModels.length;
	}
	
	private void pregen() {
		String path = MapEditorGame.currentFile.get().file().getAbsolutePath();
		MapData data = MapData.read(path);
		MapEditorGame.currentMap.set(data);
		
        // World map
        //String mapFolder = "res/maps/"; //"F:/Users/Souchy/Desktop/Robyn/eclipse-workspaces/hidden workspaces/r and d/Maps/data/maps/";
        //MapData data = MapData.read(Gdx.files.internal(mapFolder + "goulta7b.map").path());
        this.center = new Vector3(data.cellModels[0].length / 2f, data.cellModels.length / 2f, 0);

		data.foreachModel(m -> {
        	if(!m.isVoxel()) 
        		if(!LapisAssets.contains(m.model, Model.class)) {
        			LapisAssets.loadModels(Gdx.files.internal(m.model));
        		}
		});
	}
	
	private boolean loading = true;
	public void gen() {
		pregen();
		finish();
		loading = true;
	}
	
	public void finish() {
		if(loading == false) return;
		loading = false;
		var data = MapEditorGame.currentMap.get();
        cache.begin();
    	// create greedy mesh for texture-type cells
        var greed = Meshing.greedy(data, cellSize, GL20.GL_TRIANGLES);
        //greed.materials.forEach(m -> m.set(shine));
		cache.add(new ModelInstance(greed));
		genModels(data);
		cache.end();
	}
	
	private void genModels(MapData data) {
		Consumer<int[][]> generateModels = layer -> {
            for(int i = 0; i < layer[0].length; i++) {
                for(int j = 0; j < layer.length; j++) {
                	var m = data.getModel(layer[i][j]);
                	if(m != null && !m.isVoxel()) {
//                		if(!LapisAssets.assets.isLoaded(m.model))
//                			LapisAssets.assets.finishLoadingAsset(m.model);
//                		var model = LapisAssets.assets.get(m.model, Model.class);
                		//if(!LapisAssets.contains(m.model, Model.class)) 
                		//	LapisAssets.loadModels(Gdx.files.internal(m.model)); //assets.load(m.model, Model.class);
                		//assets.finishLoadingAsset(m.model); //.finishLoading();
                		Model model = LapisAssets.get(m.model, Model.class); // assets.get(m.model, Model.class); 
                		
                		var albedo = ColorAttribute.createDiffuse(Color.valueOf(m.colorAttributes[0])); // fonctionne
                    	// fonctionne, mais jsais pas si le shine fit avec mon environnement
                    	var shiny = 0f;
                    	var shine = ColorAttribute.createSpecular(shiny, shiny, shiny, shiny); 
                    	var blend = new BlendingAttribute(0.5f); // fonctionne
                    	var reflection = ColorAttribute.createReflection(1, 1, 1, 1); //  fontionne pas
                    	
                		model.materials.get(0).set(albedo);
                		model.materials.get(0).set(blend);
                		model.materials.get(0).set(reflection);
                		model.materials.get(0).set(shine); 
                		
                		var inst = new ModelInstance(model);
                		inst.transform
    					.translate(
    							i * cellSize + cellSize * m.transform[0][0], 
    							j * cellSize + cellSize * m.transform[0][1], 
    							   -cellSize + cellSize * m.transform[0][2])
    					.rotate(m.transform[1][0],  m.transform[1][1],  m.transform[1][2], 90)
    					.scale (m.transform[2][0],  m.transform[2][1],  m.transform[2][2]);
                		 
                		//Log.debug("MapWorld inst " + inst);
                		cache.add(inst);
                	}
                }
            }
		};
		
		generateModels.accept(data.layer0Models);
		generateModels.accept(data.cellModels);
		generateModels.accept(data.layer2Models);
	}
	
	
	private static ModelInstance testModelGenerator(int id) {
		float cellSize = Constants.cellWidth;
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
