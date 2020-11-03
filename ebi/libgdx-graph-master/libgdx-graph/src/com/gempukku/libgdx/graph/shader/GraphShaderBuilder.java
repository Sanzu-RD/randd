package com.gempukku.libgdx.graph.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.gempukku.libgdx.graph.LibGDXCollections;
import com.gempukku.libgdx.graph.data.Graph;
import com.gempukku.libgdx.graph.data.GraphConnection;
import com.gempukku.libgdx.graph.data.GraphNode;
import com.gempukku.libgdx.graph.data.GraphNodeInput;
import com.gempukku.libgdx.graph.data.GraphProperty;
import com.gempukku.libgdx.graph.shader.builder.FragmentShaderBuilder;
import com.gempukku.libgdx.graph.shader.builder.GLSLFragmentReader;
import com.gempukku.libgdx.graph.shader.builder.VertexShaderBuilder;
import com.gempukku.libgdx.graph.shader.node.GraphShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.attribute.AttributePositionShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.CameraPositionShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.property.GraphShaderPropertyProducer;

public class GraphShaderBuilder {
    public static GraphShader buildShader(Texture defaultTexture,
                                          Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph,
                                          boolean designTime) {
        GraphShader graphShader = new GraphShader(defaultTexture);

        ObjectMap<String, PropertySource> propertyMap = new ObjectMap<>();
        for (GraphProperty<ShaderFieldType> property : graph.getProperties()) {
            String name = property.getName();
            propertyMap.put(name, findPropertyProducerByType(property.getType()).createProperty(name, property.getData(), designTime));
        }

        VertexShaderBuilder vertexShaderBuilder = new VertexShaderBuilder(graphShader);
        FragmentShaderBuilder fragmentShaderBuilder = new FragmentShaderBuilder(graphShader);

        initializeShaders(vertexShaderBuilder, fragmentShaderBuilder);

        graphShader.setPropertySourceMap(propertyMap);

        GraphNode<ShaderFieldType> endNode = graph.getNodeById("end");
        JsonValue data = endNode.getData();

        graphShader.setCulling(BasicShader.Culling.valueOf(data.getString("culling")));
        graphShader.setTransparency(BasicShader.Transparency.valueOf(data.getString("transparency")));
        graphShader.setBlending(BasicShader.Blending.valueOf(data.getString("blending")));
        graphShader.setDepthTesting(BasicShader.DepthTesting.valueOf((data.getString("depthTest")).replace(' ', '_')));

        buildVertexShader(graph, designTime, graphShader, vertexShaderBuilder, fragmentShaderBuilder);
        buildFragmentShader(graph, designTime, graphShader, vertexShaderBuilder, fragmentShaderBuilder);

        String vertexShader = vertexShaderBuilder.buildProgram();
        String fragmentShader = fragmentShaderBuilder.buildProgram();

        Gdx.app.debug("Shader", "--------------");
        Gdx.app.debug("Shader", "Vertex color shader:");
        Gdx.app.debug("Shader", "--------------");
        Gdx.app.debug("Shader", "\n" + vertexShader);
        Gdx.app.debug("Shader", "----------------");
        Gdx.app.debug("Shader", "Fragment color shader:");
        Gdx.app.debug("Shader", "----------------");
        Gdx.app.debug("Shader", "\n" + fragmentShader);

        ShaderProgram shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        graphShader.setProgram(shaderProgram);
        graphShader.init();

        return graphShader;
    }

