package gamemechanics.ext;

public class CellData {
	/*
	 Could do that, or could keep it as it is, it's faster access to the fields without having to cast to the correct type 
	 
	public abstract boolean isVoxel();
	public static class CellModel extends CellData {
		@Override
		public boolean isVoxel() {
			return false;
		}
	}
	public static class CellVoxel extends CellData {
		@Override
		public boolean isVoxel() {
			return true;
		}
	}
	*/
	
	/**
	 * in the case of a pre-built model
	 */
	public String model = "";
	/**
	 * In the case of a pre-built model : { { transX, transY, transZ }, { rotX, rotY, rotZ }, { sclX, sclY, sclZ } }
	 * <p>
	 * The transformations are applied this way : <br>
	 * <code>
	 * inst.transform .translate(i * cellSize + cellSize * m.transform[0][0], j * cellSize + cellSize * m.transform[0][1], cellSize * m.transform[0][2]) <br>
	 * .rotate(m.transform[1][0], m.transform[1][1], m.transform[1][2], 90) <br>
	 * .scale(m.transform[2][0], m.transform[2][1], m.transform[2][2]); <br>
	 * </code>
	 */
	public float[][] transform;
	
	
	/**
	 * in the case of a voxel : 6 paths to 6 textures
	 */
	public String[] textures;
	/**
	 * in the case of a voxel with 6 textures.
	 * coordinates are { x0, y0, x1, y1 } for each 6 textures
	 */
	public int[][] coordinates;
	
	/**
	 * contains 1 color if it's a model or 6 if it's a voxel
	 */
	public String[] colorAttributes = { "ffffff" }; 
	
	
	/**
	 * if this data creates a voxel or a pre-built model
	 */
	public boolean isVoxel() {
		return textures != null && coordinates != null && textures.length == 6 && coordinates.length == 6;
	}
	
}
