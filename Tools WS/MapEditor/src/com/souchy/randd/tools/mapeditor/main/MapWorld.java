package com.souchy.randd.tools.mapeditor.main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
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
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;
import com.souchy.randd.tools.mapeditor.shaderimpl.DissolveUniforms;
import com.souchy.randd.tools.mapeditor.shaderimpl.DissolveUniforms.DissolveMaterial;

public class MapWorld extends World {

	public static MapWorld world;
	private static final int cellSize = (int) Constants.cellWidth;
	private static final ModelBuilder builder = new ModelBuilder();
	private static final Material matWhite = new Material(ColorAttribute.createDiffuse(Color.WHITE));
	private static Model modelBox;
	private static Model modelCapsule;
	private static Model modelPlane;
	private static Model modelSphere;
	
	static {
		int usage = Usage.Position | Usage.Normal | Usage.ColorUnpacked | Usage.TextureCoordinates;
		modelBox = builder.createBox(1, 1, 1, matWhite, usage);
		modelCapsule = builder.createCapsule(0.5f, 1f, 16, matWhite, usage);
		modelPlane = quad("Plane", matWhite);
		modelSphere = builder.createSphere(1, 1, 1, 16, 16, matWhite, usage, 0, 360, 0, 360);
	}
	
	
	private boolean loaded;
	public ModelInstance cursor;
	/**
	 * This is just for the ObjectsTree view
	 */
	public List<ModelInstance> cachedInstances = new ArrayList<>();
	
	public MapWorld() {
		world = this;
	}

//	public void finishLoading() {
//		if(cursor == null || !loaded) loadCursor();
//	}
	
	public void loadCursor() {
		String modelPath = "res/models/tileselector.g3dj";
		if(!LapisAssets.contains(modelPath, Model.class)) 
			LapisAssets.loadModels(Gdx.files.internal(modelPath));
		Model cursorModel = LapisAssets.get(modelPath, Model.class);  // res/models/creatures/Marian.g3dj");
		loaded = (cursorModel != LapisAssets.defaultModel);

		var scale = loaded ? 1f / 24f : 1f;
		if(cursor == null || loaded) {
			if(cursor != null) {
				//instances.remove(cursor);
				EditorEntities.removeAdaptor(cursor);
			}
			// Cursor
			cursor = new ModelInstance(cursorModel);
			cursor.materials.get(0).set(ColorAttribute.createDiffuse(Color.TEAL));
			cursor.transform.setToTranslation(0, 0, 0).scale(scale, scale, scale).rotate(Vector3.X, 90);
			//this.instances.add(cursor);
			EditorEntities.addAdaptor(cursor);
		}

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
		//instances.add(tree);
		EditorEntities.addAdaptor(tree);
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
		loadCursor();
		var data = MapEditorGame.currentMap.get();
        cache.begin();
    	// create greedy mesh for texture-type cells
        var greed = Meshing.greedy(data, cellSize, GL20.GL_TRIANGLES);
        // add dissolve to the main material
        // if we added the material to the list of mats, we would need to apply it to the mesh parts
        var greedInstance = new ModelInstance(greed);
//		var dissolve = new DissolveUniforms.DissolveMaterial(Color.BLACK, 0.05f, 0.5f);
//        greedInstance.materials.get(0).set(dissolve);
		
		cache.add(greedInstance);
		genModels(data);
		cache.end();
	}
	
