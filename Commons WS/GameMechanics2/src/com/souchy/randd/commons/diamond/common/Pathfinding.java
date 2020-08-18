package com.souchy.randd.commons.diamond.common;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statics.properties.Orientation;

public class Pathfinding {
	
	
	public List<Cell> aStar(Board board, Creature caster, Cell source, Cell target){
		List<Node> closed = new ArrayList<>();
		List<Node> open = new ArrayList<>();
		
		var start = new Node();
		start.pos = source.pos;
		start.g = 0;
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
			int min = Integer.MAX_VALUE;
			for(var n : open) {
				if(n.f < min) {
					min = n.f;
					current = n;
				}
			}
			
			// si on arrive à la fin
			if(current.pos == target.pos) {
				List<Cell> path = new ArrayList<>();
				// foreach jusqu'au début en passant par les parents
				while(current.pos.equals(source.pos) == false) {
					path.add(board.cells.get(current.pos.x, current.pos.y));
					current = current.parent;
				}
				return path;
			}
			
			open.remove(current);
			closed.add(current);

			// find and add all neighbors to the open list
			for(var o : Orientation.values()) {
				var pos = getPositionByDirection(current.pos, o);
				Cell neighbor = board.cells.get(pos.x, pos.y);
				
				// if the neighbor node has already been evaluated
				if(containsPredicate(closed, n1 -> n1.pos.equals(neighbor.pos))) {
					continue;
				}

	            // The distance from start to a neighbor
				int tentative_gScore = current.g + manhattan(current.pos, neighbor.pos);
				
				// si la liste NE contient PAS déjà un node avec la position du neighbor
				Node n = findFirst(open, n1 -> n1.pos.equals(neighbor.pos));
				if(n == null) {
					n = new Node();
					
					//cameFrom.put(n, current);
					n.parent = current;
					n.pos = neighbor.pos;
					n.g = tentative_gScore;
					n.h = manhattan(n.pos, target.pos);
					n.f = n.g + n.h;
					
					if(caster.targeting.canWalkThrough(neighbor)) {
						open.add(n);
					} else {
						closed.add(n);
					}
				} else if(tentative_gScore >= n.g) {
					continue;
				}
				
			}
		}
		return null;
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
	//public double euclidean(Vector2 v1, Vector2 v2) {
	//	return Math.hypot(v1.x - v2.x, v1.y - v2.y);
	//}
	
	
	public static class Node {
		public int f;
		public int g;
		public int h;
		
		public Node parent;
		public Vector2 pos;
		
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
	
	public Orientation getOrientationTo(Board b, Cell source, Cell target) {
		double dx = target.pos.x - source.pos.x;
		double dy = target.pos.y - source.pos.y;
		Orientation horizontal = dx >= 0 ? Orientation.East : Orientation.West;
		Orientation vertical = dy >= 0 ? Orientation.North : Orientation.South;
		if(Math.abs(dx) >= (Math.abs(dy)))
			return horizontal;
		else
			return vertical;
	}
	public static Orientation getOrientationTo(Board b, Vector2 source, Vector2 target) {
		double dx = target.x - source.x;
		double dy = target.y - source.y;
		Orientation horizontal = dx >= 0 ? Orientation.East : Orientation.West;
		Orientation vertical = dy >= 0 ? Orientation.North : Orientation.South;
		if(Math.abs(dx) >= (Math.abs(dy)))
			return horizontal;
		else
			return vertical;
	}
	
	
	/*public List<Cell> shortestEasy(Board b, Entity caster, Cell start, Cell goal, PathingMethod method) {
		if(!goal.properties.canBeStoppedOn())
			return null;
		
		List<Cell> path = new ArrayList<>();
		path.add(start);
		
		boolean done = false;
		int iter = 0;
		
		while(!done && iter < 1000) {
			
			var previous = path.get(path.size() - 1);
			var next = getNearestAdjacent(b, caster, previous, goal, method);
			if(next != null)
				path.add(next);
			else {
				
			}
		}
		
		return path;
	}*/
	
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
			var p = o.getPositionByDirection(target);
			var c = b.cells.get(p.x, p.y);
			var walk = method == PathingMethod.Walk && caster.targeting.canWalkThrough(c);
			var cast = method == PathingMethod.Cast && caster.targeting.canCastThrough(c);
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
		return o.getPositionByDirection(target);
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
				
				var loc24 = c == end && caster.targeting.canWalkOn(c); // canWalkThroughEntity
				
				
			}
			
		}
		return null;
	}
	
	
	
	
	
}
