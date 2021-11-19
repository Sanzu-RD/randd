package com.souchy.randd.ebi.ammolite.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.ebi.ammolite.util.LightningDistort.LightningNode;

public class LightningDistort {

	
	
	// from 1 point to direction
	
	// from 1 point to 1 point
	
	// around a model
	
	
	
	
	public static void distort() {
		
	}
	
	
	public static void asdf() {
		LightningNode p1 = new LightningNode(0,0,10);
		LightningNode p2 = new LightningNode(0,0,0);
		p1.n2 = p2;
		
		p1.subdivide(3);
	}
	
	public static class Lightning extends Entity {
		public Lightning(Engine engine) {
			super(engine);
		}
		
		public int timeToLive = 3;
		public float spawnTime = 3;
		public float maxInstances = 3;
		
		public List<LightningNode> nodes = new ArrayList<>();
		
		public float time = 0;
		public float spawnTimer = 0;
		@Override
		public void update(float delta) {
			super.update(delta);
			time += delta;
			spawnTimer += delta;
//			if(time % timeToLive < nodes.size()) {
//				if(nodes.size() > 2)
//					nodes.remove(nodes.size() - 1);
//			}
			
			var iter = nodes.iterator();
			while(iter.hasNext()) {
				var n = iter.next();
				n.update(delta);
				if(n.time >= timeToLive) {
					iter.remove();
//					Gdx.app.postRunnable(() -> {
//						n.dispose();
//					});
				}
			}
			
			if(nodes.size() < maxInstances && spawnTimer >= spawnTime) { //spawnTimer % spawnTime > nodes.size()) {
				var n = new LightningNode(0, 0, 10).end();
				n.subdivide(3);
				nodes.add(n);
				Gdx.app.postRunnable(() -> {
					LightningNode.recurse(n, LightningNode.createModel);
				});
				spawnTimer -= spawnTime;
			}
			
		}
	}
	
	
	public static class LightningNode {
		public float time;
		// this node's position
		public Vector3 pos = new Vector3();
		// next node on the main branch 
		public LightningNode n2;
		// next node in the fork
		public LightningNode nFork;
		
		public ModelInstance model = null;
		public static Consumer<LightningNode> createModel;
		public static Consumer<LightningNode> dispose;
		
		public LightningNode() {
		}
		public LightningNode(float x, float y, float z) {
			this.pos.set(x, y, z);
		}
		public LightningNode end() {
			if(n2 == null) n2 = new LightningNode(0, 0, 0);
			createModel.accept(this);
			return this;
		}
		public void update(float delta) {
			time += delta;
		}
		public void dispose() {
			dispose.accept(this);
		}
		public static void recurseThread() {
			
		}
		public static void recurse(LightningNode n, Consumer<LightningNode> l) {
			if(n == null) return;
			l.accept(n);
			recurse(n.n2, l);
			recurse(n.nFork, l);
		}
		public void subdivide(int divs) {
			if(divs <= 0) return;
			if(n2 == null) return;
			
			var rnd = new Random();
			
			boolean fork = rnd.nextBoolean();
			float amplitude = rnd.nextFloat(1);
			amplitude = rnd.nextFloat(-3, 3);
			
			var cross = perpendicular();
			cross.scl(amplitude);
			
			LightningNode mid = new LightningNode();
			var midpos = avg().add(cross);
			mid.pos = midpos;
			mid.n2 = n2;
			n2 = mid;
			
			if(fork) {
				mid.nFork = new LightningNode();
				mid.nFork.pos = distance().scl(rnd.nextFloat(1.5f));
				mid.nFork.subdivide(divs-2);
			}
			
			end();
			subdivide(divs-1);
			mid.subdivide(divs-1);
		}
		public Vector3 distance() {
			if(n2 == null) return null;
			return pos.cpy().sub(n2.pos);
		}
		public Vector3 avg() {
			if(n2 == null) return null;
			return pos.cpy().add(n2.pos).scl(0.5f);
		}
		public Vector3 perpendicular() {
			var v1 = distance().nor();
			var v2 = distance().nor();
			v2.x = new Random().nextFloat(10);
			v2.y = new Random().nextFloat(10);
			v2.nor();
			
			v1.crs(v2);
			return v1;
		}
		
	}
	
}
