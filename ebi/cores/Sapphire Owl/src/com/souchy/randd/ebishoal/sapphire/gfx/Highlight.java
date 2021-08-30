package com.souchy.randd.ebishoal.sapphire.gfx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.ext.CellType;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.tealwaters.commons.Pool;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.jade.matchmaking.Team;
import com.souchy.randd.moonstone.white.Moonstone;

/**
 * Creates highlight areas for spell prévisualisations, movement possibilities and movement paths
 * 
 * @author Blank
 * @date 1 nov. 2020
 */
public class Highlight {

	/** nombre de propriété par vertex (pos.xyz + norm.xyz = 6) */
	private static final int vertexPropCount = 6;
//	private static final float Constants.floorOffset = 0.01f;
//	private static final float Constants.floorZ = 1f;
	private static final float borderRadius = 0.03f;
	private static final float margin = 0.06f;
	private static final float alpha = 0.2f;
	private static final float saturation = 0.9f;
	private static final float tone = 0.05f;
	
	// Movements
	public static final Color movementPossibleColor = new Color(tone, saturation, tone, alpha);
	public static final Material movementPossibleMat = new Material(ColorAttribute.createDiffuse(movementPossibleColor), new BlendingAttribute(1));
	public static final Color movementPossibleBorderColor = new Color(tone, saturation, tone, alpha);
	public static final Material movementPossibleBorderMat = new Material(ColorAttribute.createDiffuse(movementPossibleBorderColor), new BlendingAttribute(1));
	public static final Model movementPossible = model("movementpossible", movementPossibleMat, movementPossibleBorderMat);

	public static final Color movementColor = new Color(tone, saturation, tone, alpha);
	public static final Material movementMat = new Material(ColorAttribute.createDiffuse(movementColor), new BlendingAttribute(1));
	public static final Color movementBorderColor = new Color(tone, saturation, tone, alpha);
	public static final Material movementBorderMat = new Material(ColorAttribute.createDiffuse(movementBorderColor), new BlendingAttribute(1));
	public static final Model movement = model("movement", movementMat, movementBorderMat);

	// Spells
	public static final Color spellColor = new Color(tone, tone, saturation, alpha);
	public static final Material spellMat = new Material(ColorAttribute.createDiffuse(spellColor), new BlendingAttribute(1));
	public static final Color spellBorderColor = new Color(tone, tone, saturation, alpha);
	public static final Material spellBorderMat = new Material(ColorAttribute.createDiffuse(spellBorderColor), new BlendingAttribute(1));
	public static final Model spell = model("spellpossible", spellMat, spellBorderMat);
	
	public static final Color spellLosColor = new Color(saturation, tone, tone, alpha);
	public static final Material spellLosMat = new Material(ColorAttribute.createDiffuse(spellLosColor), new BlendingAttribute(1));
	public static final Color spellLosBorderColor = new Color(saturation, tone, tone, alpha);
	public static final Material spellLosBorderMat = new Material(ColorAttribute.createDiffuse(spellLosBorderColor), new BlendingAttribute(1));
	public static final Model spellLos = model("spelllos", spellLosMat, spellLosBorderMat);

	// Teams (added to entity components)
	public static final Color teamAColor = new Color(0.8f, tone, tone, 0.8f);
	public static final Material teamAMat = new Material(ColorAttribute.createDiffuse(teamAColor), new BlendingAttribute(1));
	public static final Color teamABorderColor = new Color(0.8f, tone, tone, 0.9f);
	public static final Material teamABorderMat = new Material(ColorAttribute.createDiffuse(teamABorderColor), new BlendingAttribute(1));
	public static final Model teamA = modelCircle("teamA", teamAMat, teamABorderMat);

	public static final Color teamBColor = new Color(tone, tone, 0.8f, 0.8f);
	public static final Material teamBMat = new Material(ColorAttribute.createDiffuse(teamBColor), new BlendingAttribute(1));
	public static final Color teamBBorderColor = new Color(tone, tone, 0.8f, 0.9f);
	public static final Material teamBBorderMat = new Material(ColorAttribute.createDiffuse(teamBBorderColor), new BlendingAttribute(1));
	public static final Model teamB = modelCircle("teamB", teamBMat, teamBBorderMat);
	
	
//	public static final Model teamA = modelCircle("teamA", ); //new ModelBuilder().createCylinder(1 - margin * 2 - borderRadius * 2, 0.01f, 1 - margin * 2 - borderRadius * 2, 16, new Material(ColorAttribute.createDiffuse(1, 0, 0, 0.3f), new BlendingAttribute(1)), Usage.Position | Usage.Normal);
//	public static final Model teamB = new ModelBuilder().createCylinder(
//			1 - margin * 2 - borderRadius * 2, 
//			0.01f, 
//			1 - margin * 2 - borderRadius * 2, 
//			16, 
//			new Material(ColorAttribute.createDiffuse(0, 0, 1, 0.3f), new BlendingAttribute(1)), 
//			Usage.Position | Usage.Normal
//			);
	
