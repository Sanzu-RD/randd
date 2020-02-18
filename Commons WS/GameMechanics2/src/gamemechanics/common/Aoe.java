package gamemechanics.common;

import gamemechanics.common.generic.BoolTable;
import gamemechanics.common.generic.Vector2;

/** Aoe suppliers / functions */
public class Aoe {
	
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
	
}