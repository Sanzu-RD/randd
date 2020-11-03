package com.gempukku.libgdx.graph.shader.config.math.geometric;

import com.gempukku.libgdx.graph.NodeConfigurationImpl;
import com.gempukku.libgdx.graph.ValidateSameTypeOutputTypeFunction;
import com.gempukku.libgdx.graph.pipeline.loader.node.GraphNodeInputImpl;
import com.gempukku.libgdx.graph.pipeline.loader.node.GraphNodeOutputImpl;
import com.gempukku.libgdx.graph.shader.ShaderFieldType;

public class DotProductShaderNodeConfiguration extends NodeConfigurationImpl<ShaderFieldType> {
    public DotProductShaderNodeConfiguration() {
        super("DotProduct", "Dot product", "Math/Geometric");
        addNodeInput(
                new GraphNodeInputImpl<ShaderFieldType>("a", "A", true, ShaderFieldType.Color, ShaderFieldType.Vector3, ShaderFieldType.Vector2, ShaderFieldType.Float));
        addNodeInput(
                new GraphNodeInputImpl<ShaderFieldType>("b", "B", true, ShaderFieldType.Color, ShaderFieldType.Vector3, ShaderFieldType.Vector2, ShaderFieldType.Float));
        addNodeOutput(
                new GraphNodeOutputImpl<ShaderFieldType>("output", "Result",
                        new ValidateSameTypeOutputTypeFunction<ShaderFieldType>(ShaderFieldType.Float, "a", "b"),
                        ShaderFieldType.Float));
    }
}