	// Cell types
	public static final Map<CellType, Model> cellTypes = new HashMap<>();


	
	/** static initialize */
	public static void init() {
		for (var type : CellType.values()) {
			float r = type.t.can(Targetability.CanBeWalkedOn) ? 1 : 0;
			float g = type.t.can(Targetability.CanBeWalkedThrough) ? 1 : 0;
			float b = type.t.can(Targetability.CanBeCastedOn) ? 1 : 0;
			float a = type.t.can(Targetability.CanBeCastedThrough) ? 1 : 0.5f;
//			if(type.targetability[Targetability.CanBeCastedThrough.ordinal()]) {
//				r *= 0.5f;
//				g *= 0.5f;
//				b *= 0.5f;
//			}
			if(type == CellType.Type00) {
				r = g = b = a = 1;
			}
			if(type == CellType.Type01) {
				r = g = b = a = 0;
			}
			if(type == CellType.Type15) {
				r = 1;
				g = 0; b = 0; a = 0;
			}
			
			var mat = new Material(ColorAttribute.createDiffuse(new Color(r, g, b, a)));
			// if(type == CellType.Type00)
			cellTypes.put(type, model(type.name(), mat, mat));
		}
		
	}
	
	
	/** 
	 * List of current highlights ?? 
	 * should only have 1 highlight list at a time if we dont count glyphs/traps 
	 * 
	 * glyphs and traps should put their highlight instances as components in their entity (ECS)
	 */
	public static Pool<ModelInstance> highlights = new Pool<>(); // movement

	public static Pool<ModelInstance> highlightsM1 = new Pool<>(); // movement possibilities
	public static Pool<ModelInstance> highlightsM2 = new Pool<>(); // movement path

	public static Pool<ModelInstance> highlightsR1 = new Pool<>(); // spell cast range possible
	public static Pool<ModelInstance> highlightsR2 = new Pool<>(); // spell cast range impossible (no los)

	public static Pool<ModelInstance> highlightsS = new Pool<>(); // spell aoe
	

	public static boolean isActive() {
		return highlights.size() > 0;
	}

	public static void clearAll() {
		SapphireWorld.world.instances.removeAll(highlights);
		SapphireWorld.world.instances.removeAll(highlightsM1);
		SapphireWorld.world.instances.removeAll(highlightsM2);
		SapphireWorld.world.instances.removeAll(highlightsR1);
		SapphireWorld.world.instances.removeAll(highlightsR2);
		SapphireWorld.world.instances.removeAll(highlightsS);
		highlights.clear();
		highlightsM1.clear();
		highlightsM2.clear();
		highlightsR1.clear();
		highlightsR2.clear();
		highlightsS.clear();
	}
	public static void clear() {
		SapphireWorld.world.instances.removeAll(highlights);
		highlights.clear();
	}
	
	public static void clear(Pool<ModelInstance> pool) {
		SapphireWorld.world.instances.removeAll(pool);
		pool.clear();
	}
	
	public static List<ModelInstance> cellTypes(){
		var cells = Moonstone.fight.board.cells.values(); // .stream().map(c -> (Vector2) c.pos).collect(Collectors.toList());
		// remove current highlights 
		clear();
		// create new model instances
		for (var v : cells) {
			highlights.add(new ModelInstance(cellTypes.get(CellType.get(v.targetting)), (float) v.pos.x, (float) v.pos.y, Constants.floorZ + Constants.floorOffset));
		}
		// add instances to render list
		SapphireWorld.world.instances.addAll(highlights);
		return highlights;
	}
	
	
	public static List<ModelInstance> movementPossibilities(List<Vector2> cells) { // Pathfinding.Node
		/*
		// remove current highlights
		clear();
		// create new model instances
		for (var v : cells) {
			if(v.valid) highlights.add(new ModelInstance(movementPossible, (float) v.pos.x, (float) v.pos.y, Constants.floorZ + Constants.floorOffset));
			else highlights.add(new ModelInstance(movement, (float) v.pos.x, (float) v.pos.y, Constants.floorZ + Constants.floorOffset));
		}
		// add instances to render list
		SapphireWorld.world.instances.addAll(highlights);
		return highlights;
		// return highlight(cells, movementPossible);
		*/
		
		//cells.removeIf(n -> !n.valid);
		var vecs = cells; //cells.stream().map(n -> n.pos).collect(Collectors.toList());
		highlight(highlightsM1, vecs, movementPossible);
		
		/*
		SapphireWorld.world.instances.removeAll(highlightsM1);
		int invalids = 0;
		for(int i = 0; i < cells.size(); i++) {
			var v = cells.get(i);
			if(!v.valid) {
				 invalids++;
				 continue;
			}
			if(i - invalids < highlightsM1.size())
				highlightsM1.get(i - invalids).transform.setToTranslation((float) v.pos.x, (float) v.pos.y, Constants.floorZ + Constants.floorOffset);
			else
				highlightsM1.add(new ModelInstance(movementPossible, (float) v.pos.x, (float) v.pos.y, Constants.floorZ + Constants.floorOffset));
		}
		SapphireWorld.world.instances.addAll(highlightsM1);
		*/
		
		return highlightsM1; 
	}
	
