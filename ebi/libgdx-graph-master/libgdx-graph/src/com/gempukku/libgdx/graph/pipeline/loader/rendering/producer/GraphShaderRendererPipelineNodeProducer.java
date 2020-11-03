package com.gempukku.libgdx.graph.pipeline.loader.rendering.producer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.gempukku.libgdx.graph.GraphLoader;
import com.gempukku.libgdx.graph.WhitePixel;
import com.gempukku.libgdx.graph.pipeline.RenderPipeline;
import com.gempukku.libgdx.graph.pipeline.config.rendering.GraphShaderRendererPipelineNodeConfiguration;
import com.gempukku.libgdx.graph.pipeline.loader.PipelineRenderingContext;
import com.gempukku.libgdx.graph.pipeline.loader.node.OncePerFrameJobPipelineNode;
import com.gempukku.libgdx.graph.pipeline.loader.node.PipelineNode;
import com.gempukku.libgdx.graph.pipeline.loader.node.PipelineNodeProducerImpl;
import com.gempukku.libgdx.graph.shader.BasicShader;
import com.gempukku.libgdx.graph.shader.GraphShader;
import com.gempukku.libgdx.graph.shader.ShaderContext;
import com.gempukku.libgdx.graph.shader.ShaderLoaderCallback;
import com.gempukku.libgdx.graph.shader.environment.GraphShaderEnvironment;
import com.gempukku.libgdx.graph.shader.models.impl.GraphShaderModelInstance;
import com.gempukku.libgdx.graph.shader.models.impl.GraphShaderModelsImpl;

public class GraphShaderRendererPipelineNodeProducer extends PipelineNodeProducerImpl {
    public GraphShaderRendererPipelineNodeProducer() {
        super(new GraphShaderRendererPipelineNodeConfiguration());
    }

    @Override
    public PipelineNode createNodeForSingleInputs(JsonValue data, ObjectMap<String, PipelineNode.FieldOutput<?>> inputFields) {
        final WhitePixel whitePixel = new WhitePixel();

        final ShaderContextImpl shaderContext = new ShaderContextImpl();

        JsonValue renderPassDefinitions = data.get("renderPasses");

        final Array<RenderPass> renderPasses = new Array<>();
        for (JsonValue renderPassDefinition : renderPassDefinitions) {
            RenderPass renderPass = new RenderPass(renderPassDefinition, whitePixel);
            renderPasses.add(renderPass);
        }

        // From last pass to first - initialize RenderPasses with
        boolean needsDepthTexture = false;
        boolean needsColorTexture = false;
        for (int i = renderPasses.size - 1; i >= 0; i--) {
            RenderPass renderPass = renderPasses.get(i);
            needsDepthTexture = renderPass.initialize(needsDepthTexture);
            needsColorTexture |= renderPass.isRequiringSceneColor();
        }

        final PipelineNode.FieldOutput<GraphShaderEnvironment> lightsInput = (PipelineNode.FieldOutput<GraphShaderEnvironment>) inputFields.get("lights");
        final PipelineNode.FieldOutput<Camera> cameraInput = (PipelineNode.FieldOutput<Camera>) inputFields.get("camera");
        final PipelineNode.FieldOutput<RenderPipeline> renderPipelineInput = (PipelineNode.FieldOutput<RenderPipeline>) inputFields.get("input");

        final boolean finalNeedsDepthTexture = needsDepthTexture;
        final boolean finalNeedsColorTexture = needsColorTexture;
        return new OncePerFrameJobPipelineNode(configuration, inputFields) {
            private final RenderContext renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.LRU, 1));

