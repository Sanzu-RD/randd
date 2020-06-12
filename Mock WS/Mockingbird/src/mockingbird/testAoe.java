package mockingbird;

import com.souchy.randd.commons.tealwaters.logging.Log;

import gamemechanics.common.AoeBuilders;

public class testAoe {
	
	public static void main() {

		//var aoe1 = Aoe.circleEmpty.apply(3);
		var interieur = AoeBuilders.circle.apply(2).move(1, 1);
		var aoe1 = AoeBuilders.circle.apply(3).sub(interieur);
		var aoe2 = AoeBuilders.lineV.apply(5);

		Log.info(aoe1.toString());
		
		//var result = aoe2.table.move(2, 2).mirrorV().mirrorH().mirrorH();
		
		var result = AoeBuilders.diag1.apply(6).table.or(aoe2.table);
		
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
