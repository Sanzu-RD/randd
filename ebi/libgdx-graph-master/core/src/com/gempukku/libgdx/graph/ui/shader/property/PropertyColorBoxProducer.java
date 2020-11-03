package com.gempukku.libgdx.graph.ui.shader.property;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.gempukku.libgdx.graph.shader.ShaderFieldType;
import com.gempukku.libgdx.graph.ui.graph.GraphChangedEvent;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyBox;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyBoxImpl;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyBoxProducer;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyDefaultBox;
import com.kotcrab.vis.ui.widget.color.ColorPicker;
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter;


public class PropertyColorBoxProducer implements PropertyBoxProducer<ShaderFieldType> {
    @Override
    public ShaderFieldType getType() {
        return ShaderFieldType.Color;
    }

    @Override
    public PropertyBox<ShaderFieldType> createPropertyBox(Skin skin, String name, JsonValue jsonObject) {
        String color = jsonObject.getString("color");
        return createPropertyBoxDefault(skin, name, color);
    }

    @Override
    public PropertyBox<ShaderFieldType> createDefaultPropertyBox(Skin skin) {
        return createPropertyBoxDefault(skin, "New Color", "FFFFFFFF");
    }

    private PropertyBox<ShaderFieldType> createPropertyBoxDefault(Skin skin, String name, String colorStr) {
        Color color = Color.valueOf(colorStr);

        final TextureRegionDrawable drawable = new TextureRegionDrawable(WhitePixel.sharedInstance.texture);
        final BaseDrawable baseDrawable = new BaseDrawable(drawable) {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {
                Color oldColor = new Color(batch.getColor());
                batch.setColor(Color.WHITE);
                drawable.draw(batch, x - 1, y - 1, width + 2, height + 2);
                batch.setColor(oldColor);
                drawable.draw(batch, x, y, width, height);
            }
        };
        baseDrawable.setPadding(1, 1, 1, 1);
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

        final Table table = new Table();
        table.add(new Label("Default color", skin)).growX();
        table.add(image);

        return new PropertyBoxImpl<ShaderFieldType>(skin,
                name,
                ShaderFieldType.Color,
                new PropertyDefaultBox() {
                    @Override
                    public Actor getActor() {
                        return table;
                    }

                    @Override
                    public JsonValue serializeData() {
                        JsonValue result = new JsonValue(JsonValue.ValueType.object);
                        result.addChild("color", new JsonValue(image.getColor().toString()));
                        return result;
                    }
                }) {
            @Override
            public void dispose() {
                picker.dispose();
            }
        };
    }
}
