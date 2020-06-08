package com.souchy.randd.ebishoal.sapphire.main;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.Meshing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

import gamemechanics.ext.MapData;

public class SapphireWorld extends World {
	
	public static SapphireWorld world;
	
	private AssetManager assets = new AssetManager();
	
	private ModelBuilder modelBuilder = new ModelBuilder();
	
	private int cellSize = 1;
	
	/*
	 * Types of models I can have : 
	 * 		Terrain blocks (cells)
	 * 		Creatures
	 * 		Glyphs
	 * 		Traps
	 *		Highlighting area (ex: previsualising/targetting a spell aoe)
	 */
	
	public ModelInstance cursor;

	
	public SapphireWorld() {
		world = this;
		
		Model cursorModel = LapisAssets.assets.get("res/models/tileselector.g3dj");
		cursor = new ModelInstance(cursorModel);
		cursor.materials.get(0).set(ColorAttribute.createDiffuse(Color.CYAN));
		var scale = 1f / 24f;
		cursor.transform.setToTranslation(0, 0, 0).scale(scale, scale, scale).rotate(Vector3.X, 90);
		this.instances.add(cursor);
		
		
		/*
		String devPath = "gdx/g3d/";
		String deployPath = "res/" + devPath;
		String path = new File(devPath).exists() ? devPath : deployPath;

		// model files
		String cubeFile = path + "board/cell_models/cube.obj"; // roundedCube05
		String treeFile = path + "object_models/pinetree.obj";

		// load models
		assets.load(cubeFile, Model.class);
		assets.load(treeFile, Model.class);
        assets.finishLoading();
        var names = assets.getAssetNames();
        Log.info("loaded assets :" + names);
        
        var normalColor = Color.WHITE;
        var shading = 0.15f;
        
        // models
       
        var cube = assets.get(cubeFile, Model.class);
        var pineTree = assets.get(treeFile, Model.class);
        
        var cubeNormal1 = createModel(normalColor); 
        var cubeNormal2 = createModel(new Color(normalColor.r-shading, normalColor.g-shading, normalColor.b-shading, 1f)); 
        cubeNormal2.materials.get(0).set(ColorAttribute.createDiffuse(0.5f, 0.5f, 0.5f, 1f));
        var cubeBlock = createModel(Color.SKY);
        var cubeFloor = createModel(Color.SKY);
        var cubePink = createModel(Color.PINK);
        */
        
        
        // create instances
        String mapFolder = "res/maps/"; //"F:/Users/Souchy/Desktop/Robyn/eclipse-workspaces/hidden workspaces/r and d/Maps/data/maps/";
        MapData data = MapData.read(Gdx.files.internal(mapFolder + "goulta7b.map").path());
        this.center = new Vector3(data.cellModels[0].length / 2f, data.cellModels.length / 2f, 0);
        
        if(true) {
            cache.begin();
        	
        	// create greedy mesh for texture-type cells
            var greed = Meshing.greedy(data, cellSize, GL20.GL_TRIANGLES);
    		cache.add(new ModelInstance(greed));
    		// add every other models as instances

    		// adds singular models
    		if(true) {
	    		Consumer<int[][]> generateModels = layer -> {
	                for(int i = 0; i < layer[0].length; i++) {
	                    for(int j = 0; j < layer.length; j++) {
	                    	var m = data.getModel(layer[i][j]);
	                    	if(m != null && !m.isVoxel()) {
	                    		assets.load(m.model, Model.class);
	                    		assets.finishLoading();
	                    		var model = assets.get(m.model, Model.class);
	                    		model.materials.get(0).set(ColorAttribute.createDiffuse(Color.valueOf(m.colorAttributes[0])));
	                    		var inst = new ModelInstance(model);
	                    		inst.transform
	        					.translate(i * cellSize + cellSize * m.transform[0][0], j * cellSize + cellSize * m.transform[0][1], -cellSize + cellSize * m.transform[0][2])
	        					.rotate(m.transform[1][0],  m.transform[1][1],  m.transform[1][2], 90)
	        					.scale( m.transform[2][0],  m.transform[2][1],  m.transform[2][2]);
	                    		cache.add(inst);
	                    	}
	                    }
	                }
	    		};
	    		
	    		generateModels.accept(data.layer0Models);
	    		generateModels.accept(data.cellModels);
	    		generateModels.accept(data.layer2Models);
	    		
    		}
    		if(false) {
        		// water plane
        		var water = waterplane();
        		//water.transform.rotate(0, 0, 1, 45);
        		cache.add(water);
    		}

            cache.end();
        } 
        
	}

	
	private ModelInstance waterplane() {
		int renderType = GL20.GL_TRIANGLES;
		float r = 0, g = 0, b = 0;
		var areaColor = new Color(r, g, b, 0.0f);
		//var borderColor = new Color(r, g, b, 1f);
		Material mat = new Material(ColorAttribute.createDiffuse(areaColor), new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
		Model highlight = new Model();
		Vector3 size = new Vector3(500, 500, 0);
		Vector3 pos = new Vector3(-size.x/2f, -size.y/2f, 0.3f);
		Vector3 normal = new Vector3(0, 0, 1);
		
		int vertexPropCount = 6; // nombre de propriété par vertex (pos.xyz + norm.xyz = 6)
		
		var vertices = new float[] { 
				pos.x, 			pos.y, 			pos.z, normal.x, normal.y, normal.z, 
				pos.x + size.x, pos.y, 			pos.z, normal.x, normal.y, normal.z,
				pos.x + size.x, pos.y + size.y, pos.z, normal.x, normal.y, normal.z, 
				pos.x, 			pos.y + size.y, pos.z, normal.x, normal.y, normal.z, 
		};
		var indices = new short[] { 0, 1, 2, 2, 3, 0 };
		Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
		mesh.setVertices(vertices);
		mesh.setIndices(indices);

		Log.info("mesh vertices " + mesh.getNumVertices() + " / " + mesh.getMaxVertices());
		Log.info("mesh indices " + mesh.getNumIndices() + " / " + mesh.getMaxIndices());
		
		String id = "water";
		// if(mat == null) mat = mat3;
		MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, indices.length, renderType);
		Node node = new Node();
		node.id = "node_" + id;
		node.parts.add(new NodePart(meshPart, mat));
		
		highlight.meshes.add(mesh);
		highlight.meshParts.add(meshPart);
		highlight.nodes.add(node);
		
		/*
		float borderRadius = 0.03f;
		Material borderMat = new Material(ColorAttribute.createDiffuse(borderColor));
		var c = vertices.length - vertexPropCount;
		for (int i = 0; i < c + 1; i += vertexPropCount) {
			int i2 = (i != c) ? i + vertexPropCount : 0;
			Vector3 v1 = new Vector3(vertices[i], vertices[i + 1], vertices[i + 2]);
			Vector3 v2 = new Vector3(vertices[i2], vertices[i2 + 1], vertices[i2 + 2]);
			
			// direction du vecteur 1 au vecteur 2
			Vector3 dir = new Vector3(v2.x - v1.x, v2.y - v1.y, 0).nor();
			Vector3 pDir = dir.cpy().crs(0, 0, 1); // vecteur perpendiculaire à la direction
			
			// vu qu'on va du v1 au v2, on sait dans quel sens offset les points pour créer
			// la bordure
			var b1 = new Vector3(v1.x, v1.y, v1.z);
			b1.sub(dir.cpy().scl(borderRadius));
			b1.add(pDir.cpy().scl(borderRadius));
			var b2 = new Vector3(v1.x, v1.y, v1.z);
			b2.sub(dir.cpy().scl(borderRadius));
			b2.sub(pDir.cpy().scl(borderRadius));
			var b3 = new Vector3(v2.x, v2.y, v2.z);
			b3.add(dir.cpy().scl(borderRadius));
			b3.add(pDir.cpy().scl(borderRadius));
			var b4 = new Vector3(v2.x, v2.y, v2.z);
			b4.add(dir.cpy().scl(borderRadius));
			b4.sub(pDir.cpy().scl(borderRadius));
			
			var verticesBorder = new float[] { 
					b2.x, b2.y, b2.z, normal.x, normal.y, normal.z, 
					b1.x, b1.y, b1.z, normal.x, normal.y, normal.z, 
					b3.x, b3.y, b3.z, normal.x, normal.y, normal.z, 
					b4.x, b4.y, b4.z, normal.x, normal.y, normal.z, 
			};
			var indicesBorder = new short[] { 0, 1, 2, 2, 3, 0 };
			
			
			Mesh meshBorder = new Mesh(true, verticesBorder.length, indicesBorder.length, VertexAttribute.Position(), VertexAttribute.Normal());
			meshBorder.setVertices(verticesBorder);
			meshBorder.setIndices(indicesBorder);
			
			Log.info("border vertices " + meshBorder.getNumVertices() + " / " + meshBorder.getMaxVertices());
			Log.info("border indices " + meshBorder.getNumIndices() + " / " + meshBorder.getMaxIndices());
			
			String idBorder = "border" + i + "_" + i2;
			MeshPart meshPartBorder = new MeshPart("meshpart_" + idBorder, meshBorder, 0, indicesBorder.length, renderType);
			Node nodeBorder = new Node();
			nodeBorder.id = "node_" + idBorder;
			nodeBorder.parts.add(new NodePart(meshPartBorder, borderMat));
			
			highlight.meshes.add(meshBorder);
			highlight.meshParts.add(meshPartBorder);
			highlight.nodes.add(nodeBorder);
			
		}
		*/
		
		return /* cellHighlighterInst = */ new ModelInstance(highlight);
	}
	
	
	/*
    private Model createModel(Color color) {
    	var pix = new Pixmap(512, 512, Format.RGBA8888);
    	pix.setColor(color);
    	pix.fill();
    	Texture tex = new Texture(pix);
    	tex.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
    	//tex.setFilter(TextureFilter.MipMap, TextureFilter.Nearest);
    	var colorMat = new Material(TextureAttribute.createDiffuse(tex));
    	
    	Texture terracotta = new Texture(Gdx.files.absolute("G://Assets//test//glazedTerracotta.png"));
    	var terracottaMat = new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.absolute("G://Assets//test//glazedTerracotta.png"))));

    	Texture grass = new Texture(Gdx.files.absolute("G://Assets//test//grass512x512.png"));
    	var grassMat = new Material(TextureAttribute.createDiffuse(grass));
    	
    	var mats = new Material[] {colorMat, grassMat, terracottaMat};
    	
    	Model model = modelBuilder.createBox(
    			cellSize, cellSize, cellSize, 
    			terracottaMat, //	colorMat, // mats[rnd.nextInt(mats.length)], //new Material(ColorAttribute.createDiffuse(color)),  // new Material(ta), //
    			//new Material(ColorAttribute.createDiffuse(color)), 
    			Usage.Position | Usage.Normal | Usage.TextureCoordinates);
    	// model.meshParts.forEach(m -> m.primitiveType = GL20.GL_LINES);
    	return model;
    }
	*/
	
}
