package com.souchy.randd.ebishoal.commons.lapis.world;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.diamond.ext.CellData;
import com.souchy.randd.commons.diamond.ext.MapData;

/**
 * References :
 * https://www.youtube.com/watch?v=0OZxZZCea8I
 * https://github.com/roboleary/GreedyMesh/blob/master/src/mygame/Main.java
 * https://gamedev.stackexchange.com/questions/166584/shadow-artifacts-acne-on-block-intersections
 * 
 * 
 * @author Blank
 *
 */
public class Meshing {
	
    /*
     * These are just constants to keep track of which face we're dealing with - their actual 
     * values are unimportantly - only that they're constant.
     */
    private static final int TOP        = 0; // z up
    private static final int BOTTOM     = 1; // z down
    private static final int EAST       = 2; // x up
    private static final int WEST       = 3; // x down
    private static final int NORTH      = 4; // y up
    private static final int SOUTH      = 5; // y down
    
	private static class VoxelFace {
		public final Material m;
		public final int[] texCoord;
		public final float[] origin;
		public final boolean backface;
		public VoxelFace(Material material, int[] texCoordinates, float[] origin, boolean b) {
			m = material;
			texCoord = texCoordinates;
			backface = b;
			this.origin = origin;
		}
		public boolean canFuse(VoxelFace f) {
			return 	m == f.m &&
					backface == f.backface;
		}
	}
	
	public static Model greedy(MapData data, int cellSize, int renderType) {
		Model root = new Model();
		int[] id = { 0 };
		
		loadMaterials(data, root);
		
		greedLayer(root, data, data.layer0Models, cellSize, renderType, id);
		greedLayer(root, data, data.cellModels, cellSize, renderType, id);
		greedLayer(root, data, data.layer2Models, cellSize, renderType, id);
		
		return root;
	}
	
	private static void greedLayer(Model root, MapData data, int[][] layer, int cellSize, int renderType, int[] id) {
		int[] dims = new int[] {layer[0].length, layer.length, 1};
		
		// sweep 3 axes d = {0,1,2}
		for(var d=0; d<3; ++d) {
		    int u = (d + 1) % 3;
			int v = (d + 2) % 3;
			// d = 0,1,2
			// uv = 12, 20, 01
			// donc d est ma normale et uv sont les deux autres axes qui forment le plan (d tjrs != uv)
			// d = x+, y+, z+  (q est le vecteur unitaire de ça)
			// plans : yz, zx, xy
			// sides : east, north, top
			
			// X = actual coordinates : 
			// x[d] = position dans l'axe en cours (exemple position Z)
			// x[u] = la largeur sur le plan en cours (exemple position X)
			// x[v] = la hauteur sur le plan en cours (exemple position Y)
			int[] x = new int[] { 0, 0, 0 };
			
			// Q sert à incrémenter la position dans la bonne direction pour savoir le 'next voxel' dans le mask
			// c'est égal à un vecteur unitaire de l'axe en cours / la direction dans laquelle on va/incrémente
			int[] q = new int[] { 0, 0, 0 };
			q[d] = 1; 
			
			VoxelFace[] mask = new VoxelFace[dims[u] * dims[v]];
			
			
			for(x[d]=-1; x[d]<dims[d]; ) {
				// Compute mask
				computeMask(root, data, layer, mask, dims, x, q, u, v, d);
				
				// Increment x[d]
				++x[d];
				
				generateMesh(root, mask, cellSize, renderType, id, dims, x, v, u, d);
			}
		}
	}

	private static void loadMaterials(MapData data, Model root) {
		// load textures, make materials and add them to the model
		var assets = new AssetManager();
		data.foreachModel(m -> {
			if(m.isVoxel()) {
				for(int t = 0; t < m.textures.length; t ++) {
					assets.load(m.textures[t], Texture.class);
					assets.finishLoading();
					//Log.info("assets : " + assets.getAssetNames());
					Texture tex = assets.get(m.textures[t], Texture.class);
			    	//Texture tex = new Texture(Gdx.files.absolute(m.textures[t]));
			    	tex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			    	var color = Color.valueOf(m.colorAttributes[t]);
			    	var mat = new Material(TextureAttribute.createDiffuse(tex), ColorAttribute.createDiffuse(color));
			    	mat.id = m.textures[t] + m.colorAttributes[t];
			    	root.materials.add(mat);
				}
			}
		});
	}
	
