package gamemechanics.common;

import gamemechanics.ext.MapData;
import gamemechanics.models.Board;
import gamemechanics.models.entities.Cell;

public class BoardGenerator {
	
	public Board generate(MapData data) {
		Board board = new Board();
		var types = data.cellTypes;
		for (int x = 0; x < types[0].length; x++) {
			for (int y = 0; y < types.length; y++) {
				Cell cell = new Cell(x, y);
				//cell.properties.set(flags[y][x]);
				board.cells.put(x, y, cell);
			}
		}
		return board;
	}
	
}
