package com.gempukku.libgdx.graph.pipeline.loader.rendering.producer;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.gempukku.libgdx.graph.pipeline.RenderPipeline;
import com.gempukku.libgdx.graph.pipeline.config.rendering.UIRendererPipelineNodeConfiguration;
import com.gempukku.libgdx.graph.pipeline.loader.PipelineRenderingContext;
import com.gempukku.libgdx.graph.pipeline.loader.node.OncePerFrameJobPipelineNode;
import com.gempukku.libgdx.graph.pipeline.loader.node.PipelineNode;
import com.gempukku.libgdx.graph.pipeline.loader.node.PipelineNodeProducerImpl;

public class UIRendererPipelineNodeProducer extends PipelineNodeProducerImpl {
    public UIRendererPipelineNodeProducer() {
        super(new UIRendererPipelineNodeConfiguration());
    }

    @Override
    public PipelineNode createNodeForSingleInputs(JsonValue data, ObjectMap<String, PipelineNode.FieldOutput<?>> inputFields) {
        final PipelineNode.FieldOutput<Stage> stageInput = (PipelineNode.FieldOutput<Stage>) inputFields.get("stage");
        final PipelineNode.FieldOutput<RenderPipeline> renderPipelineInput = (PipelineNode.FieldOutput<RenderPipeline>) inputFields.get("input");
        return new OncePerFrameJobPipelineNode(configuration, inputFields) {
            @Override
            protected void executeJob(PipelineRenderingContext pipelineRenderingContext, ObjectMap<String, ? extends OutputValue> outputValues) {
                RenderPipeline renderPipeline = renderPipelineInput.getValue(pipelineRenderingContext);
                Stage stage = stageInput.getValue(pipelineRenderingContext);
                if (stage != null) {
                    FrameBuffer currentBuffer = renderPipeline.getCurrentBuffer();
                    int width = currentBuffer.getWidth();
                    int height = currentBuffer.getHeight();
                    int screenWidth = stage.getViewport().getScreenWidth();
                    int screenHeight = stage.getViewport().getScreenHeight();
                    if (screenWidth != width || screenHeight != height)
                        stage.getViewport().update(width, height, true);
                    currentBuffer.begin();
                    stage.draw();
                    currentBuffer.end();
                }
                OutputValue output = outputValues.get("output");
                if (output != null)
                    output.setValue(renderPipeline);
            }
        };
    }
}