            @Override
            protected void executeJob(PipelineRenderingContext pipelineRenderingContext, ObjectMap<String, ? extends OutputValue> outputValues) {
                RenderPipeline renderPipeline = renderPipelineInput.getValue(pipelineRenderingContext);
                GraphShaderModelsImpl models = pipelineRenderingContext.getGraphShaderModels();
                Camera camera = cameraInput.getValue(pipelineRenderingContext);
                FrameBuffer currentBuffer = renderPipeline.getCurrentBuffer();
                int width = currentBuffer.getWidth();
                int height = currentBuffer.getHeight();
                float viewportWidth = camera.viewportWidth;
                float viewportHeight = camera.viewportHeight;
                if (width != viewportWidth || height != viewportHeight) {
                    camera.viewportWidth = width;
                    camera.viewportHeight = height;
                    camera.update();
                }
                GraphShaderEnvironment environment = lightsInput != null ? lightsInput.getValue(pipelineRenderingContext) : null;
                models.prepareForRendering(camera);

                shaderContext.setCamera(camera);
                shaderContext.setGraphShaderEnvironment(environment);
                shaderContext.setTimeProvider(pipelineRenderingContext.getTimeProvider());
                FrameBuffer depthFrameBuffer = null;
                FrameBuffer sceneColorBuffer = null;

                if (finalNeedsDepthTexture) {
                    depthFrameBuffer = renderPipeline.getDepthFrameBuffer();
                    shaderContext.setDepthTexture(depthFrameBuffer.getColorBufferTexture());
                }
                if (finalNeedsColorTexture) {
                    sceneColorBuffer = renderPipeline.getSceneColorBuffer();
                    shaderContext.setColorTexture(sceneColorBuffer.getColorBufferTexture());
                }

                renderContext.begin();
                currentBuffer.begin();

                for (int i = 0; i < renderPasses.size; i++) {
                    RenderPass renderPass = renderPasses.get(i);
                    if (renderPass.isRequiringSceneColor()) {
                        currentBuffer.end();
                        renderPipeline.getBufferCopyHelper().copy(currentBuffer, sceneColorBuffer);
                        currentBuffer.begin();
                    }

                    Array<GraphShader> opaqueShaders = renderPass.getOpaqueShaders();
                    Array<GraphShader> depthShaders = renderPass.getDepthShaders();
                    Array<GraphShader> transparentShaders = renderPass.getTransparentShaders();
                    // Initialize shaders for drawing
                    for (GraphShader shader : opaqueShaders) {
                        shader.setTimeProvider(pipelineRenderingContext.getTimeProvider());
                        shader.setEnvironment(environment);
                    }
                    for (GraphShader shader : depthShaders) {
                        shader.setTimeProvider(pipelineRenderingContext.getTimeProvider());
                        shader.setEnvironment(environment);
                    }
                    for (GraphShader shader : transparentShaders) {
                        shader.setTimeProvider(pipelineRenderingContext.getTimeProvider());
                        shader.setEnvironment(environment);
                    }

                    // First render opaque models
                    if (!opaqueShaders.isEmpty()) {
                        models.orderFrontToBack();
                        for (GraphShader shader : opaqueShaders) {
                            String tag = shader.getTag();
                            renderWithShaderOpaquePass(tag, shader, models, shaderContext);
                        }
                    }

                    // Then render transparent models
                    if (!transparentShaders.isEmpty()) {
                        models.orderBackToFront();
                        GraphShader lastShader = null;
                        for (GraphShaderModelInstance graphShaderModelInstance : models.getModels()) {
                            for (GraphShader shader : transparentShaders) {
                                if (graphShaderModelInstance.hasTag(shader.getTag())) {
                                    if (lastShader != shader) {
                                        if (lastShader != null)
                                            lastShader.end();
                                        shader.begin(shaderContext, renderContext);
                                    }
                                    shader.render(shaderContext, graphShaderModelInstance);
                                    lastShader = shader;
                                }
                            }
                        }
                        if (lastShader != null)
                            lastShader.end();
                    }

                    // Then if depth buffer is needed for later passes - draw to it
                    if (!depthShaders.isEmpty()) {
                        currentBuffer.end();
                        depthFrameBuffer.begin();

                        models.orderFrontToBack();
                        for (GraphShader shader : depthShaders) {
                            String tag = shader.getTag();
                            renderWithShaderOpaquePass(tag, shader, models, shaderContext);
                        }

                        depthFrameBuffer.end();
                        currentBuffer.begin();
                    }
                }

                currentBuffer.end();
                renderContext.end();
                OutputValue<RenderPipeline> output = outputValues.get("output");
                if (output != null)
                    output.setValue(renderPipeline);
            }

