package com.gempukku.libgdx.graph.shader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.gempukku.libgdx.graph.GraphDataLoaderCallback;
import com.gempukku.libgdx.graph.data.GraphConnection;
import com.gempukku.libgdx.graph.data.GraphNode;
import com.gempukku.libgdx.graph.data.GraphProperty;
import com.gempukku.libgdx.graph.data.GraphValidator;
import com.gempukku.libgdx.graph.data.NodeConfiguration;

public class ShaderLoaderCallback extends GraphDataLoaderCallback<GraphShader, ShaderFieldType> {
    private Texture defaultTexture;
    private boolean depthShader;

    public ShaderLoaderCallback(Texture defaultTexture) {
        this(defaultTexture, false);
    }

    public ShaderLoaderCallback(Texture defaultTexture, boolean depthShader) {
        this.defaultTexture = defaultTexture;
        this.depthShader = depthShader;
    }

    @Override
    public void start() {

    }

    @Override
    public GraphShader end() {
        GraphValidator<GraphNode<ShaderFieldType>, GraphConnection, GraphProperty<ShaderFieldType>, ShaderFieldType> graphValidator = new GraphValidator<>();
        GraphValidator.ValidationResult<GraphNode<ShaderFieldType>, GraphConnection, GraphProperty<ShaderFieldType>, ShaderFieldType> result = graphValidator.validateGraph(this, "end");
        if (result.hasErrors())
            throw new IllegalStateException("The graph contains errors, open it in the graph designer and correct them");

        if (depthShader)
            return GraphShaderBuilder.buildDepthShader(defaultTexture, this, false);
        else
            return GraphShaderBuilder.buildShader(defaultTexture, this, false);
    }

    @Override
    protected ShaderFieldType getFieldType(String type) {
        return ShaderFieldType.valueOf(type);
    }

    @Override
    protected NodeConfiguration<ShaderFieldType> getNodeConfiguration(String type, JsonValue data) {
        return GraphShaderConfiguration.graphShaderNodeBuilders.get(type).getConfiguration(data);
    }
}
