package com.souchy.randd.mockingbird.lapismock.more.pfx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.batches.BufferedParticleBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class DecalParticleBatch extends BufferedParticleBatch<DecalControllerRenderData> {

	protected DecalParticleBatch(Class<DecalControllerRenderData> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(AssetManager manager, ResourceData assetDependencyData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(AssetManager manager, ResourceData assetDependencyData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void allocParticlesData(int capacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void flush(int[] offsets) {
		// TODO Auto-generated method stub
		
	}
	
}
