package com.souchy.randd.mockingbird.lapismock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
//import com.bitfire.postprocessing.PostProcessor;
//import com.bitfire.postprocessing.effects.Bloom;
//import com.bitfire.utils.ShaderLoader;

public abstract class BaseScreen implements Screen {

	public Camera cam;
    public Environment env;
    public ModelBatch modelBatch;
    public CameraInputController camController;
    public World world;

	public BoundingBox worldBB;
	public Vector3 worldCenter;
    
    public BaseScreen() {
        modelBatch = new ModelBatch();

        createEnvironment();
        world = new World(env);
        worldBB = world.getBoundingBox();
        worldCenter = worldBB.getCenter(new Vector3());
        
        createCam();
        
        setupShader();

	    /*
	   	renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		shader = new TestShader();
		shader.init();
		*/
	}
    
    public boolean useOrtho() {
    	return false;
    }
    
    public void createCam() {
    	if(useOrtho()) {
    		//cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    		float viewportSize = 30;
    		cam = new OrthographicCamera(viewportSize*16/9, viewportSize);
            //cam.position.set(worldCenter.x, worldCenter.y*0.6f, worldCenter.x*1.5f);
            //cam.lookAt(worldCenter.x, worldCenter.y*0.9f, 0);
            cam.direction.set(-1, 1, -1f);
            cam.up.set(-1, 1, 1f);
            cam.position.set(14, 14, 0);
            cam.near = -30f;
            cam.far = 300f;
            cam.update();
    	} else {
    		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//            cam.position.set(worldCenter.x, worldCenter.y*0.6f, worldCenter.x*1.8f);
//            cam.lookAt(worldCenter.x, worldCenter.y*0.9f, 0);
           //  cam.lookAt(14, 14, 0);
            cam.direction.set(-1, 1, -1f);
            cam.up.set(-1, 1, 1f);
            cam.position.set(25, -5, 15);
            cam.near = 1f;
            cam.far = 300f;
            cam.update();
    	}
    }
    
    public void createEnvironment() {
        env = new Environment();
        var ambiant = 0.6f;
        var dir = 0.6f;
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 1f));
        env.add(new DirectionalLight().set(dir, dir, dir, -1f, -0.8f, -0.2f));
    }
    
    
    public abstract void setupShader();
	
	
	@Override
	public void show() {
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
	}
	
	public void clearScreen() {
		Color color = getBackgroundColor();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
	}
	
	public Color getBackgroundColor() {
		return Color.SKY;//Color.BLACK;
	}

	private FrameBuffer fbo;
	private SpriteBatch batch;
	private TextureRegion fboRegion;
//	private PostProcessor pp;
//	private PostProcessor pfxPp;
	private Texture slider;
	private Boolean fbob = true;
	private boolean ppb = false; //!fbob;
	{
		fbo = new FrameBuffer(Format.RGBA8888, 1024 * 2 * 2, 1024 * 2 * 2, true, false);
		batch = new SpriteBatch(1000, new ShaderProgram(Gdx.files.internal("data/shaders/postProcess.vertex.glsl"), Gdx.files.internal("data/shaders/postProcess.fragment.glsl")));
		slider = new Texture(Gdx.files.internal("data/textures/slider.png"));
		
//		ShaderLoader.BasePath = "data/shaders/manuelbua_shaders/";
//		pp = new PostProcessor(true, true, true);
//		pfxPp = new PostProcessor(true, true, true);
//		
//		float f = 0.25f;
//		Bloom bloom = new Bloom((int) (Gdx.graphics.getWidth() * f), (int) (Gdx.graphics.getHeight() * f));
		/*bloom.setBaseIntesity(1);
		bloom.setBloomSaturation(1);
		//bloom.enableBlending(1, 1);
		bloom.setBlurType(BlurType.Gaussian5x5);
		bloom.setBlurAmount(1);
		bloom.setBlurPasses(1);*/
		//bloom.setEnabled(false);
		//pp.addEffect(bloom);
//		pp.addEffect(bloom);
	}
	@Override
	public void render(float delta) {
        camController.update();

		clearScreen();

        act(delta);
        updateLight(delta);
        
        renderShadows(world);

		if(fbob) fbo.begin();
//		if(ppb) pp.capture();
		
		{
			clearScreen();
	        renderWorld(world);
	        renderParticleEffects();
	        
		}
        
        /*
        renderContext.begin();
        shader.begin(cam, renderContext);
        renderables.forEach(r -> shader.render(r));
        //shader.render(renderable);
        shader.end();
        renderContext.end();
        */
		
//        if(ppb) pp.render();
        if(fbob) {
    		//Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    		fbo.end();
            clearScreen();
            
            // récupère l'image générée par les model batch
            Texture tex = fbo.getColorBufferTexture();
            fboRegion = new TextureRegion(tex
    				//, 0, 0,
    				// Gdx.graphics.getWidth(), Gdx.graphics.getHeight() // track.getRegionWidth(), track.getRegionHeight()
    				);
    		fboRegion.flip(false, true); // FBO uses lower left, TextureRegion uses upper-left

    		// render l'image à travers la sprite batch du post processor
    		batch.begin();
    		batch.draw(fboRegion, 0, 0);
    		//batch.draw(slider, 0, 0);
    		//batch.draw(new Texture(Gdx.files.absolute("G:\\Assets\\test\\glazedTerracotta.png")), 500, 500);
    		batch.end();
        }
        
        //pfxPp.capture();
        //renderParticleEffects();
        //pfxPp.render();
		
        Gdx.graphics.setTitle(getTitleText());
	}
	

	public String getTitleText() {
		return "FPS : " + Gdx.graphics.getFramesPerSecond();
	}
	
	/*
	 * Update the lightning direction 
	 */
	protected abstract void updateLight(float delta);
	
	/*
	 * For stuff like animations & effects ...
	 */
	protected abstract void act(float delta);

	/*
	 * Render the shadowmap for the world
	 */
	public abstract void renderShadows(World world);
	
	/*
	 * Render the world models, characters, particle effects, etc
	 */
	public abstract void renderWorld(World world);
	
	/**
	 * Render particle effects
	 */
	public abstract void renderParticleEffects();
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void dispose () {
        modelBatch.dispose();
        world.dispose();
    }
	
	
}
