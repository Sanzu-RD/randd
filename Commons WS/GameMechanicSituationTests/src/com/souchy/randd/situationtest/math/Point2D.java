package com.souchy.randd.situationtest.math;

public class Point2D {

	public static final Point2D Zero = new Point2D();
	
	public int x;
	public int y;

	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D() {
		this(0, 0);
	}

	private Point2D(Point2D p) {
		this(p.x, p.y);
	}

	public Point2D copy() {
		return new Point2D(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o instanceof Point2D == false) return false;
		Point2D p = (Point2D) o; 
		return p.x == x && p.y == y;
	}
	
	public Point2D add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	public Point2D sub(int x, int y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	public Point2D add(Point2D p) {
		return add(p.x, p.y);
	}
	public Point2D sub(Point2D p) {
		return sub(p.x, p.y);
	}
	
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
	
}
