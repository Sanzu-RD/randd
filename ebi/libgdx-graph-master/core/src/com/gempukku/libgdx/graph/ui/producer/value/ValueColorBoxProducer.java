package com.gempukku.libgdx.graph.ui.producer.value;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonValue;
import com.gempukku.libgdx.graph.WhitePixel;
import com.gempukku.libgdx.graph.data.FieldType;
import com.gempukku.libgdx.graph.data.NodeConfiguration;
import com.gempukku.libgdx.graph.ui.graph.GraphBox;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxImpl;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxOutputConnector;
import com.gempukku.libgdx.graph.ui.graph.GraphBoxPartImpl;
import com.gempukku.libgdx.graph.ui.graph.GraphChangedEvent;
import com.kotcrab.vis.ui.widget.color.ColorPicker;
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter;


public class ValueColorBoxProducer<T extends FieldType> extends ValueGraphBoxProducer<T> {
    public ValueColorBoxProducer(NodeConfiguration<T> configuration) {
        super(configuration);
    }

    @Override
    public GraphBox<T> createPipelineGraphBox(Skin skin, String id, JsonValue data) {
        String value = data.getString("color");

        return createGraphBox(skin, id, value);
    }

    @Override
    public GraphBox<T> createDefault(Skin skin, String id) {
        return createGraphBox(skin, id, "FFFFFFFF");
    }

    private GraphBox<T> createGraphBox(Skin skin, String id, String value) {
        GraphBoxImpl<T> end = new GraphBoxImpl<T>(id, configuration, skin);
        end.addGraphBoxPart(createValuePart(skin, value));

        return end;
    }

    private GraphBoxPartImpl<T> createValuePart(Skin skin, String value) {
        Color color = Color.valueOf(value);

        final TextureRegionDrawable drawable = new TextureRegionDrawable(WhitePixel.sharedInstance.texture);
        BaseDrawable baseDrawable = new BaseDrawable(drawable) {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {
                drawable.draw(batch, x, y, width, height);
            }
        };
        baseDrawable.setMinSize(20, 20);

        final Image image = new Image(baseDrawable);
        image.setColor(color);

        final ColorPicker picker = new ColorPicker(new ColorPickerAdapter() {
            @Override
            public void finished(Color newColor) {
                image.setColor(newColor);
                image.fire(new GraphChangedEvent(false, true));
            }
        });
        picker.setColor(color);

        image.addListener(
                new ClickListener(Input.Buttons.LEFT) {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //displaying picker with fade in animation
                        image.getStage().addActor(picker.fadeIn());
                    }
                });


        Table table = new Table();
        table.add(new Label("Color", skin)).growX();
        table.add(image);
        table.row();

        GraphBoxPartImpl<T> colorPart = new GraphBoxPartImpl<T>(table,
                new GraphBoxPartImpl.Callback() {
                    @Override
                    public void serialize(JsonValue object) {
                        object.addChild("color", new JsonValue(image.getColor().toString()));
                    }
                }) {
            @Override
            public void dispose() {
                picker.dispose();
            }
        };
        colorPart.setOutputConnector(GraphBoxOutputConnector.Side.Right, configuration.getNodeOutputs().get("value"));
        return colorPart;
    }
}
