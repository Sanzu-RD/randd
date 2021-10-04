package com.souchy.randd.mockingbird.lapismock.worlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

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
		public void setMats(Material mat) {
			setTop(mat); setBottom(mat);
			setEast(mat); setWest(mat);
			setNoth(mat); setSouth(mat); 
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
		public void setNoth(Material mat) {
			mats[SOUTH] = mat;
		}
		public void setSouth(Material mat) {
			mats[NORTH] = mat;
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
    public Material mat2;
    public Material mat3;
    public int renderType = GL20.GL_TRIANGLES;
    public int id = 0;
    public Random rng = new Random();

    
    public final int HEIGHT = 50;
    public final int WIDTH = 50;
	Voxel[][] voxels = new Voxel[HEIGHT][WIDTH];

	//var quads = new int[0];
	int[] dims = new int[] {WIDTH, HEIGHT, 3};
	Voxel[][][] volume;
	
	public int vertexCount = 0;
	public int faceCount = 0;
	
	public boolean oneMesh = true;
	
	public CustomGreedyMesh() {
		color = Color.valueOf("AEE897");
		//color.a = 0.3f;
		mat1 = new Material("mat1", ColorAttribute.createDiffuse(color)); //, new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
		mat2 = new Material("mat2", ColorAttribute.createDiffuse(Color.SKY));
		mat3 = new Material("mat3", ColorAttribute.createDiffuse(Color.PINK));
		
        
		
		volume = mikolalysenkoGenerateVolume(dims[0], dims[1], dims[2]);
		
		/*for(int j = 0; j < voxels.length; j++) { // y
			for(int i = 0; i < voxels[j].length; i++) { // x
				voxels[j][i] = new Voxel(mat1);
				voxels[j][j].height = 0;
			}
		}
		voxels[5][5] = null;
		voxels[3][3].height = 2;
		
		generateMesh();*/
		
		mikolalysenko();

		if(oneMesh) {
	        rootNode.materials.add(mat1);
			
			var vs = new float[vertic.size()];
			for(int i = 0; i < vertic.size(); i++)
				vs[i] = vertic.get(i);

			var is = new short[indic.size()];
			for(int i = 0; i < indic.size(); i++)
				is[i] = indic.get(i);
			
	        Mesh mesh = new Mesh(true, vs.length, is.length, VertexAttribute.Position(), VertexAttribute.Normal());
	        mesh.setVertices(vs);
	        mesh.setIndices(is);
	        
	    	MeshPart meshPart = new MeshPart("meshpart_" + "root", mesh, 0, vs.length, renderType);
	        Node node = new Node();
	        NodePart np = new NodePart(meshPart, mat1);
	        
	        node.id = "node_" + "root";
	        node.parts.add(np);
	        
	        rootNode.meshes.add(mesh);
	        rootNode.meshParts.add(meshPart);
	        rootNode.nodes.add(node);
		}
        
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
	
	
	public static class VoxelFace {
		public final Material m;
		public final boolean backface;
		public VoxelFace(Material material, boolean b) {
			m = material;
			backface = b;
		}
		public boolean canFuse(VoxelFace f) {
			return 	m == f.m &&
					backface == f.backface;
		}
	}
	
	public int getSide(int d, boolean backface) {
		int side = 0;
		if (d == 0)      { side = backface ? WEST   : EAST; }
        else if (d == 1) { side = backface ? SOUTH  : NORTH; }
        else if (d == 2) { side = backface ? BOTTOM : TOP; }  
		return side;
	}
	
	private void sushi() {
		// sweep 3 axes d = {0,1,2}
		for(var d=0; d<2; ++d) {
			int i, j, k,  // coordonées dans le plan actuel
			l, 
			w, h // largeur et hauteur du current chunk being fused
			;
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
			// x[u] = la largeur du le plan en cours (exemple position X)
			// x[v] = la hauteur du le plan en cours (exemple position Y)
			int[] x = new int[] { 0, 0, 0 };
			// Q sert à incrémenter la position dans la bonne direction pour savoir le 'next voxel' dans le mask
			// c'est égal à un vecteur unitaire de l'axe en cours / la direction dans laquelle on va/incrémente
			int[] q = new int[] { 0, 0, 0 };
			q[d] = 1; 

			VoxelFace[] mask = new VoxelFace[dims[u] * dims[v]];

			for(x[d]=-1; x[d]<dims[d]; ) {
				// Compute mask
				var n = 0;
			    for(x[v]=0; x[v]<dims[v]; ++x[v])
			    for(x[u]=0; x[u]<dims[u]; ++x[u]) {
			    	// Observe le voxel courrant et le suivant dans la direction normale au plan
			    	// - Si les deux voxels sont nulls, on ne créé pas de face entre les deux
			    	// - Si les deux voxels sont ne sont pas nulls, on ne créé pas de face entre les deux non plus car on fusionne
			    	// - Si seulement a n'est pas null, on créé la frontface de a
			    	// - Si seulement b n'est pas null, on créé la backface de b
			    	Voxel a = x[d] >=         0 ? v(volume, x[0],      x[1],      x[2]) : null;
			    	Voxel b = x[d] <  dims[d]-1 ? v(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]) : null;
			    	
			    	var nulla = a == null;
			    	var nullb = b == null;
			    	if(nulla == nullb) {
			    		mask[n++] = null;
			    	} else if(!nulla) {
			    		mask[n++] = new VoxelFace(a.mats[getSide(d, false)], false);
			    	}else if(!nullb) {
			    		mask[n++] = new VoxelFace(b.mats[getSide(d, true)], true);
			    	}
				}
				
				// Increment x[d]
				++x[d];
				
				// Generate mesh for mask using lexicographic ordering
				// Iterate each cell in the mask to check if they have a material 
				n = 0;
				for (j = 0; j < dims[v]; ++j)  // coordonée verticale dans le plan actuel
				for (i = 0; i < dims[u];) { // coordonnée horizontale dans le plan actuel
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
						
						var curr = v(volume, x[0],      x[1],      x[2]);
						var next = v(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]);
						if(curr.height != next.height) {
							
						}
						
						mikolalysenkoQuad(
								new Vector3(x[0],             x[1],             x[2]            ),
								new Vector3(x[0]+du[0],       x[1]+du[1],       x[2]+du[2]      ),
								new Vector3(x[0]+du[0]+dv[0], x[1]+du[1]+dv[1], x[2]+du[2]+dv[2]),
								new Vector3(x[0]      +dv[0], x[1]      +dv[1], x[2]      +dv[2]),
								getSide(d, vox.backface),
								vox.backface,
								vox 
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
		}
	}
	
	private void mikolalysenko() {
		/*Function<Integer[], Boolean> f = (ijk) -> {
			volume[ijk[0] + dims[0] * (ijk[1] + dims[1] * ijk[2])];
			return true;
		};*/
		
		// sweep 3 axes d = {0,1,2}
		for(var d=0; d<3; ++d) {
			int i, j, k,  // coordonées dans le plan actuel
			l, 
			w, h // largeur et hauteur du current chunk being fused
			;
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
				var n = 0;
			    for(x[v]=0; x[v]<dims[v]; ++x[v])
			    for(x[u]=0; x[u]<dims[u]; ++x[u]) {
			    	/*mask[n++] =
			    			(0    <= x[d]      ? f(volume, x[0],      x[1],      x[2])      : false) 
			    			!=
			    			(x[d] <  dims[d]-1 ? f(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]) : false);*/
			    	
			    	
					/*boolean a = x[d] >=         0  &&  f(volume, x[0],      x[1],      x[2]); 			
					boolean b = x[d] <  dims[d]-1  &&  f(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]);
					mask[n++] = a != b;
					*/
					
			    	// Observe le voxel courrant et le suivant dans la direction normale au plan
			    	// - Si les deux voxels sont nulls, on ne créé pas de face entre les deux
			    	// - Si les deux voxels sont ne sont pas nulls, on ne créé pas de face entre les deux non plus car on fusionne
			    	// - Si seulement a n'est pas null, on créé la frontface de a
			    	// - Si seulement b n'est pas null, on créé la backface de b
			    	Voxel a = x[d] >=         0 ? v(volume, x[0],      x[1],      x[2]) : null;
			    	Voxel b = x[d] <  dims[d]-1 ? v(volume, x[0]+q[0], x[1]+q[1], x[2]+q[2]) : null;

			    	var nulla = a == null;
			    	var nullb = b == null;
			    	if(nulla == nullb) {
			    		mask[n++] = null;
			    	} else if(!nulla) {
			    		mask[n++] = new VoxelFace(a.mats[getSide(d, false)], false);
			    	}else if(!nullb) {
			    		mask[n++] = new VoxelFace(b.mats[getSide(d, true)], true);
			    	}
				}
				
				// Increment x[d]
				++x[d];
				
				// Generate mesh for mask using lexicographic ordering
				// Iterate each cell in the mask to check if they have a material 
				n = 0;
				for (j = 0; j < dims[v]; ++j)  // coordonée verticale dans le plan actuel
				for (i = 0; i < dims[u];) { // coordonnée horizontale dans le plan actuel
					var vox = mask[n];
					if(vox != null) {
						// Compute width
						for (w = 1; i + w < dims[u] && mask[n + w] != null && vox.canFuse(mask[n + w]) /*mask[n + w].mats[side] == vox.mats[side]*/; ++w) {
						}
						// Compute height (this is slightly awkward
						var done = false;
						for (h = 1; j + h < dims[v]; ++h) {
							for (k = 0; k < w; ++k) {
								// cherche jusqu'au prochain voxel null ou d'un matériau différent
								var vox2 = mask[n + k + h * dims[u]];
								if(vox2 == null || !vox.canFuse(vox2)) { //vox2.mats[side] != vox.mats[side]) {
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
						//getVoxel(x, d, u, v);
						mikolalysenkoQuad(
								new Vector3(x[0],             x[1],             x[2]            ),
								new Vector3(x[0]+du[0],       x[1]+du[1],       x[2]+du[2]      ),
								new Vector3(x[0]+du[0]+dv[0], x[1]+du[1]+dv[1], x[2]+du[2]+dv[2]),
								new Vector3(x[0]      +dv[0], x[1]      +dv[1], x[2]      +dv[2]),
								getSide(d, vox.backface),
								vox.backface,
								vox //mask[n].mats[side]
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
	private void mikolalysenkoQuad(Vector3 botLeft, Vector3 botRight, Vector3 topRight, Vector3 topLeft, int side, boolean backface, VoxelFace face) {
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
    	
    	if(!oneMesh) {
        	Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
        	mesh.setVertices(vertices);
        	mesh.setIndices(indices);
        	
        	//if(mat == null) mat = mat3;
            MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, vertices.length, renderType);
            Node node = new Node();
            node.id = "node_" + id;
            node.parts.add(new NodePart(meshPart, face.m));
            
            rootNode.meshes.add(mesh);
            rootNode.meshParts.add(meshPart);
            rootNode.nodes.add(node);
    	}
    
        vertexCount += 4;
        faceCount++;
        id++;
        
        
        for(var s : vertices)
        	vertic.add(s);
        for(var i : indices)
        	indic.add(i);
	}
	
	public List<Float> vertic = new ArrayList<>();
	public List<Short> indic = new ArrayList<>();
	

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
		Voxel[][][] volume = new Voxel[d][h][w];
		for(int k = 0; k < d; k++) {
			for(int j = 0; j < h; j++) {
				for(int i = 0; i < w; i++) {
					if(k >= d-2) continue;
//					if(rng.nextInt(20) == 1) continue;
					//if((i + j) % 2 == 0) continue;
					//if(x == side/2 && y == side/2) continue;
					if(rng.nextBoolean()) continue;
					
					volume[k][j][i] = new Voxel(mat1);
					volume[k][j][i].height = k; // height in this object is different (z) than in this function (y)
					
			    	Material mat = null;
			    	if(i >= 13 && j >= 13 && i <= 15 && j <= 15) {
			    		mat = mat2;
			    	} else {
			    		mat = mat1;
			    	}
			    	volume[k][j][i].setMats(mat);
				}
			}
		}
		volume[d-3][5][5] = null;
		volume[d-1][3][3] = new Voxel(mat1);
		
		return volume;
	}
	
	
	private void foreachVoxels(BiConsumer<Integer, Integer> action) {
		for(int j = 0; j < voxels.length; j++) { // y
			for(int i = 0; i < voxels[j].length; i++) { // x
				action.accept(i, j);
			}
		}
	}
	
	private void quad(int side, Vector3 pos, int w, int h, Material mat) {
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
