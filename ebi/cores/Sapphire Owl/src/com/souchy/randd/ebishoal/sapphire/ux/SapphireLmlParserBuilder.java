package com.souchy.randd.ebishoal.sapphire.ux;

import com.github.czyzby.lml.parser.LmlData;
import com.github.czyzby.lml.parser.impl.AbstractLmlParser;
import com.github.czyzby.lml.util.LmlParserBuilder;
import com.github.czyzby.lml.vis.parser.impl.VisLmlSyntax;
import com.kotcrab.vis.ui.VisUI;

public class SapphireLmlParserBuilder extends LmlParserBuilder {

    /** @param lmlData contains LML parsing data.
     * @return a new instance of an extension of {@link AbstractLmlParser}. */
    protected AbstractLmlParser getInstanceOfParser(final LmlData lmlData) {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }
        lmlData.setDefaultSkin(VisUI.getSkin());
        return new SapphireLmlParser(lmlData, new VisLmlSyntax());
    }
    
}
