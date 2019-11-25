package com.souchy.randd.deathshadows.opal.freemarker;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerConfigurationFactory;

import freemarker.template.Configuration;

public class FTLSuppliedConfigurationFactory implements FreemarkerConfigurationFactory {

    private final Configuration configuration;

    public FTLSuppliedConfigurationFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}