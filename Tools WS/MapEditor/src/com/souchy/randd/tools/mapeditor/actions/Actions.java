package com.souchy.randd.tools.mapeditor.actions;

import java.util.Random;

import com.badlogic.gdx.Gdx;
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
		clearWorld();
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
				LightningNode.recurse(n, n1 -> EditorEntities.removeAdaptor(n1.model));
			//});
		};
		LightningNode.createModel = (n) -> {
			//Gdx.app.postRunnable(() -> {
				EditorEntities.removeAdaptor(n.model);
				if(n.n2 == null) return;
				var line = MapWorld.createLine(n.pos, n.n2.pos, 0.05f);
				var color = (ColorAttribute) line.materials.get(0).get(ColorAttribute.Diffuse);
				var rnd = new Random();
				color.color.set(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1);
				n.model = line;
			//});
		};
		MapEditorGame.entities.add(new LightningDistort.Lightning(MapEditorGame.engine));
	
	}
	
}
