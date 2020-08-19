package com.souchy.randd.commons.diamond.common;

import com.souchy.randd.commons.diamond.ext.MapData;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Fight;

public class BoardGenerator {
	
	public Board generate(Fight f, MapData data) {
		Board board = new Board(f);
		var types = data.cellTypes;
		for (int x = 0; x < types[0].length; x++) {
			for (int y = 0; y < types.length; y++) {
				Cell cell = new Cell(f, x, y);
				//cell.properties.set(flags[y][x]);
				board.cells.put(x, y, cell);
			}
		}
		return board;
	}
	
}
