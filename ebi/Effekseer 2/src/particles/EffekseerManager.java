package particles;

import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EffekseerManager implements Disposable {
	
	protected EffekseerManagerCore effekseerManagerCore;
	protected Camera camera;
	private ArrayList<ParticleEffekseer> effekseers;
//	private Viewport viewport;
	
	private Matrix4 matrix;
	
	public static void InitializeEffekseer() {
		try {
			System.loadLibrary("src");
		} catch (Error | Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public EffekseerManager(Camera camera) {
		this.camera = camera;
		effekseers = new ArrayList<>();
		EffekseerBackendCore.InitializeAsOpenGL();
		effekseerManagerCore = new EffekseerManagerCore();
		
		if(Gdx.app.getType() == Application.ApplicationType.Android) {
			effekseerManagerCore.Initialize(600, EffekseerCore.TypeOpenGL.OPEN_GLES2);
		} else {
			effekseerManagerCore.Initialize(2000, EffekseerCore.TypeOpenGL.OPEN_GL3);
		}
		
		// matrix = (camera).view.cpy().rotate(Vector3.X, 90);
	}
	
//	public Viewport getViewport() {
//		return viewport;
//	}
//	
//	public void setViewport(Viewport viewport) {
//		this.viewport = viewport;
//	}
	
	
	
	public void addParticleEffekseer(ParticleEffekseer effect) {
		effect.manager = this;
		this.effekseers.add(effect);
	}
	
	public void draw(float delta) {
		camera.update();
		//
		// Gdx.gl.glEnable(GL20.GL_BLEND);
		// Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		// Gdx.gl.glCullFace(GL20.GL_BACK);
		
		effekseers.forEach(effekseer -> {
			if(effekseer.isPlay()) {
				if(!effekseerManagerCore.isPlaying(effekseer.getHandle())) {
					effekseer.setPlay(false);
					if(effekseer.getOnAnimationComplete() != null) {
						effekseer.getOnAnimationComplete().finish();
					}
					
				}
			}
		});
		
		if(camera instanceof OrthographicCamera) {
			// if(viewport != null) {
			// effekseerManagerCore.setProjectionMatrix((camera).projection, (camera).view,
			// true, viewport.getWorldWidth(), viewport.getWorldHeight());
			// } else {
			effekseerManagerCore.setProjectionMatrix(
					camera.projection, 
//					camera.view,
					(camera).view.cpy().rotate(Vector3.X, 90), 
					true, 
					camera.viewportWidth,
					camera.viewportHeight
			);
			// }
			
		}
		
		if(camera instanceof PerspectiveCamera) {
			effekseerManagerCore.setProjectionMatrix((camera).projection, (camera).view, true, 0, 0);
		}
		
		effekseerManagerCore.Update(delta / (1.0f / 60.0f));
		effekseerManagerCore.DrawBack();
		effekseerManagerCore.DrawFront();
		//
		// Gdx.gl.glCullFace(GL20.GL_FRONT);
		// Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	@Override
	public void dispose() {
		effekseerManagerCore.delete();
		for (ParticleEffekseer effekseer : this.effekseers) {
			effekseer.delete();
		}
		EffekseerBackendCore.Terminate();
		
	}
}
