package com.souchy.randd.tools.mapeditor.shader;

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
	
	
	/**
	 * Contains : 
	 * - Model reference
	 * - nodes copied
	 * - materials copied
	 * - animations copied
	 * - transform
	 * - userdata
	 * 
	 * @author Blank
	 * @date 1 nov. 2021
	 */
	public static class ModelInstance2 extends ModelInstance {
//		public Shader shader;
		public ModelInstance2(Model model) {
			super(model);
		}
		// Main method called by the batch, Gets renderables for every nodepart from every node
		@Override
		public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
			for (Node node : nodes) {
				getRenderables(node, renderables, pool);
			}
		}
		/**
		 * Gets renderables for every node parts of this node and its children.<p>
		 * A Node has :
		 * - Node children, <br> 
		 * - NodeParts, <br>
		 * - translation, rotation, scale, <r>
		 * - localTransform, globalTransform <br>
		 * ... 
		 */
		@Override
		protected void getRenderables(Node node, Array<Renderable> renderables, Pool<Renderable> pool) {
			if (node.parts.size > 0) {
				for (NodePart nodePart : node.parts) {
					if (nodePart.enabled) renderables.add(getRenderable(pool.obtain(), node, nodePart));
				}
			}
			for (Node child : node.getChildren()) {
				getRenderables(child, renderables, pool);
			}
		}
		/**
		 * Get a renderable for a node part. <p>
		 * 
		 * A Node part has : <br>
		 * - MeshPart, <br>
		 * - Material, <br>
		 * - Bones, <br>
		 * - isEnabled <br>
		 * ...
		 */
		@Override
		public Renderable getRenderable(Renderable out, Node node, NodePart nodePart) {
			// set material, meshpart, bones
			nodePart.setRenderable(out);
			// set transforms
			if (nodePart.bones == null && transform != null)
				out.worldTransform.set(transform).mul(node.globalTransform);
			else if (transform != null)
				out.worldTransform.set(transform);
			else
				out.worldTransform.idt();
			// set user data
			out.userData = userData;
//			out.shader = shader;
			return out;
		}
		

		/** this is never used, but it's to get the renderable of the first node's first part */
		@Override
		public Renderable getRenderable(Renderable out) {
			return super.getRenderable(out);
		}
		/** this is never used, but it's to get the renderable of the first node's first part */
		@Override
		public Renderable getRenderable(Renderable out, Node node) {
			return super.getRenderable(out, node);
		}
	}
	
	
}
