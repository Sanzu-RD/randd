package com.souchy.randd.ebishoal.commons.lapis.screens;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * {@link ExtendViewport} or {@link Scaling#fill} are pretty good
 * 
 * @author Souchy
 */
public class Viewports {
	
	/** 
	 * Creates a new viewport using a new {@link OrthographicCamera} with no maximum world size.  <p>
	 * 
	 * minWorldWidth and minWorldHeight is how much of the world you want to show. <br>
	 * Ex 2000x2000 units in LoL or 100x100 units in HiddenPiranha <br>
	 */
	public static ExtendViewport extend(float minWorldWidth, float minWorldHeight) {
		return new ExtendViewport(minWorldWidth, minWorldHeight, 0, 0, new OrthographicCamera());
	}

	/** Creates a new viewport with no maximum world size. <p>
	 * 
	 * minWorldWidth and minWorldHeight is how much of the world you want to show. <br>
	 * Ex 2000x2000 units in LoL or 100x100 units in HiddenPiranha <br>
	 */
	public static ExtendViewport extend(float minWorldWidth, float minWorldHeight, Camera camera) {
		return new ExtendViewport(minWorldWidth, minWorldHeight, 0, 0, camera);
	}

	/** Creates a new viewport using a new {@link OrthographicCamera} and a maximum world size. <p>
	 * 
	 * minWorldWidth and minWorldHeight is how much of the world you want to show. <br>
	 * Ex 2000x2000 units in LoL or 100x100 units in HiddenPiranha <br>
	 * 
	 * @see ExtendViewport#ExtendViewport(float, float, float, float, Camera) */
	public static ExtendViewport extend(float minWorldWidth, float minWorldHeight, float maxWorldWidth, float maxWorldHeight) {
		return new ExtendViewport(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, new OrthographicCamera());
	}

	/** Creates a new viewport with a maximum world size. <p>
	 * 
	 * minWorldWidth and minWorldHeight is how much of the world you want to show. <br>
	 * Ex 2000x2000 units in LoL or 100x100 units in HiddenPiranha <br>
	 * 
	 * @param maxWorldWidth User 0 for no maximum width.
	 * @param maxWorldHeight User 0 for no maximum height. */
	public static ExtendViewport extend(float minWorldWidth, float minWorldHeight, float maxWorldWidth, float maxWorldHeight, Camera camera) {
		return new ExtendViewport(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, camera);
	}
	

	/** Creates a new viewport using a new {@link OrthographicCamera}. */
	public static ScalingViewport scaling(Scaling scaling, float worldWidth, float worldHeight) {
		return new ScalingViewport(scaling, worldWidth, worldHeight, new OrthographicCamera());
	}
	
	/**
	 *  A viewport that scales the world using {@link Scaling}.
	 * <p>
	 * {@link Scaling#fit} keeps the aspect ratio by scaling the world up to fit the screen, adding black bars (letterboxing) for the
	 * remaining space.
	 * <p>
	 * {@link Scaling#fill} keeps the aspect ratio by scaling the world up to take the whole screen (some of the world may be off
	 * screen).
	 * <p>
	 * {@link Scaling#stretch} does not keep the aspect ratio, the world is scaled to take the whole screen.
	 * <p>
	 * {@link Scaling#none} keeps the aspect ratio by using a fixed size world (the world may not fill the screen or some of the world
	 * may be off screen).
	 *  
	 */
	public static ScalingViewport scaling(Scaling scaling, float worldWidth, float worldHeight, Camera camera) {
		return new ScalingViewport(scaling, worldWidth, worldHeight, camera);
	}
	
	/** Creates a new viewport using a new {@link OrthographicCamera}. */
	public static ScreenViewport screen() {
		return new ScreenViewport();
	}
	/**
	 *  A viewport where the world size is based on the size of the screen. 
	 *  By default 1 world unit == 1 screen pixel, but this ratiocan be {@link #setUnitsPerPixel(float) changed}.
	 * @param camera
	 */
	public static ScreenViewport screen(Camera camera) {
		return new ScreenViewport(camera);
	}
}
