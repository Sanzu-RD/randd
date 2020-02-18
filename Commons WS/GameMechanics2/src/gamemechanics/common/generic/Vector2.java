package gamemechanics.common.generic;

public class Vector2 {
	
	public double x;
	public double y;
	
	public Vector2(double x2, double y2) {
		x = x2;
		y = y2;
	}


	public Vector2 add(int i, int j) {
		x += i;
		y += j;
		return this;
	}
	
	public Vector2 sub(Vector2 pos) {
		this.x -= pos.x;
		this.y -= pos.y;
		return this;
	}

	public double sum() {
		return x + y;
	}
	
	public double hypot() {
		return Math.hypot(x, y);
	}

	public Vector2 abs() {
		x = Math.abs(x);
		y = Math.abs(y);
		return this;
	}
	
	public Vector2 copy() {
		return new Vector2(x, y);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o instanceof Vector2 == false) return false;
		Vector2 v = (Vector2) o;
		return v.x == y && v.y == y;
	}


	
}
