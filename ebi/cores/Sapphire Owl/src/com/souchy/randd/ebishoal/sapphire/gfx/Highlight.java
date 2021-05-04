package com.souchy.randd.ebishoal.sapphire.gfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.ext.CellType;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
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
	private static final float floorOffset = 0.01f;
	private static final float floorHeight = 1f;
	private static final float borderRadius = 0.03f;
	private static final float margin = 0.03f;

	// Movements
	public static final Color movementPossibleColor = new Color(0.1f, 0.8f, 0.1f, 0.5f);
	public static final Material movementPossibleMat = new Material(ColorAttribute.createDiffuse(movementPossibleColor));
	public static final Color movementPossibleBorderColor = new Color(0.1f, 0.8f, 0.1f, 0.7f);
	public static final Material movementPossibleBorderMat = new Material(ColorAttribute.createDiffuse(movementPossibleBorderColor));
	public static final Model movementPossible = model("movement", movementPossibleMat, movementPossibleBorderMat);

	public static final Color movementColor = new Color(0.1f, 0.6f, 0.1f, 0.2f);
	public static final Material movementMat = new Material(ColorAttribute.createDiffuse(movementColor), new BlendingAttribute(1));
	public static final Color movementBorderColor = new Color(0.1f, 0.6f, 0.1f, 0.9f);
	public static final Material movementBorderMat = new Material(ColorAttribute.createDiffuse(movementBorderColor), new BlendingAttribute(1));
	public static final Model movement = model("movement", movementMat, movementBorderMat);

	// Spells
	public static final Color spellColor = new Color(0.1f, 0.1f, 0.8f, 0.2f);
	public static final Material spellMat = new Material(ColorAttribute.createDiffuse(spellColor), new BlendingAttribute(1));
	public static final Color spellBorderColor = new Color(0.1f, 0.1f, 0.8f, 0.9f);
	public static final Material spellBorderMat = new Material(ColorAttribute.createDiffuse(spellBorderColor), new BlendingAttribute(1));
	public static final Model spell = model("spell", spellMat, spellBorderMat);
	
	public static final Color spellLosColor = new Color(0.8f, 0.1f, 0.1f, 0.2f);
	public static final Material spellLosMat = new Material(ColorAttribute.createDiffuse(spellLosColor), new BlendingAttribute(1));
	public static final Color spellLosBorderColor = new Color(0.8f, 0.1f, 0.1f, 0.9f);
	public static final Material spellLosBorderMat = new Material(ColorAttribute.createDiffuse(spellLosBorderColor), new BlendingAttribute(1));
	public static final Model spellLos = model("spell", spellLosMat, spellLosBorderMat);

	// Teams
	public static final Color teamColor = new Color(0.1f, 0.1f, 0.8f, 0.2f);
	public static final Material teamMat = new Material(ColorAttribute.createDiffuse(teamColor), new BlendingAttribute(1));
	public static final Color teamBorderColor = new Color(0.1f, 0.1f, 0.8f, 0.9f);
	public static final Material teamBorderMat = new Material(ColorAttribute.createDiffuse(teamBorderColor), new BlendingAttribute(1));
	public static final Model team = model("spell", teamMat, teamBorderMat);
	
	public static final Model teamA = new ModelBuilder().createCylinder(1 - margin * 2 - borderRadius * 2, 0, 1 - margin * 2 - borderRadius * 2, 12, new Material(ColorAttribute.createDiffuse(1, 0, 0, 0.3f), new BlendingAttribute(1)), Usage.Position | Usage.Normal);
	public static final Model teamB = new ModelBuilder().createCylinder(1 - margin * 2 - borderRadius * 2, 0, 1 - margin * 2 - borderRadius * 2, 12, new Material(ColorAttribute.createDiffuse(0, 0, 1, 0.3f), new BlendingAttribute(1)), Usage.Position | Usage.Normal);
	
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
	private static List<ModelInstance> highlights = new ArrayList<>();

	public static boolean isActive() {
		return highlights.size() > 0;
	}
	
	public static void clear() {
		SapphireWorld.world.instances.removeAll(highlights);
		highlights.clear();
	}
	
	public static List<ModelInstance> cellTypes(){
		var cells = Moonstone.fight.board.cells.values(); // .stream().map(c -> (Vector2) c.pos).collect(Collectors.toList());
		// remove current highlights 
		clear();
		// create new model instances
		for (var v : cells) {
			highlights.add(new ModelInstance(cellTypes.get(CellType.get(v.targetting)), (float) v.pos.x, (float) v.pos.y, floorHeight + floorOffset));
		}
		// add instances to render list
		SapphireWorld.world.instances.addAll(highlights);
		return highlights;
	}
	
	public static List<ModelInstance> movementPossibilities(List<Pathfinding.Node> cells) {
		// remove current highlights
		clear();
		// create new model instances
		for (var v : cells) {
			if(v.valid) highlights.add(new ModelInstance(movementPossible, (float) v.pos.x, (float) v.pos.y, floorHeight + floorOffset));
			else highlights.add(new ModelInstance(movement, (float) v.pos.x, (float) v.pos.y, floorHeight + floorOffset));
		}
		// add instances to render list
		SapphireWorld.world.instances.addAll(highlights);
		return highlights;
		// return highlight(cells, movementPossible);
	}
	
	public static List<ModelInstance> movement(List<Vector2> cells) {
		return highlight(cells, movement);
	}

	public static List<ModelInstance> spell(List<Vector2> cells) {
		return highlight(cells, spell);
	}
	public static List<ModelInstance> spell(List<Vector2> cells, List<Vector2> noLos) {
		// remove current highlights 
		clear();
		// create new model instances
		for (var v : cells) {
			highlights.add(new ModelInstance(spell, (float) v.x, (float) v.y, floorHeight + floorOffset));
		}
		for (var v : noLos) {
			highlights.add(new ModelInstance(spellLos, (float) v.x, (float) v.y, floorHeight + floorOffset));
		}
		// add instances to render list
		SapphireWorld.world.instances.addAll(highlights);
		return highlights;
	}
	
	public static ModelInstance team(Team t) {
		if(t == Team.A) return new ModelInstance(teamA, 0, 0, floorOffset);
		if(t == Team.B) return new ModelInstance(teamB, 0, 0, floorOffset);
		return null;
	}
	
	public static List<ModelInstance> highlight(List<Vector2> cells, Model model) {
//		Gdx.gl.glEnable(GL20.GL_BLEND);
//		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND); // Or GL20
		
		// remove current highlights 
		clear();
		// create new model instances
		for (var v : cells) {
//			Log.info("Highlight pos " + v); //  + " -> " + v.copy().sub(new Vector2(1, 1)));
			highlights.add(new ModelInstance(model, (float) v.x, (float) v.y, floorHeight + floorOffset));
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
//		});
		return model;
    }
    
	
}
