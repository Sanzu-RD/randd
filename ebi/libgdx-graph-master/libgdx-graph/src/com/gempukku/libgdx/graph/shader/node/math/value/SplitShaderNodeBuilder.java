package com.gempukku.libgdx.graph.shader.node.math.value;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.gempukku.libgdx.graph.shader.GraphShader;
import com.gempukku.libgdx.graph.shader.GraphShaderContext;
import com.gempukku.libgdx.graph.shader.ShaderFieldType;
import com.gempukku.libgdx.graph.shader.builder.CommonShaderBuilder;
import com.gempukku.libgdx.graph.shader.config.math.value.SplitShaderNodeConfiguration;
import com.gempukku.libgdx.graph.shader.node.ConfigurationCommonShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.DefaultFieldOutput;

public class SplitShaderNodeBuilder extends ConfigurationCommonShaderNodeBuilder {
    public SplitShaderNodeBuilder() {
        super(new SplitShaderNodeConfiguration());
    }

    @Override
    protected ObjectMap<String, ? extends FieldOutput> buildCommonNode(boolean designTime, String nodeId, JsonValue data, ObjectMap<String, FieldOutput> inputs, ObjectSet<String> producedOutputs,
                                                                       CommonShaderBuilder commonShaderBuilder, GraphShaderContext graphShaderContext, GraphShader graphShader) {
        FieldOutput inputValue = inputs.get("input");
        ShaderFieldType fieldType = inputValue.getFieldType();

        String x, y, z, w;
        if (fieldType == ShaderFieldType.Color) {
            x = inputValue.getRepresentation() + ".r";
            y = inputValue.getRepresentation() + ".g";
            z = inputValue.getRepresentation() + ".b";
            w = inputValue.getRepresentation() + ".a";
        } else if (fieldType == ShaderFieldType.Vector3) {
            x = inputValue.getRepresentation() + ".x";
            y = inputValue.getRepresentation() + ".y";
            z = inputValue.getRepresentation() + ".z";
            w = "0.0";
        } else if (fieldType == ShaderFieldType.Vector2) {
            x = inputValue.getRepresentation() + ".x";
            y = inputValue.getRepresentation() + ".y";
            z = "0.0";
            w = "0.0";
        } else {
            throw new UnsupportedOperationException();
        }

        ObjectMap<String, DefaultFieldOutput> result = new ObjectMap<>();
        if (producedOutputs.contains("x")) {
            result.put("x", new DefaultFieldOutput(ShaderFieldType.Float, x));
        }
        if (producedOutputs.contains("y")) {
            result.put("y", new DefaultFieldOutput(ShaderFieldType.Float, y));
        }
        if (producedOutputs.contains("z")) {
            result.put("z", new DefaultFieldOutput(ShaderFieldType.Float, z));
        }
        if (producedOutputs.contains("w")) {
            result.put("w", new DefaultFieldOutput(ShaderFieldType.Float, w));
        }
        return result;
    }
}
