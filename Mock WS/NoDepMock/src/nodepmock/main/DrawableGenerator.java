package nodepmock.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class DrawableGenerator {
	
	private Actor currentActor;
	private int w = (int) currentActor.getWidth(), h = (int) currentActor.getHeight();
	private Color colorBackground = currentActor.getColor();
	private Color colorBorder = currentActor.getColor();
	private int borderWidth = 2;
	private int cornerRadius = 2;
	private Color transparent = new Color(0, 0, 0, 0);
	
	public Drawable asdf() {
		
		Pixmap pix = new Pixmap(w, h, Format.RGBA8888);
		pix.setColor(colorBackground);
		pix.fillRectangle(0, 0, w, h);
		pix.setColor(colorBorder);
		pix.drawRectangle(0, 0, w, h);
		
		pix.setColor(colorBorder);
		pix.fillRectangle(0, 0, w, h);
		pix.setColor(colorBackground);
		pix.fillRectangle(borderWidth, borderWidth, w - borderWidth * 2, h - borderWidth * 2);
		
		
		drawCorner(colorBackground, 1, 1);
		drawCorner(colorBackground, 0, 1);
		drawCorner(colorBackground, 0, 0);
		drawCorner(colorBackground, 1, 0);
		drawSide(colorBackground, 1, 1);
		drawSide(colorBackground, 0, 1);
		drawSide(colorBackground, 0, 0);
		drawSide(colorBackground, 1, 0);
		drawCenter(colorBackground);
		
		drawCorner(colorBorder, 1, 1);
		drawCorner(colorBorder, 0, 1);
		drawCorner(colorBorder, 0, 0);
		drawCorner(colorBorder, 1, 0);
		drawSide(colorBorder, 1, 1);
		drawSide(colorBorder, 0, 1);
		drawSide(colorBorder, 0, 0);
		drawSide(colorBorder, 1, 0);
		
		
		Texture tex = new Texture(pix);
		TextureRegion region = new TextureRegion(tex);
		Drawable drw = new TextureRegionDrawable(region);
		
		return drw;
	}
	
	
	public Pixmap drawCorner(Color color, int u, int v) {
		Pixmap pix = new Pixmap(cornerRadius, cornerRadius, Format.RGBA8888);
		pix.setColor(color);
		pix.fillCircle(pix.getWidth() * u, pix.getHeight() * v, cornerRadius);
		if(cornerRadius - borderWidth > 0) {
			pix.setColor(colorBackground);
			pix.fillCircle(pix.getWidth() * u, pix.getHeight() * v, cornerRadius - borderWidth);
		}
		return pix;
	}
	
	public Pixmap drawSide(Color color, int u, int v) {
		var pw = u == 0 ? borderWidth : w;
		var ph = v == 0 ? borderWidth : h;
		Pixmap pix = new Pixmap(pw, ph, Format.RGBA8888);
		pix.setColor(color);
		pix.fillRectangle(0, 0, pw, ph);
		return pix;
	}
	
	public void drawCenter(Color color) {
		
	}
	
	
	

	public static ModelInstance cellHighlighterModelTest() {
		float r = 1, g = 0, b = 0;
		var areaColor = new Color(r, g, b, 0.15f);
		var borderColor = new Color(r, g, b, 1f);
		Material mat = new Material(ColorAttribute.createDiffuse(areaColor), new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
		Model highlight = new Model();
		Vector3 pos = new Vector3(0, 0, 1f);
		Vector3 size = new Vector3(15, 15, 0);
		Vector3 normal = new Vector3(0, 0, 1);
		
		int vertexPropCount = 6; // nombre de propriété par vertex (pos.xyz + norm.xyz = 6)
		
		var vertices = new float[] { pos.x, pos.y, pos.z, normal.x, normal.y, normal.z, pos.x + size.x, pos.y, pos.z, normal.x, normal.y, normal.z,
				pos.x + size.x, pos.y + size.y, pos.z, normal.x, normal.y, normal.z, pos.x, pos.y + size.y, pos.z, normal.x, normal.y, normal.z, };
		var indices = new short[] { 0, 1, 2, 2, 3, 0 };
		Mesh mesh = new Mesh(true, vertices.length, indices.length, VertexAttribute.Position(), VertexAttribute.Normal());
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		
		String id = "highlight";
		int renderType = GL20.GL_TRIANGLES;
		// if(mat == null) mat = mat3;
		MeshPart meshPart = new MeshPart("meshpart_" + id, mesh, 0, indices.length, renderType);
		Node node = new Node();
		node.id = "node_" + id;
		node.parts.add(new NodePart(meshPart, mat));
		
		highlight.meshes.add(mesh);
		highlight.meshParts.add(meshPart);
		highlight.nodes.add(node);
		
		float borderThickness = 0.03f;
		Material borderMat = new Material(ColorAttribute.createDiffuse(borderColor));
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
			b1.sub(dir.cpy().scl(borderThickness));
			b1.add(pDir.cpy().scl(borderThickness));
			var b2 = new Vector3(v1.x, v1.y, v1.z);
			b2.sub(dir.cpy().scl(borderThickness));
			b2.sub(pDir.cpy().scl(borderThickness));
			var b3 = new Vector3(v2.x, v2.y, v2.z);
			b3.add(dir.cpy().scl(borderThickness));
			b3.add(pDir.cpy().scl(borderThickness));
			var b4 = new Vector3(v2.x, v2.y, v2.z);
			b4.add(dir.cpy().scl(borderThickness));
			b4.sub(pDir.cpy().scl(borderThickness));
			
			var verticesBorder = new float[] { b2.x, b2.y, b2.z, normal.x, normal.y, normal.z, b1.x, b1.y, b1.z, normal.x, normal.y, normal.z, b3.x, b3.y, b3.z,
					normal.x, normal.y, normal.z, b4.x, b4.y, b4.z, normal.x, normal.y, normal.z, };
			var indicesBorder = new short[] { 0, 1, 2, 2, 3, 0 };
			
			Mesh meshBorder = new Mesh(true, verticesBorder.length, indicesBorder.length, VertexAttribute.Position(), VertexAttribute.Normal());
			meshBorder.setVertices(verticesBorder);
			meshBorder.setIndices(indicesBorder);
			
			String idBorder = "border" + i + "_" + i2;
			MeshPart meshPartBorder = new MeshPart("meshpart_" + idBorder, meshBorder, 0, indices.length, renderType);
			Node nodeBorder = new Node();
			nodeBorder.id = "node_" + idBorder;
			nodeBorder.parts.add(new NodePart(meshPartBorder, borderMat));
			
			highlight.meshes.add(meshBorder);
			highlight.meshParts.add(meshPartBorder);
			highlight.nodes.add(nodeBorder);
		}
		
		return new ModelInstance(highlight);
	}
	
	

}
