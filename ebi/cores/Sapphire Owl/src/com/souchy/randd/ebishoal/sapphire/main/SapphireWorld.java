package com.souchy.randd.ebishoal.sapphire.main;

import java.util.function.Consumer;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.ebishoal.commons.lapis.world.Meshing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

import gamemechanics.ext.MapData;

@SuppressWarnings("unused")
public class SapphireWorld extends World {
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
	
	
	public SapphireWorld() {
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
        String mapFolder = "F:/Users/Souchy/Desktop/Robyn/eclipse-workspaces/hidden workspaces/r and d/Maps/data/maps/";
        MapData data = MapData.read(mapFolder + "goulta7.map");
        this.center = new Vector3(data.cellModels[0].length / 2f, data.cellModels.length / 2f, 0);
        
        if(true) {
        	// add the voxels as an instance
            var greed = Meshing.greedy(data, cellSize, GL20.GL_TRIANGLES);
    		instances.add(new ModelInstance(greed));
    		// add every other models as instances
    		
    		Vector3 origin = Vector3.Zero;
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
                    		instances.add(inst);
                    	}
                    }
                }
    		};
    		
    		generateModels.accept(data.layer0Models);
    		generateModels.accept(data.cellModels);
    		generateModels.accept(data.layer2Models);
            
        } 
        
        cache.begin();
        for(var instance : instances)
            cache.add(instance);
        cache.end();
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
