package gamemechanics.models;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import gamemechanics.models.entities.Cell;

public class Board {
	
	public Table<Integer, Integer, Cell> cells;
	
	public Board() {
		readMap();
	}
	
	/**
	 * Load json with cell properties (line of sight, walkable, ..)
	 */
	public void readMap() { 
		cells = HashBasedTable.create();
		for(int i = 0; i < 30; i++)
			for(int j = 0; j < 30; j++)
				cells.put(i, j, new Cell(i, j));
	}
	
}