	public static List<ModelInstance> movement(List<Vector2> cells) {
		highlight(highlightsM2, cells, movement);
		return highlightsM2; // highlight(cells, movement);
	}

	
	public static List<ModelInstance> spell(List<Vector2> cells) {
		highlight(highlightsS, cells, spell);
		return highlightsS; //highlight(cells, spell);
	}
	public static List<ModelInstance> spellRange(List<Vector2> cells, List<Vector2> noLos) {
		/*
		// remove current highlights 
		clear();
		// create new model instances
		for (var v : cells) {
			highlights.add(new ModelInstance(spell, (float) v.x, (float) v.y, Constants.floorZ + Constants.floorOffset));
		}
		for (var v : noLos) {
			highlights.add(new ModelInstance(spellLos, (float) v.x, (float) v.y, Constants.floorZ + Constants.floorOffset));
		}
		// add instances to render list
		SapphireWorld.world.instances.addAll(highlights);
		return highlights;
		*/

		highlight(highlightsR1, cells, spell);
		highlight(highlightsR2, noLos, spellLos);
		
		return highlightsM1; 
	}
	
	private static void highlight(Pool<ModelInstance> pool, List<Vector2> cells, Model model) {
		SapphireWorld.world.instances.removeAll(pool);
		pool.clear();
		for (int i = 0; i < cells.size(); i++) {
			// Log.info("Highlight " + i);
			var v = cells.get(i);
			if(i < pool.poolSize()) {
//				Log.format("Highlight " + i + " move (%s, %s, %s)", (float) v.x, (float) v.y, Constants.floorZ + Constants.floorOffset);
				//pool.get(i)
				pool.obtain(i).transform.setToTranslation((float) v.x, (float) v.y, Constants.floorZ + Constants.floorOffset);
			} else {
//				Log.format("Highlight " + i + " new (%s, %s, %s)", (float) v.x, (float) v.y, Constants.floorZ + Constants.floorOffset);
				//pool.add
				pool.extend(new ModelInstance(model, (float) v.x, (float) v.y, Constants.floorZ + Constants.floorOffset));
			}
		}
		SapphireWorld.world.instances.addAll(pool);
	}
	
	public static ModelInstance team(Team t) {
		if(t == Team.A) return new ModelInstance(teamA, 0, 0, 0);
		if(t == Team.B) return new ModelInstance(teamB, 0, 0, 0);
		return null;
	}
	
	
	
	
	
	private static List<ModelInstance> highlight(List<Vector2> cells, Model model) {
//		Gdx.gl.glEnable(GL20.GL_BLEND);
//		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND); // Or GL20
		
		// remove current highlights 
		clear();
		// create new model instances
		for (var v : cells) {
//			Log.info("Highlight pos " + v); //  + " -> " + v.copy().sub(new Vector2(1, 1)));
			highlights.add(new ModelInstance(model, (float) v.x, (float) v.y, Constants.floorZ + Constants.floorOffset));
		}
		// add instances to render list
		SapphireWorld.world.instances.addAll(highlights);
		return highlights;
	}
	
