package mockingbird;

import com.souchy.randd.commons.tealwaters.logging.Log;

import gamemechanics.statics.filters.AoePattern.Aoe;

public class testAoe {
	
	public static void main() {

		var aoe1 = Aoe.circleEmpty.apply(3);
		var aoe2 = Aoe.lineV.apply(5);
		
		//var result = aoe2.table.move(2, 2).mirrorV().mirrorH().mirrorH();
		
		var result = Aoe.diag1.apply(6).table.or(aoe2.table);
		
		Log.info(result.toString());
		
		
//		int x = 2, y = 0;
//		
//		// copy this to a temporary table
//		var copy = aoe2.table.copy();
//
//		Log.info("copy1 : " + copy.toString());
//		
//		// empty this
//		aoe2.table.empty();
//
//		Log.info("aoe : " + aoe2.toString());
//		Log.info("copy2 : " + copy.toString());
//		
//		// then bring back the values but with the offset (only if coordinates are positive)
//		copy.foreach((i, j) -> {
//			aoe2.table.put(i, j, false);
//			Log.info("[i,j] = v = " + copy.get(i, j));
//			if(i + x > 0 && j + y > 0) 
//				aoe2.table.put(i + x, j + y, copy.get(i, j));
//		});
//
//		Log.info("aoe : " + aoe2.toString());
//		Log.info("copy2 : " + copy.toString());
//
		
	}
	
}