	/**
	 * Generate voxel faces with a material and backface (or null if they are fused etc)
	 */
	private static void computeMask(Model root, MapData data, int[][] layer, VoxelFace[] mask, int[] dims, int[] x, int[] q, int u, int v, int d) {
		int n = 0;
	    for(x[v]=0; x[v]<dims[v]; ++x[v])
	    for(x[u]=0; x[u]<dims[u]; ++x[u]) {
	    	//Log.info("x : "+x+", dims : " + dims);
	    	CellData a = null;
	    	CellData b = null;
	    	if(x[d] >= 0) {
		    	int aModelId = layer[x[0]][x[1]];
		    	a = data.getModel(aModelId);
	    	}
	    	if(x[d] < dims[d]-1) {
		    	int bModelId = layer[x[0]+q[0]][x[1]+q[1]];
		    	b = data.getModel(bModelId);
	    	}
	    	// Observe le voxel courrant et le suivant dans la direction normale au plan
	    	// - Si les deux voxels sont nulls, on ne créé pas de face entre les deux
	    	// - Si les deux voxels sont ne sont pas nulls, on ne créé pas de face entre les deux non plus car on fusionne
	    	// - Si seulement a n'est pas null, on créé la frontface de a
	    	// - Si seulement b n'est pas null, on créé la backface de b
//	    	Voxel a = x[d] >=         0 ? v(volume, x[0],      x[1],      x[2]) : null;
//	    	Voxel b = x[d] <  dims[d]-1 ? v(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]) : null;
	    	var aVox = a != null && a.isVoxel(); 
	    	var bVox = b != null && b.isVoxel();
	    	if(aVox && !bVox) {
	    		var side = getSide(d, false);
	    		var material = root.getMaterial(a.textures[side] + a.colorAttributes[side]);
	    		var origin = a.transform != null && a.transform.length > 0 ? a.transform[0] : new float[]{ 0f, 0f, 0f };
	    		mask[n++] = new VoxelFace(material, a.coordinates[side], origin,  false);
	    	} else 
	    	if(!aVox && bVox) {
	    		var side = getSide(d, true);
	    		var material = root.getMaterial(b.textures[side] + b.colorAttributes[side]);
	    		var origin = b.transform != null && b.transform.length > 0 ? b.transform[0] : new float[]{ 0f, 0f, 0f };
	    		mask[n++] = new VoxelFace(material, b.coordinates[side], origin, true);
	    	} else {
	    		mask[n++] = null;
	    	}
		}
	}
	
	/**
	 * Fuse all voxel faces into quads
	 */
	private static void generateMesh(Model root, VoxelFace[] mask, int cellSize, int renderType, int[] id, int[] dims, int[] x, int v, int u, int d) {
		int i, j, k,  // coordonées dans le plan actuel
		l, 
		w, h // largeur et hauteur du current chunk being fused
		;
		// Generate mesh for mask using lexicographic ordering
		// Iterate each cell in the mask to check if they have a material 
		var n = 0;
		for (j = 0; j < dims[v]; ++j)  	// coordonée verticale dans le plan actuel
		for (i = 0; i < dims[u];) { 	// coordonnée horizontale dans le plan actuel
			var vox = mask[n];
			if(vox != null) {
				// Compute width
				for (w = 1; i + w < dims[u] && mask[n + w] != null && vox.canFuse(mask[n + w]); ++w) {
				}
				// Compute height (this is slightly awkward
				var done = false;
				for (h = 1; j + h < dims[v]; ++h) {
					for (k = 0; k < w; ++k) {
						// cherche jusqu'au prochain voxel null ou d'un matériau différent
						var vox2 = mask[n + k + h * dims[u]];
						if(vox2 == null || !vox.canFuse(vox2)) { 
							done = true;
							break;
						}
					}
					if(done) {
						break;
					}
				}
				// Add quad
				x[u] = i; x[v] = j;
				int[] du = new int[] {0,0,0}; du[u] = w;
				int[] dv = new int[] {0,0,0}; dv[v] = h;
				mikolalysenkoQuad(root, id[0]++, renderType, vox,
						new Vector3(x[0],             x[1],             x[2]            ),
						new Vector3(x[0]+du[0],       x[1]+du[1],       x[2]+du[2]      ),
						new Vector3(x[0]+du[0]+dv[0], x[1]+du[1]+dv[1], x[2]+du[2]+dv[2]),
						new Vector3(x[0]      +dv[0], x[1]      +dv[1], x[2]      +dv[2]),
						getSide(d, vox.backface),
						vox.backface,
						cellSize
						);
				// Zero-out mask
				for (l = 0; l < h; ++l) 
				for (k = 0; k < w; ++k) {
					mask[n + k + l * dims[u]] = null;
				}
				// Increment counters and continue
				i += w;
				n += w;
			} else {
				++i;
				++n;
			}
		}
	}

