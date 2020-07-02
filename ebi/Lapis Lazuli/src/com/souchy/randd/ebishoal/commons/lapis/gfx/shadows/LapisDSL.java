package com.souchy.randd.ebishoal.commons.lapis.gfx.shadows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** @deprecated Experimental, likely to change, do not use!
 * @author Xoppa */
public class LapisDSL extends DirectionalLight implements ShadowMap, Disposable {
	protected FrameBuffer fbo;
	protected Camera cam;
	protected float halfDepth; // world units
	protected float halfHeight; // world units
	protected final Vector3 tmpV = new Vector3();
	protected final TextureDescriptor textureDesc;
	
	// souchy added viewport to manage the camera
	protected Viewport viewport;

	/** @deprecated Experimental, likely to change, do not use! */
	public LapisDSL (int shadowMapWidth, int shadowMapHeight, float shadowViewportWidth, float shadowViewportHeight, float shadowNear, float shadowFar) {
		
		cam = new OrthographicCamera();
		cam.near = shadowNear;
		cam.far = shadowFar;
		cam.up.x = 0;
		cam.up.y = 0;
		cam.up.z = 1;

		viewport = new ExtendViewport(shadowViewportWidth, shadowViewportHeight, cam);

		// update viewport and create fbo
		update(shadowMapWidth, shadowMapHeight);
		
		halfDepth = shadowNear + 0.5f * (shadowFar - shadowNear);
		textureDesc = new TextureDescriptor();
		textureDesc.minFilter = textureDesc.magFilter = Texture.TextureFilter.Nearest;
		textureDesc.uWrap = textureDesc.vWrap = Texture.TextureWrap.ClampToEdge;
	}

	public void update (final Camera camera) {
		update(tmpV.set(camera.direction).scl(halfHeight), camera.direction);
	}

	public void update (final Vector3 center, final Vector3 forward) {
		// cam.position.set(10,10,10);
		cam.position.set(direction).scl(-halfDepth).add(center);
		cam.direction.set(direction).nor();
		// cam.up.set(forward).nor();
	//	cam.normalizeUp();
		cam.update();
	}

	public void begin (final Camera camera) {
		update(camera);
		begin();
	}

	public void begin (final Vector3 center, final Vector3 forward) {
		update(center, forward);
		begin();
	}

	public void begin () {
		final int w = fbo.getWidth();
		final int h = fbo.getHeight();
		fbo.begin();
		Gdx.gl.glViewport(0, 0, w, h);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
		Gdx.gl.glScissor(1, 1, w - 2, h - 2);
		//Gdx.gl.glCullFace(GL20.GL_BACK);
	}

	public void end () {
		//Gdx.gl.glCullFace(GL20.GL_FRONT);
		Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
		fbo.end();
	}

	public FrameBuffer getFrameBuffer () {
		return fbo;
	}

	public Camera getCamera () {
		return cam;
	}

	@Override
	public Matrix4 getProjViewTrans () {
		return cam.combined;
	}

	@Override
	public TextureDescriptor getDepthMap () {
		textureDesc.texture = fbo.getColorBufferTexture();
		return textureDesc;
	}
	
	public Viewport getViewport() {
		return viewport;
	}

	@Override
	public void dispose () {
		if (fbo != null) fbo.dispose();
		fbo = null;
	}

	public void update(int screenWidth, int screenHeight) {
		// resize viewport
		getViewport().update(screenWidth, screenHeight, false);
		// calc half of world height
		halfHeight = viewport.getWorldHeight() * 0.5f;
		// resize framebuffer
		createFbo(screenWidth, screenHeight);
	}
	
	private void createFbo(int shadowMapWidth, int shadowMapHeight) {
		//if(fbo == null)
		fbo = new FrameBuffer(Format.RGBA8888, shadowMapWidth, shadowMapHeight, true);
	}

	/**
	 * @param shadowViewportWidth world units
	 * @param shadowViewportHeight world units
	 */
	public void zoom(float shadowViewportWidth, float shadowViewportHeight) {
		if(shadowViewportWidth > 30 || shadowViewportHeight > 20) return;
		
		this.viewport.setWorldSize(shadowViewportWidth, shadowViewportHeight);
		this.cam.viewportWidth = shadowViewportWidth;
		this.cam.viewportHeight = shadowViewportHeight;
		this.cam.update();
	}
	
	
}
