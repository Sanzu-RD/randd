package com.souchy.randd.tools.mapeditor.actions;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebi.ammolite.util.LightningDistort;
import com.souchy.randd.ebi.ammolite.util.LightningDistort.LightningNode;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.configs.EditorConf;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

public class Actions {

	
	public static void resetConf() {
		MapEditorCore.conf = new EditorConf(); 
		MapEditorCore.conf.save();
	}

	public static void reloadAssets() {
		reloadAssetModels();
		reloadAssetTextures();
	}
	public static void reloadAssetModels() {
		LapisAssets.loadModels(Gdx.files.internal("res/models/"));
	}
	public static void reloadAssetTextures() {
		LapisAssets.loadTextures(Gdx.files.internal("res/textures/"));
	}
	
	public static void reloadAssetData(Object... args) {
		AssetData.loadResources();
	}

	public static void clearWorld() {
		MapEditorGame.entities.clear();
	}
	public static void testLightningNodes() {
		//clearWorld();
		/*
		var rootNode = new LightningDistort.LightningNode(0, 0, 10);
		rootNode.n2 = new LightningNode(0, 0, 0);
		rootNode.subdivide(3);
		var n = rootNode;
		while(n.n2 != null) {
			//MapWorld.createSphere().transform.setToTranslation(n.pos).scl(0.5f);
//			var line = MapWorld.createPlane();
//			line.transform.scale(0, 0, 0)
			var line = MapWorld.createLine(n.pos, n.n2.pos, 0.05f);
			n = n.n2;
		}
		*/
		
		LightningNode.dispose = n -> {
			//Gdx.app.postRunnable(() -> {
				LightningNode.recurse(n, n1 -> {
					if(n1.model != null) {
						MapWorld.world.instances.remove(n1.model);
						Log.info("lightning node remove instance %s", n1.model);
					}
				}); // EditorEntities.removeAdaptor(n1.model));
			//});
		};
		LightningNode.createModel = (n) -> {
			//Gdx.app.postRunnable(() -> {
				EditorEntities.removeAdaptor(n.model);
				if(n.n2 == null) return;
				var line = MapWorld.createLine(false, n.pos, n.n2.pos, 0.02f);
				var col = new Color(42f/255f, 136f/255f, 185f/255f, 1f);
				var color = (ColorAttribute) line.materials.get(0).get(ColorAttribute.Diffuse);
				var rnd = new Random();
				color.color.set(col); //rnd.nextFloat(0.01f), rnd.nextFloat(0.1f, 0.2f), rnd.nextFloat(0.5f, 1f), 1);
				line.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE, 0.8f));
				
				//line.materials.get(0).set(ColorAttribute.createEmissive(0, 0.1f, 0.9f, 1f));
				//line.materials.get(0).set(ColorAttribute.createReflection(1, 0, 0, 1));
				//line.materials.get(0).set(ColorAttribute.createSpecular(1, 0, 0, 1));
				
				n.model = line;
				MapWorld.world.instances.add(line);
			//});
		};
		MapEditorGame.entities.add(new LightningDistort.Lightning(MapEditorGame.engine));
	
	}
	
}