	/**
	 * Add a quad to the root mesh
	 */
	private static void mikolalysenkoQuad(Model root, int id, int renderType, VoxelFace face, Vector3 botLeft, Vector3 botRight, Vector3 topRight, Vector3 topLeft, 
			int side, boolean backface, int cellSize) {

		float width = 1;
		float height = 1;
		switch(side) {
			case TOP:
			case BOTTOM:
				width = Math.abs(botLeft.x - topRight.x);
				height = Math.abs(botLeft.y - topRight.y);
				break;
			case EAST:
			case WEST:
				width = -Math.abs(botLeft.y - topRight.y);
				height = Math.abs(botLeft.z - topRight.z);
				break;
			case NORTH:
			case SOUTH:
				width = Math.abs(botLeft.z - topRight.z);
				height = Math.abs(botLeft.x - topRight.x);
				break;
		}
		
		//if(face.m.id.contains("cartesian")) {
		//	Log.info(String.format("w h [%s, %s]  { %s, %s, %s, %s }", width, height, botLeft, botRight, topLeft, topRight));
		//}
		
		Vector3 normal = getNormal(side);
		var vertices = new float[] {
				 botLeft.x + face.origin[0],  botLeft.y + face.origin[1],  botLeft.z + face.origin[2],	normal.x, normal.y, normal.z, face.texCoord[0] * 1, face.texCoord[3] * height,
				botRight.x + face.origin[0], botRight.y + face.origin[1], botRight.z + face.origin[2],  normal.x, normal.y, normal.z, face.texCoord[2] * width, face.texCoord[3] * height,
				topRight.x + face.origin[0], topRight.y + face.origin[1], topRight.z + face.origin[2],	normal.x, normal.y, normal.z, face.texCoord[2] * width, face.texCoord[1] * 1,
				 topLeft.x + face.origin[0],  topLeft.y + face.origin[1],  topLeft.z + face.origin[2], 	normal.x, normal.y, normal.z, face.texCoord[0] * 1, face.texCoord[1] * 1
		};
    	var indices = new short[] {0,   1,   2,       2,   3,   0};
    	if(backface) indices = new short[] { indices[5], indices[4], indices[3], indices[2], indices[1], indices[0] }; // flip l'array
    	
		Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		
		// if(mat == null) mat = mat3;
		MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, indices.length, renderType);
		Node node = new Node();
		node.id = "node_" + id;
		node.parts.add(new NodePart(meshPart, face.m));
		
		root.meshes.add(mesh);
		root.meshParts.add(meshPart);
		root.nodes.add(node);
	}
	
	private static int getSide(int d, boolean backface) {
		switch(d) {
			case 0 : return backface ? WEST   : EAST;
			case 1 : return backface ? SOUTH  : NORTH;
			case 2 : return backface ? BOTTOM : TOP;
			default : throw new IllegalArgumentException("Unexpected value: " + d);
		}
	}
	
	private static Vector3 getNormal(int side) {
		switch(side) {
			case TOP: 		return new Vector3( 0,  0,  1);
			case BOTTOM: 	return new Vector3( 0,  0, -1);
			case NORTH: 	return new Vector3( 0,  1,  0);
			case SOUTH: 	return new Vector3( 0, -1,  0);
			case EAST: 		return new Vector3( 1,  0,  0);
			case WEST: 		return new Vector3(-1,  0,  0);
			default: 		return null;
		}
	}

	
	
}
