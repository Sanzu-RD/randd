package com.souchy.randd.deathshadows.opal.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerConfigurationFactory;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerDefaultConfigurationFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;

public class FTLDefaultConfigurationFactory implements FreemarkerConfigurationFactory {

    protected final Configuration configuration;

    public FTLDefaultConfigurationFactory(ServletContext servletContext) {
        super();

        // Create different loaders.
        final List<TemplateLoader> loaders = new ArrayList<>();
        if (servletContext != null) {
            loaders.add(new WebappTemplateLoader(servletContext));
        }
        loaders.add(new ClassTemplateLoader(FreemarkerDefaultConfigurationFactory.class, "/"));
        try {
        	System.out.println("default file template loader : " + new File("").getAbsoluteFile());
        	
            loaders.add(new FileTemplateLoader(new File("").getAbsoluteFile()));
        } catch (IOException e) {
            // NOOP
        }

        // Create Base configuration.
        configuration = new Configuration();
        configuration.setTemplateLoader(new MultiTemplateLoader(loaders.toArray(new TemplateLoader[loaders.size()])));
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}