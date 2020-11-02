package com.souchy.randd.commons.diamond.common.generic;

import java.util.function.BiConsumer;

public class BoolTable extends Table<Boolean> {
	
	public BoolTable(int width, int height) {
		super(width, height, false);
	}
	
	/**
	 * @param mult90
	 *            Nombre de rotationde 90 degrés : 1 = 90°, 2 = 180°, 3 = 270°, 4 =
	 *            360°
	 */
	public BoolTable rotate(int mult90) {
		int w = width();
		int h = height();
		var temp = mult90 % 2 == 0 ? new Table<Boolean>(w, h, false) : new Table<Boolean>(h, w, false);
		foreach((i, j) -> {
			var a = get(i, j);
			int ht, x = i, y = j;
			// pour chaque rotation de 90°
			for (int k = 0; k < mult90; k++) {
				ht = k % 2 == 0 ? h : w; // hauteur de la matrice à ce moment
				x = ht - y - 1; // nouveau x = hauteur - y - 1
				y = x; // nouveau y = x
			}
			temp.put(x, y, a);
		});
		copyTo(this);
		return this;
	}
	
	public BoolTable set(BoolTable table) {
		table.foreach((i, j) -> {
			var b = table.get(i, j);
			put(i, j, b);
		});
		return this;
	}
	
	public BoolTable sub(BoolTable table) {
		table.foreach((i, j) -> {
			var a = get(i, j);
			var b = table.get(i, j);
			put(i, j, a == true && b == false);
		});
		return this;
	}
	
	public BoolTable and(BoolTable table) {
		foreach((i, j) -> {
			var a = get(i, j);
			var b = table.get(i, j);
			put(i, j, a == true && b == true);
		});
		return this;
	}
	
	public BoolTable or(BoolTable table) {
		table.foreach((i, j) -> {
			var a = get(i, j);
			var b = table.get(i, j);
			put(i, j, a == true || b == true);
		});
		return this;
	}
	
	public BoolTable xor(BoolTable table) {
		int w = Math.max(width(), table.width());
		int h = Math.max(height(), table.height());
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var a = get(i, j);
				var b = table.get(i, j);
				put(i, j, a != b);
			}
		}
		return this;
	}
	
	public BoolTable inverse() {
		foreach((i, j) -> {
			put(i, j, !get(i, j));
		});
		return this;
	}
	
	public void foreachTrue(BiConsumer<Integer, Integer> action) {
		int w = width();
		int h = height();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if(get(i, j)) action.accept(i, j);
			}
		}
	}
	
	@Override
	public String toString() {
		var str = super.toString();
		str = str.replace("false", "0");
		str = str.replace("true", "1");
		return str;
	}
}