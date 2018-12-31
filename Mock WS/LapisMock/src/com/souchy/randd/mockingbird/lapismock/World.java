package com.souchy.randd.mockingbird.lapismock;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class World {
	

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
    	var sum = cellSize + offset;
    	
		
		
		//String file = "models/cube.obj";
		String file = "g3d/board/cell_models/roundedCube05.g3dj";
        //ModelLoader loader = new ObjLoader();
        //model = loader.loadModel(Gdx.files.internal(file));

        AssetManager assets = new AssetManager();
        assets.load(file, Model.class);
        assets.finishLoading();
        

		Model model;
		model = createModel(Color.GREEN);
        //model = assets.get(file);
        
        model.materials.clear();
        model.materials.add(new Material(ColorAttribute.createDiffuse(Color.GREEN)));
    	
        int side = 29;
		for(int x = 0; x < side; x++) {
			for(int y = 0; y < side; y++) {
				if(x == side/2 && y == side/2) continue;
				//Vector3 pos = new Vector3(sum * x + sum/2, sum * y + sum/2, 0);//z * cellSize - cellSize/2));
				Vector3 pos = new Vector3(sum * x, sum * y,  0);//z * cellSize - cellSize/2));
				addBlock(model, pos);
				
			}
		}
		addBlock(createModel(Color.SKY), new Vector3(side/3, side/3, 2));
		addBlock(createModel(Color.RED), new Vector3(0, 1, 1)); // Y
		addBlock(createModel(Color.BLUE), new Vector3(1, 0, 1)); // X

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
    	Model model = modelBuilder.createBox(
    			cellSize, cellSize, cellSize, 
    			new Material(ColorAttribute.createDiffuse(color)), 
    			Usage.Position | Usage.Normal | Usage.TextureCoordinates);
    	return model;
    }

	private void addBlock(Model model, Vector3 pos) {
		var instance = createBlock(model, pos);
//		var renderable = createRenderable(instance);
		instances.add(instance);
//		renderables.add(renderable);
	}
	
	private ModelInstance createBlock(Model model, Vector3 pos) {
    	ModelInstance instance = new ModelInstance(model);
    	instance.transform.setToTranslation(pos).translate(-cellSize/2, -cellSize/2, -cellSize/2);
        instance.calculateTransforms();
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
