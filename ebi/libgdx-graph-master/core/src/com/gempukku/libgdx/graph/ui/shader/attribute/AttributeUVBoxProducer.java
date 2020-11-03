package com.gempukku.libgdx.graph.ui.shader.attribute;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonValue;
import com.gempukku.libgdx.graph.data.GraphNodeInput;
import com.gempukku.libgdx.graph.data.GraphNodeOutput;
import com.gempukku.libgdx.graph.data.NodeConfiguration;
import com.gempukku.libgdx.graph.shader.ShaderFieldType;
import com.gempukku.libgdx.graph.shader.config.attribute.AttributeUVShaderNodeConfiguration;
import com.gempukku.libgdx.graph.ui.graph.GraphBox;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxImpl;
import com.gempukku.libgdx.graph.ui.part.SelectBoxPart;
import com.gempukku.libgdx.graph.ui.producer.GraphBoxProducer;

import java.util.Iterator;

public class AttributeUVBoxProducer implements GraphBoxProducer<ShaderFieldType> {
    private NodeConfiguration<ShaderFieldType> configuration = new AttributeUVShaderNodeConfiguration();

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
    public GraphBox<ShaderFieldType> createPipelineGraphBox(Skin skin, String id, JsonValue data) {
        GraphBoxImpl<ShaderFieldType> result = new GraphBoxImpl<ShaderFieldType>(id, configuration, skin);
        addInputsAndOutputs(result, skin);
        SelectBoxPart<ShaderFieldType> attributeUV = new SelectBoxPart<>(skin, "Channel", "channel", "UV0", "UV1", "UV2", "UV3");
        attributeUV.initialize(data);
        result.addGraphBoxPart(attributeUV);
        return result;
    }

    @Override
    public GraphBox<ShaderFieldType> createDefault(Skin skin, String id) {
        return createPipelineGraphBox(skin, id, null);
    }

    private void addInputsAndOutputs(GraphBoxImpl<ShaderFieldType> graphBox, Skin skin) {
        Iterator<GraphNodeInput<ShaderFieldType>> inputIterator = configuration.getNodeInputs().values().iterator();
        Iterator<GraphNodeOutput<ShaderFieldType>> outputIterator = configuration.getNodeOutputs().values().iterator();
        while (inputIterator.hasNext() || outputIterator.hasNext()) {
            GraphNodeInput<ShaderFieldType> input = null;
            GraphNodeOutput<ShaderFieldType> output = null;
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
}