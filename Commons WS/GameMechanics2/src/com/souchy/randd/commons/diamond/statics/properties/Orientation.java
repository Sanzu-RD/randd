package com.souchy.randd.commons.diamond.statics.properties;

import com.souchy.randd.commons.diamond.common.generic.Vector2;

public enum Orientation {
	
	North(0,1) {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(0, 1); //target.x, target.y + 1)
		}
	},
	East(1,0) {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(1, 0);
		}
	},
	South(0,-1) {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(0, -1);
		}
	},
	West(-1,0) {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(-1, 0);
		}
	};
	
	public abstract Vector2 getPositionByDirection(Vector2 target);

	
	public int x, y;
	private Orientation(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public int scaledDistance(int dx, int dy) {
		return x * dx + y * dy;
	}
	
}
