package com.souchy.randd.situationtest.math;

public class Point3D {

	public static final Point3D Zero = new Point3D();
	
	public int x;
	public int y;
	public int z;

	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D() {
		this(0, 0, 0);
	}

	private Point3D(Point3D p) {
		this(p.x, p.y, p.z);
	}

	public Point3D copy() {
		return new Point3D(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o instanceof Point3D == false) return false;
		Point3D p = (Point3D) o; 
		return p.x == x && p.y == y && p.z == z;
	}

}
