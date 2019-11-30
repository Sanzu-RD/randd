package com.souchy.randd.ebishoal.sapphire.pfx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.ModelInstanceParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.FaceDirection;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.PolarAcceleration;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class ProjectilePfx {
	
	
	public void adf() {
		Camera cam = null;
		var assets = new AssetManager();

		// Particle System
		var particleSystem = ParticleSystem.get();
		
		// Particle Batches
		PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
		pointSpriteBatch.setCamera(cam);
		particleSystem.add(pointSpriteBatch);
		
		ModelInstanceParticleBatch modelParticleBatch = new ModelInstanceParticleBatch();
		particleSystem.add(modelParticleBatch);
		
		// Loader
		var assetPath = "G:/Assets/test/pentagram.png";
		ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
		assets.load(assetPath, ParticleEffect.class, loadParam);
		assets.finishLoading();
		

		ParticleEffect kunaiFXo = assets.get(assetPath);
		// we cannot use the originalEffect, we must make a copy each time we create new
		// particle effect
		var kunaiFX = kunaiFXo.copy();
		Matrix4 kunaiTransform = new Matrix4();
		kunaiTransform.idt();
		kunaiTransform.setToTranslation(new Vector3(10, -10, 5));
		kunaiTransform.rotate(new Vector3(1, 1, 1), 90);
		kunaiFX.setTransform(kunaiTransform);
		ParticleController co = kunaiFX.getControllers().get(0);
		for (int i = 1; i < 8; i++) {
			var controller = co.copy();
			ColorInfluencer ci = controller.findInfluencer(ColorInfluencer.class);
			DynamicsInfluencer di = controller.findInfluencer(DynamicsInfluencer.class); // (DynamicsInfluencer) controller.influencers.select(v -> v instanceof
																							// DynamicsInfluencer).iterator().next();
			PolarAcceleration pa = (PolarAcceleration) di.velocities.select(v -> v instanceof PolarAcceleration).iterator().next();
			pa.thetaValue.setHigh(0);
			pa.phiValue.setHigh(i * 45);
			// pa.strengthValue.setHigh(10);
			di.velocities.clear();
			di.velocities.add(pa);
			di.velocities.add(new FaceDirection());
			kunaiFX.getControllers().add(controller);
		}
		kunaiFX.getControllers().removeIndex(0);
		kunaiFX.init();
		kunaiFX.start(); // optional: particle will begin playing immediately
		particleSystem.add(kunaiFX);
	}
	
	public void renderParticleEffects(ParticleSystem particleSystem) {
		particleSystem.update();
	}
	
	public static class CustomPfx extends ParticleEffect {
		
	}
	
	
}
