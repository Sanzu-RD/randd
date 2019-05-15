package com.souchy.randd.ebishoal.commons.lapis.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.souchy.randd.commons.cache.map.MapCache;
import com.souchy.randd.ebishoal.commons.lapis.managers.ModelManager;
import com.souchy.randd.situationtest.models.map.MapData;
import com.souchy.randd.situationtest.models.map.MapData.MaterialData;
import com.souchy.randd.situationtest.models.map.MapData.ModelData;

public class WorldGenerator {

    /*
     * A voxel has 6 faces
     * Each face has a material
     */
	public static class Voxel {
		
		public float height = 0;
		public Material[] mats = new Material[6]; 
		public Model model;
		
		public Voxel(Model m, Material[] mat, float height) {
			model = m;
			mats = mat;
			this.height = height;
		}
		public Voxel(Model m, Material mat, float height) {
			this(m, new Material[] {mat, mat, mat, mat, mat, mat}, height);
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
	
	public static class VoxelFace {
		public final Material m;
		public final boolean backface;
		public final boolean vertical;
		public final float y0;
		public final float y1;
		public VoxelFace(Material material, boolean b, boolean vertical, float y0, float y1) {
			this.m = material;
			this.backface = b;
			this.vertical = vertical;
			this.y0 = y0;
			this.y1 = y1;
		}
		public boolean canFuse(VoxelFace f) {
			return 	m == f.m &&
					backface == f.backface &&
					(vertical ? y0 == f.y0 && y1 == f.y1 : true);
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
    public Material mat1;
    public int renderType = GL20.GL_TRIANGLES;
    public int id = 0;

	private int[] dims;
	private Voxel[][] volume;
	
	public int vertexCount = 0;
	public int faceCount = 0;
	
	MapData data;
	public WorldGenerator(ModelManager modelManager, MapData data) {
		this.data = data;
		//Elevations depths = data.getElevations();
		dims = new int[] {data.getWidth(), data.getHeight(), data.getMaxElevation()};
		volume = new Voxel/*[dims[2]]*/[dims[1]][dims[0]];
		Material mats[] = new Material[data.materials.length];

		for(int i = 0; i < data.materials.length; i++) {
			MaterialData md = data.materials[i];
			Material mat = new Material(
					ColorAttribute.createAmbient(md.ambient[0], md.ambient[2], md.ambient[3], md.opacity),
					ColorAttribute.createDiffuse(md.diffuse[0], md.diffuse[2], md.diffuse[3], md.opacity)
					//ColorAttribute.createEmissive(mat.ambient[0], mat.ambient[2], mat.ambient[3], mat.opacity),
					//ColorAttribute.createSpecular(mat.ambient[0], mat.ambient[2], mat.ambient[3], mat.opacity),
			   		//ColorAttribute.createReflective(mat.ambient[0], mat.ambient[2], mat.ambient[3], mat.opacity),
					); 
			mats[i] = mat;
		}
		
		for(int y = 0; y < data.getHeight(); y++) {
			for(int x = 0; x < data.getWidth(); x++) {

				ModelData md = data.getModelData(x, y);
				Model m = modelManager.get(md.model);
				Material mat = mats[md.material];

				if(data.getModel(x, y) != 0) {
					int ele = data.getElevation(x, y);
					volume[y][x] = new Voxel(m, mat, ele);
					/*if(md.model == "cube") {
						for(int z = 0; z < ele; z++) {
							volume[z][y][x] = new Voxel(m, mat);
						}
					} else {
						volume[ele][y][x] = new Voxel(m, mat);
					}*/
					
					
					// should have smth like that
					//volume[][] = Voxel(elevation, model, material);
					
					// for the mask :
					//ya rien en z en fait ?
							
					// les seules faces BOTTTOM sont le fond qu'on ne voit jamais en fait 
					// on pourrait complètement ignorer les faces BOTTOM et ne pas les générer du tout 
					
				}
			}
		}
		

		
	}
	
	public void setData() {
		
	}
	
	public void setMaterials() {
		
	}
	
	public Model generate() {
		mikolalysenko();
		return rootNode;
	}
	
	
	private int getSide(int d, boolean backface) {
		int side = 0;
		if (d == 0)      { side = backface ? WEST   : EAST; }
        else if (d == 1) { side = backface ? SOUTH  : NORTH; }
        else if (d == 2) { side = backface ? BOTTOM : TOP; }  
		return side;
	}

	private Vector3 getNormal(int side) {
		switch(side) {
			case TOP: 		return new Vector3( 0,  0,  1);
			case BOTTOM: 	return new Vector3( 0,  0, -1);
			case NORTH: 	return new Vector3( 0,  1,  0);
			case SOUTH: 	return new Vector3( 0, -1,  0);
			case EAST: 		return new Vector3( 1,  0,  0);
			case WEST: 		return new Vector3(-1,  0,  0);
			default:
				try {
					throw new Exception("");
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
		}
	}

	private Voxel v(Voxel[][]/*[]*/ volume, int i, int j/*, int k*/) {
		return volume/*[k]*/[j][i];
	}

	private void mikolalysenkoQuad(Vector3 botLeft, Vector3 botRight, Vector3 topRight, Vector3 topLeft, int side, boolean backface, VoxelFace face) {
		Vector3 normal = getNormal(side);
		var vertices = new float[] {
				botLeft.x,  botLeft.y,  botLeft.z,	 normal.x, normal.y, normal.z,
				botRight.x, botRight.y, botRight.z,  normal.x, normal.y, normal.z,
				topRight.x, topRight.y, topRight.z,	 normal.x, normal.y, normal.z,
				topLeft.x,  topLeft.y,  topLeft.z, 	 normal.x, normal.y, normal.z,
		};
		//var norm = crossProduct(topLeft, botRight);
    	var indices = new short[] {0,   1,   2,       2,   3,   0};
    	if(backface) indices = new short[] { indices[5], indices[4], indices[3], indices[2], indices[1], indices[0] };
    	Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
    	mesh.setVertices(vertices);
    	mesh.setIndices(indices);
    	
        MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, vertices.length, renderType);
        Node node = new Node();
        node.id = "node_" + id;
        node.parts.add(new NodePart(meshPart, face.m));
        
        rootNode.meshes.add(mesh);
        rootNode.meshParts.add(meshPart);
        rootNode.nodes.add(node);
        
        vertexCount +=4;
        faceCount++;
        id++;
	}
	
	private void mikolalysenko() {
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
			
			
			// for each les plans dans un axe (ex: plan yz positionné à x0, x1, x2...)
			for(x[d]=-1; x[d]<dims[d]; ) {
				// Compute mask
			    makeMask(x, u, v, d, q, mask);
				
				// Increment x[d]
				++x[d];
				
				boolean vertical = d != 2;
				

				// Generate mesh for mask using lexicographic ordering
				// Iterate each cell in the mask to check if they have a material 
				var n = 0;

				int dimV = vertical ? 1 : dims[v]; // max V in the current plane, meaning theres only 1 row in vertical mode (xz and yz move only horizontally)
				
				for (j = 0; j < dimV; ++j)  // coordonnée verticale dans le plan actuel
				for (i = 0; i < dims[u];) { // coordonnée horizontale dans le plan actuel
					var vox = mask[n];
					if(vox != null) {
						// Compute width
						for (w = 1; i + w < dims[u] && mask[n + w] != null && vox.canFuse(mask[n + w]) /*mask[n + w].mats[side] == vox.mats[side]*/; ++w) {
						}
						// Compute height (this is slightly awkward)
						var done = false;
						for (h = 1; j + h < dimV; ++h) {
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
						//x[u] = i; x[v] = j;
						float[] pos  = new float[] {0,0,0};
						float[] vecW = new float[] {0,0,0};  // anciennement du
						float[] vecH = new float[] {0,0,0};  // anciennement dv

						pos[d] = x[d];
						pos[u] = i;
						pos[v] = vertical ? vox.y0 : j;
						vecW[u] = w;
						vecH[v] = vertical ? vox.y1 : h;

						
						mikolalysenkoQuad(
								new Vector3(pos[0],             	pos[1],                 pos[2]                ),
								new Vector3(pos[0]+vecW[0],         pos[1]+vecW[1],         pos[2]+vecW[2]        ),
								new Vector3(pos[0]+vecW[0]+vecH[0], pos[1]+vecW[1]+vecH[1], pos[2]+vecW[2]+vecH[2]),
								new Vector3(pos[0]        +vecH[0], pos[1]        +vecH[1], pos[2]        +vecH[2]),
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
	
	public static interface asdf {
		public void asd();
		private void asdasd() {
			
		}
	}
	
	
	/**
	 * Pour toutes les faces dans un plan, choisi si on met la frontface, la backface, ou rien du tout.
	 * @param x
	 * @param u
	 * @param v
	 * @param d
	 * @param q
	 * @param mask
	 */
	private void makeMask(int[] x, int u, int v, int d, int[] q, VoxelFace[] mask) {
		boolean vertical = d != 2;
		int dimV = vertical ? 1 : dims[v];
		var n = 0;
	    for(x[v]=0; x[v]<dimV; ++x[v])
	    for(x[u]=0; x[u]<dims[u]; ++x[u]) {
	    	// Observe le voxel courant et le suivant dans la direction normale au plan
	    	// - Si les deux voxels sont nulls, on ne créé pas de face entre les deux
	    	// - Si les deux voxels sont ne sont pas nulls, on ne créé pas de face entre les deux non plus car on fusionne
	    	// - Si seulement a n'est pas null, on créé la frontface de a
	    	// - Si seulement b n'est pas null, on créé la backface de b
	    	
	    	// si on est au plan x=0, on check les voxels autours qui sont x=-1 et x=0, donc voxel a = null car x=-1 dont on affiche le voxel b
	    	Voxel a = x[d] >=         0 ? v(volume, x[0],      x[1]     /*,  x[2]*/) : null;
	    	Voxel b = x[d] <  dims[d]-1 ? v(volume, x[0]+q[0], x[1]+q[1]/*, x[2]+q[2]*/) : null;
	    	var nulla = a == null;
	    	var nullb = b == null;
	    	if(nulla && nullb) { // si les deux sont nulls, on met pas de quad
	    		mask[n++] = null;
	    	} else if(!nulla && !nullb) { // si les deux sont pas nulls, faut checker la hauteur
	    		if(a.height == b.height) { // s'ils sont de la même hauteur, on peut fusionner le plancher et ne pas mettre de section verticale entre les deux
		    		mask[n++] = null;
	    		} else if(a.height > b.height) { // si a est plus haut, on met une section de b à a du côté externe de a
		    		mask[n++] = new VoxelFace(a.mats[getSide(d, false)], false, vertical, b.height, a.height);
	    		} else if(a.height < b.height){
		    		mask[n++] = new VoxelFace(a.mats[getSide(d, false)], false, vertical, a.height, b.height);
	    		}
	    	//} else if(nulla == nullb) {
	    	//	mask[n++] = null;
	    	} else if(!nulla) {
	    		mask[n++] = new VoxelFace(a.mats[getSide(d, false)], false, vertical, 0, a.height);
	    	} else if(!nullb) {
	    		mask[n++] = new VoxelFace(b.mats[getSide(d, true)], true, vertical, 0, b.height);
	    	}
		}
	}

	private void addNode() {
		
	}
	
}
