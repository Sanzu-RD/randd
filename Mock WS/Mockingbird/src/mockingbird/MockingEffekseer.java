package mockingbird;

import Effekseer.swig.EffekseerManagerCore;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Effekseer.swig.*;

import java.io.Console;
import java.io.File;

public class MockingEffekseer extends ApplicationAdapter {
	
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MockingEffekseer(), config);
	}
	
//	SpriteBatch batch;
//	Texture img;
	EffekseerManagerCore effekseerManagerCore;
	EffekseerEffectCore effekseerEffectCore;
	
	@Override
	public void create() {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
		
		System.loadLibrary("EffekseerNativeForJava");
		
		EffekseerBackendCore.InitializeAsOpenGL();
		
		effekseerManagerCore = new EffekseerManagerCore();
		effekseerManagerCore.Initialize(8000);
		
//		String effectPath = "fx/Sample/02_Tktk03/ToonWater.efkefc";
		String effectPath = "fx/Sample/01_Pierre02/Sword_Ember.efkefc";
		effekseerEffectCore = loadEffect(effectPath, 50.0f);
		if(effekseerEffectCore == null) {
			System.out.print("Failed to load.");
			return;
		}
		
		int efkhandle = effekseerManagerCore.Play(effekseerEffectCore);
		effekseerManagerCore.SetEffectPosition(efkhandle, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, 0.0f);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		// It needs to clear depth
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		// batch.begin();
		// batch.draw(img, 0, 0);
		// batch.end();
		
		effekseerManagerCore.SetViewProjectionMatrixWithSimpleWindow(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		effekseerManagerCore.Update(Gdx.graphics.getDeltaTime() / (1.0f / 60.0f));
		effekseerManagerCore.DrawBack();
		effekseerManagerCore.DrawFront();
	}
	
	@Override
	public void dispose() {
//		batch.dispose();
//		img.dispose();
		effekseerManagerCore.delete();
		effekseerEffectCore.delete();
		EffekseerBackendCore.Terminate();
	}
	
	public static EffekseerEffectCore loadEffect(String effectPath, float magnification) {
		FileHandle handle = Gdx.files.internal(effectPath);
		EffekseerEffectCore effectCore = new EffekseerEffectCore();
		
		// load an effect
		{
			byte[] bytes = handle.readBytes();
			if(!effectCore.Load(bytes, bytes.length, magnification)) {
				System.out.print("Failed to load.");
				return null;
			}
		}
		
		// load textures
		EffekseerTextureType[] textureTypes = new EffekseerTextureType[] { 
				EffekseerTextureType.Color,
				EffekseerTextureType.Normal,
				EffekseerTextureType.Distortion, 
		};
		
		for (int t = 0; t < 3; t++) {
			for (int i = 0; i < effectCore.GetTextureCount(textureTypes[t]); i++) {
				String path = (new File(effectPath)).getParent();
				if(path != null) {
					path += "/" + effectCore.GetTexturePath(i, textureTypes[t]);
				} else {
					path = effectCore.GetTexturePath(i, textureTypes[t]);
				}
				
				handle = Gdx.files.internal(path);
				byte[] bytes = handle.readBytes();
				effectCore.LoadTexture(bytes, bytes.length, i, textureTypes[t]);
			}
		}
		
		for (int i = 0; i < effectCore.GetModelCount(); i++) {
			String path = (new File(effectPath)).getParent();
			if(path != null) {
				path += "/" + effectCore.GetModelPath(i);
			} else {
				path = effectCore.GetModelPath(i);
			}
			
			handle = Gdx.files.internal(path);
			byte[] bytes = handle.readBytes();
			effectCore.LoadModel(bytes, bytes.length, i);
		}
		
		for (int i = 0; i < effectCore.GetMaterialCount(); i++) {
			String path = (new File(effectPath)).getParent();
			if(path != null) {
				path += "/" + effectCore.GetMaterialPath(i);
			} else {
				path = effectCore.GetMaterialPath(i);
			}
			
			handle = Gdx.files.internal(path);
			byte[] bytes = handle.readBytes();
			effectCore.LoadMaterial(bytes, bytes.length, i);
		}
		
		// TODO sound
		
		return effectCore;
	}
	
}
