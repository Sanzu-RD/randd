package nodepmock.main;

import com.github.czyzby.lml.parser.LmlData;
import com.github.czyzby.lml.parser.impl.AbstractLmlParser;
import com.github.czyzby.lml.util.LmlParserBuilder;

public class SapphireLmlParserBuilder extends LmlParserBuilder {

    /** @param lmlData contains LML parsing data.
     * @return a new instance of an extension of {@link AbstractLmlParser}. */
    protected SapphireLmlParser getInstanceOfParser(final LmlData lmlData) {
        return new SapphireLmlParser(lmlData);
    }
    
}
