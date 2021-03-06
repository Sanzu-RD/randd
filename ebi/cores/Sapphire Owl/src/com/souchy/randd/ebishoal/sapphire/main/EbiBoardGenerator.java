package com.souchy.randd.ebishoal.sapphire.main;

import com.souchy.randd.commons.diamond.common.BoardGenerator;
import com.souchy.randd.commons.diamond.ext.CellType;
import com.souchy.randd.commons.mapio.MapData;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class EbiBoardGenerator extends BoardGenerator {
	
	@Override
	public Board generate(Fight f, MapData data) {
//		Board board = new Board(f);
		var board = f.board;
		var types = data.cellTypes;

		//data.models;
		
		for (int x = 0; x < types[0].length; x++) {
			for (int y = 0; y < types.length; y++) {

				int typeOrdinal = types[x][y];
				CellType type = CellType.values()[typeOrdinal];
				//int model = data.cellModels[typeOrdinal];
				//Model m = SapphireOwl.core.getGame().modelManager.loadSync(data.modelList[model]);
				
				Cell cell = new Cell(f, x, y); //EbiCell(x, y, new ModelInstance(m));
				
				// Log.format("new Cell(%s, %s, %s)", x, y, type);
//				for (int t = 0; t < Targetability.values().length; t++)
//					cell.targeting.setCan(Targetability.values()[t], type.targetability[t]);
				type.t.copyTo(cell.targetting);
				
				
				board.cells.put(x, y, cell);
			}
		}
		return board;
	}
	
}
