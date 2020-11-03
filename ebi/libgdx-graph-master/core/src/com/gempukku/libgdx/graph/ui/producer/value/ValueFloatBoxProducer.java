package com.gempukku.libgdx.graph.ui.producer.value;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonValue;
import com.gempukku.libgdx.graph.data.FieldType;
import com.gempukku.libgdx.graph.data.NodeConfiguration;
import com.gempukku.libgdx.graph.ui.graph.GraphBox;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxImpl;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxOutputConnector;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxPartImpl;
import com.gempukku.libgdx.graph.ui.graph.GraphChangedEvent;
import com.kotcrab.vis.ui.util.Validators;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;


public class ValueFloatBoxProducer<T extends FieldType> extends ValueGraphBoxProducer<T> {
    public ValueFloatBoxProducer(NodeConfiguration<T> configuration) {
        super(configuration);
    }

    @Override
    public GraphBox<T> createPipelineGraphBox(Skin skin, String id, JsonValue data) {
        float v1 = data.getFloat("v1");

        return createGraphBox(skin, id, v1);
    }

    @Override
    public GraphBox<T> createDefault(Skin skin, String id) {
        return createGraphBox(skin, id, 0);
    }

    private GraphBox<T> createGraphBox(Skin skin, String id, float v1) {
        GraphBoxImpl<T> end = new GraphBoxImpl<T>(id, configuration, skin);
        end.addGraphBoxPart(createValuePart(skin, v1));

        return end;
    }

    private GraphBoxPartImpl<T> createValuePart(Skin skin, float v1) {
        final VisValidatableTextField v1Input = new VisValidatableTextField(Validators.FLOATS) {
            @Override
            public float getPrefWidth() {
                return 50;
            }
        };
        v1Input.setText(String.valueOf(v1));
        v1Input.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        v1Input.fire(new GraphChangedEvent(false, true));
                    }
                });

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.addActor(new Label("x", skin));
        horizontalGroup.addActor(v1Input);

        GraphBoxPartImpl<T> colorPart = new GraphBoxPartImpl<T>(horizontalGroup,
                new GraphBoxPartImpl.Callback() {
                    @Override
                    public void serialize(JsonValue object) {
                        object.addChild("v1", new JsonValue(Float.parseFloat(v1Input.getText())));
                    }
                });
        colorPart.setOutputConnector(GraphBoxOutputConnector.Side.Right, configuration.getNodeOutputs().get("value"));
        return colorPart;
    }
}
