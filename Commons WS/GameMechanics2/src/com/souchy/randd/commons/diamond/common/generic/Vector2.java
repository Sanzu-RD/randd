package com.souchy.randd.commons.diamond.common.generic;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

public class Vector2 implements BBSerializer, BBDeserializer {
	
	public double x;
	public double y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2 set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2 add(int i, int j) {
		x += i;
		y += j;
		return this;
	}
	
	public Vector2 sub(Vector2 pos) {
		x -= pos.x;
		y -= pos.y;
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
	
	public Vector2 mult(double scl) {
		x *= scl;
		y *= scl;
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o instanceof Vector2 == false) return false;
		Vector2 v = (Vector2) o;
		return v.x == x && v.y == y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeDouble(x);
		out.writeDouble(y);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		x = in.readDouble();
		y = in.readDouble();
		return null;
	}

	
}
