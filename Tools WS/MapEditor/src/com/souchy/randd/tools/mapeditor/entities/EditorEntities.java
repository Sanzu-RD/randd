package com.souchy.randd.tools.mapeditor.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveIntensityAttribute;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveTextureAttribute;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

public class EditorEntities extends Family<Entity> {

	public static EditorEntities thiz;

	public List<SystemAction<?>> actions = new ArrayList<>();
	
	public EditorEntities(Engine engine) {
		super(engine, Entity.class);
		thiz = this;
		
		// animationcontroller updater
		this.actions.add(new SystemAction<AnimationController>() {
			@Override
			public Class<AnimationController> getTClass() {
				return AnimationController.class;
			}
			@Override
			public void process(float delta, AnimationController t) {
				t.update(delta);
			}
		});
	}

	@Override
	public void update(float delta) {
		
		this.map(e -> e.get(ModelInstance.class)).forEach(i -> {
			
		});
		
		//Log.info("update entities " + this.size());
		this.foreach(e -> {
			// animation
			//var anime = e.get(AnimationController.class);
			//if(anime != null) anime.update(delta);
			
			// model
			//var inst = e.get(ModelInstance.class);
			//if(inst != null) move(inst);
			
			// particle effects
			
			for(var action : actions)
				action.update(delta, e);
		});
	}
	
	/**
	 * should be in Family when we change it to Family<T extends Entity>
	 */
	public <R> List<R> map(Class<R> c) {
		return this.map(e -> e.get(c), e -> e.has(c));
	}
	public static List<ModelInstance> getInstances(){
		return thiz.map(ModelInstance.class);
	}
	public static List<AnimationController> getAnimes(){
		return thiz.map(AnimationController.class);
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
	
	
	
}
