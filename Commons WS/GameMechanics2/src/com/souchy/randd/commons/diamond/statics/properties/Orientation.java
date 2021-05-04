package com.souchy.randd.commons.diamond.statics.properties;

import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.generic.Vector2;

public enum Orientation {
	
	North(0, 1) {
	},
	East(1, 0) {
	},
	South(0, -1) {
	},
	West(-1, 0) {
	};
	
	public static Orientation getOrientation(Vector2 dir) {
		return Pathfinding.getOrientation(dir.x, dir.y);
	}
	
	public int x, y;
	private Orientation(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public int scaledDistance(int dx, int dy) {
		return x * dx + y * dy;
	}

	public Vector2 add(Vector2 target) {
		return add(target, true);
	}
	public Vector2 add(Vector2 target, boolean copy) {
		var v = copy ? target.copy() : target;
		return v.add(x, y);
	}
	public Vector2 mult(Vector2 target) {
		return mult(target, true);
	}
	public Vector2 mult(Vector2 target, boolean copy) {
		var v = copy ? target.copy() : target;
		v.x = v.x * x;
		v.y = v.y * y;
		return v;
	}
	
	public Vector2 toVector() {
		return new Vector2(x, y);
	}
	public Vector2 mult(int dist) {
		return toVector().mult(dist);
	}


}
