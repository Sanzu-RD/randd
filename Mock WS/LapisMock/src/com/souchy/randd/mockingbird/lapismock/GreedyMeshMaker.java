package com.souchy.randd.mockingbird.lapismock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;

public class GreedyMeshMaker {

    /*
     * In this test each voxel has a size of one world unit - in reality a voxel engine 
     * might have larger voxels - and there's a multiplication of the vertex coordinates 
     * below to account for this.
     */
    private static final int VOXEL_SIZE = 1;
    
    /*
     * These are the chunk dimensions - it may not be the case in every voxel engine that 
     * the data is rendered in chunks - but this demo assumes so.  Anyway the chunk size is 
     * just used to populate the sample data array.  Also, in reality the chunk size will likely 
     * be larger - for example, in my voxel engine chunks are 16x16x16 - but the small size 
     * here allows for a simple demostration.
     */
    private static final int CHUNK_WIDTH = 10;
    private static final int CHUNK_HEIGHT = 10;
    
    /*
     * This is a 3D array of sample data - I'm using voxel faces here because I'm returning 
     * the same data for each face in this example - but calls to the getVoxelFace function below 
     * will return variations on voxel data per face in a real engine.  For example, in my system 
     * each voxel has a type, temperature, humidity, etc - which are constant across all faces, and 
     * then attributes like sunlight, artificial light which face per face or even per vertex.
     */
    private final VoxelFace [][][] voxels = new VoxelFace [CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];

    /*
     * These are just constants to keep track of which face we're dealing with - their actual 
     * values are unimportantly - only that they're constant.
     */
    private static final int SOUTH      = 0;
    private static final int NORTH      = 1;
    private static final int EAST       = 2;
    private static final int WEST       = 3;
    private static final int TOP        = 4;
    private static final int BOTTOM     = 5;    
    
    /**
     * This class is used to encapsulate all information about a single voxel face.  Any number of attributes can be 
     * included - and the equals function will be called in order to compare faces.  This is important because it 
     * allows different faces of the same voxel to be merged based on varying attributes.
     * 
     * Each face can contain vertex data - for example, int[] sunlight, in order to compare vertex attributes.
     * 
     * Since it's optimal to combine greedy meshing with face culling, I have included a "transparent" attribute here 
     * and the mesher skips transparent voxel faces.  The getVoxelData function below - or whatever it's equivalent 
     * might be when this algorithm is used in a real engine - could set the transparent attribute on faces based 
     * on whether they should be visible or not.
     */
    class VoxelFace {
    
        public boolean transparent;
        public int type;
        public int side;
        
        public boolean equals(final VoxelFace face) { return face.transparent == this.transparent && face.type == this.type; }
    }
    
    /**
     * This is just the main function used to start the demo on JMonkey.
     * 
     * @param args 
     */
    /*public static void main(String[] args) {
        
        final Main app = new Main();
        app.settings = new AppSettings(true);
        app.setShowSettings(false);
        app.settings.setResolution(1280, 720);
        
        app.start();
    }*/
    
    /**
     * This is an initialization function used here to set up the sample voxel data 
     * and launch the greedy meshing.
     */
   // @Override
    public void simpleInitApp() {

        VoxelFace face;

        for (int i = 0; i < CHUNK_WIDTH; i++) {
            
            for (int j = 0; j < CHUNK_HEIGHT; j++) {
            
                for (int k = 0; k < CHUNK_HEIGHT; k++) {
                
                    if (i > CHUNK_WIDTH/2 && i < CHUNK_WIDTH*0.75 && 
                        j > CHUNK_HEIGHT/2 && j < CHUNK_HEIGHT*0.75 && 
                        k > CHUNK_HEIGHT/2 && k < CHUNK_HEIGHT*0.75) {

                        /*
                         * We add a set of voxels of type 1 at the top-right of the chunk.
                         * 
                         */
                        face = new VoxelFace();
                        face.type = 1;
                        
                        /*
                         * To see an example of face culling being used in combination with 
                         * greedy meshing, you could set the trasparent attribute to true.
                         */                        
//                        face.transparent = true;

                    } else if (i == 0) {

                        /*
                         * We add a set of voxels of type 2 on the left of the chunk. 
                         */
                        face = new VoxelFace();
                        face.type = 2;
                        
                    } else {

                        /*
                         * And the rest are set to type 3.
                         */
                        face = new VoxelFace();
                        face.type = 3;
                    }
                
                    voxels[i][j][k] = face;
                }
            }            
        }

        /*
         * And now that the sample data is prepared, we launch the greedy meshing.
         */
        greedy();
    }

