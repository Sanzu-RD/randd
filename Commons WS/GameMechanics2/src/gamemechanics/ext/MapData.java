package gamemechanics.ext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

import com.google.gson.JsonSyntaxException;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

import gamemechanics.models.entities.Cell;
import gamemechanics.statics.properties.Targetability;

public class MapData {
	
	public static MapData read(String path) {
		try {
			var mapPath = new File(path).toPath();
			MapData data = JsonConfig.gson.fromJson(Files.readString(mapPath), MapData.class);

//			data.layer0Models = reverse(data.layer0Models);
//			data.cellModels = reverse(data.cellModels);
//			data.layer2Models = reverse(data.layer2Models);
	        
			return data;
		} catch (JsonSyntaxException | IOException e) {
			Log.warning("MapData.read error : ", e);
			return null;
		}
	}
	
	private static int[][] reverse(int[][] array) {
        var list = Arrays.asList(array);
        Collections.reverse(list);
        return array;
	}
	
	/**
	 * Cell types for the whole table
	 */
	public int[][] cellTypes;
	

	/**
	 * IDs for a model for each cell in the second layer. IDs start at one (1).
	 */
	public int[][] layer0Models;
	
	/**
	 * IDs for a model for each cell. IDs start at one (1).
	 */
	public int[][] cellModels;

	/**
	 * IDs for a model for each cell in the second layer. IDs start at one (1).
	 */
	public int[][] layer2Models;
	
	/**
	 * List of models. id 1 = index 0
	 */
	private CellData[] modelList;

	public CellData getModel(int modelID) {
		if(modelID <= 0) return null;
		return modelList[modelID - 1];
	}
	public void foreachModel(Consumer<CellData> consumer) {
		for(var m : modelList)
			consumer.accept(m);
	}
	
	
	/**
	 * Model path for each type of cell
	 */
	//public CellModel[] typeModels;
	
	
	// first half of this would be in a board generator class (cell creation)
	// then sapphireowl would extend the generator class to add model generation
	public void generate() {
		
		for (int i = 0; i < cellTypes.length; i++) {
			int j = 0;
			
			int typeOrdinal = cellTypes[i][j];
			CellType type = CellType.values()[typeOrdinal];
			int modelID = cellModels[i][j]; //typeModels[typeOrdinal];
			CellData model = modelList[modelID];
			
			
			var cell = new Cell(i, j);
			for(int t = 0; t < Targetability.values().length; t++) {
				cell.properties.setCan(Targetability.values()[t], type.targetability[t]);
			}
			
			/*
			String path = (String) models[i][j++];
			double x = (double) models[i][j++];
			double y = (double) models[i][j++];
			double z = (double) models[i][j++];
			double qx = (double) models[i][j++];
			double qy = (double) models[i][j++];
			double qz = (double) models[i][j++];
			*/
			//new Model("");
		}
		
		
		
	}
	
	
}
