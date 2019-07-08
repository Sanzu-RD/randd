package com.souchy.randd.ebishoal.sapphire.ui.roundImage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.tag.AbstractNonParentalActorLmlTag;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;

/** Handles {@link RoundImage} actor. Cannot have children. Expects drawable name in style attribute. Mapped to "roundImage".
 *
 * @author Blank */
public class RoundImageLmlTag extends AbstractNonParentalActorLmlTag {
    public RoundImageLmlTag(final LmlParser parser, final LmlTag parentTag, final StringBuilder rawTagData) {
        super(parser, parentTag, rawTagData);
    }

    @Override
    protected Actor getNewInstanceOfActor(final LmlActorBuilder builder) {
    	//var drawable = getSkin(builder).getDrawable(builder.getStyleName());
    	var drawable = getSkin(builder).optional(builder.getStyleName(), TextureRegionDrawable.class);
        return new RoundImage(drawable); //new TextureRegionDrawable(drawable));
    }
}