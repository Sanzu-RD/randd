package com.gempukku.libgdx.graph.pipeline.loader.postprocessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.IndexBufferObject;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.gempukku.libgdx.graph.pipeline.RenderPipeline;
import com.gempukku.libgdx.graph.pipeline.config.postprocessor.GaussianBlurPipelineNodeConfiguration;
import com.gempukku.libgdx.graph.pipeline.loader.FloatFieldOutput;
import com.gempukku.libgdx.graph.pipeline.loader.PipelineRenderingContext;
import com.gempukku.libgdx.graph.pipeline.loader.node.OncePerFrameJobPipelineNode;
import com.gempukku.libgdx.graph.pipeline.loader.node.PipelineNode;
import com.gempukku.libgdx.graph.pipeline.loader.node.PipelineNodeProducerImpl;

public class GaussianBlurPipelineNodeProducer extends PipelineNodeProducerImpl {
    public GaussianBlurPipelineNodeProducer() {
        super(new GaussianBlurPipelineNodeConfiguration());
    }

    @Override
    public PipelineNode createNodeForSingleInputs(JsonValue data, ObjectMap<String, PipelineNode.FieldOutput<?>> inputFields) {
        final ShaderProgram shaderProgram = new ShaderProgram(
                Gdx.files.classpath("shader/viewToScreenCoords.vert"),
                Gdx.files.classpath("shader/gaussianBlur.frag"));
        if (!shaderProgram.isCompiled())
            throw new IllegalArgumentException("Error compiling shader: " + shaderProgram.getLog());

        float[] verticeData = new float[]{
                0, 0, 0,
                0, 1, 0,
                1, 0, 0,
                1, 1, 0};
        short[] indices = {0, 1, 2, 2, 1, 3};

        final VertexBufferObject vertexBufferObject = new VertexBufferObject(true, 4, VertexAttribute.Position());
        final IndexBufferObject indexBufferObject = new IndexBufferObject(true, indices.length);
        vertexBufferObject.setVertices(verticeData, 0, verticeData.length);
        indexBufferObject.setIndices(indices, 0, indices.length);

        PipelineNode.FieldOutput<Float> blurRadius = (PipelineNode.FieldOutput<Float>) inputFields.get("blurRadius");
        if (blurRadius == null)
            blurRadius = new FloatFieldOutput(0f);
        final PipelineNode.FieldOutput<RenderPipeline> renderPipelineInput = (PipelineNode.FieldOutput<RenderPipeline>) inputFields.get("input");

        final PipelineNode.FieldOutput<Float> finalBlurRadius = blurRadius;
        return new OncePerFrameJobPipelineNode(configuration, inputFields) {
            @Override
            protected void executeJob(PipelineRenderingContext pipelineRenderingContext, ObjectMap<String, ? extends OutputValue> outputValues) {
                RenderPipeline renderPipeline = renderPipelineInput.getValue(pipelineRenderingContext);

                int blurRadius = MathUtils.round(finalBlurRadius.getValue(pipelineRenderingContext));
                if (blurRadius > 0) {
                    float[] kernel = GaussianBlurKernel.getKernel(blurRadius);
                    FrameBuffer currentBuffer = renderPipeline.getCurrentBuffer();

                    shaderProgram.bind();
                    shaderProgram.setUniformi("u_sourceTexture", 0);
                    shaderProgram.setUniformi("u_blurRadius", blurRadius);
                    shaderProgram.setUniformf("u_pixelSize", 1f / currentBuffer.getWidth(), 1f / currentBuffer.getHeight());
                    shaderProgram.setUniform1fv("u_kernel", kernel, 0, kernel.length);

                    vertexBufferObject.bind(shaderProgram);
                    indexBufferObject.bind();

                    shaderProgram.setUniformi("u_vertical", 1);
                    FrameBuffer tempBuffer = executeBlur(renderPipeline, currentBuffer, indexBufferObject);
                    renderPipeline.returnFrameBuffer(currentBuffer);
                    shaderProgram.setUniformi("u_vertical", 0);
                    FrameBuffer finalBuffer = executeBlur(renderPipeline, tempBuffer, indexBufferObject);
                    renderPipeline.returnFrameBuffer(tempBuffer);
                    renderPipeline.setCurrentBuffer(finalBuffer);

                    indexBufferObject.unbind();
                    vertexBufferObject.unbind(shaderProgram);
                }

                OutputValue<RenderPipeline> output = outputValues.get("output");
                if (output != null)
                    output.setValue(renderPipeline);
            }

            @Override
            public void dispose() {
                vertexBufferObject.dispose();
                indexBufferObject.dispose();
                shaderProgram.dispose();
            }
        };
    }

    private FrameBuffer executeBlur(RenderPipeline renderPipeline, FrameBuffer sourceBuffer, IndexBufferObject indexBufferObject) {

        FrameBuffer resultBuffer = renderPipeline.getNewFrameBuffer(sourceBuffer);
        resultBuffer.begin();

        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        Gdx.gl.glBindTexture(GL20.GL_TEXTURE_2D, sourceBuffer.getColorBufferTexture().getTextureObjectHandle());

        Gdx.gl20.glDrawElements(Gdx.gl20.GL_TRIANGLES, indexBufferObject.getNumIndices(), GL20.GL_UNSIGNED_SHORT, 0);

        resultBuffer.end();

        return resultBuffer;
    }
}
