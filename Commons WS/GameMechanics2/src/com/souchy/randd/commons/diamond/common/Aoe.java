package com.souchy.randd.commons.diamond.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.souchy.randd.commons.diamond.common.generic.BoolTable;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

/** Aoe suppliers / functions */
public class Aoe implements BBSerializer, BBDeserializer {
	
	/** targeted cell from where the aoe expands */
	public Vector2 source;
	
	/** 2d boolean table showing what cells around the source are part of the aoe */
	@BsonIgnore
	public BoolTable table;
	
	public Aoe(int w, int h) {
		table = new BoolTable(w, h);
		source = new Vector2(Math.floor(w / 2d), Math.floor(h / 2d));
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
	
	/* * foreach cell contained by the aoe (= set to true) */
//	public void forEach(BiConsumer<Integer, Integer> action) {
//		table.foreachTrue(
//				(i, j) -> action.accept(
//						(int) (i - source.x), // - Math.floor(table.width() / 2d)), 
//						(int) (j - source.y) // - Math.floor(table.height() / 2d))
//						)
//				);
//	}
	
	/**
	 * List of cells in the aoe projected on the board.
	 * @param target Position of the target cell on the board
	 */
	public List<Vector2> toBoard(Vector2 target) {
		var list = new ArrayList<Vector2>();
		table.foreachTrue((i, j) -> list.add(toBoard(i, j, target.x, target.y)));
		return list;
	}
	
	public Vector2 toBoard(double i, double j, double x, double y) {
		return new Vector2(i - source.x + x, j - source.y + y);
	}
	
	public double projectX(double i, double x) {
		return i - source.x + x;	
	}
	public double projectY(double j, double y) {
		return j - source.y + y;
	}
	
	/* * new list of all 'true' cells */
//	public List<Vector2> asList(){
//		var list = new ArrayList<Vector2>();
//		foreach((x, y) -> list.add(new Vector2(x, y)));
//		return list;
//	}
	
	@Override
	public String toString() {
		return table.toString();
	}

	public Aoe copy() {
		Aoe a = new Aoe(table.width(), table.height());
//		if(source != null) 
		a.source = source.copy();
		table.copyTo(a.table);
		return a;
	}
	@Override
	public ByteBuf serialize(ByteBuf out) {
//		out.writeBoolean(source != null);
//		if(source != null) 
		source.serialize(out);
		out.writeInt(table.width());
		out.writeInt(table.height());
		table.foreach((x, y) -> {
			out.writeBoolean(table.get(x, y));
		});
		return null;
	}
	@Override
	public BBMessage deserialize(ByteBuf in) {
//		boolean hassource = in.readBoolean();
//		if(hassource) {
			source = new Vector2(0, 0);
			source.deserialize(in);
//		}
		int width = in.readInt();
		int height = in.readInt();
		table = new BoolTable(width, height);
		table.foreach((x, y) -> {
			table.put(x, y, in.readBoolean());
		});
		return null;
	}
	
}