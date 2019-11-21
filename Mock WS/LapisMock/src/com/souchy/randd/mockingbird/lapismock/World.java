package com.souchy.randd.mockingbird.lapismock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class World {
	

	private AssetManager assets = new AssetManager();
	private final Random rnd = new Random();
    public final Environment env;
    public List<ModelInstance> instances;
    public List<Renderable> renderables;
    public ModelCache cache;
    public ModelBuilder modelBuilder = new ModelBuilder();
    public float cellSize = 1.0f;
    public float offset = 0.0001f;
    
    public ModelInstance merge;
    
    public World(Environment environment) {
    	env = environment;
        instances = new ArrayList<>();
        renderables = new ArrayList<>();
        cache = new ModelCache();

        createWorld();
    }
    
    public void createWorld() {
    	var sum = cellSize; // + offset;
		
		//String file = "models/cube.obj";
		String file = "delete/roundedCube05.g3dj";
		String treeFile1 = "models/decor/pinetree.obj";
		String penguinFile = "F:/Users/Souchy/Desktop/Robyn/Git/res/assets/models/characters/playables/penguin.g3dj";
		String snowTextureFile = "delete/snow_ice.png";
		String cliffFile = "F:/Users/Souchy/Desktop/Robyn/Git/res/assets/models/scenery/3d/clifftile.obj";
        //ModelLoader loader = new ObjLoader();
        //model = loader.loadModel(Gdx.files.internal(file));

        assets.load(file, Model.class);
        assets.load(treeFile1, Model.class);
        assets.load(snowTextureFile, Texture.class);
        assets.load(cliffFile, Model.class);
        assets.load(penguinFile, Model.class);
        assets.finishLoading();
        
        var names = assets.getAssetNames();
        System.out.println(names);
        
		Model model;
		model = createModel(Color.valueOf("AEE897"));
		//model = snowModel(Color.WHITE);
       // model = assets.get(file);
		//model = assets.get(cliffFile);
        
        //model.materials.clear();
        //model.materials.add(new Material(ColorAttribute.createDiffuse(Color.GREEN)));

		var rng = new Random();
        int side = 50;
		for(int x = 0; x < side; x++) {
			for(int y = 0; y < side; y++) {
			//	if(x == side/2 && y == side/2) continue;
			//	if(rng.nextBoolean()) continue;
				//Vector3 pos = new Vector3(sum * x + sum/2, sum * y + sum/2, 0);//z * cellSize - cellSize/2));
				
				//model = snowModel(Color.WHITE); //(x + y) % 2 == 0 ? new Color(0.9f,0.9f,0.9f,0.9f) : Color.WHITE);
				Vector3 pos = new Vector3(sum * x, sum * y,  0);//z * cellSize - cellSize/2));
				addInstance(model, pos);
			}
		}
		addInstance(createModel(Color.SKY), new Vector3(side/3, side/3, 2));
		addInstance(createModel(Color.SKY), new Vector3(0, 1, 1)); // Y
		addInstance(createModel(Color.SKY), new Vector3(1, 0, 1)); // X
		//ModelLoader a = new ObjLoader();
		//Model penguin = a.loadModel(Gdx.files.absolute(treeFile1));
		
	//	addInstance(assets.get(treeFile1), new Vector3(1, 1, 0), true);
		
		/*modelBuilder.begin();
		for(int i = 0; i < instances.size(); i++) {
			var inst = instances.get(i);
			var m = inst.model;
			m.meshes.get(0).transform(inst.transform);
			modelBuilder.node("i"+i, m);
		}
		merge = new ModelInstance(modelBuilder.end());*/

		cache.begin();
		cache.add(instances);
		cache.end();
    }
    
    
    public BoundingBox getBoundingBox() {
    	Vector3 min = new Vector3();
    	Vector3 max = new Vector3();
		Vector3 v = new Vector3();
    	instances.forEach(inst -> {
    		inst.transform.getTranslation(v);
    		if(v.x > max.x) {
    			max.x = v.x;
    		}
    		if(v.x < min.x) {
    			min.x = v.x;
    		}
    		if(v.y > max.y) {
    			max.y = v.y;
    		}
    		if(v.y < min.y) {
    			min.y = v.y;
    		}
    		if(v.z > max.z) {
    			max.z = v.z;
    		}
    		if(v.z < min.z) {
    			min.z = v.z;
    		}
    	});
    	return new BoundingBox(min, max);
    }
    
    
    private Model createModel(Color color) {
    	var pix = new Pixmap(512, 512, Format.RGBA8888);
    	pix.setColor(color);
    	pix.fill();
    	Texture tex = new Texture(pix);
    	tex.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
    	//tex.setFilter(TextureFilter.MipMap, TextureFilter.Nearest);
    	var colorMat = new Material(TextureAttribute.createDiffuse(tex));
    	
    	Texture terracotta = new Texture(Gdx.files.absolute("G:\\Assets\\test\\glazedTerracotta.png"));
    	var terracottaMat = new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.absolute("G:\\Assets\\test\\glazedTerracotta.png"))));

    	Texture grass = new Texture(Gdx.files.absolute("G:\\Assets\\test\\grass512x512.png"));
    	var grassMat = new Material(TextureAttribute.createDiffuse(grass));
    	
    	var mats = new Material[] {colorMat, grassMat, terracottaMat};
    	
    	Model model = modelBuilder.createBox(
    			cellSize, cellSize, cellSize, 
    			colorMat, // mats[rnd.nextInt(mats.length)], //new Material(ColorAttribute.createDiffuse(color)),  // new Material(ta), //
    			//new Material(ColorAttribute.createDiffuse(color)), 
    			Usage.Position | Usage.Normal | Usage.TextureCoordinates);
    	// model.meshParts.forEach(m -> m.primitiveType = GL20.GL_LINES);
    	return model;
    }
    
    private Model snowModel(Color top) {
    	var pixCyan = new Pixmap(64, 64, Format.RGBA8888);
    	pixCyan.setColor(Color.CYAN);
    	pixCyan.fill();
    	Material mat1 = new Material(ColorAttribute.createDiffuse(top));
    	Material mat2 = new Material(TextureAttribute.createDiffuse(new Texture(pixCyan)));
    	Texture snowTex = assets.get("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/g3d/board/textures/snow_ice.png");
    	Material mat3 = new Material(TextureAttribute.createDiffuse(snowTex));
    	
    	if(true) {
    	//	mat3 = mat2;
    	}
    	int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
    	float d = 0.5f;
    	modelBuilder.begin();
    	modelBuilder.part("top", GL20.GL_TRIANGLES, attr, mat1).rect(-d,d,d, -d,-d,d,  d,-d,d, d,d,d, 0,0,1);
//    	modelBuilder.part("bottom", GL20.GL_TRIANGLES, attr, mat2).rect(-d,-d,-d, -d,d,-d,  d,d,-d, d,-d,-d, 0,0,-1);
//    	modelBuilder.part("front", GL20.GL_TRIANGLES, attr, mat3).rect(-d,-d,-d, d,-d,-d, d,-d,d, -d,-d,d, 0,-1,0);
//    	modelBuilder.part("back", GL20.GL_TRIANGLES, attr, mat3).rect(d,d,-d, -d,d,-d, -d,d,d,  d,d,d, 0,1,0);
//    	modelBuilder.part("left", GL20.GL_TRIANGLES, attr, mat3).rect(-d,d,-d, -d,-d,-d, -d,-d,d, -d,d,d,  -1,0,0);
//    	modelBuilder.part("right", GL20.GL_TRIANGLES, attr, mat3).rect(d,-d,-d, d,d,-d,  d,d,d, d,-d,d, 1,0,0);
    	Model box = modelBuilder.end();
    	return box;
    }

	private void addInstance(Model model, Vector3 pos, boolean magica) {
		ModelInstance instance;
		if(magica) {
			instance = createInstanceMagica(model, pos);
		} else {
			instance = createInstance(model, pos);
		}
//		var renderable = createRenderable(instance);
		instances.add(instance);
//		renderables.add(renderable);
	}
	private void addInstance(Model model, Vector3 pos) {
		addInstance(model, pos, false);
	}
	
	private ModelInstance createInstance(Model model, Vector3 pos) {
    	ModelInstance instance = new ModelInstance(model);
    	instance.transform.setToTranslation(pos).translate(-cellSize/2f, -cellSize/2f, -cellSize/2f);
      //  instance.calculateTransforms();
        return instance;
	}
	private ModelInstance createInstanceMagica(Model model, Vector3 pos) {
    	ModelInstance instance = new ModelInstance(model);
		float scl = 0.3f;
		/*instance.transform.scale(scl, scl, scl);
		instance.transform.rotate(Vector3.X, 90);
    	instance.transform.translate(pos.y, 0, -pos.x).translate(-cellSize/2, -cellSize/2, -cellSize/2);
        instance.calculateTransforms();*/
		instance.transform.setToRotation(Vector3.X, 90).trn(pos).scale(scl, scl, scl);
        return instance;
	}
	
	private Renderable createRenderable(ModelInstance instance) {
		var renderable = new Renderable();
		instance.getRenderable(renderable);
		renderable.environment = env;
		renderable.meshPart.primitiveType = GL20.GL_LINES;
		renderable.worldTransform.idt();
		return renderable;
	}
	
	
	public void dispose() {
        cache.dispose();
        instances.forEach(i -> i.model.dispose());
	}
	
	
}
