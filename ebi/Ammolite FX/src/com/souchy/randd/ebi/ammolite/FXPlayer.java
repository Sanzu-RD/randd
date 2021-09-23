package com.souchy.randd.ebi.ammolite;

import java.util.function.Function;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

public abstract class FXPlayer<T extends Event> extends Entity {
	
	public FXPlayer(Engine engine) {
		super(engine);
	}
	
	public abstract Class<?> modelClass();
	public abstract void onCreation(T event);
	
	@SuppressWarnings("unchecked")
	public FXPlayer<T> copy(Engine e) {
		try {
			var copy = this.getClass().getDeclaredConstructor(Engine.class).newInstance(e);
			return copy;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * When the parent entity is disposed, dispose the FXPlayer as well
	 */
	@Subscribe
	public void onParentDispose(DisposeEntityEvent e) {
		this.dispose();
	}
	
	
	public static class FXDuration {
		private double duration = 0;
		private double time = 0;
		public FXDuration(double duration) {
			this.duration = duration;
		}
		public boolean update(float delta) {
			if(time >= duration) return true;
			time += delta;
			return false;
		}
	}
	
	/**
	 * Interpolation d'une valeur (multiplicateur double ou vecteur par exemple) basé sur un temps / durée.
	 * @param <R>
	 * @author Blank
	 * @date 8 sept. 2021
	 */
	public static class FXInterpolation<R> {
		private double duration = 0;
		private double time = 0;
		protected Function<Double, R> multiplier;
		public FXInterpolation(double duration) {
			this.duration = duration;
		}
		public FXInterpolation(double duration, Function<Double, R> multiplier) {
			this.duration = duration;
			this.multiplier = multiplier;
		}
		public void update(float delta) {
			time += delta;
		}
		public double percent() {
			return Math.min(1d, time / duration);
		}
		public R value() {
			if(multiplier == null) return null;
			return multiplier.apply(percent());
		}
	}

	/**
	 * Interpole un double basé sur un temps / durée
	 * 
	 * @author Blank
	 * @date 8 sept. 2021
	 */
	public static class FXInterpolationD extends FXInterpolation<Double> {
		public FXInterpolationD(double duration, double v) {
			super(duration);
			multiplier = (f) -> v * f;
		}
	}
	/**
	 * Interpole un vecteur2 basé sur un temps / durée
	 * 
	 * @author Blank
	 * @date 8 sept. 2021
	 */
	public static class FXInterpolationV2 extends FXInterpolation<Vector2> {
		public FXInterpolationV2(double duration, Vector2 v) {
			super(duration);
			multiplier = (f) -> v.copy().mult(f);
		}
	}
	
}
