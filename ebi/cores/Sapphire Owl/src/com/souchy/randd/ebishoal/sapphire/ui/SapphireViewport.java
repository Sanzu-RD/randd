package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class SapphireViewport extends Viewport {
	
	private final int baseScreenWidth;
	private final int baseScreenHeight;
	
	/** Creates a new viewport with no maximum world size. */
	public SapphireViewport (float worldWidth, float worldHeight, Camera camera) {
		baseScreenWidth = Gdx.graphics.getWidth();
		baseScreenHeight = Gdx.graphics.getHeight();
		this.setWorldWidth(worldWidth);
		this.setWorldHeight(worldHeight);
		this.setScreenBounds(0, 0, baseScreenWidth, baseScreenHeight);
		this.setCamera(camera);
	}

	/** Calls {@link GL20#glViewport(int, int, int, int)}, expecting the coordinates and sizes given in logical coordinates and
	 * automatically converts them to backbuffer coordinates, which may be bigger on HDPI screens. */
	public static void glViewport (int x, int y, int width, int height) {
		if (Gdx.graphics.getWidth() != Gdx.graphics.getBackBufferWidth()
			|| Gdx.graphics.getHeight() != Gdx.graphics.getBackBufferHeight()) {
			Gdx.gl.glViewport(HdpiUtils.toBackBufferX(x), HdpiUtils.toBackBufferY(y), HdpiUtils.toBackBufferX(width), HdpiUtils.toBackBufferY(height));
		} else {
			Gdx.gl.glViewport(x, y, width, height);
		}
	}

	/** Applies the viewport to the camera and sets the glViewport.
	 * @param centerCamera If true, the camera position is set to the center of the world. */
	public void apply (boolean centerCamera) {
		Log.info("apply : " + String.format("[%s, %s, %s, %s] [%s, %s]", getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight(), getWorldWidth(), getWorldHeight()));
		HdpiUtils.glViewport(getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight());
		
		getCamera().viewportWidth = getWorldWidth();
		getCamera().viewportHeight = getWorldHeight();
		if (centerCamera) {
			//getCamera().position.set(getWorldWidth() / 2, getWorldHeight() / 2, 0);
			//getCamera().position.set(9.5f, 9.5f, 0);
		}
		getCamera().update();
	}
	
	@Override
	public void update(int screenWidth1, int screenHeight1, boolean centerCamera) {
		//super.update(screenWidth, screenHeight, centerCamera);
		
		if(false) {
			extend(screenWidth1, screenHeight1, centerCamera);
		} else {
			var worldWidth0 = getWorldWidth();
			var worldHeight0 = getWorldHeight();
			var screenWidth0 = getScreenWidth();
			var screenHeight0 = getScreenHeight();
			var scaleX = (float) screenWidth0 / (float) screenWidth1;
			var scaleY = (float) screenHeight0 / (float) screenHeight1;
			
//			scaleX = (scaleX - 1) * 2 + 1;
//			scaleY = (scaleY - 1) * 2 + 1;
//			scaleX = Math.abs(scaleX);
//			scaleY = Math.abs(scaleY);
			
		//	if(screenWidth1 < screenWidth) scaleX *= 9f / 16f;
			var worldWidth1 = worldWidth0 / scaleX;
			var worldHeight1 = worldHeight0 / scaleY;
			
			//Vector2 scaled = Scaling.fit.apply(worldWidth, worldHeight, screenWidth, screenHeight);
			Log.info("update : " + String.format("%s, %s, %s, %s, [%s, %s]", worldWidth0, worldWidth1, screenWidth0, screenWidth1, scaleX, scaleY));
			
			setWorldSize(worldWidth1, worldHeight1);
			
			//setScreenBounds(Math.abs(screenWidth1 - screenWidth) / 2, Math.abs(screenHeight1 - screenHeight) / 2, screenWidth, screenHeight);
			setScreenBounds((baseScreenWidth - screenWidth1) / 2, (baseScreenHeight - screenHeight1) / 2, screenWidth1, screenHeight1);
			
		}
		
		apply(centerCamera);
	}
	
	private void extend(int screenWidth, int screenHeight, boolean centerCamera) {
		// Fit min size to the screen.
		float worldWidth = getWorldWidth(); //minWorldWidth;
		float worldHeight = getWorldHeight(); //minWorldHeight;
		//Vector2 scaled = Scaling.fit.apply(worldWidth, worldHeight, screenWidth, screenHeight);
		
		Vector2 scaled = new Vector2();
		float targetRatio = screenHeight / screenWidth;
		float sourceRatio = worldHeight / worldWidth;
		float scale = targetRatio > sourceRatio ? screenWidth / worldWidth : screenHeight / worldHeight;
		scaled.x = worldWidth * scale;
		scaled.y = worldHeight * scale;

		// Extend in the short direction.
		int viewportWidth = Math.round(scaled.x);
		int viewportHeight = Math.round(scaled.y);
		if (viewportWidth < screenWidth) {
			float toViewportSpace = viewportHeight / worldHeight;
			float toWorldSpace = worldHeight / viewportHeight;
			float lengthen = (screenWidth - viewportWidth) * toWorldSpace;
			//if (maxWorldWidth > 0) lengthen = Math.min(lengthen, maxWorldWidth - minWorldWidth);
			worldWidth += lengthen;
			viewportWidth += Math.round(lengthen * toViewportSpace);
		} else if (viewportHeight < screenHeight) {
			float toViewportSpace = viewportWidth / worldWidth;
			float toWorldSpace = worldWidth / viewportWidth;
			float lengthen = (screenHeight - viewportHeight) * toWorldSpace;
			//if (maxWorldHeight > 0) lengthen = Math.min(lengthen, maxWorldHeight - minWorldHeight);
			worldHeight += lengthen;
			viewportHeight += Math.round(lengthen * toViewportSpace);
		}

		setWorldSize(worldWidth, worldHeight);

		Log.info("update : " + String.format("%s, %s, %s, %s", worldWidth, worldHeight, screenWidth, viewportWidth));
		
		// Center.
		setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
	}
	
}
