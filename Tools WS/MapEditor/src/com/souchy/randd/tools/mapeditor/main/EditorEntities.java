package com.souchy.randd.tools.mapeditor.main;

import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveIntensityAttribute;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveTextureAttribute;

public class EditorEntities extends Family<Entity> {

	public static EditorEntities thiz;
	
	public EditorEntities(Engine engine) {
		super(engine, Entity.class);
		thiz = this;
	}

	@Override
	public void update(float delta) {
		
		this.map(e -> e.get(ModelInstance.class)).forEach(i -> {
			
		});
		
		//Log.info("update entities " + this.size());
		this.foreach(e -> {
			// animation
			var anime = e.get(AnimationController.class);
			anime.update(delta);
			// model
			var inst = e.get(ModelInstance.class);
			move(inst);
		});
	}
	
	public static List<ModelInstance> getInstances(){
		return thiz.map(e -> e.get(ModelInstance.class));
	}
	public static List<AnimationController> getAnimes(){
		return thiz.map(e -> e.get(AnimationController.class));
	}
	
	public static AnimationController getAnime(ModelInstance i) {
		return thiz.first(e -> e.get(ModelInstance.class) == i).get(AnimationController.class);
	}
	
	public void clear() {
		super.clear();
		MapWorld.world.instances.clear();
		MapWorld.world.cache.begin();
		MapWorld.world.cache.end();
	}
	public static void clears() {
		thiz.clear();
	}
	
	public static void addAdaptor(ModelInstance inst) {
		var e = new Entity(MapEditorGame.engine);
		e.add(inst);
		e.add(new AnimationController(inst));
		MapWorld.world.instances.add(inst);
	}
	
	public static void removeAdaptor(ModelInstance inst) {
		var ent = thiz.first(e -> e.get(ModelInstance.class) == inst);
		thiz.remove(ent);
		MapWorld.world.instances.remove(inst);
	}
	
	
	private void move(ModelInstance inst) {
		try {
			var attr = inst.materials.get(0).get(DissolveIntensityAttribute.DissolveIntensityType);
			if(attr != null){
				DissolveIntensityAttribute intensity = (DissolveIntensityAttribute) attr;
				intensity.value = (float) Math.sin(MapEditorGame.screen.time * 2f) * 0.35f + 0.45f; //time * 2f / period;
				//intensity.value = (float) (time * -1f % 1) + 0.5f; //time * 2f / period;

				//var col = (DissolveBorderColorAttribute) inst.materials.get(0).get(DissolveBorderColorAttribute.DissolveBorderColorType);
				//col.color.set(Color.CYAN);

				//var width = (DissolveBorderWidthAttribute) inst.materials.get(0).get(DissolveBorderWidthAttribute.DissolveBorderWidthType);
				//width.value = 0.03f;
				
				var attrdissTex = inst.materials.get(0).get(DissolveTextureAttribute.DissolveTextureType);
				if(attrdissTex != null) {
					DissolveTextureAttribute tex = (DissolveTextureAttribute) attrdissTex;
					// move the texture while it's completely white or completely blend to add randomness to each cycle
					if(intensity.value >= 0.90f || intensity.value <= 0.11f) {
						tex.offsetU += 0.005f;
						tex.offsetV += 0.005f;
						//Log.info("asd");
						//tex.offsetU = time * 2.0f;
						//tex.offsetV = time * 2.0f;
					}
				}
			}
		} catch (Exception e) {
			
		}
	}
	
}