    /**
     * 
     */
    void greedy() {

        /*
         * These are just working variables for the algorithm - almost all taken 
         * directly from Mikola Lysenko's javascript implementation.
         */
        int i, j, k, l, w, h, u, v, n, side = 0;
        
        final int[] x = new int []{0,0,0};
        final int[] q = new int []{0,0,0};
        final int[] du = new int[]{0,0,0}; 
        final int[] dv = new int[]{0,0,0};         
        
        /*
         * We create a mask - this will contain the groups of matching voxel faces 
         * as we proceed through the chunk in 6 directions - once for each face.
         */
        final VoxelFace[] mask = new VoxelFace [CHUNK_WIDTH * CHUNK_HEIGHT];
        
        /*
         * These are just working variables to hold two faces during comparison.
         */
        VoxelFace voxelFace, voxelFace1;

        /**
         * We start with the lesser-spotted boolean for-loop (also known as the old flippy floppy). 
         * 
         * The variable backFace will be TRUE on the first iteration and FALSE on the second - this allows 
         * us to track which direction the indices should run during creation of the quad.
         * 
         * This loop runs twice, and the inner loop 3 times - totally 6 iterations - one for each 
         * voxel face.
         */
        for (boolean backFace = true, b = false; b != backFace; backFace = backFace && b, b = !b) { 

            /*
             * We sweep over the 3 dimensions - most of what follows is well described by Mikola Lysenko 
             * in his post - and is ported from his Javascript implementation.  Where this implementation 
             * diverges, I've added commentary.
             */
            for(int d = 0; d < 3; d++) {

                u = (d + 1) % 3; 
                v = (d + 2) % 3;

                x[0] = 0;
                x[1] = 0;
                x[2] = 0;

                q[0] = 0;
                q[1] = 0;
                q[2] = 0;
                q[d] = 1;

                /*
                 * Here we're keeping track of the side that we're meshing.
                 */
                if (d == 0)      { side = backFace ? WEST   : EAST;  }
                else if (d == 1) { side = backFace ? BOTTOM : TOP;   }
                else if (d == 2) { side = backFace ? SOUTH  : NORTH; }                

                /*
                 * We move through the dimension from front to back
                 */            
                for(x[d] = -1; x[d] < CHUNK_WIDTH;) {

                    /*
                     * -------------------------------------------------------------------
                     *   We compute the mask
                     * -------------------------------------------------------------------
                     */
                    n = 0;

                    for(x[v] = 0; x[v] < CHUNK_HEIGHT; x[v]++) {

                        for(x[u] = 0; x[u] < CHUNK_WIDTH; x[u]++) {

                            /*
                             * Here we retrieve two voxel faces for comparison.
                             */
                            voxelFace  = (x[d] >= 0 )             ? getVoxelFace(x[0], x[1], x[2], side)                      : null;
                            voxelFace1 = (x[d] < CHUNK_WIDTH - 1) ? getVoxelFace(x[0] + q[0], x[1] + q[1], x[2] + q[2], side) : null;

                            /*
                             * Note that we're using the equals function in the voxel face class here, which lets the faces 
                             * be compared based on any number of attributes.
                             * 
                             * Also, we choose the face to add to the mask depending on whether we're moving through on a backface or not.
                             */
                            mask[n++] = ((voxelFace != null && voxelFace1 != null && voxelFace.equals(voxelFace1))) 
                                        ? null 
                                        : backFace ? voxelFace1 : voxelFace;
                        }
                    }

                    x[d]++;

                    /*
                     * Now we generate the mesh for the mask
                     */
                    n = 0;

                    for(j = 0; j < CHUNK_HEIGHT; j++) {

                        for(i = 0; i < CHUNK_WIDTH;) {

                            if(mask[n] != null) {

                                /*
                                 * We compute the width
                                 */
                                for(w = 1; i + w < CHUNK_WIDTH && mask[n + w] != null && mask[n + w].equals(mask[n]); w++) {}

                                /*
                                 * Then we compute height
                                 */
                                boolean done = false;

                                for(h = 1; j + h < CHUNK_HEIGHT; h++) {

                                    for(k = 0; k < w; k++) {

                                        if(mask[n + k + h * CHUNK_WIDTH] == null || !mask[n + k + h * CHUNK_WIDTH].equals(mask[n])) { done = true; break; }
                                    }

                                    if(done) { break; }
                                }

                                /*
                                 * Here we check the "transparent" attribute in the VoxelFace class to ensure that we don't mesh 
                                 * any culled faces.
                                 */
                                if (!mask[n].transparent) {
                                    /*
                                     * Add quad
                                     */
                                	 x[u] = i;  
                                     x[v] = j;

                                     du[0] = 0;
                                     du[1] = 0;
                                     du[2] = 0;
                                     du[u] = w;

                                     dv[0] = 0;
                                     dv[1] = 0;
                                     dv[2] = 0;
                                     dv[v] = h;

                                    /*
                                     * And here we call the quad function in order to render a merged quad in the scene.
                                     * 
                                     * We pass mask[n] to the function, which is an instance of the VoxelFace class containing 
                                     * all the attributes of the face - which allows for variables to be passed to shaders - for 
                                     * example lighting values used to create ambient occlusion.
                                     */
                                     // 000, 010, 110, 100
                                    quad(new Vector3(x[0],                 x[1],                   x[2]), 
                                         new Vector3(x[0] + du[0],         x[1] + du[1],           x[2] + du[2]), 
                                         new Vector3(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1],   x[2] + du[2] + dv[2]), 
                                         new Vector3(x[0]         + dv[0], x[1]         + dv[1],   x[2]         + dv[2]), 
                                         w,
                                         h,
                                         mask[n],
                                         backFace);
                                }

                                /*
                                 * We zero out the mask
                                 */
                                for(l = 0; l < h; ++l) {

                                    for(k = 0; k < w; ++k) { 
                                    	mask[n + k + l * CHUNK_WIDTH] = null; 
                                    }
                                }

                                /*
                                 * And then finally increment the counters and continue
                                 */
                                i += w; 
                                n += w;

                            } else {

                              i++;
                              n++;
                            }
                        }
                    } 
                }
            }        
        }
    }

    /**
     * This function returns an instance of VoxelFace containing the attributes for 
     * one side of a voxel.  In this simple demo we just return a value from the 
     * sample data array.  However, in an actual voxel engine, this function would 
     * check if the voxel face should be culled, and set per-face and per-vertex 
     * values as well as voxel values in the returned instance.
     * 
     * @param x
     * @param y
     * @param z
     * @param face
     * @return 
     */
    VoxelFace getVoxelFace(final int x, final int y, final int z, final int side) {

        VoxelFace voxelFace = voxels[x][y][z];
        
        voxelFace.side = side;

        return voxelFace;
    }
    
    private void setColor(float[] colorArray, VoxelFace voxel) {
        for (int i = 0; i < colorArray.length; i+=4) {
            /*
             * Here I set different colors for quads depending on the "type" attribute, just 
             * so that the different groups of voxels can be clearly seen.
             * 
             */
            if (voxel.type == 1) {
                colorArray[i]   = 1.0f;
                colorArray[i+1] = 0.0f;
                colorArray[i+2] = 0.0f;
                colorArray[i+3] = 1.0f;                
                
            } else if (voxel.type == 2) {
                colorArray[i]   = 0.0f;
                colorArray[i+1] = 1.0f;
                colorArray[i+2] = 0.0f;
                colorArray[i+3] = 1.0f;
                
            } else {
                colorArray[i]   = 0.0f;
                colorArray[i+1] = 0.0f;
                colorArray[i+2] = 1.0f;
                colorArray[i+3] = 1.0f;                
            }
        }
    }
    
    /**
     * This function renders a single quad in the scene. This quad may represent many adjacent voxel 
     * faces - so in order to create the illusion of many faces, you might consider using a tiling 
     * function in your voxel shader. For this reason I've included the quad width and height as parameters.
     * 
     * For example, if your texture coordinates for a single voxel face were 0 - 1 on a given axis, they should now 
     * be 0 - width or 0 - height. Then you can calculate the correct texture coordinate in your fragement 
     * shader using coord.xy = fract(coord.xy). 
     * 
     * 
     * @param bottomLeft
     * @param topLeft
     * @param topRight
     * @param bottomRight
     * @param width
     * @param height
     * @param voxel
     * @param backFace 
     */
    void quad(final Vector3 bottomLeft, 
              final Vector3 topLeft, 
              final Vector3 topRight, 
              final Vector3 bottomRight,
              final int width,
              final int height,
              final VoxelFace voxel, 
              final boolean backFace) {
 
        final Vector3 [] vertices = new Vector3[4];

        vertices[0] = bottomLeft.scl(VOXEL_SIZE); //.multLocal(VOXEL_SIZE); // 0
        vertices[1] = topLeft.scl(VOXEL_SIZE); //.multLocal(VOXEL_SIZE); // 2
        vertices[2] = topRight.scl(VOXEL_SIZE); //.multLocal(VOXEL_SIZE);  // 3
        vertices[3] = bottomRight.scl(VOXEL_SIZE); //.multLocal(VOXEL_SIZE); // 1

       /* vertices[0] = new Vector3(0, 0, 0);
        vertices[1] = new Vector3(0, 0, 10);
        vertices[2] = new Vector3(0, 10, 10);
        vertices[3] = new Vector3(0, 10, 0);*/
        
        
        final short [] indexes = backFace ? new short[] { 2,0,1, 1,3,2 } : new short[]{ 2,3,1, 1,0,2 };
        
        final float[] colorArray = new float[4*4];
        
        setColor(colorArray, voxel);
        
        /*mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(Type.Color,    4, colorArray);
        mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();*/
        
        var a_pos = VertexAttribute.Position(); //new VertexAttribute(Usage.Position, 3, "a_position");
        var a_color = VertexAttribute.ColorUnpacked(); //new VertexAttribute(Usage.ColorPacked, 4, "a_color");

        // vertices attributes array
        float[] arr = new float[vertices.length * (3 + 4) ]; //+ colorArray.length * 4];
        int i = 0; // array index
        int j = 0; // vertex index
        int k = 0; // color index
        for(j = 0; j < vertices.length; j++) {
        	k = j * 4;
        	// vertex position attribute
        	arr[i++] = vertices[j].x;
        	arr[i++] = vertices[j].y;
        	arr[i++] = vertices[j].z;
        	// vertex color attribute
        	arr[i++] = color.r; //colorArray[k];
        	arr[i++] = color.g; //colorArray[k+1];
        	arr[i++] = color.b; //colorArray[k+2];
        	arr[i++] = color.a; //colorArray[k+3];
        }
        Mesh mesh = new Mesh(true, vertices.length, 6, a_pos, a_color);
        mesh.setVertices(arr);
        //mesh.setIndices(indexes); 
        mesh.setIndices(new short[] {2, 0, 3,   1, 0, 2});
        // libgdx standard : 
        //mesh.setIndices(new short[] {0, 1, 2,   2, 3, 0});
        var a = Gdx.gl.GL_CW;
        
        /*Geometry geo = new Geometry("ColoredMesh", mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setBoolean("VertexColor", true);*/

        /*
         * To see the actual rendered quads rather than the wireframe, just comment outthis line.
         */
        // //mat.getAdditionalRenderState().setWireframe(true);
        
        // //geo.setMaterial(mat);

        // //rootNode.attachChild(geo);
        
        MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, vertices.length, renderType);
        
        Node node = new Node();
        node.id = "node_" + id;
        NodePart nodePart = new NodePart(meshPart, mat1);
        node.parts.add(nodePart);
        rootNode.meshes.add(mesh);
        rootNode.meshParts.add(meshPart);
        rootNode.nodes.add(node);
        
        id++;
    }
    

    public Model rootNode = new Model();
    public Color color;
    public Material mat1;
    public static int renderType = GL20.GL_TRIANGLES;
    public int id = 0;
    
    public GreedyMeshMaker() {
    	color = Color.valueOf("AEE897");
    	mat1 = new Material("mat1", ColorAttribute.createDiffuse(color)); //, ColorAttribute.createAmbient(null), ColorAttribute.createEmissive(null));
    	rootNode.materials.add(mat1);
    	
/*
        Mesh mesh = new Mesh(true, 4, 0, VertexAttribute.Position(), VertexAttribute.ColorUnpacked());
        mesh.setVertices(new float[] {
        		20,20,0,
        		1,0,0,1,
        		25,20,0,
        		0,1,0,1,
        		25,25,0,
        		0,0,1,1,
        		20,25,0,
        		1,1,1,1
        });
     //   mesh = createFullScreenQuad();

        MeshPart meshPart = new MeshPart(null, mesh, 0, 4, renderType);
        Node node = new Node();
        node.id = null;
        node.parts.add(new NodePart(meshPart, mat1));
        
        rootNode.meshes.add(mesh);
        rootNode.meshParts.add(meshPart);
        rootNode.nodes.add(node);
        */
    	for(int i = 0; i < CHUNK_WIDTH; i++) {
        	for(int j = 0; j < CHUNK_HEIGHT; j++) {
       //         createBlock(new Vector3(i, j, 0));
        	}
    	}
        
    	simpleInitApp();
    }
    
    public static void createBlock(Model rootNode, Vector3 pos, Material mat1, int renderType, int id) {
		var vertices = new float[] {
				// west
				0.000000f, 1.000000f, 1.000000f, -1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				0.000000f, 0.000000f, 0.000000f, -1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				0.000000f, 0.000000f, 1.000000f, -1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				0.000000f, 1.000000f, 0.000000f, -1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				// east
				1.000000f, 0.000000f, 1.000000f, 1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				1.000000f, 0.000000f, 0.000000f, 1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				1.000000f, 1.000000f, 1.000000f, 1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				1.000000f, 1.000000f, 0.000000f, 1.000000f, 0.000000f, 0.000000f, // 0.958984f, 1.000000f,
				// top
				1.000000f, 0.000000f, 1.000000f, 0.000000f, 0.000000f, 1.000000f, // 0.958984f, 1.000000f,
				0.000000f, 1.000000f, 1.000000f, 0.000000f, 0.000000f, 1.000000f, // 0.958984f, 1.000000f,
				0.000000f, 0.000000f, 1.000000f, 0.000000f, 0.000000f, 1.000000f, // 0.958984f, 1.000000f,
				1.000000f, 1.000000f, 1.000000f, 0.000000f, 0.000000f, 1.000000f, // 0.958984f, 1.000000f,
				// bot
				0.000000f, 0.000000f, 0.000000f, 0.000000f, 0.000000f, -1.000000f, // 0.958984f, 1.000000f,
				0.000000f, 1.000000f, 0.000000f, 0.000000f, 0.000000f, -1.000000f, // 0.958984f, 1.000000f,
				1.000000f, 0.000000f, 0.000000f, 0.000000f, 0.000000f, -1.000000f, // 0.958984f, 1.000000f,
				1.000000f, 1.000000f, 0.000000f, 0.000000f, 0.000000f, -1.000000f, // 0.958984f, 1.000000f,
				// south
				0.000000f, 0.000000f, 0.000000f, 0.000000f, -1.000000f, 0.000000f, // 0.958984f, 1.000000f,
				1.000000f, 0.000000f, 1.000000f, 0.000000f, -1.000000f, 0.000000f, // 0.958984f, 1.000000f,
				0.000000f, 0.000000f, 1.000000f, 0.000000f, -1.000000f, 0.000000f, // 0.958984f, 1.000000f,
				1.000000f, 0.000000f, 0.000000f, 0.000000f, -1.000000f, 0.000000f, // 0.958984f, 1.000000f,
				// north
				0.000000f, 1.000000f, 1.000000f, 0.000000f, 1.000000f, 0.000000f, // 0.958984f, 1.000000f,
				1.000000f, 1.000000f, 1.000000f, 0.000000f, 1.000000f, 0.000000f, // 0.958984f, 1.000000f,
				0.000000f, 1.000000f, 0.000000f, 0.000000f, 1.000000f, 0.000000f, // 0.958984f, 1.000000f,
				1.000000f, 1.000000f, 0.000000f, 0.000000f, 1.000000f, 0.000000f,// 0.958984f, 1.000000f
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
    
    private Mesh createFullScreenQuad() {

    	  float[] verts = new float[20];
    	  int i = 0;

    	  verts[i++] = -1; // x1
    	  verts[i++] = -1; // y1
    	  verts[i++] = 0;
    	  verts[i++] = 0f; // u1
    	  verts[i++] = 0f; // v1

    	  verts[i++] = 1f; // x2
    	  verts[i++] = -1; // y2
    	  verts[i++] = 0;
    	  verts[i++] = 1f; // u2
    	  verts[i++] = 0f; // v2

    	  verts[i++] = 1f; // x3
    	  verts[i++] = 1f; // y2
    	  verts[i++] = 0;
    	  verts[i++] = 1f; // u3
    	  verts[i++] = 1f; // v3

    	  verts[i++] = -1; // x4
    	  verts[i++] = 1f; // y4
    	  verts[i++] = 0;
    	  verts[i++] = 0f; // u4
    	  verts[i++] = 1f; // v4

    	  Mesh mesh = new Mesh( true, 4, 0,  // static mesh with 4 vertices and no indices
    	    new VertexAttribute( Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE ),
    	    new VertexAttribute( Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0" ) );

    	  mesh.setVertices( verts );
    	  return mesh;
    	}
    
    
}
