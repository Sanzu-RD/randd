package com.gempukku.libgdx.graph;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.gempukku.libgdx.graph.data.FieldType;
import com.gempukku.libgdx.graph.data.Graph;
import com.gempukku.libgdx.graph.data.GraphConnection;
import com.gempukku.libgdx.graph.data.GraphNode;
import com.gempukku.libgdx.graph.data.GraphProperty;
import com.gempukku.libgdx.graph.data.NodeConfiguration;

public abstract class GraphDataLoaderCallback<T, U extends FieldType> implements GraphLoaderCallback<T>, Graph<GraphNode<U>, GraphConnection, GraphProperty<U>, U> {
    private ObjectMap<String, GraphNodeData<U>> graphNodes = new ObjectMap<>();
    private Array<GraphConnectionData> graphConnections = new Array<>();
    private ObjectMap<String, GraphPropertyData<U>> graphProperties = new ObjectMap<>();

    @Override
    public void addPipelineNode(String id, String type, float x, float y, JsonValue data) {
        graphNodes.put(id, new GraphNodeData<U>(id, type, getNodeConfiguration(type, data), data));
    }

    @Override
    public void addPipelineVertex(String fromNode, String fromField, String toNode, String toField) {
        graphConnections.add(new GraphConnectionData(fromNode, fromField, toNode, toField));
    }

    @Override
    public void addPipelineProperty(String type, String name, JsonValue data) {
        graphProperties.put(name, new GraphPropertyData<U>(name, getFieldType(type), data));
    }

    @Override
    public void addNodeGroup(String name, ObjectSet<String> nodeIds) {
        // Ignore - used only in UI
    }

    @Override
    public GraphNodeData<U> getNodeById(String id) {
        return graphNodes.get(id);
    }

    @Override
    public GraphPropertyData<U> getPropertyByName(String name) {
        return graphProperties.get(name);
    }

    @Override
    public Iterable<? extends GraphConnectionData> getConnections() {
        return graphConnections;
    }

    @Override
    public Iterable<? extends GraphNodeData<U>> getNodes() {
        return graphNodes.values();
    }

    @Override
    public Iterable<? extends GraphPropertyData<U>> getProperties() {
        return graphProperties.values();
    }

    protected abstract U getFieldType(String type);

    protected abstract NodeConfiguration<U> getNodeConfiguration(String type, JsonValue data);

    private static class GraphNodeData<T extends FieldType> implements GraphNode<T> {
        private String id;
        private String type;
        private NodeConfiguration<T> configuration;
        private JsonValue data;

        public GraphNodeData(String id, String type, NodeConfiguration<T> configuration, JsonValue data) {
            this.id = id;
            this.type = type;
            this.configuration = configuration;
            this.data = data;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public boolean isInputField(String fieldId) {
            return configuration.getNodeInputs().containsKey(fieldId);
        }

        @Override
        public JsonValue getData() {
            return data;
        }

        @Override
        public NodeConfiguration<T> getConfiguration() {
            return configuration;
        }
    }

    private static class GraphPropertyData<T extends FieldType> implements GraphProperty<T> {
        private String name;
        private T type;
        private JsonValue data;

        public GraphPropertyData(String name, T type, JsonValue data) {
            this.name = name;
            this.type = type;
            this.data = data;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public T getType() {
            return type;
        }

        @Override
        public JsonValue getData() {
            return data;
        }
    }

    private static class GraphConnectionData implements GraphConnection {
        private String nodeFrom;
        private String fieldFrom;
        private String nodeTo;
        private String fieldTo;

        public GraphConnectionData(String nodeFrom, String fieldFrom, String nodeTo, String fieldTo) {
            this.nodeFrom = nodeFrom;
            this.fieldFrom = fieldFrom;
            this.nodeTo = nodeTo;
            this.fieldTo = fieldTo;
        }

        @Override
        public String getNodeFrom() {
            return nodeFrom;
        }

        @Override
        public String getFieldFrom() {
            return fieldFrom;
        }

        @Override
        public String getNodeTo() {
            return nodeTo;
        }

        @Override
        public String getFieldTo() {
            return fieldTo;
        }
    }
}
