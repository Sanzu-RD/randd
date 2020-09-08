package com.souchy.randd.commons.diamond.models;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

public class Board extends Entity {
	
	public Table<Integer, Integer, Cell> cells;
	
	public Board(Fight f) {
		super(f);
		readMap();
	}
	
	/**
	 * Load json with cell properties (line of sight, walkable, ..)
	 */
	public void readMap() { 
		var fight = get(Fight.class);
		cells = HashBasedTable.create();
		for(int i = 0; i < 30; i++)
			for(int j = 0; j < 30; j++)
				cells.put(i, j, new Cell(fight, i, j));
	}
	
}