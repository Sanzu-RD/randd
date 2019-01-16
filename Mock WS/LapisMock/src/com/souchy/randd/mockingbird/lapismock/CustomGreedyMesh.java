package com.souchy.randd.mockingbird.lapismock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Vector3;

public class CustomGreedyMesh {

    /*
     * A voxel has 6 faces
     * Each face has a material
     */
	public static class Voxel {
		
		public int height = 0;
		public Material[] mats = new Material[6]; 
		
		public Voxel(Material mat) {
			mats = new Material[] {mat, mat, mat, mat, mat, mat};
		}
		public Voxel(Material[] mat) {
			mats = mat;
		}
		public void setTop(Material mat) {
			mats[TOP] = mat;
		}
		public void setBottom(Material mat) {
			mats[BOTTOM] = mat;
		}
		public void setEast(Material mat) {
			mats[EAST] = mat;
		}
		public void setWest(Material mat) {
			mats[WEST] = mat;
		}
		public void setSouth(Material mat) {
			mats[NORTH] = mat;
		}
		public void setNoth(Material mat) {
			mats[SOUTH] = mat;
		}
		public boolean canFuse(Voxel v2, int face) {
			return mats[face] == v2.mats[face];
		}
	}
	

    /*
     * In this test each voxel has a size of one world unit - in reality a voxel engine 
     * might have larger voxels - and there's a multiplication of the vertex coordinates 
     * below to account for this.
     */
    private static final int VOXEL_SIZE = 1;

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

    public Model rootNode = new Model();
    public Color color;
    public Material mat1;
    public int renderType = GL20.GL_TRIANGLES;
    public int id = 0;
    
    public final int HEIGHT = 25;
    public final int WIDTH = 25;
	Voxel[][] voxels = new Voxel[HEIGHT][WIDTH];
	
	public CustomGreedyMesh() {
		color = Color.valueOf("AEE897");
		mat1 = new Material("mat1", ColorAttribute.createDiffuse(color));
		
		
		/*for(int j = 0; j < voxels.length; j++) { // y
			for(int i = 0; i < voxels[j].length; i++) { // x
				voxels[j][i] = new Voxel(mat1);
				voxels[j][j].height = 0;
			}
		}
		voxels[5][5] = null;
		voxels[3][3].height = 2;
		
		generateMesh();*/
		

		mikolalysenko(true);
		mikolalysenko(false);
	}
	
	private void generateMesh() {
		
		Map<Integer, Voxel[][]> heightMap = new HashMap<>();

		foreachVoxels((i, j) -> {
			var v = voxels[j][i];
			var h = v.height;
			if(!heightMap.containsKey(h)) {
				heightMap.put(h, new Voxel[HEIGHT][WIDTH]);
			}
			heightMap.get(h)[j][i] = v;
		});
		
		foreachVoxels((i, j) -> {
			
		});
		
		// foreach direction
		int side = 0;
        for (boolean backFace = true, b = false; b != backFace; backFace = backFace && b, b = !b) { 
            for(int d = 0; d < 3; d++) {
            	
            	if (d == 0)      { side = backFace ? WEST   : EAST;  }
                else if (d == 1) { side = backFace ? SOUTH  : NORTH;   }
                else if (d == 2) { side = backFace ? BOTTOM : TOP; }  

        		for(int k : heightMap.keySet()) {
        			for(int j = 0; j < HEIGHT; j++) { // y 	// voxels.length
        				for(int i = 0; i < WIDTH; i++) { // x  // voxels[j].length
        					
        					if(heightMap.get(k)[j][i] != null) {
        						
        					}
        					
        				}
        			}
        		}
        		
            	
            }
        }
		
		

		quad(TOP, new Vector3(), 1, 1, mat1);
	}
	
	
	//var quads = new int[0];
	int[] dims = new int[] {WIDTH, HEIGHT, 3};
	Voxel[][][] volume = mikolalysenkoGenerateVolume(dims[0], dims[1], dims[2]);
	
