package com.souchy.randd.situationtest.situations;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.souchy.randd.situationtest.math.Point2D;
import com.souchy.randd.situationtest.math.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.math.matrixes.Matrix;

public class Situation1 {
	
	public void test() {
		Matrix map = new Matrix(new int[][]{
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
		});
		
		EffectMatrix effect = new EffectMatrix(new int[][] {
			{2,2,1,2,2,},
		});
		
		apply(map, effect, new Point2D(9,1), (appliedMap, eff, local, global) -> {
			appliedMap.set(global.x, global.y, eff.get(local.x, local.y));
		});
	}
	
	@FunctionalInterface
	interface Function1<One, Two, Three, Four> {
	    public void apply(One one, Two two, Three three, Four four);
	}
	
	public static <T> void apply(Matrix map, EffectMatrix effect, Point2D target, Function1<Matrix, EffectMatrix, Point2D, Point2D> action) {
		Matrix appliedMap = map.copy();
		effect.foreach((i, j) -> {
			// i et j sont les positions relative à la matrice de l'effet
			Point2D local = new Point2D(i, j);
			// x et y sont les positions absolue dans la map
			Point2D global = local.copy().add(target).sub(effect.insideSource);
			action.apply(appliedMap, effect, local, global);
			//System.out.println(new Point2D(x, y));
		});

		System.out.println("Effect source : \n" + effect.insideSource);
		System.out.println("Effect map : \n" + effect.toString());
		System.out.println("Applied map : \n" + appliedMap.toString());
	}
	

}
