package com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms;

import java.util.Random;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class SsaoGenUniforms implements UniformsModule {
	
	// ------------- SSAO data
	public int sampleSize = 64;
	//protected TextureDescriptor resultDesc;
	protected TextureDescriptor noiseDesc;
	public float[] samples = new float[(int) sampleSize * 3];
	//public Vector3[] samples = new Vector3[(int) sampleSize * 3];
	// ------------- SSAO uniforms
	// would be final but I create it in hemisphereSamples
	protected int u_samples;
	protected int u_noiseTexture;
	
	public SsaoGenUniforms(BaseShader s) {
		/* create our data and register our uniforms */
		hemisphereSamples(s);
		noiseTexture(s);
	}

	@Override 
	public void bind(BaseShader s, Renderable renderable, Config config, final Attributes attributes) {
		// bind sample size 64
		//if(u_samples != null)
//			for(int i = 0; i < sampleSize; i++) 
//				s.set(u_samples[i], samples[i]);

			s.program.setUniform3fv(u_samples, samples, 0, samples.length);
		// bind noise desc
		if(u_noiseTexture != 0) 
			s.set(u_noiseTexture, noiseDesc);
	}
	
	private float lerp(float a, float b, float f) {
		return a + f * (b - a);
	}
	
	private void hemisphereSamples(BaseShader s) {
		var rnd = new Random();
//		u_samples = new int[sampleSize];
		// random samples
		for(int i = 0; i < sampleSize; i++) {
			var vec = new Vector3(rnd.nextFloat() * 2 - 1, rnd.nextFloat() * 2 - 1, rnd.nextFloat());
			vec.nor();
			vec.scl(rnd.nextFloat());
			
			float scale = (float) i / (float) sampleSize;
			vec.scl(lerp(0.1f, 1.0f, scale * scale)); // lerp towards the center
			
			samples[3*i+0] = vec.x;
			samples[3*i+1] = vec.y;
			samples[3*i+2] = vec.z;
			//u_samples[i] = s.register(new Uniform("u_samples["+i+"]"));
			u_samples =  s.register(new Uniform("u_samples[0]"));
		}
	}
	
	private void noiseTexture(BaseShader s) {
		u_noiseTexture = s.register(new Uniform("u_noiseTexture"));
		var rnd = new Random();
		// noise texture
		noiseDesc = new TextureDescriptor();
		noiseDesc.minFilter = noiseDesc.magFilter = Texture.TextureFilter.Nearest;
		noiseDesc.uWrap = noiseDesc.vWrap = Texture.TextureWrap.Repeat; 
		var p = new Pixmap(4, 4, Format.RGB888);
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
			    var noise = new Vector3(
			        rnd.nextFloat() * 2.0f - 1.0f, 
			        rnd.nextFloat() * 2.0f - 1.0f, 
			        0.0f); 
			    //ssaoNoise.add(noise); //push_back(noise);
				p.setColor(noise.x, noise.y, noise.z, 1);
				p.drawPixel(i, j);
			}
		}  
		noiseDesc.texture = new Texture(p); //new Texture(4, 4, Format.RGB888);
	}
	
	
}
