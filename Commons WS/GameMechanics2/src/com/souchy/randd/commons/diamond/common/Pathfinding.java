package com.souchy.randd.commons.diamond.common;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.special.Targetting;
import com.souchy.randd.commons.diamond.statics.properties.Orientation;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class Pathfinding {
	
	
	/**
	 * Return the furthest position possible to dash to 
	 * Dashes are always in a line
	 * 
	 * @param allOrNothing : Si true, dash seulement sur la case target. Sinon essaie de dash le plus loin possible vers la case target même si elle est occupée.
	 */
	public static Vector2 dashFunction(Creature caster, Cell target, int distance, boolean allOrNothing, Targetting parameters) {
		Board board = caster.get(Fight.class).board;
		// Vecteur delta entre le target et la source
		Vector2 p1 = caster.pos; //.getCell().pos;
		Vector2 p2 = target.pos;
		Vector2 delta = p2.copy().sub(p1); // delta en absolu

		Orientation o = Orientation.getOrientation(delta); // vecteur unitaire de direction
		
		// if distance is not set, take the distance from the source to the target
		if(distance == 0) {
			o.mult(delta, false); // vecteur de distance dans la bonne direction sur un seul axe (ex: [-5,6] x [-1,0] = [5,0])
			delta.abs();
			distance = (int) delta.sum(); // distance 
		}
		
		// dans un sens: détermine la cell sur laquelle on peut finir
		// dans l'autre sens: détermine si on peut passer à travers
		
		int ds = allOrNothing ? distance : 1; 
		
		Vector2 testPos = caster.pos.copy();
		Vector2 finalpos = caster.pos.copy();
		
		for(; ds <= distance; ds++) {
//			Vector2 tp = o.mult(ds).add(p1);
			testPos.add(o.x, o.y);
			// check if cell exists
			var testCell = board.get(testPos);
			if(testCell == null)
				continue;
			// check if can walk on
			if(parameters.canWalkOn(testCell)) { // caster.targeting
				testPos.copyTo(finalpos);
			}
			// check if can dash through
			if(!parameters.canDashThrough(testCell)) { //caster.targeting
				// si on ne peut pas dash through une cell, on arrête de chercher puisqu'on ne peut pas aller plus loin.
				break; 
			}
		}
		
		return finalpos;
	}
	

	/** checks that 2 cells are in line */
	public static boolean checkAlign(double x0, double y0, double x1, double y1) {
		return x0 == x1 || y0 == y1;
	}
	public static boolean checkAlign(Vector2 p0, Vector2 p1) {
		return checkAlign(p0.x, p0.y, p1.x, p1.y);
	}
	
	/** checks that 2 cells are in diagonal */
	public static boolean checkDiagonal(double x0, double y0, double x1, double y1) {
		var dx = Math.abs(x1 - x1);
		var dy = Math.abs(y1 - y1);
		return dx == dy;
	}
	public static boolean checkDiagonal(Vector2 p0, Vector2 p1) {
		return checkDiagonal(p0.x, p0.y, p1.x, p1.y);
	}

	/** checks on 4 axis that 2 cells are adjacent */
	public static boolean checkAdjacent4(Vector2 p0, Vector2 p1) {
		return checkAdjacent8(p0, p1) && checkAlign(p0, p1);
	}
	
	/** checks on 8 axis that 2 cells touch */
	public static boolean checkAdjacent8(Vector2 p0, Vector2 p1) {
		return Math.abs(p0.x - p1.x) <= 1 && Math.abs(p0.y - p1.y) <= 1;
	}
	
	public static List<Cell> possibleMovement(Board board, Creature caster){
		var list = new ArrayList<Cell>();
		
		return list;
	}
	
	public static List<Node> aStar(Board board, Creature caster, Cell source, Cell target) {
		List<Node> path = new ArrayList<>();
		List<Node> closed = new ArrayList<>();
		List<Node> open = new ArrayList<>();
		
		if(source == null || target == null) return path;
		
		var start = new Node();
		start.pos = source.pos;
		start.g = 0;
		start.v = 0;
		start.h = manhattan(start.pos, target.pos);
		start.f = start.g + start.h;
		
//		var goal = new Node();
//		goal.pos = source.pos;
//		goal.g = manhattan(goal.pos, start.pos);
//		goal.h = 0;
//		goal.f = goal.g + goal.h;

		open.add(start);
		
		
		while(!open.isEmpty()) {
			Node current = null; //open.stream().min((n1, n2) -> n1.compare(n2)).get();
			double min = Integer.MAX_VALUE;
			for(var n : open) {
				if(n.f < min) {
					min = n.f;
					current = n;
				}
			}
			
//			Log.info("current = " + current.pos + " = " + current.f);
			
			// si on arrive à la fin
			if(current.pos.equals(target.pos)) {
				// foreach jusqu'au début en passant par les parents
				while(current != null && current.pos.equals(source.pos) == false) {
//					path.add(board.get(current.pos.x, current.pos.y));
					path.add(current);
					current = current.parent;
				}
				return path; // reverse?
			}
			
			open.remove(current);
			closed.add(current);

			// find and add all neighbors to the open list
			for(var o : Orientation.values()) { // getOrientations(current.pos, target.pos)) { // 
				var pos = getPositionByDirection(current.pos, o);
				
				// if the neighbor node has already been evaluated (aka if its position is in the list of closed nodes)
//				if(containsPredicate(closed, n1 -> n1.pos.equals(pos))) {
//					continue;
//				}

	            // The distance from start to a neighbor
				double tentative_gScore = current.g + 1; // manhattan(current.pos, pos);
				
				// si la liste NE contient PAS déjà un node avec la position du neighbor ou si le chemin jusqu'au neighbor est plus court
				Node n = findFirst(open, n1 -> n1.pos.equals(pos));
				if(n == null || tentative_gScore < n.g) {
					if(n == null) n = new Node();
					else open.remove(n);
					
					// neighbor could be null if it's outside of the map
					Cell neighbor = board.get(pos.x, pos.y);
					
					
					//cameFrom.put(n, current);
					n.parent = current;
					n.pos = pos;
					n.o = o;
					n.g = tentative_gScore;
					n.h = euclidean(n.pos, target.pos);
					n.v = current.v + 1;
					
					if(n.pos.equals(target.pos)) Log.format("add target %s %s", n.pos, n.f);
					
					if(neighbor == null) n.v += 1000;
					else if(!caster.targetting.canWalkThrough(neighbor)) n.v += 1000;
					if(n.v >= 1000) n.valid = false;
					
					// if(n.pos.equals(target.pos)) n.v -= 1000;
					
					n.f = n.v + n.h;

					// if(!open.contains(n)) { // && neighbor != null &&
					// caster.targeting.canWalkThrough(neighbor)) {
					// Log.info("add " + n.pos + " = " + n.f);
					
					open.add(n);
					// }
					
//					if(neighbor != null && caster.targeting.canWalkThrough(neighbor)) {
//						open.add(n);
//						Log.info("Pathfinding add open " + n.pos);
//					} else {
//						closed.add(n);
//						Log.info("Pathfinding add closed " + n.pos);
//					}
				} 
//				else 
//					if(tentative_gScore >= n.g) {
//					continue;
//				}
				
			}
		}
		
		
		return null;
	}
	
	private static List<Orientation> getOrientations(Vector2 pos, Vector2 target) {
		var list = new ArrayList<Orientation>();
		for(var o : Orientation.values())
			list.add(o);
		
		int dx = (int) (target.x - pos.x);
		int dy = (int) (target.y - pos.y);
//		Orientation horizontal = dx >= 0 ? Orientation.East : Orientation.West;
//		Orientation vertical = dy >= 0 ? Orientation.North : Orientation.South;
		
		list.sort((o1,o2) -> {
			int d1 = o1.scaledDistance(dx, dy);
			int d2 = o2.scaledDistance(dx, dy);
			return d2 - d1;
		});
		
//		Log.format("Pathfinding Orientations %s - %s = %s, %s, %s, %s", pos, target, list.get(0), list.get(1), list.get(2), list.get(3));
		
//		while (list.size() < 4) {
//			var max = Orientation.North;
//			double maxi = 0;
//			for (var o : Orientation.values()) {
//				if(o.x * dx + o.y * dy)
//			}
//			list.add(max);
//		}
		
//		if(Math.abs(dx) >= (Math.abs(dy))) {
//			list.add(horizontal);
//			list.add(vertical);
//			
//		}
////			return horizontal;
//		else
////			return vertical;
		
		
		
		return list;
	}

	
	public static <T> T findFirst(List<T> obj, Predicate<T> pred) {
		for(var o : obj) {
			if(pred.apply(o)) {
				return o;
			}
		}
		return null;
	}
	public static <T> boolean containsPredicate(List<T> obj, Predicate<T> pred) {
		for(var o : obj) {
			if(pred.apply(o)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	public static int manhattan(Vector2 v1, Vector2 v2) {
		return (int) (Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y));
	}
	public static double euclidean(Vector2 v1, Vector2 v2) {
		return Math.hypot(v1.x - v2.x, v1.y - v2.y);
	}
	
	
	public static class Node {
		public double f;
		public double g;
		public double h;
		
		public double v;
		
		public Orientation o;
		
		public Node parent;
		public Vector2 pos;
		
		public boolean valid = true;
		
		public int compare(Node n) {
			if(n.f > f) return 1;
			if(n.f < f) return -1;
			return 0;
		}
		
	}
	
	/*
	public List<Cell> dashPath(Board b, Entity dasher, Cell start, Cell goal){
		List<Cell> path = new ArrayList<>();
		path.add(start);
		
		var o = getOrientationTo(b, start, goal);
		var dist = manhattan(start.pos, goal.pos);
		for(int i = 0; i < dist; i++) {
			Cell c = o.getNextCellByDirection(b, path.get(path.size() - 1));
			if(dasher.canWalkThrough(c)) 
				path.add(c);
			else
				break;
		}
		
		// enlève les cells sur lesquelles on ne peut pas s'arrêter à partir de la fin
		while(path.size() > 0 && !path.get(path.size() - 1).properties.canBeStoppedOn()) {
			path.remove(path.size() - 1);
		}
		
		return path;
	}*/

	public static Orientation getOrientation(double dx, double dy) {
		Orientation horizontal = dx >= 0 ? Orientation.East : Orientation.West;
		Orientation vertical = dy >= 0 ? Orientation.North : Orientation.South;
		if(Math.abs(dx) >= (Math.abs(dy)))
			return horizontal;
		else
			return vertical;
	}
	public static Orientation getOrientationTo(Board b, Cell source, Cell target) {
		double dx = target.pos.x - source.pos.x;
		double dy = target.pos.y - source.pos.y;
		return getOrientation(dx, dy);
	}
	public static Orientation getOrientationTo(Board b, Vector2 source, Vector2 target) {
		double dx = target.x - source.x;
		double dy = target.y - source.y;
		return getOrientation(dx, dy);
	}
	


	public enum PathingMethod {
		None(0),
		Walk(1),
		Cast(2);
		private int bit;
		private PathingMethod(int bit) {
			this.bit = bit;
		}
		public boolean contains(int method) {
			return (method & bit) == bit;
		}
	}
	
	
	public Cell getNearestAdjacent(Board b, Creature caster, Vector2 target, Vector2 goal, PathingMethod method) {
		Cell nearest = null;
		int dist = Integer.MAX_VALUE;
		for(var o : Orientation.values()) {
			var p = o.add(target);
			var c = b.cells.get(p.x, p.y);
			var walk = method == PathingMethod.Walk && caster.targetting.canWalkThrough(c);
			var cast = method == PathingMethod.Cast && caster.targetting.canCastThrough(c);
			// if needs to walk and can walk || if needs to cast and can cast
			if(walk || cast) {
				int d = manhattan(p, goal);
				if(d < dist) {
					nearest = c;
					dist = d;
				}
			}
		}
		
		return nearest;
	}
	
	
	public static Vector2 getPositionByDirection(Vector2 target, Orientation o) {
		return o.add(target);
	}
	
	
	/*
	public static class PathingProperties {
		public boolean canWalkThroughBlocks; // if this entity can walk through normally blocking entities
		public boolean canBeWalkedThrough; // if this entity can be walked through by normal entities
		//public boolean canStopOn; // ça ca veut rien dire
		public boolean canBeStoppedOn; // if an entity can walk and stop on this entity
		
		public boolean canWalkThrough(Entity source, Entity target) {
			if(source.properties.canWalkThroughBlocks()) return true;
			return target.properties.canBeWalkedThrough();
		}
	}*/
	
	
	
	public static class NodeDofus {
		int num, // cell id on map
		g, // distance to start cell
		v, 
		h, // distance to end cell
		f, // total h + v
		d, // direction
		l, // ground level
		m; // movement
		Vector2 pos;
		NodeDofus parent;
	}
	
	public static List<Orientation> asdf(Board b, Creature caster, Cell begin, Cell end, Object... params) {
		boolean done = false;
		
		List<NodeDofus> nodes = new ArrayList<>();
		List<NodeDofus> nodes2 = new ArrayList<>();
		
		while(!done) {
			NodeDofus current = null;
			int min = Integer.MAX_VALUE;
			for(var n : nodes) {
				if(n.f < min) {
					min = n.f;
					current = n;
				}
			}
			nodes.remove(current);
			
			if(current.pos.equals(end.pos)) {
				List<Orientation> path = new ArrayList<>();
				while(current.pos.equals(begin.pos) == false) {
					path.add(getOrientationTo(b, current.parent.pos, current.pos));
					current = current.parent;
				}
				return path;
			}

			for(var o : Orientation.values()) {
				var pos = getPositionByDirection(current.pos, o);
				var c = b.cells.get(pos.x, pos.y);
				
				var loc24 = c == end && caster.targetting.canWalkOn(c); // canWalkThroughEntity
				
				
			}
			
		}
		return null;
	}
	
	
	
	
	
}