	private void genModels(MapData data) {
		var dissolve = new DissolveUniforms.DissolveMaterial(Color.CYAN, 0.05f, 0.5f);
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
                    	
                		var inst = new ModelInstance(model);
                		
                		inst.materials.get(0).set(albedo);
                		inst.materials.get(0).set(blend);
                		inst.materials.get(0).set(reflection);
                		inst.materials.get(0).set(shine); 
                		//inst.materials.get(0).set(dissolve);
                		
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

	
	
	public void translateCursor(float x, float y, float z) {
		if(world.cursor != null)
			world.cursor.transform.setTranslation(x + Constants.cellHalf, y + Constants.cellHalf, z);
	}
	
	public void addInstance(String modelPath, Vector3 pos) {
		var model = LapisAssets.get(modelPath, Model.class);
		
		//model = new ModelBuilder().createCylinder(0.5f, 1, 1, 16, new DissolveMaterial(), Usage.Position | Usage.Normal | Usage.TextureCoordinates | Usage.ColorPacked);
		
		if(model != LapisAssets.defaultModel){
			var inst = new ModelInstance(model);

			inst.materials.get(0).set(ColorAttribute.createDiffuse(Color.PURPLE));
			inst.materials.get(0).set(new BlendingAttribute(1));
			inst.materials.get(0).set(new DissolveMaterial());
			
			//inst.nodes.get(0).parts.get(0).meshPart.mesh.getVertexAttribute(0);
			
			pos.add(Constants.cellHalf, 0, Constants.cellHalf);
			inst.transform.translate(pos);
			//MapWorld.world.instances.add(inst);
			EditorEntities.addAdaptor(inst);
		} else 
		if(!LapisAssets.contains(modelPath, Model.class)) {
			LapisAssets.loadModels(Gdx.files.internal(modelPath));
		}
	}
	public ModelInstance getAt(Vector3 pos) {
		pos.add(Constants.cellHalf, 0, Constants.cellHalf);
		var temp = new Vector3();
		for(var inst : EditorEntities.getInstances())
			if(inst != cursor && inst != MapEditorGame.screen.controller.getSelectedInstance() && inst.transform.getTranslation(temp).equals(pos))
				return inst;
		return null;
	}
	
	public void removeInstanceAt(Vector3 pos) {
		//instances.remove(getAt(pos));
		EditorEntities.removeAdaptor(getAt(pos));
		//pos.add(Constants.cellHalf, 0, Constants.cellHalf);
		//var temp = new Vector3();
		//instances.removeIf(inst -> inst != cursor && inst.transform.getTranslation(temp).equals(pos));
	}

	
	public static ModelInstance createBox() {
		ModelInstance inst = new ModelInstance(modelBox);
		EditorEntities.addAdaptor(inst);
		return inst;
	}
	public static ModelInstance createPlane() {
		ModelInstance inst = new ModelInstance(modelPlane);
		EditorEntities.addAdaptor(inst);
		return inst;
	}
	public static ModelInstance createCapsule() {
		ModelInstance inst = new ModelInstance(modelCapsule);
		EditorEntities.addAdaptor(inst);
		return inst;
	}
	public static ModelInstance createSphere() {
		ModelInstance inst = new ModelInstance(modelSphere);
		EditorEntities.addAdaptor(inst);
		return inst;
	}
	
	public static ModelInstance createLine(boolean entity, Vector3 from, Vector3 to, float width) {
		var m = quad("line", matWhite, from, from.cpy().add(width), to.cpy().add(width), to);
		var inst = new ModelInstance(m);
		if(entity) EditorEntities.addAdaptor(inst);
		return inst;
	}

	private static Model quad(String name, Material mat) {
		Model root = new Model();
		int renderType = GL20.GL_TRIANGLES;

		var vertices = new float[] {
				0, 0, 0, 	0, 0, 1, 	0, 0,
				1, 0, 0, 	0, 0, 1, 	1, 0,
				1, 1, 0, 	0, 0, 1, 	1, 1,
				0, 1, 0, 	0, 0, 1, 	0, 1
		};
    	var indices = new short[] {0,   1,   2,       2,   3,   0};
    	
		Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		
		MeshPart meshPart = new MeshPart(name + "_meshpart", mesh, 0, indices.length, renderType);
		Node node = new Node();
		node.id = name + "_node";
		node.parts.add(new NodePart(meshPart, mat));
		
		root.meshes.add(mesh);
		root.meshParts.add(meshPart);
		root.nodes.add(node);
		return root;
	}
	private static Model quad(String name, Material mat, Vector3 a, Vector3 b, Vector3 c, Vector3 d) {
		Model root = new Model();
		int renderType = GL20.GL_LINE_STRIP;
		
		var vertices = new float[] {
				a.x, a.y, a.z, 	0, 0, 1, 	0, 0,
				b.x, b.y, b.z, 	0, 0, 1, 	1, 0,
				c.x, c.y, c.z, 	0, 0, 1, 	1, 1,
				d.x, d.y, d.z, 	0, 0, 1, 	0, 1
		};
		
    	var indices = new short[] {0,   1,   2,       2,   3,   0};
    	
		Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		
		MeshPart meshPart = new MeshPart(name + "_meshpart", mesh, 0, indices.length, renderType);
		Node node = new Node();
		node.id = name + "_node";
		node.parts.add(new NodePart(meshPart, mat));
		
		root.meshes.add(mesh);
		root.meshParts.add(meshPart);
		root.nodes.add(node);
		return root;
	}
	
	public static void meshLightning() {
		
	}
	
}
