package com.souchy.randd.commons.diamond.common;

import com.souchy.randd.commons.diamond.common.generic.BoolTable;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

/** Aoe suppliers / functions */
public class Aoe implements BBSerializer, BBDeserializer {
	
	/** targeted cell from where the aoe expands */
	public Vector2 source;
	
	/** 2d boolean table showing what cells around the source are part of the aoe */
	public BoolTable table;
	
	public Aoe(int w, int h) {
		table = new BoolTable(w, h);
	}
	
	public Aoe fill() {
		table.fill(true);
		return this;
	}
	
	public Aoe inverse() {
		table.inverse();
		return this;
	}
	
	public Aoe sub(Aoe a) {
		table.sub(a.table);
		return this;
	}
	
	public Aoe move(int x, int y) {
		table.move(x, y);
		return this;
	}
	
	@Override
	public String toString() {
		return table.toString();
	}

	public Aoe copy() {
		Aoe a = new Aoe(table.width(), table.height());
		if(source != null) a.source = source.copy();
		table.copyTo(a.table);
		return a;
	}
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(source != null);
		if(source != null) source.serialize(out);
		out.writeInt(table.width());
		out.writeInt(table.height());
		table.foreach((x, y) -> {
			out.writeBoolean(table.get(x, y));
		});
		return null;
	}
	@Override
	public BBMessage deserialize(ByteBuf in) {
		boolean hassource = in.readBoolean();
		if(hassource) {
			source = new Vector2(0, 0);
			source.deserialize(in);
		}
		int width = in.readInt();
		int height = in.readInt();
		table = new BoolTable(width, height);
		table.foreach((x, y) -> {
			table.put(x, y, in.readBoolean());
		});
		return null;
	}
	
}