    public static GraphShader buildDepthShader(Texture defaultTexture,
                                               Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph,
                                               boolean designTime) {
        GraphShader graphShader = new GraphShader(defaultTexture);

        ObjectMap<String, PropertySource> propertyMap = new ObjectMap<>();
        for (GraphProperty<ShaderFieldType> property : graph.getProperties()) {
            String name = property.getName();
            propertyMap.put(name, findPropertyProducerByType(property.getType()).createProperty(name, property.getData(), designTime));
        }

        VertexShaderBuilder vertexShaderBuilder = new VertexShaderBuilder(graphShader);
        FragmentShaderBuilder fragmentShaderBuilder = new FragmentShaderBuilder(graphShader);

        initializeShaders(vertexShaderBuilder, fragmentShaderBuilder);

        graphShader.setPropertySourceMap(propertyMap);

        GraphNode<ShaderFieldType> endNode = graph.getNodeById("end");
        JsonValue data = endNode.getData();

        graphShader.setCulling(BasicShader.Culling.valueOf(data.getString("culling")));
        graphShader.setTransparency(BasicShader.Transparency.valueOf(data.getString("transparency")));
        graphShader.setBlending(BasicShader.Blending.valueOf(data.getString("blending")));
        graphShader.setDepthTesting(BasicShader.DepthTesting.valueOf((data.getString("depthTest")).replace(' ', '_')));

        buildVertexShader(graph, designTime, graphShader, vertexShaderBuilder, fragmentShaderBuilder);

        fragmentShaderBuilder.addUniformVariable("u_cameraClipping", "vec2", true, UniformSetters.cameraClipping);

        AttributePositionShaderNodeBuilder position = new AttributePositionShaderNodeBuilder();
        JsonValue positionData = new JsonValue(JsonValue.ValueType.object);
        positionData.addChild("coordinates", new JsonValue("world"));
        GraphShaderNodeBuilder.FieldOutput positionField = position.buildFragmentNodeSingleInputs(false, "defaultPositionAttribute", positionData, LibGDXCollections.<String, GraphShaderNodeBuilder.FieldOutput>emptyMap(),
                LibGDXCollections.singleton("position"), vertexShaderBuilder, fragmentShaderBuilder, graphShader, graphShader).get("position");

        CameraPositionShaderNodeBuilder cameraPosition = new CameraPositionShaderNodeBuilder();
        GraphShaderNodeBuilder.FieldOutput cameraPositionField = cameraPosition.buildFragmentNodeSingleInputs(false, "cameraPosition", null, LibGDXCollections.<String, GraphShaderNodeBuilder.FieldOutput>emptyMap(),
                LibGDXCollections.singleton("position"), vertexShaderBuilder, fragmentShaderBuilder, graphShader, graphShader).get("position");

        fragmentShaderBuilder.addFunction("packFloatToVec3", GLSLFragmentReader.getFragment("packFloatToVec3"));

        ObjectMap<String, ObjectMap<String, GraphShaderNodeBuilder.FieldOutput>> fragmentNodeOutputs = new ObjectMap<>();
        GraphShaderNodeBuilder.FieldOutput alphaField = getOutput(findInputVertices(graph, "end", "alpha"),
                designTime, true, graph, graphShader, graphShader, fragmentNodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
        String alpha = (alphaField != null) ? alphaField.getRepresentation() : "1.0";
        GraphShaderNodeBuilder.FieldOutput alphaClipField = getOutput(findInputVertices(graph, "end", "alphaClip"),
                designTime, true, graph, graphShader, graphShader, fragmentNodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
        String alphaClip = (alphaClipField != null) ? alphaClipField.getRepresentation() : "0.0";
        if (alphaField != null || alphaClipField != null) {
            fragmentShaderBuilder.addMainLine("// End Graph Node");
            fragmentShaderBuilder.addMainLine("if (" + alpha + " <= " + alphaClip + ")");
            fragmentShaderBuilder.addMainLine("  discard;");
        }
        fragmentShaderBuilder.addMainLine("gl_FragColor = vec4(packFloatToVec3(distance(" + positionField.getRepresentation() + ", " + cameraPositionField.getRepresentation() + ")), 1.0);");


        String vertexShader = vertexShaderBuilder.buildProgram();
        String fragmentShader = fragmentShaderBuilder.buildProgram();

        Gdx.app.debug("Shader", "--------------");
        Gdx.app.debug("Shader", "Vertex depth shader:");
        Gdx.app.debug("Shader", "--------------");
        Gdx.app.debug("Shader", "\n" + vertexShader);
        Gdx.app.debug("Shader", "----------------");
        Gdx.app.debug("Shader", "Fragment depth shader:");
        Gdx.app.debug("Shader", "----------------");
        Gdx.app.debug("Shader", "\n" + fragmentShader);

        ShaderProgram shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        graphShader.setProgram(shaderProgram);
        graphShader.init();

        return graphShader;
    }

    private static void buildFragmentShader(Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph, boolean designTime, GraphShader graphShader, VertexShaderBuilder vertexShaderBuilder, FragmentShaderBuilder fragmentShaderBuilder) {
        // Fragment part
        ObjectMap<String, ObjectMap<String, GraphShaderNodeBuilder.FieldOutput>> fragmentNodeOutputs = new ObjectMap<>();
        GraphShaderNodeBuilder.FieldOutput alphaField = getOutput(findInputVertices(graph, "end", "alpha"),
                designTime, true, graph, graphShader, graphShader, fragmentNodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
        String alpha = (alphaField != null) ? alphaField.getRepresentation() : "1.0";
        GraphShaderNodeBuilder.FieldOutput alphaClipField = getOutput(findInputVertices(graph, "end", "alphaClip"),
                designTime, true, graph, graphShader, graphShader, fragmentNodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
        String alphaClip = (alphaClipField != null) ? alphaClipField.getRepresentation() : "0.0";
        if (alphaField != null || alphaClipField != null) {
            fragmentShaderBuilder.addMainLine("// End Graph Node");
            fragmentShaderBuilder.addMainLine("if (" + alpha + " <= " + alphaClip + ")");
            fragmentShaderBuilder.addMainLine("  discard;");
        }

        GraphShaderNodeBuilder.FieldOutput colorField = getOutput(findInputVertices(graph, "end", "color"),
                designTime, true, graph, graphShader, graphShader, fragmentNodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
        String color;
        if (colorField == null) {
            color = "vec4(1.0, 1.0, 1.0, " + alpha + ")";
        } else if (colorField.getFieldType() == ShaderFieldType.Color) {
            color = "vec4(" + colorField.getRepresentation() + ".rgb, " + alpha + ")";
        } else {
            color = "vec4(" + colorField.getRepresentation() + ", " + alpha + ")";
        }
        fragmentShaderBuilder.addMainLine("// End Graph Node");
        fragmentShaderBuilder.addMainLine("gl_FragColor = " + color + ";");
    }

    private static void buildVertexShader(Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph, boolean designTime, GraphShader graphShader, VertexShaderBuilder vertexShaderBuilder, FragmentShaderBuilder fragmentShaderBuilder) {
        // Vertex part
        int boneCount = GraphShaderConfig.getMaxNumberOfBonesPerMesh();
        int boneWeightCount = GraphShaderConfig.getMaxNumberOfBoneWeights();
        vertexShaderBuilder.addArrayUniformVariable("u_bones", boneCount, "mat4", false, new UniformSetters.Bones(boneCount));
        for (int i = 0; i < boneWeightCount; i++) {
            vertexShaderBuilder.addAttributeVariable("a_boneWeight" + i, "vec2");
        }
        StringBuilder getSkinning = new StringBuilder();
        getSkinning.append("mat4 getSkinning() {\n");
        getSkinning.append("  mat4 skinning = mat4(0.0);\n");
        for (int i = 0; i < boneWeightCount; i++) {
            getSkinning.append("  skinning += (a_boneWeight").append(i).append(".y) * u_bones[int(a_boneWeight").append(i).append(".x)];\n");
        }
        getSkinning.append("  return skinning;\n");
        getSkinning.append("}\n");

        vertexShaderBuilder.addFunction("getSkinning", getSkinning.toString());

        vertexShaderBuilder.addMainLine("mat4 skinning = getSkinning();");

        ObjectMap<String, ObjectMap<String, GraphShaderNodeBuilder.FieldOutput>> vertexNodeOutputs = new ObjectMap<>();
        GraphShaderNodeBuilder.FieldOutput positionField = getOutput(findInputVertices(graph, "end", "position"),
                designTime, false, graph, graphShader, graphShader, vertexNodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
        if (positionField == null) {
            AttributePositionShaderNodeBuilder position = new AttributePositionShaderNodeBuilder();
            JsonValue positionData = new JsonValue(JsonValue.ValueType.object);
            positionData.addChild("coordinates", new JsonValue("world"));
            positionField = position.buildVertexNodeSingleInputs(false, "defaultPositionAttribute", positionData, LibGDXCollections.<String, GraphShaderNodeBuilder.FieldOutput>emptyMap(),
                    LibGDXCollections.singleton("position"), vertexShaderBuilder, graphShader, graphShader).get("position");
        }
        vertexShaderBuilder.addUniformVariable("u_projViewTrans", "mat4", true, UniformSetters.projViewTrans);
        String worldPosition = "vec4(" + positionField.getRepresentation() + ", 1.0)";
        vertexShaderBuilder.addMainLine("// End Graph Node");
        vertexShaderBuilder.addMainLine("gl_Position = u_projViewTrans * " + worldPosition + ";");
    }

    private static GraphShaderNodeBuilder.FieldOutput getOutput(Array<GraphConnection> connections,
                                                                boolean designTime, boolean fragmentShader,
                                                                Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph,
                                                                GraphShaderContext context, GraphShader graphShader, ObjectMap<String, ObjectMap<String, GraphShaderNodeBuilder.FieldOutput>> nodeOutputs,
                                                                VertexShaderBuilder vertexShaderBuilder, FragmentShaderBuilder fragmentShaderBuilder) {
        if (connections == null || connections.size == 0)
            return null;
        GraphConnection connection = connections.get(0);
        ObjectMap<String, ? extends GraphShaderNodeBuilder.FieldOutput> output = buildNode(designTime, fragmentShader, graph, context, graphShader, connection.getNodeFrom(), nodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
        return output.get(connection.getFieldFrom());
    }


    private static void initializeShaders(VertexShaderBuilder vertexShaderBuilder, FragmentShaderBuilder fragmentShaderBuilder) {
        vertexShaderBuilder.addInitialLine("#ifdef GL_ES");
        vertexShaderBuilder.addInitialLine("#define LOWP lowp");
        vertexShaderBuilder.addInitialLine("#define MED mediump");
        vertexShaderBuilder.addInitialLine("#define HIGH highp");
        vertexShaderBuilder.addInitialLine("precision mediump float;");
        vertexShaderBuilder.addInitialLine("#else");
        vertexShaderBuilder.addInitialLine("#define MED");
        vertexShaderBuilder.addInitialLine("#define LOWP");
        vertexShaderBuilder.addInitialLine("#define HIGH");
        vertexShaderBuilder.addInitialLine("#endif");

        fragmentShaderBuilder.addInitialLine("#ifdef GL_ES");
        fragmentShaderBuilder.addInitialLine("#define LOWP lowp");
        fragmentShaderBuilder.addInitialLine("#define MED mediump");
        fragmentShaderBuilder.addInitialLine("#define HIGH highp");
        fragmentShaderBuilder.addInitialLine("precision mediump float;");
        fragmentShaderBuilder.addInitialLine("#else");
        fragmentShaderBuilder.addInitialLine("#define MED");
        fragmentShaderBuilder.addInitialLine("#define LOWP");
        fragmentShaderBuilder.addInitialLine("#define HIGH");
        fragmentShaderBuilder.addInitialLine("#endif");
    }

    private static ObjectMap<String, GraphShaderNodeBuilder.FieldOutput> buildNode(
            boolean designTime, boolean fragmentShader,
            Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph,
            GraphShaderContext context, GraphShader graphShader, String nodeId, ObjectMap<String, ObjectMap<String, GraphShaderNodeBuilder.FieldOutput>> nodeOutputs,
            VertexShaderBuilder vertexShaderBuilder, FragmentShaderBuilder fragmentShaderBuilder) {
        ObjectMap<String, GraphShaderNodeBuilder.FieldOutput> nodeOutput = nodeOutputs.get(nodeId);
        if (nodeOutput == null) {
            GraphNode<ShaderFieldType> nodeInfo = graph.getNodeById(nodeId);
            String nodeInfoType = nodeInfo.getType();
            GraphShaderNodeBuilder nodeBuilder = GraphShaderConfiguration.graphShaderNodeBuilders.get(nodeInfoType);
            if (nodeBuilder == null)
                throw new IllegalStateException("Unable to find graph shader node builder for type: " + nodeInfoType);
            ObjectMap<String, Array<GraphShaderNodeBuilder.FieldOutput>> inputFields = new ObjectMap<>();
            for (GraphNodeInput<ShaderFieldType> nodeInput : new ObjectMap.Values<>(nodeBuilder.getConfiguration(nodeInfo.getData()).getNodeInputs())) {
                String fieldId = nodeInput.getFieldId();
                Array<GraphConnection> vertexInfos = findInputVertices(graph, nodeId, fieldId);
                if (vertexInfos.size == 0 && nodeInput.isRequired())
                    throw new IllegalStateException("Required input not provided");
                Array<ShaderFieldType> fieldTypes = new Array<>();
                Array<GraphShaderNodeBuilder.FieldOutput> fieldOutputs = new Array<>();
                for (GraphConnection vertexInfo : vertexInfos) {
                    ObjectMap<String, GraphShaderNodeBuilder.FieldOutput> output = buildNode(designTime, fragmentShader, graph, context, graphShader, vertexInfo.getNodeFrom(), nodeOutputs, vertexShaderBuilder, fragmentShaderBuilder);
                    GraphShaderNodeBuilder.FieldOutput fieldOutput = output.get(vertexInfo.getFieldFrom());
                    ShaderFieldType fieldType = fieldOutput.getFieldType();
                    fieldTypes.add(fieldType);
                    fieldOutputs.add(fieldOutput);
                }
                if (!nodeInput.acceptsInputTypes(fieldTypes))
                    throw new IllegalStateException("Producer produces a field of value not compatible with consumer");
                inputFields.put(fieldId, fieldOutputs);
            }
            ObjectSet<String> requiredOutputs = findRequiredOutputs(graph, nodeId);
            if (fragmentShader) {
                nodeOutput = (ObjectMap<String, GraphShaderNodeBuilder.FieldOutput>) nodeBuilder.buildFragmentNode(designTime, nodeId, nodeInfo.getData(), inputFields, requiredOutputs, vertexShaderBuilder, fragmentShaderBuilder, context, graphShader);
            } else {
                nodeOutput = (ObjectMap<String, GraphShaderNodeBuilder.FieldOutput>) nodeBuilder.buildVertexNode(designTime, nodeId, nodeInfo.getData(), inputFields, requiredOutputs, vertexShaderBuilder, context, graphShader);
            }
            nodeOutputs.put(nodeId, nodeOutput);
        }

        return nodeOutput;
    }

    private static ObjectSet<String> findRequiredOutputs(Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph,
                                                         String nodeId) {
        ObjectSet<String> result = new ObjectSet<>();
        for (GraphConnection vertex : graph.getConnections()) {
            if (vertex.getNodeFrom().equals(nodeId))
                result.add(vertex.getFieldFrom());
        }
        return result;
    }

    private static Array<GraphConnection> findInputVertices(Graph<? extends GraphNode<ShaderFieldType>, ? extends GraphConnection, ? extends GraphProperty<ShaderFieldType>, ShaderFieldType> graph,
                                                            String nodeId, String nodeField) {
        Array<GraphConnection> result = new Array<>();
        for (GraphConnection vertex : graph.getConnections()) {
            if (vertex.getNodeTo().equals(nodeId) && vertex.getFieldTo().equals(nodeField))
                result.add(vertex);
        }
        return result;
    }

    private static GraphShaderPropertyProducer findPropertyProducerByType(ShaderFieldType type) {
        for (GraphShaderPropertyProducer graphShaderPropertyProducer : GraphShaderConfiguration.graphShaderPropertyProducers) {
            if (graphShaderPropertyProducer.getType() == type)
                return graphShaderPropertyProducer;
        }
        return null;
    }
}
