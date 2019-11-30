package com.souchy.randd.mockingbird.lapismock.more.pfx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalMaterial;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardControllerRenderData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderer;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisResources;

public class DecalRenderer extends ParticleControllerRenderer<DecalControllerRenderData, DecalParticleBatch> {

	@Override
	public boolean isCompatible(ParticleBatch<?> batch) {
		Camera cam = null;
		Texture tex = LapisResources.get("");
		Decal a = Decal.newDecal(new TextureRegion(tex), true);
		DecalBatch d = new DecalBatch(new CameraGroupStrategy(cam));
		d.add(a);
		d.flush();
		
		
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ParticleControllerComponent copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
