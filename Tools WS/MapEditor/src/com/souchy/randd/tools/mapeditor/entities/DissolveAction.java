package com.souchy.randd.tools.mapeditor.entities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.shaderimpl.DissolveUniforms.DissolveAttributes;

public class DissolveAction implements SystemAction<ModelInstance> {
	
	@Override
	public Class<ModelInstance> getTClass() {
		return ModelInstance.class;
	}

	public void process(float delta, ModelInstance inst) { //ModelInstance inst) {
		try {
			for(var mat : inst.materials) {
				var attr = mat.get(DissolveAttributes.DissolveIntensityType);
				if(attr != null){
					FloatAttribute intensity = (FloatAttribute) attr;
					intensity.value = (float) Math.sin(MapEditorGame.screen.time * 2f) * 0.35f + 0.45f; //time * 2f / period;
					//intensity.value = (float) (time * -1f % 1) + 0.5f; //time * 2f / period;
					
					//var col = (DissolveBorderColorAttribute) inst.materials.get(0).get(DissolveBorderColorAttribute.DissolveBorderColorType);
					//col.color.set(Color.CYAN);
					
					//var width = (DissolveBorderWidthAttribute) inst.materials.get(0).get(DissolveBorderWidthAttribute.DissolveBorderWidthType);
					//width.value = 0.03f;
					
					var attrdissTex = mat.get(DissolveAttributes.DissolveTextureType);
					if(attrdissTex != null) {
						TextureAttribute tex = (TextureAttribute) attrdissTex;
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
