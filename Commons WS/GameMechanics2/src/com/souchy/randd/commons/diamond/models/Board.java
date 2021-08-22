package com.souchy.randd.commons.diamond.models;

import com.google.common.base.Function;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.jade.Constants;

public class Board extends Entity {
	
	public Table<Integer, Integer, Cell> cells = HashBasedTable.create();
	
	public Board(Fight f) {
		super(f);
//		readMap();
	}
	
	/**
	 * Load json with cell properties (line of sight, walkable, ..)
	 */
//	public void readMap() { 
//		var fight = get(Fight.class);
//		cells = HashBasedTable.create();
//		for(int i = 0; i < 30; i++)
//			for(int j = 0; j < 30; j++)
//				cells.put(i, j, new Cell(fight, i, j));
//	}
	
	public Cell get(int x, int y) {
		return cells.get(x, y);
	}
	
	public Cell get(double x, double y) {
		return get((int) x, (int) y);
	}
	
	public Cell get(float x, float y) {
		return get((int) x, (int) y);
	}
	public Cell get(Vector2 pos) {
		return get(pos.x, pos.y);
	}
	
	
	

	/**
	 * Check line of sight of a caster onto a cell
	 */
	/*
	public boolean checkView(Creature caster, Vector2 pos1) {
		var pos0 = caster.pos;
		var target = get(pos1);
		var view = true;
		view &= checkCellViewOn(caster, target);
		
		var slope = (pos0.y - pos1.y) / (pos0.x - pos1.x);
		var c = pos0.y - slope * pos0.x;
		Function<Double, Double> f = x -> slope * x + c;
		
		var dirX = Math.signum(pos1.x - pos0.x);
		var dirY = Math.signum(pos1.y - pos0.y);

	    var x1Abs = pos1.x * dirX;
	    var y1Abs = pos1.y * dirY;
		
		var x = pos0.x + 0.5 * dirX;
		var y = pos0.y;
		
		var u0 = pos0.x;
		var v0 = pos0.y;
		
		while (x * dirX <= x1Abs) {
			x += dirX;
			
			double y2 = f.apply(x);
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
			
			while (y * dirY <= l22 * dirY) {
				y += dirY;
				if(!checkCellView(caster, get(x - 0.5 * dirX, y))) return false;
			}
			
			v0 = l21;
		}
		
		return view;
	}
	*/

	public boolean checkView(Creature caster, Vector2 pos1) {
		Vector2 pos0 = caster.pos;
		boolean log = false;
//		if(pos1.x == 4 && pos1.y == 7) log = true;
		
		double dirY = pos1.y - pos0.y >= 0 ? 1 : -1;
		double dirX = pos1.x - pos0.x >= 0 ? 1 : -1;
		
		if(pos0.x == pos1.x) {
			for(double i = pos0.y; i * dirY <= pos1.y * dirY; i += dirY) {
				int tx = (int) pos0.x;
				int ty = (int) i;
				var cell = get(tx, ty);
				if(!checkCellView(caster, cell)) return false;
			}
			return true;
		}
		
		double slope = (pos0.y - pos1.y) / (pos0.x - pos1.x);
		
		double c = pos0.y - slope * pos0.x;
		Function<Double, Double> f = x -> slope * x + c;
		

		double x1Abs = pos1.x * dirX;
		
		double x = pos0.x + Constants.cellHalf * dirX;
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
				l22 = Math.ceil(y2 - Constants.cellHalf);
			} else {
				l21 = Math.ceil(y2 - Constants.cellHalf);
				l22 = Math.round(y2);
			}
			y = v0;
			
			if(log) System.out.println("a " + x + ", " + y + " // y2: " + y2 + " l21: " + l21 + ", l22: " + l22);
			
			while (y * dirY <= l22 * dirY) {
				int tx = (int) (x - Constants.cellHalf * dirX);
				int ty = (int) y;
				Cell cell = get(tx, ty);
				if(log) System.out.println("test " + cell.pos);
				if(!checkCellView(caster, cell)) {
					if(log) System.out.println("return false 1 " + cell.pos);
					return false;
				}
				y += dirY;
			}
			v0 = l21;
			x += dirX;
		}
		y = v0;
		
		while (y * dirY <= pos1.y * dirY) {
			int tx = (int) (x - Constants.cellHalf * dirX);
			int ty = (int) y;
			Cell cell = get(tx, ty);
			if(!checkCellView(caster, cell)) {
				if(log) System.out.println("return false 2 " + cell.pos);
				return false;
			}
			y += dirY;
		}
		
		{
			int tx = (int) (x - Constants.cellHalf * dirX);
			int ty = (int) (y - dirY);
			Cell cell = get(tx, ty);
			if(!checkCellView(caster, cell)) {
				if(log) System.out.println("return false 3" + cell.pos);
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Check if the caster can cast through the cell and through any creature on the cell
	 */
	public boolean checkCellView(Creature caster, Cell c) {
		if(caster == null || c == null) return false;
		if(caster.pos.same(c.pos)) return true;
		var view = caster.targetting.canCastThrough(c);
		if(c.hasCreature()) {
			for(var crea : c.getCreatures())
				view &= caster.targetting.canCastThrough(crea);
		}
		return view;
	}
	
	/**
	 * Check if the caster can cast on the cell and on any creature on the cell
	 */
	public boolean checkCellViewOn(Creature caster, Cell c) {
		var view = caster.targetting.canCastOn(c);
		if(c.hasCreature()) {
			for(var crea : c.getCreatures())
				view &= caster.targetting.canCastOn(crea);
		}
		return view;
	}
	
	public boolean checkRange(Aoe range, Position caster,  Position target) { // int rangeMin, int rangeMax, boolean line, boolean diagonal,
		// vector de différence/distance entre la source et le target
		var d = target.copy().sub(caster);
		
		// le vecteur différentiel se transpose dans l'aoe de portée pour savoir s'il est contenu ou en dehors 
		var isContained = range.table.get((int) d.x, (int) d.y);
		
		return isContained;
	}
	
	
	
}