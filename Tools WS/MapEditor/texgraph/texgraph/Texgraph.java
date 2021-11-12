package texgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.*;

import texgraph.model.NodeModel;

public class Texgraph {

	public static SpriteBatch batch;
	public static Map<String, List<NodeModel>> models;
	
	static {
		batch = new SpriteBatch();
		models = new HashMap<>();
		
		var types = new DefaultClassDiscoverer<NodeModel>(NodeModel.class).explore("nodes");
		for(var t : types) {
			try {
				var model = t.getDeclaredConstructor().newInstance();
				if(!models.containsKey(model.category)) 
					models.put(model.category, new ArrayList<>());
				models.get(model.category).add(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public static final String defaultShaderVertex = 
		  "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		+ "uniform mat4 u_projTrans;\n" //
		+ "varying vec4 v_color;\n" //
		+ "varying vec2 v_texCoords;\n" //
		+ "\n" //
		+ "void main()\n" //
		+ "{\n" //
		+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
		+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		+ "}\n";
	


	public static final String defaultShaderFragment = """
			#ifdef GL_ES
			#define LOWP lowp
			precision mediump float;
			#else
			#define LOWP
			#endif\n
			
			varying LOWP vec4 v_color;
			varying vec2 v_texCoords;
			uniform sampler2D u_texture;
			
			void main()
			{
				gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
			}
			""";
	
	public static String frag(String vars, String shade) {
		return """
				#ifdef GL_ES
				#define LOWP lowp
				precision mediump float;
				#else
				#define LOWP
				#endif\n
				
				varying LOWP vec4 v_color;
				varying vec2 v_texCoords;
				""" + vars + """
				
				void main()
				{
						""" + shade + """
				}
				""";
	}
//	uniform sampler2D u_texture;
//	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
	
}