            private void renderWithShaderOpaquePass(String tag, GraphShader shader, GraphShaderModelsImpl models, ShaderContext shaderContext) {
                boolean begun = false;
                for (GraphShaderModelInstance graphShaderModelInstance : models.getModels()) {
                    if (graphShaderModelInstance.hasTag(tag)) {
                        if (!begun) {
                            shader.begin(shaderContext, renderContext);
                            begun = true;
                        }
                        shader.render(shaderContext, graphShaderModelInstance);
                    }
                }
                if (begun)
                    shader.end();
            }

            @Override
            public void dispose() {
                for (RenderPass renderPass : renderPasses) {
                    renderPass.dispose();
                }
                whitePixel.dispose();
            }
        };
    }

    private static GraphShader createColorShader(JsonValue shaderDefinition, Texture defaultTexture) {
        JsonValue shaderGraph = shaderDefinition.get("shader");
        return GraphLoader.loadGraph(shaderGraph, new ShaderLoaderCallback(defaultTexture));
    }

    private static GraphShader createDepthShader(JsonValue shaderDefinition, Texture defaultTexture) {
        JsonValue shaderGraph = shaderDefinition.get("shader");
        return GraphLoader.loadGraph(shaderGraph, new ShaderLoaderCallback(defaultTexture, true));
    }

    private class RenderPass implements Disposable {
        private boolean requiringSceneColor;
        private Array<ShaderGroup> shaderGroups = new Array<>();

        private Array<GraphShader> opaqueShaders = new Array<>();
        private Array<GraphShader> transparentShaders = new Array<>();

        private Array<GraphShader> depthShaders = new Array<>();

        public RenderPass(JsonValue data, WhitePixel whitePixel) {
            final JsonValue shaderDefinitions = data.get("shaders");
            for (JsonValue shaderDefinition : shaderDefinitions) {
                ShaderGroup shaderGroup = new ShaderGroup(shaderDefinition, whitePixel);
                shaderGroups.add(shaderGroup);
            }
        }

        public boolean initialize(final boolean depthShaderNeeded) {
            boolean depthNeededForPass = depthShaderNeeded;
            for (ShaderGroup shaderGroup : shaderGroups) {
                GraphShader shader = shaderGroup.createColorShader();
                if (shader.isUsingColorTexture())
                    requiringSceneColor = true;
                depthNeededForPass |= shader.isUsingDepthTexture();
                if (shader.getTransparency() == BasicShader.Transparency.opaque) {
                    opaqueShaders.add(shader);
                    if (depthShaderNeeded)
                        depthShaders.add(shaderGroup.createDepthShader());
                } else {
                    transparentShaders.add(shader);
                }
            }
            return depthNeededForPass;
        }

        public Array<GraphShader> getOpaqueShaders() {
            return opaqueShaders;
        }

        public Array<GraphShader> getDepthShaders() {
            return depthShaders;
        }

        public Array<GraphShader> getTransparentShaders() {
            return transparentShaders;
        }

        public boolean isRequiringSceneColor() {
            return requiringSceneColor;
        }

        @Override
        public void dispose() {
            for (GraphShader shader : opaqueShaders) {
                shader.dispose();
            }
            for (GraphShader depthShader : depthShaders) {
                depthShader.dispose();
            }
            for (GraphShader shader : transparentShaders) {
                shader.dispose();
            }
        }
    }

    private class ShaderGroup {
        private JsonValue shaderDefinition;
        private WhitePixel whitePixel;
        private final String tag;

        public ShaderGroup(JsonValue shaderDefinition, WhitePixel whitePixel) {
            this.shaderDefinition = shaderDefinition;
            this.whitePixel = whitePixel;
            this.tag = shaderDefinition.getString("tag");
        }

        public GraphShader createColorShader() {
            GraphShader colorShader = GraphShaderRendererPipelineNodeProducer.createColorShader(shaderDefinition, whitePixel.texture);
            colorShader.setTag(tag);
            return colorShader;
        }

        public GraphShader createDepthShader() {
            GraphShader depthShader = GraphShaderRendererPipelineNodeProducer.createDepthShader(shaderDefinition, whitePixel.texture);
            depthShader.setTag(tag);
            return depthShader;
        }
    }
}
