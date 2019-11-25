package com.souchy.randd.deathshadows.opal.freemarker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerConfigurationFactory;
import org.glassfish.jersey.server.mvc.spi.AbstractTemplateProcessor;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FTLViewProcessor extends AbstractTemplateProcessor<Template> {

    private final FreemarkerConfigurationFactory factory;

    /**
     * Create an instance of this processor with injected {@link javax.ws.rs.core.Configuration config} and
     * (optional) {@link javax.servlet.ServletContext servlet context}.
     *
     * @param config           config to configure this processor from.
     * @param injectionManager injection manager.
     */
    @Inject
    public FTLViewProcessor(javax.ws.rs.core.Configuration config, InjectionManager injectionManager) {
        super(config, injectionManager.getInstance(ServletContext.class), "freemarker", "ftl");

        this.factory = getTemplateObjectFactory(injectionManager::createAndInitialize, FreemarkerConfigurationFactory.class,
                () -> {
                    Configuration configuration =
                            getTemplateObjectFactory(injectionManager::createAndInitialize, Configuration.class, Values.empty());
                    if (configuration == null) {
                        return new FTLDefaultConfigurationFactory(injectionManager.getInstance(ServletContext.class));
                    } else {
                        return new FTLSuppliedConfigurationFactory(configuration);
                    }
                });
    }

    @Override
    protected Template resolve(final String templateReference, final Reader reader) throws Exception {
    	System.out.println("templateReference : " + templateReference);
        return factory.getConfiguration().getTemplate(templateReference);
    }

    @Override
    public void writeTo(final Template template, final Viewable viewable, final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders, final OutputStream out) throws IOException {
        try {
            Object model = viewable.getModel();
            if (!(model instanceof Map)) {
                model = new HashMap<String, Object>() {{
                    put("model", viewable.getModel());
                }};
            }
            Charset encoding = setContentType(mediaType, httpHeaders);

            template.process(model, new OutputStreamWriter(out, encoding));
        } catch (TemplateException te) {
            throw new org.glassfish.jersey.server.ContainerException(te);
        }
    }
}