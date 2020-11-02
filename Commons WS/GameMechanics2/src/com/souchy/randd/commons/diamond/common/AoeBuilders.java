package com.souchy.randd.commons.diamond.common;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class AoeBuilders {
	
	
	public static Supplier<Aoe> single = () -> new Aoe(1, 1).fill();
	public static Function<Integer, Aoe> lineH = (length) -> new Aoe(length, 1).fill();
	public static Function<Integer, Aoe> lineV = (length) -> new Aoe(1, length).fill();
	public static BiFunction<Integer, Integer, Aoe> rectangle = (width, height) -> new Aoe(width, height).fill();
	
	// je le laisse pour l'instant, mais pas besoin de ça. Il vaut mieux faire aoe =
	// rect(3,3).sub(rect(2,2);
	public static BiFunction<Integer, Integer, Aoe> rectEmpty = (width, height) -> {
		var aoe = new Aoe(width, height);
		for (int i = 0; i < width; i++) {
			aoe.table.put(i, 0, true);
			aoe.table.put(i, height - 1, true);
		}
		for (int j = 0; j < height; j++) {
			aoe.table.put(0, j, true);
			aoe.table.put(width - 1, j, true);
		}
		return aoe;
	};
	/** circle = diamond */
	public static Function<Integer, Aoe> circle = (radius) -> {
		radius++;
		int w = radius * 2; // - 1;
		var aoe = new Aoe(w, w);
		for (int i = -radius; i < radius; i++) {
			for (int j = -radius; j < radius; j++) {
				if(Math.abs(i) + Math.abs(j) <= radius - 1) {
					aoe.table.put(i + radius - 1, j + radius - 1, true);
				}
			}
		}
		return aoe;
	};
	/** une moitié de cercle */
	// public static Function<Integer, Aoe> cone = (radius) -> {
	//
	// };
	// je le laisse pour l'instant, mais pas besoin de ça. Il vaut mieux faire aoe =
	// circle(3,3).sub(circle(2,2);
	public static Function<Integer, Aoe> circleEmpty = (radius) -> {
		radius++;
		int w = radius * 2; // - 1;
		var aoe = new Aoe(w, w);
		for (int i = -radius; i < radius; i++) {
			for (int j = -radius; j < radius; j++) {
				if(Math.abs(i) + Math.abs(j) == radius - 1) {
					aoe.table.put(i + radius - 1, j + radius - 1, true);
				}
			}
		}
		return aoe;
	};
	public static Function<Integer, Aoe> diag1 = (length) -> {
		var aoe = new Aoe(length, length);
		for (int i = 0; i < length; i++) {
			aoe.table.put(i, length - i - 1, true);
		}
		return aoe;
	};
	public static Function<Integer, Aoe> diag2 = (length) -> {
		var aoe = new Aoe(length, length);
		for (int i = 0; i < length; i++) {
			aoe.table.put(i, i, true);
		}
		return aoe;
	};
	
	
}