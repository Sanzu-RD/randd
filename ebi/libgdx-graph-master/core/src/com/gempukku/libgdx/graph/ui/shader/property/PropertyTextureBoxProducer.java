package com.gempukku.libgdx.graph.ui.shader.property;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.JsonValue;
import com.gempukku.libgdx.graph.shader.ShaderFieldType;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyBox;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyBoxImpl;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyBoxProducer;
import com.gempukku.libgdx.graph.ui.graph.property.PropertyDefaultBox;


public class PropertyTextureBoxProducer implements PropertyBoxProducer<ShaderFieldType> {
    @Override
    public ShaderFieldType getType() {
        return ShaderFieldType.TextureRegion;
    }

    @Override
    public PropertyBox<ShaderFieldType> createPropertyBox(Skin skin, String name, JsonValue jsonObject) {
        return createPropertyBoxDefault(skin, name);
    }

    @Override
    public PropertyBox<ShaderFieldType> createDefaultPropertyBox(Skin skin) {
        return createPropertyBoxDefault(skin, "New Texture");
    }

    private PropertyBox<ShaderFieldType> createPropertyBoxDefault(Skin skin, String name) {
        final Table table = new Table();

        return new PropertyBoxImpl<ShaderFieldType>(skin,
                name,
                ShaderFieldType.TextureRegion,
                new PropertyDefaultBox() {
                    @Override
                    public Actor getActor() {
                        return table;
                    }

                    @Override
                    public JsonValue serializeData() {
                        return null;
                    }
                });
    }
}
