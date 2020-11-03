package com.gempukku.libgdx.graph.ui.producer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonValue;
import com.gempukku.libgdx.graph.data.FieldType;
import com.gempukku.libgdx.graph.data.GraphNodeInput;
import com.gempukku.libgdx.graph.data.GraphNodeOutput;
import com.gempukku.libgdx.graph.data.NodeConfiguration;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxImpl;

import java.util.Iterator;

public class GraphBoxProducerImpl<T extends FieldType> implements GraphBoxProducer<T> {
    private NodeConfiguration<T> configuration;

    public GraphBoxProducerImpl(NodeConfiguration<T> configuration) {
        this.configuration = configuration;
    }

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
    public GraphBoxImpl<T> createPipelineGraphBox(Skin skin, String id, JsonValue data) {
        GraphBoxImpl<T> start = new GraphBoxImpl<T>(id, configuration, skin);
        Iterator<GraphNodeInput<T>> inputIterator = configuration.getNodeInputs().values().iterator();
        Iterator<GraphNodeOutput<T>> outputIterator = configuration.getNodeOutputs().values().iterator();
        while (inputIterator.hasNext() || outputIterator.hasNext()) {
            GraphNodeInput<T> input = null;
            GraphNodeOutput<T> output = null;
            while (inputIterator.hasNext()) {
                input = inputIterator.next();
                if (input.isMainConnection()) {
                    start.addTopConnector(input);
                    input = null;
                } else {
                    break;
                }
            }
            while (outputIterator.hasNext()) {
                output = outputIterator.next();
                if (output.isMainConnection()) {
                    start.addBottomConnector(output);
                    output = null;
                } else {
                    break;
                }
            }

            if (input != null && output != null) {
                start.addTwoSideGraphPart(skin, input, output);
            } else if (input != null) {
                start.addInputGraphPart(skin, input);
            } else if (output != null) {
                start.addOutputGraphPart(skin, output);
            }
        }

        return start;
    }

    @Override
    public GraphBoxImpl<T> createDefault(Skin skin, String id) {
        return createPipelineGraphBox(skin, id, null);
    }
}
