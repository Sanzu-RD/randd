package com.gempukku.libgdx.graph.ui.pipeline.shader;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonValue;
import com.gempukku.libgdx.graph.data.GraphNodeInput;
import com.gempukku.libgdx.graph.data.GraphNodeOutput;
import com.gempukku.libgdx.graph.data.NodeConfiguration;
import com.gempukku.libgdx.graph.pipeline.PipelineFieldType;
import com.gempukku.libgdx.graph.pipeline.config.rendering.GraphShaderRendererPipelineNodeConfiguration;
import com.gempukku.libgdx.graph.ui.graph.GraphBox;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxImpl;
import com.gempukku.libgdx.graph.ui.producer.GraphBoxProducer;

import java.util.Iterator;

public class GraphShaderRendererBoxProducer implements GraphBoxProducer<PipelineFieldType> {
    private NodeConfiguration<PipelineFieldType> configuration = new GraphShaderRendererPipelineNodeConfiguration();

    @Override
    public String getType() {
        return configuration.getType();
    }

    @Override
    public boolean isCloseable() {
        return true;
    }

    @Override
    public String getName() {
        return configuration.getName();
    }

    @Override
    public String getMenuLocation() {
        return configuration.getMenuLocation();
    }

    @Override
    public GraphBox<PipelineFieldType> createPipelineGraphBox(Skin skin, String id, JsonValue data) {
        GraphBoxImpl<PipelineFieldType> result = new GraphBoxImpl<PipelineFieldType>(id, configuration, skin);
        addInputsAndOutputs(result, skin);
        GraphShadersBoxPart graphBoxPart = new GraphShadersBoxPart(skin);
        graphBoxPart.initialize(data);
        result.addGraphBoxPart(graphBoxPart);
        return result;
    }

    private void addInputsAndOutputs(GraphBoxImpl<PipelineFieldType> graphBox, Skin skin) {
        Iterator<GraphNodeInput<PipelineFieldType>> inputIterator = configuration.getNodeInputs().values().iterator();
        Iterator<GraphNodeOutput<PipelineFieldType>> outputIterator = configuration.getNodeOutputs().values().iterator();
        while (inputIterator.hasNext() || outputIterator.hasNext()) {
            GraphNodeInput<PipelineFieldType> input = null;
            GraphNodeOutput<PipelineFieldType> output = null;
            while (inputIterator.hasNext()) {
                input = inputIterator.next();
                if (input.isMainConnection()) {
                    graphBox.addTopConnector(input);
                    input = null;
                } else {
                    break;
                }
            }
            while (outputIterator.hasNext()) {
                output = outputIterator.next();
                if (output.isMainConnection()) {
                    graphBox.addBottomConnector(output);
                    output = null;
                } else {
                    break;
                }
            }

            if (input != null && output != null) {
                graphBox.addTwoSideGraphPart(skin, input, output);
            } else if (input != null) {
                graphBox.addInputGraphPart(skin, input);
            } else if (output != null) {
                graphBox.addOutputGraphPart(skin, output);
            }
        }
    }

    @Override
    public GraphBox<PipelineFieldType> createDefault(Skin skin, String id) {
        GraphBoxImpl<PipelineFieldType> result = new GraphBoxImpl<PipelineFieldType>(id, configuration, skin);
        addInputsAndOutputs(result, skin);
        result.addGraphBoxPart(new GraphShadersBoxPart(skin));
        return result;
    }
}
