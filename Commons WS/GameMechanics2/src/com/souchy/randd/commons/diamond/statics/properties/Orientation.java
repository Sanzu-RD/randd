package com.souchy.randd.commons.diamond.statics.properties;

import com.souchy.randd.commons.diamond.common.generic.Vector2;

public enum Orientation {
	
	North {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(0, 1); //target.x, target.y + 1)
		}
	},
	East {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(1, 0);
		}
	},
	South {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(0, -1);
		}
	},
	West {
		@Override
		public Vector2 getPositionByDirection(Vector2 target) {
			return target.copy().add(-1, 0);
		}
	};
	
	public abstract Vector2 getPositionByDirection(Vector2 target);

}
