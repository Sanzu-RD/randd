package mockingbird;

import com.google.common.base.Function;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.tealwaters.data.tables.BoolTable;
import com.souchy.randd.commons.tealwaters.data.tables.Table;
import com.souchy.randd.commons.tealwaters.logging.Logging;

public class Mockingbird {
	
	
	public static void main(String[] args) {
		Logging.registerLogModule(Mockingbird.class);
		
		testAoe.main();
		if(true) return;
		
		
		var fight = new Fight();
		var b = new Board(fight);
		int w = 15;
		int h = 15;
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				var cell = new Cell(fight, i, j);
				cell.targeting.initCellFloor();
				b.cells.put(i, j, cell);
			}
		}
//		b.cells.get(2, 0).targeting.setCan(Targetability.CanBeCastedThrough, false);
//		b.cells.get(1, 1).targeting.setCan(Targetability.CanBeCastedThrough, false);
//		b.cells.get(4, 4).targeting.setCan(Targetability.CanBeCastedThrough, false);
//		b.cells.get(6, 6).targeting.setCan(Targetability.CanBeCastedThrough, false);
		b.cells.get(4, 3).targeting.setCan(Targetability.CanBeCastedThrough, false);
		b.cells.get(6, 7).targeting.setCan(Targetability.CanBeCastedThrough, false);
		b.cells.get(6, 4).targeting.setCan(Targetability.CanBeCastedThrough, false);
		b.cells.get(4, 6).targeting.setCan(Targetability.CanBeCastedThrough, false);
//		b.cells.get(3, 5).targeting.setCan(Targetability.CanBeCastedThrough, false);
//		b.cells.get(5, 8).targeting.setCan(Targetability.CanBeCastedThrough, false);
//		b.cells.get(5, 3).targeting.setCan(Targetability.CanBeCastedThrough, false);
//		b.cells.get(8, 5).targeting.setCan(Targetability.CanBeCastedThrough, false);
		

		var result = new Table<Integer>(w, h, 0);
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				result.put(i, j, b.get(i, j).targeting.can(Targetability.CanBeCastedThrough) ? 0 : 3);
			}
		}
		
		
		var pos0 = new Vector2(5, 5);
//		var pos1 = new Vector2(2, 4);

		result.put((int) pos0.x, (int) pos0.y, 5);
		
//		var target = b.get(pos1);
		var view = true;
		// view &= checkCellViewOn(caster, target);
		
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if(result.get(i, j) == 0) result.put(i, j, checkView(b, pos0, new Vector2(i, j)) ? 1 : 0);
			}
		}

		System.out.println(result.toString());
	}
	
	private static boolean checkView(Board b, Vector2 pos0, Vector2 pos1) {
		boolean log = false;
		if(pos1.x == 4 && pos1.y == 7) log = true;
		
		double dirY = pos1.y - pos0.y >= 0 ? 1 : -1;
		double dirX = pos1.x - pos0.x >= 0 ? 1 : -1;
		
		if(pos0.x == pos1.x) {
			for(double i = pos0.y; i * dirY <= pos1.y * dirY; i += dirY) {
				int tx = (int) pos0.x;
				int ty = (int) i;
				var asdc = b.get(tx, ty);
				if(!mockCheckCellView(asdc)) return false;
			}
			return true;
		}
		
		double slope = (pos0.y - pos1.y) / (pos0.x - pos1.x);
		
		double c = pos0.y - slope * pos0.x;
		Function<Double, Double> f = x -> slope * x + c;
		

		double x1Abs = pos1.x * dirX;
		
		double x = pos0.x + 0.5 * dirX;
		double y = pos0.y;

		double v0 = pos0.y;

		if(log) System.out.println("pos0 " + pos0 + ", pos1 " + pos1);
		if(log) System.out.println("dir(" + dirX + "," + dirY + ") x1a: " + x1Abs + " slope: " + slope);
		if(log) System.out.println("x: " + x + ",  x * dirX: " + (x * dirX) + ", x1Abs + dirX: " + (x1Abs + dirX));
		
		while (x * dirX <= x1Abs) {
			double y2 = f.apply(x);
			if(dirY > 0) y2 = Math.min(pos1.y, y2);
			else y2 = Math.max(pos1.y, y2);
			
			double l21 = 0;
			double l22 = 0;
			if(dirY > 0) {
				l21 = Math.round(y2);
				l22 = Math.ceil(y2 - 0.5);
			} else {
				l21 = Math.ceil(y2 - 0.5);
				l22 = Math.round(y2);
			}
			y = v0;
			
			if(log) System.out.println("a " + x + ", " + y + " // y2: " + y2 + " l21: " + l21 + ", l22: " + l22);
			
			while (y * dirY <= l22 * dirY) {
				int tx = (int) (x - 0.5 * dirX);
				int ty = (int) y;
				Cell asdc = b.get(tx, ty);
				if(log) System.out.println("test " + asdc.pos);
				if(!mockCheckCellView(asdc)) {
					if(log) System.out.println("return false 1");
					return false;
				}
				y += dirY;
			}
			v0 = l21;
			x += dirX;
		}
		y = v0;
		
		while (y * dirY <= pos1.y * dirY) {
			int tx = (int) (x - 0.5 * dirX);
			int ty = (int) y;
			Cell cell = b.get(tx, ty);
			if(!mockCheckCellView(cell)) {
				if(log) System.out.println("return false 2 " + cell.pos);
				return false;
			}
			y += dirY;
		}
		
		{
			int tx = (int) (x - 0.5 * dirX);
			int ty = (int) (y - dirY);
			Cell cell = b.get(tx, ty);
			if(!mockCheckCellView(cell)) {
				if(log) System.out.println("return false 3" + cell.pos);
				return false;
			}
		}

		return true;
	}
	
	
	private static boolean mockCheckCellView(Cell c) {
		return c.targeting.can(Targetability.CanBeCastedThrough);
	}
	
	
	
	
	
	
	
}
