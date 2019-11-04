package com.souchy.randd.ebishoal.sapphire.main;

import gamemechanics.common.BoardGenerator;
import gamemechanics.ext.CellType;
import gamemechanics.ext.MapData;
import gamemechanics.models.Board;
import gamemechanics.models.entities.Cell;
import gamemechanics.statics.properties.Targetability;

public class EbiBoardGenerator extends BoardGenerator {
	
	@Override
	public Board generate(MapData data) {
		Board board = new Board();
		var types = data.cellTypes;

		//data.models;
		
		for (int x = 0; x < types[0].length; x++) {
			for (int y = 0; y < types.length; y++) {

				int typeOrdinal = types[y][x];
				CellType type = CellType.values()[typeOrdinal];
				//int model = data.cellModels[typeOrdinal];
				//Model m = SapphireOwl.core.getGame().modelManager.loadSync(data.modelList[model]);
				
				Cell cell = new Cell(x, y); //EbiCell(x, y, new ModelInstance(m));

				for(int t = 0; t < Targetability.values().length; t++) 
					cell.properties.setCan(Targetability.values()[t], type.targetability[t]);
				board.cells.put(x, y, cell);
			}
		}
		return board;
	}
	
}
