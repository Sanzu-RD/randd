package com.gempukku.libgdx.graph.shader;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.gempukku.libgdx.graph.shader.models.impl.GraphShaderModelInstance;

public interface UniformRegistry {
    void registerAttribute(final String alias);

    void registerUniform(final String alias, final boolean global, final UniformSetter setter);

    void registerStructArrayUniform(final String alias, String[] fieldNames, final boolean global, StructArrayUniformSetter setter);

    interface UniformSetter {
        void set(final BasicShader shader, final int location, ShaderContext shaderContext, GraphShaderModelInstance graphShaderModelInstance, Renderable renderable);
    }

    interface StructArrayUniformSetter {
        void set(final BasicShader shader, final int startingLocation, int[] fieldOffsets, int structSize, ShaderContext shaderContext, GraphShaderModelInstance graphShaderModelInstance, Renderable renderable);
    }
}
