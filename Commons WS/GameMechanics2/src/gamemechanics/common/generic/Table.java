package gamemechanics.common.generic;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class Table<V> {
	
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