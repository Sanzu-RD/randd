package com.souchy.randd.mockingbird.lapismock.lwjgl1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class LShader implements Shader {

	public static class LShaderProvider extends BaseShaderProvider {
		@Override
		protected Shader createShader(Renderable renderable) {
			return new LShader(); 
		}
	}
	
	
    public ShaderProgram program;
   // public Camera camera;
    public RenderContext context;
    
    // ============= uniforms =============  
	private int u_projTrans;
	private int u_worldTrans;
	private int u_colorU;
	private int u_colorV;
	
	private Color ambiantLight;
    
	@Override
	public void init() {
        String vert = Gdx.files.internal("shaders/test.vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/test.fragment.glsl").readString();
        program = new ShaderProgram(vert, frag);
        if (!program.isCompiled())
            throw new GdxRuntimeException(program.getLog());
        u_projTrans = program.getUniformLocation("u_projViewTrans");
        u_worldTrans = program.getUniformLocation("u_worldTrans");
        u_colorU = program.getUniformLocation("u_colorU");
        u_colorV = program.getUniformLocation("u_colorV");
	}

	@Override
	public int compareTo(Shader other) {
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		return true;
	}

	@Override
	public void begin(Camera camera, RenderContext context) {
       // this.camera = camera;
        this.context = context;
        program.begin();
		program.setUniformMatrix(u_projTrans, camera.combined);
        context.setDepthTest(GL20.GL_LEQUAL);
        context.setCullFace(GL20.GL_BACK);
	}

	@Override
	public void render(Renderable renderable) {
		program.setUniformMatrix(u_worldTrans, renderable.worldTransform);
		Color colorU = ((ColorAttribute)renderable.material.get(ColorAttribute.Diffuse)).color;
        Color colorV = ((ColorAttribute)renderable.material.get(ColorAttribute.Diffuse)).color; //Color.BLUE;
        program.setUniformf(u_colorU, colorU.r, colorU.g, colorU.b);
        program.setUniformf(u_colorV, colorV.r, colorV.g, colorV.b);

        renderable.meshPart.render(program);
	}

	@Override
	public void end() {
        program.end();
	}

	@Override
	public void dispose() {
		program.dispose();
	}
	
	
}
