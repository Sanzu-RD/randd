package com.souchy.randd.commons.diamond.common;

import com.souchy.randd.commons.diamond.ext.CellType;
import com.souchy.randd.commons.diamond.ext.MapData;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class BoardGenerator {
	
	public Board generate(Fight f, MapData data) {
		var board = f.board;
		var types = data.cellTypes;
		
		for (int x = 0; x < types[0].length; x++) {
			for (int y = 0; y < types.length; y++) {
				Cell cell = new Cell(f, x, y);
				
				int typeOrdinal = types[x][y];
				CellType type = CellType.values()[typeOrdinal];
				type.t.copyTo(cell.targetting);
				
				//Log.format("BoardGenerator new Cell %s, %s, %s", x, y, cell);

				board.cells.put(x, y, cell);
			}
		}
		
		return board;
	}
	
}