	/**
	 * Creates a model of a rectangle with a border
	 */
	private static Model model(String name, Material mat, Material matBorder) {
		Model model = new Model();
//		Gdx.app.postRunnable(() -> {
			// Model mod = modelBuilder.createRect(
			// 0, 0, 0,
			// 1, 0, 0,
			// 1, 1, 0,
			// 0, 1, 0,
			// 0, 0, 1, movementMat, 0);
			Vector3 pos = new Vector3(margin + borderRadius, margin + borderRadius, 0);
			Vector3 size = new Vector3(1 - margin * 2 - borderRadius * 2, 1 - margin * 2 - borderRadius * 2, 0);
			Vector3 normal = new Vector3(0, 0, 1);
			var vertices = new float[] { pos.x, pos.y, pos.z, normal.x, normal.y, normal.z, pos.x + size.x, pos.y, pos.z, normal.x, normal.y, normal.z,
					pos.x + size.x, pos.y + size.y, pos.z, normal.x, normal.y, normal.z, pos.x, pos.y + size.y, pos.z, normal.x, normal.y, normal.z, };
			var indices = new short[] { 0, 1, 2, 2, 3, 0 };
			
			Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
			mesh.setVertices(vertices);
			mesh.setIndices(indices);
			
			String id = name; // "highlight";
			int renderType = GL20.GL_TRIANGLES;
			// if(mat == null) mat = mat3;
			MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, indices.length, renderType);
			Node node = new Node();
			node.id = "node_" + id;
			node.parts.add(new NodePart(meshPart, mat));
			
			model.meshes.add(mesh);
			model.meshParts.add(meshPart);
			model.nodes.add(node);
	
			boolean borders = false;
			if(borders) {
				// border
				var c = vertices.length - vertexPropCount;
				for (int i = 0; i < c + 1; i += vertexPropCount) {
					int i2 = (i != c) ? i + vertexPropCount : 0;
					Vector3 v1 = new Vector3(vertices[i], vertices[i + 1], vertices[i + 2]);
					Vector3 v2 = new Vector3(vertices[i2], vertices[i2 + 1], vertices[i2 + 2]);
					
					// direction du vecteur 1 au vecteur 2
					Vector3 dir = new Vector3(v2.x - v1.x, v2.y - v1.y, 0).nor();
					Vector3 pDir = dir.cpy().crs(0, 0, 1); // vecteur perpendiculaire à la direction
					
					// vu qu'on va du v1 au v2, on sait dans quel sens offset les points pour créer
					// la bordure
					var b1 = new Vector3(v1.x, v1.y, v1.z);
					b1.sub(dir.cpy().scl(borderRadius));
					b1.add(pDir.cpy().scl(borderRadius));
					var b2 = new Vector3(v1.x, v1.y, v1.z);
					b2.sub(dir.cpy().scl(borderRadius));
					b2.sub(pDir.cpy().scl(borderRadius));
					var b3 = new Vector3(v2.x, v2.y, v2.z);
					b3.add(dir.cpy().scl(borderRadius));
					b3.add(pDir.cpy().scl(borderRadius));
					var b4 = new Vector3(v2.x, v2.y, v2.z);
					b4.add(dir.cpy().scl(borderRadius));
					b4.sub(pDir.cpy().scl(borderRadius));
					
					var verticesBorder = new float[] { b2.x, b2.y, b2.z, normal.x, normal.y, normal.z, b1.x, b1.y, b1.z, normal.x, normal.y, normal.z, b3.x, b3.y,
							b3.z, normal.x, normal.y, normal.z, b4.x, b4.y, b4.z, normal.x, normal.y, normal.z, };
					var indicesBorder = new short[] { 0, 1, 2, 2, 3, 0 };
					
					Mesh meshBorder = new Mesh(true, verticesBorder.length, indicesBorder.length, VertexAttribute.Position(), VertexAttribute.Normal());
					meshBorder.setVertices(verticesBorder);
					meshBorder.setIndices(indicesBorder);
					
					String idBorder = id + "_border" + i + "_" + i2;
					MeshPart meshPartBorder = new MeshPart("meshpart_" + idBorder, meshBorder, 0, indices.length, renderType);
					Node nodeBorder = new Node();
					nodeBorder.id = "node_" + idBorder;
					nodeBorder.parts.add(new NodePart(meshPartBorder, matBorder));
					
					model.meshes.add(meshBorder);
					model.meshParts.add(meshPartBorder);
					model.nodes.add(nodeBorder);
				}
			}
			
//		});
		return model;
    }

	@SuppressWarnings("deprecation")
	private static Model modelCircle(String name, Material mat, Material matBorder) {
		var modelbuilder = new ModelBuilder();
		modelbuilder.begin();

		var meshbuilder = modelbuilder.part("circle", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, mat); // new MeshBuilder();
		meshbuilder.circle(
				Constants.cellHalf - margin * 2 - borderRadius * 2, 
				16, 
				0, Constants.floorOffset, 0, 
				0, 1, 0, 
				0, 360);
		//var node = modelbuilder.node();
		
		var model = modelbuilder.end();
		return model;
	}
	
	
	
}
