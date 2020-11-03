package com.gempukku.libgdx.graph.shader.config.math.utility;

import com.gempukku.libgdx.graph.NodeConfigurationImpl;
import com.gempukku.libgdx.graph.pipeline.loader.node.GraphNodeInputImpl;
import com.gempukku.libgdx.graph.pipeline.loader.node.GraphNodeOutputImpl;
import com.gempukku.libgdx.graph.shader.ShaderFieldType;

public class DistanceFromPlaneShaderNodeConfiguration extends NodeConfigurationImpl<ShaderFieldType> {
    public DistanceFromPlaneShaderNodeConfiguration() {
        super("DistancePlane", "Distance from plane", "Math/Utility");
        addNodeInput(
                new GraphNodeInputImpl<ShaderFieldType>("point", "Point", true, ShaderFieldType.Vector3));
        addNodeInput(
                new GraphNodeInputImpl<ShaderFieldType>("planePoint", "Point on plane", true, ShaderFieldType.Vector3));
        addNodeInput(
                new GraphNodeInputImpl<ShaderFieldType>("planeNormal", "Normal to plane", true, ShaderFieldType.Vector3));
        addNodeOutput(
                new GraphNodeOutputImpl<ShaderFieldType>("output", "Distance", ShaderFieldType.Float));
    }
}
