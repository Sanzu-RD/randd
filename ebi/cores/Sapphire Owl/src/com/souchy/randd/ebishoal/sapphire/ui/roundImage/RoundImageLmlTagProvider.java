package com.souchy.randd.ebishoal.sapphire.ui.roundImage;

import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;

/** Provides RoundImage tags.
*
* @author Blank */
public class RoundImageLmlTagProvider implements LmlTagProvider {
   @Override
   public LmlTag create(final LmlParser parser, final LmlTag parentTag, final StringBuilder rawTagData) {
       return new RoundImageLmlTag(parser, parentTag, rawTagData);
   }
}