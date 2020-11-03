package com.gempukku.libgdx.graph.pipeline.loader;

import com.gempukku.libgdx.graph.TimeProvider;
import com.gempukku.libgdx.graph.pipeline.PipelinePropertySource;
import com.gempukku.libgdx.graph.shader.models.impl.GraphShaderModelsImpl;

public interface PipelineRenderingContext {
    int getRenderWidth();

    int getRenderHeight();

    GraphShaderModelsImpl getGraphShaderModels();

    PipelinePropertySource getPipelinePropertySource();

    TimeProvider getTimeProvider();
}
