package com.gempukku.libgdx.graph.ui.shader.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Disposable;
import com.gempukku.libgdx.graph.DefaultTimeKeeper;
import com.gempukku.libgdx.graph.RandomIdGenerator;
import com.gempukku.libgdx.graph.WhitePixel;
import com.gempukku.libgdx.graph.data.Graph;
import com.gempukku.libgdx.graph.data.GraphConnection;
import com.gempukku.libgdx.graph.data.GraphNode;
import com.gempukku.libgdx.graph.data.GraphProperty;
import com.gempukku.libgdx.graph.pipeline.loader.rendering.producer.ShaderContextImpl;
import com.gempukku.libgdx.graph.shader.GraphShader;
import com.gempukku.libgdx.graph.shader.GraphShaderAttribute;
import com.gempukku.libgdx.graph.shader.GraphShaderBuilder;
import com.gempukku.libgdx.graph.shader.ShaderFieldType;
import com.gempukku.libgdx.graph.shader.environment.GraphShaderEnvironment;
import com.gempukku.libgdx.graph.shader.models.ModelInstanceOptimizationHints;
import com.gempukku.libgdx.graph.shader.models.impl.GraphShaderModel;
import com.gempukku.libgdx.graph.shader.models.impl.GraphShaderModelInstance;

public class ShaderPreviewWidget extends Widget implements Disposable {
    public enum ShaderPreviewModel {
        Sphere, Rectangle
    }

    private boolean shaderInitialized;
    private int width;
    private int height;

    private FrameBuffer frameBuffer;
    private GraphShader graphShader;
    private RenderContext renderContext;

    private Model rectangleModel;
    private GraphShaderModel rectangleShaderModel;
    private GraphShaderModelInstance rectangleModelInstance;
    private Model sphereModel;
    private GraphShaderModel sphereShaderModel;
    private GraphShaderModelInstance sphereModelInstance;

    private Camera camera;
    private DefaultTimeKeeper timeKeeper;
    private GraphShaderEnvironment graphShaderEnvironment;
    private ShaderContextImpl shaderContext;
    private ShaderPreviewModel model = ShaderPreviewModel.Sphere;

    public ShaderPreviewWidget(int width, int height) {
        this.width = width;
        this.height = height;
        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.LRU, 1));
        camera = new PerspectiveCamera();
        camera.near = 0.5f;
        camera.far = 100f;
        camera.position.set(-1f, 0f, 0f);
        camera.up.set(0f, 1f, 0f);
        camera.lookAt(0, 0f, 0f);
        camera.update();

        graphShaderEnvironment = new GraphShaderEnvironment();
        graphShaderEnvironment.setAmbientColor(Color.DARK_GRAY);
        PointLight pointLight = new PointLight();
        pointLight.set(Color.WHITE, -2f, 1f, 1f, 2f);
        graphShaderEnvironment.addPointLight(pointLight);

        shaderContext = new ShaderContextImpl();
        shaderContext.setGraphShaderEnvironment(graphShaderEnvironment);
        shaderContext.setCamera(camera);
    }

    public void setModel(ShaderPreviewModel model) {
        this.model = model;
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        if (stage == null && shaderInitialized) {
            destroyShader();
        }
    }

    @Override
    public float getPrefWidth() {
        return width;
    }

    @Override
    public float getPrefHeight() {
        return height;
    }

    private void createShader(Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph) {
        try {
            timeKeeper = new DefaultTimeKeeper();
            shaderContext.setTimeProvider(timeKeeper);
            graphShader = GraphShaderBuilder.buildShader(WhitePixel.sharedInstance.texture, graph, true);
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
            createModel();

            shaderInitialized = true;
        } catch (Exception exp) {
            exp.printStackTrace();
            if (graphShader != null)
                graphShader.dispose();
        }
    }

    private void createModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        Material material = new Material(new GraphShaderAttribute());

        rectangleModel = modelBuilder.createRect(
                0, -0.5f, -0.5f,
                0, -0.5f, 0.5f,
                0, 0.5f, 0.5f,
                0, 0.5f, -0.5f,
                1, 0, 0,
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.Tangent | VertexAttributes.Usage.TextureCoordinates);
        rectangleShaderModel = new GraphShaderModel(new RandomIdGenerator(16), rectangleModel);
        rectangleModelInstance = rectangleShaderModel.createInstance(ModelInstanceOptimizationHints.unoptimized);

        sphereModel = modelBuilder.createSphere(1, 1, 1, 50, 50,
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.Tangent | VertexAttributes.Usage.TextureCoordinates);
        sphereShaderModel = new GraphShaderModel(new RandomIdGenerator(16), sphereModel);
        sphereModelInstance = sphereShaderModel.createInstance(ModelInstanceOptimizationHints.unoptimized);
    }

    private void destroyShader() {
        sphereModel.dispose();
        sphereShaderModel.dispose();
        rectangleModel.dispose();
        rectangleShaderModel.dispose();
        frameBuffer.dispose();
        frameBuffer = null;
        graphShader.dispose();
        shaderInitialized = false;
    }

    @Override
    public void dispose() {
        if (shaderInitialized)
            destroyShader();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (frameBuffer != null) {
            batch.end();

            timeKeeper.updateTime(Gdx.graphics.getDeltaTime());
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
            try {
                frameBuffer.begin();
                camera.viewportWidth = width;
                camera.viewportHeight = height;
                camera.update();

                graphShader.setTimeProvider(timeKeeper);

                renderContext.begin();
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                graphShader.begin(shaderContext, renderContext);
                if (model == ShaderPreviewModel.Sphere)
                    graphShader.render(shaderContext, sphereModelInstance);
                else if (model == ShaderPreviewModel.Rectangle)
                    graphShader.render(shaderContext, rectangleModelInstance);
                graphShader.end();
                frameBuffer.end();
                renderContext.end();
            } catch (Exception exp) {
                // Ignore
                exp.printStackTrace();
            } finally {
                if (ScissorStack.peekScissors() != null)
                    Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            }

            batch.begin();
            batch.draw(frameBuffer.getColorBufferTexture(), getX(), getY() + height, width, -height);
        }
    }

    public void graphChanged(boolean hasErrors, Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph) {
        if (hasErrors && shaderInitialized) {
            destroyShader();
        } else if (!hasErrors && !shaderInitialized) {
            createShader(graph);
        } else if (!hasErrors && shaderInitialized) {
            destroyShader();
            createShader(graph);
        }
    }
}
