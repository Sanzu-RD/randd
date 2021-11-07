package com.souchy.randd.tools.mapeditor.entities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveIntensityAttribute;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveTextureAttribute;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;

public class DissolveAction implements SystemAction<ModelInstance> {
	
	@Override
	public Class<ModelInstance> getTClass() {
		return ModelInstance.class;
	}

	public void process(float delta, ModelInstance inst) { //ModelInstance inst) {
		try {
			for(var mat : inst.materials) {
				var attr = mat.get(DissolveIntensityAttribute.DissolveIntensityType);
				if(attr != null){
					DissolveIntensityAttribute intensity = (DissolveIntensityAttribute) attr;
					intensity.value = (float) Math.sin(MapEditorGame.screen.time * 2f) * 0.35f + 0.45f; //time * 2f / period;
					//intensity.value = (float) (time * -1f % 1) + 0.5f; //time * 2f / period;
					
					//var col = (DissolveBorderColorAttribute) inst.materials.get(0).get(DissolveBorderColorAttribute.DissolveBorderColorType);
					//col.color.set(Color.CYAN);
					
					//var width = (DissolveBorderWidthAttribute) inst.materials.get(0).get(DissolveBorderWidthAttribute.DissolveBorderWidthType);
					//width.value = 0.03f;
					
					var attrdissTex = mat.get(DissolveTextureAttribute.DissolveTextureType);
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
			}
		} catch (Exception e) {
			Log.info("", e);
		}
	}

	
}