	private void mikolalysenko(boolean backface) {
		
		/*Function<Integer[], Boolean> f = (ijk) -> {
			volume[ijk[0] + dims[0] * (ijk[1] + dims[1] * ijk[2])];
			return true;
		};*/
		int side = 0; // added
		
		// sweep 3 axes d = {0,1,2}
		for(var d=0; d<3; ++d) {
			int i, j, k, l, w, h;
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
			
			boolean[] mask = new boolean[dims[u] * dims[v]];
			
			if (d == 0)      { side = backface ? WEST   : EAST; }
            else if (d == 1) { side = backface ? SOUTH  : NORTH; }
            else if (d == 2) { side = backface ? BOTTOM : TOP; }  
			
			
			for(x[d]=-1; x[d]<dims[d]; ) {
				// Compute mask
				var n = 0;
			    for(x[v]=0; x[v]<dims[v]; ++x[v])
			    for(x[u]=0; x[u]<dims[u]; ++x[u]) {
			    	/*mask[n++] =
			    			(0    <= x[d]      ? f(volume, x[0],      x[1],      x[2])      : false) 
			    			!=
			    			(x[d] <  dims[d]-1 ? f(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]) : false);*/
			    	
			    	// check si le voxel actuel et le voxel suivant peuvent être fusionné (les deux sont non-nulls, etc)
					boolean a = x[d] >=         0  &&  f(volume, x[0],      x[1],      x[2]); 			
					boolean b = x[d] <  dims[d]-1  &&  f(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]);
					mask[n++] = a != b;
			    	/*Voxel v1 = x[d] >=         0 ? v(volume, x[0],      x[1],      x[2]) : null;
			    	Voxel v2 = x[d] <  dims[d]-1 ? v(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]) : null;
			    	if(v1 != null && v2 != null && v1.canFuse(v2, side)) {
			    		
			    	}*/
				}
				
				// Increment x[d]
				++x[d];
				
				// Generate mesh for mask using lexicographic ordering
				n = 0;
				for (j = 0; j < dims[v]; ++j) 
				for (i = 0; i < dims[u];) {
					if(mask[n]) {
						// Compute width
						for (w = 1; mask[n + w] && i + w < dims[u]; ++w) {
						}
						// Compute height (this is slightly awkward
						var done = false;
						for (h = 1; j + h < dims[v]; ++h) {
							for (k = 0; k < w; ++k) {
								if(!mask[n + k + h * dims[u]]) {
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
						 /*quads.push([
				              [x[0],             x[1],             x[2]            ]
				            , [x[0]+du[0],       x[1]+du[1],       x[2]+du[2]      ]
				            , [x[0]+du[0]+dv[0], x[1]+du[1]+dv[1], x[2]+du[2]+dv[2]]
				            , [x[0]      +dv[0], x[1]      +dv[1], x[2]      +dv[2]]
			          	]);*/
						
						getVoxel(x, d, u, v);
						mikolalysenkoQuad(
								new Vector3(x[0],             x[1],             x[2]            ),
								new Vector3(x[0]+du[0],       x[1]+du[1],       x[2]+du[2]      ),
								new Vector3(x[0]+du[0]+dv[0], x[1]+du[1]+dv[1], x[2]+du[2]+dv[2]),
								new Vector3(x[0]      +dv[0], x[1]      +dv[1], x[2]      +dv[2]),
								side,
								backface
								);
						// Zero-out mask
						for (l = 0; l < h; ++l) 
						for (k = 0; k < w; ++k) {
								mask[n + k + l * dims[u]] = false;
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
			
		}
	}
	
	private Voxel getVoxel(int[] pos, int d, int u, int v) {
		
		
		return null;
	}
	
	private Vector3 getNormal(int side) {
		switch(side) {
			case TOP: 		return new Vector3( 0,  0,  1);
			case BOTTOM: 	return new Vector3( 0,  0, -1);
			case NORTH: 	return new Vector3( 0,  1,  0);
			case SOUTH: 	return new Vector3( 0, -1,  0);
			case EAST: 		return new Vector3( 1,  0,  0);
			case WEST: 		return new Vector3(-1,  0,  0);
			default: try {
					throw new Exception("");
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
		}
	}
	
	private Vector3 crossProduct(Vector3 p1, Vector3 p2) {
		var out = new Vector3();
		out.x = p1.y * p2.z - p2.y * p1.z;
		out.y = p1.z * p2.x - p2.z * p1.x;
		out.z = p1.x * p2.y - p2.x * p1.y;
		return out;
	}
	
	private static final int cellSize = 1;
	private void mikolalysenkoQuad(Vector3 botLeft, Vector3 botRight, Vector3 topRight, Vector3 topLeft, int side, boolean backface) {
		if(false) {
			botLeft.sub(cellSize);
			botRight.sub(cellSize);
			topRight.sub(cellSize);
			topLeft.sub(cellSize);
		}
		Vector3 normal = getNormal(side);
		var vertices = new float[] {
				botLeft.x,  botLeft.y,  botLeft.z,	 normal.x, normal.y, normal.z,
				botRight.x, botRight.y, botRight.z,  normal.x, normal.y, normal.z,
				topRight.x, topRight.y, topRight.z,	 normal.x, normal.y, normal.z,
				topLeft.x,  topLeft.y,  topLeft.z, 	 normal.x, normal.y, normal.z,
		};
		var norm = crossProduct(topLeft, botRight);
    	var indices = new short[] {0,   1,   2,       2,   3,   0};
    	if(backface) indices = new short[] { indices[5], indices[4], indices[3], indices[2], indices[1], indices[0] };
    	Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
    	mesh.setVertices(vertices);
    	mesh.setIndices(indices);

        MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, vertices.length, renderType);
        Node node = new Node();
        node.id = "node_" + id;
        node.parts.add(new NodePart(meshPart, mat1));
        
        rootNode.meshes.add(mesh);
        rootNode.meshParts.add(meshPart);
        rootNode.nodes.add(node);
        id++;
	}
	

	private Voxel v(Voxel[][][] volume, int i, int j, int k) {
		return volume[k][j][i];
	}
	private boolean f(Voxel[][][] volume, int i, int j, int k) {
		var canFuse = volume[k][j][i] != null;
		return canFuse;
	}
	/**
	 * @param w - width (x)
	 * @param h - height (y)
	 * @param d - depth (z)
	 * @return
	 */
	private Voxel[][][] mikolalysenkoGenerateVolume(int w, int h, int d){
		var rng = new Random();
		Voxel[][][] volume = new Voxel[d][h][w];
		for(int k = 0; k < d; k++) {
			for(int j = 0; j < h; j++) {
				for(int i = 0; i < w; i++) {
					if(k >= d-2) continue;
					if(rng.nextBoolean()) continue;
					//if((i + j) % 2 == 0) continue;
					volume[k][j][i] = new Voxel(mat1);
					volume[k][j][i].height = k; // height in this object is different (z) than in this function (y)
				}
			}
		}
	//	volume[d-3][5][5] = null;
	//	volume[d-1][3][3] = new Voxel(mat1);
		return volume;
	}
	
	
	public void foreachVoxels(BiConsumer<Integer, Integer> action) {
		for(int j = 0; j < voxels.length; j++) { // y
			for(int i = 0; i < voxels[j].length; i++) { // x
				action.accept(i, j);
			}
		}
	}
	
	public void quad(int side, Vector3 pos, int w, int h, Material mat) {
		var vertices = new float[] {
				0.000000f, 1.000000f, 1.000000f, 
				0.000000f, 0.000000f, 0.000000f, 
				0.000000f, 0.000000f, 1.000000f, 
		};
    	for(int i = 0; i < vertices.length; i++) {
    		if(i % 6 == 0) vertices[i] += pos.x;
    		if(i % 6 == 1) vertices[i] += pos.y;
    		if(i % 6 == 2) vertices[i] += pos.z;
    	}
    	var indices = new short[] {
    			 0,   1,   2,      3,   1,   0,   
    			 4,   5,   6,      6,   5,   7, 
			  	 8,   9,  10,     11,   9,   8,  
				12,  13,  14,     14,  13,  15, 
				16,  17,  18,     19,  17,  16,  
				20,  21,  22,     22,  21,  23};
    	Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
    	mesh.setVertices(vertices);
    	mesh.setIndices(indices);

        MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, vertices.length, renderType);
        Node node = new Node();
        node.id = "node_" + id;
        node.parts.add(new NodePart(meshPart, mat1));
        
        rootNode.meshes.add(mesh);
        rootNode.meshParts.add(meshPart);
        rootNode.nodes.add(node);
        id++;
	}
	
	
	/**
	 * unrelated to this class
	 */
	public void shadowMap() {
		float[][] shadowmap;
	}
	
	
	
	
}
