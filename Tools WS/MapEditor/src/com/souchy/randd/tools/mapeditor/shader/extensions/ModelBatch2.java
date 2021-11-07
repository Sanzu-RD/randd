package com.souchy.randd.tools.mapeditor.shader.extensions;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * 
 * 
 * @author Blank
 * @date 1 nov. 2021
 */
public class ModelBatch2 extends ModelBatch {
	
	
	/**
	 * Render a model instance
	 */
	@Override
	public void render(RenderableProvider renderableProvider) {
		final int offset = renderables.size; // 
		// obtain renderable objects from "renderablesPool"
		// set renderables properties from the every node and node part of modelinstance
		// add the renderable objects to the render list "renderables"
		renderableProvider.getRenderables(renderables, renderablesPool);  
		for (int i = offset; i < renderables.size; i++) {
			Renderable renderable = renderables.get(i); 
			renderable.shader = shaderProvider.getShader(renderable);
		}
	}
	
	/**
	 * Render a renderable
	 */
	@Override
	public void render(Renderable renderable) {
		renderable.shader = shaderProvider.getShader(renderable);
		renderables.add(renderable);
	}
	
	
}
