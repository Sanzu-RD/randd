package gamemechanics.statics.filters;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.souchy.randd.commons.tealwaters.logging.Log;

public enum AoePattern {
	
	Single(new int[][] { { 1 } }),
	
	H2(new int[][] { { 1, 1 } }),
	
	V2(new int[][] { { 1 }, { 1 }, }), V3(new int[][] { { 1 }, { 1 }, { 1 }, }), V4(new int[][] { { 1 }, { 1 }, { 1 }, { 1 }, }),
	
	Square3(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }),
	
	;
	
	public final int[][] pattern;
	
	private AoePattern(int[][] pattern) {
		this.pattern = pattern;
	}
	
	public static class Aoe {
		public static Supplier<Aoe> single = () -> new Aoe(1, 1);
		public static Function<Integer, Aoe> lineH = (length) -> {
			var aoe = new Aoe(length, 1);
			aoe.table.fill(true);
			return aoe;
		};
		public static Function<Integer, Aoe> lineV = (length) -> {
			var aoe = new Aoe(1, length);
			aoe.table.fill(true);
			return aoe;
		};
		public static BiFunction<Integer, Integer, Aoe> rect = (width, height) -> {
			var aoe = new Aoe(width, height);
			aoe.table.fill(true);
			return aoe;
		};
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
		public static Function<Integer, Aoe> circle = (radius) -> {
			int w = radius * 2 - 1;
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
		public static Function<Integer, Aoe> circleEmpty = (radius) -> {
			int w = radius * 2 - 1;
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
		
		public BoolTable table;
		
		public Aoe(int w, int h) {
			table = new BoolTable(w, h);
		}
		
		@Override
		public String toString() {
			return table.toString();
		}
	}
	
	public static class Table<V> {
		private HashMap<Integer, HashMap<Integer, V>> map;
		private V defaul;
		
		/**
		 * Create a table and fills x rows and y columns with the default value. <br>
		 * The default value will be the one returned if get(x,y) is null.
		 */
		public Table(int width, int height, V defaul) {
			this.defaul = defaul;
			map = new HashMap<>();
			for (int i = 0; i < height; i++) {
				map.put(i, new HashMap<>());
				for (int j = 0; j < width; j++) {
					map.get(i).put(j, defaul);
				}
			}
		}
		
		public int width() {
			int max = 0;
			for (var c : map.values())
				if(c.size() > max) max = c.size();
			return max;
		}
		
		public int height() {
			return map.size();
		}
		
		/**
		 * @param x
		 *            = column
		 * @param y
		 *            = row
		 * @return a value or the default value if null
		 */
		public V get(Integer x, Integer y) {
			var column = map.get(y);
			if(column == null) return defaul;
			var v = column.get(x);
			if(v == null) return defaul;
			return v;
		}
		
		/**
		 * @param x
		 *            = column
		 * @param y
		 *            = row
		 * @param v
		 *            value to set
		 * @return Table<V> return this for chaining
		 */
		public Table<V> put(Integer x, Integer y, V v) {
			var column = map.get(y);
			if(column == null) map.put(y, new HashMap<>());
			map.get(y).put(x, v);
			return this;
		}
		
		/**
		 * Fill the table with a value
		 * 
		 * @return Table<V> return this for chaining
		 */
		public Table<V> fill(V v) {
			foreach((i, j) -> {
				put(i, j, v);
			});
			return this;
		}
		
		/**
		 * Fill a rectangle in the table a value
		 * 
		 * @return Table<V> return this for chaining
		 */
		public Table<V> fill(int w, int h, V v) {
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					put(i, j, v);
				}
			}
			return this;
		}
		
		/**
		 * Keeps the present values and sets the default to any new cells. <br>
		 * This can be used to cut a table in 2 and keep only one part.
		 * 
		 * @return Table<V> return this for chaining
		 */
		public Table<V> setSize(int w, int h) {
			var copy = copy();
			empty();
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					put(i, j, copy.get(i, j));
				}
			}
			return this;
		}
		
		/**
		 * Move the values by an indent in x and y. <br>
		 * Coordinates that become negative are ignored. <br>
		 * Fills previous space with default value;
		 * 
		 * @return Table<V> return this for chaining
		 */
		public Table<V> move(int x, int y) {
			// copy this to a temporary table
			var copy = copy();
			// empty this
			empty();
			fill(copy.width() + x, copy.height() + y, defaul);
			// then bring back the values but with the offset (only if the resulting
			// coordinates are positive)
			copy.foreach((i, j) -> {
				if(i + x >= 0 && j + y >= 0) {
					put(i + x, j + y, copy.get(i, j));
				}
			});
			return this;
		}
		
		public void foreach(BiConsumer<Integer, Integer> action) {
			int w = width();
			int h = height();
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					action.accept(i, j);
				}
			}
		}
		
		@Override
		public String toString() {
			var str = "\n";
			int w = width();
			int h = height();
			for (int j = 0; j < h; j++) {
				for (int i = 0; i < w; i++) {
					str += get(i, j);
					str += " ";
				}
				str += "\n";
			}
			return str;
		}
		
		public Table<V> copy() {
			return copyTo(new Table<V>(width(), height(), defaul));
		}
		
		public Table<V> copyTo(Table<V> copy) {
			copy.empty();
			foreach((i, j) -> {
				copy.put(i, j, get(i, j));
			});
			return copy;
		}
		
		public Table<V> empty() {
			this.map.clear();
			return this;
		}
		
		public Table<V> mirrorH() {
			var copy = copy();
			var w = width();
			copy.foreach((i, j) -> {
				put(i, j, copy.get(w - i - 1, j));
			});
			return this;
		}
		
		public Table<V> mirrorV() {
			var copy = copy();
			var h = height();
			copy.foreach((i, j) -> {
				put(i, j, copy.get(i, h - j - 1));
			});
			return this;
		}
	}
	
	public static class BoolTable extends Table<Boolean> {
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
		
		@Override
		public String toString() {
			var str = super.toString();
			str = str.replace("false", "0");
			str = str.replace("true", "1");
			return str;
		}
	}
	
}
