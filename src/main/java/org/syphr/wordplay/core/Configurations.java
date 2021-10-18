package org.syphr.wordplay.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.impl.JaxbFactory;
import org.syphr.wordplay.core.impl.XmlV1Configuration;

public class Configurations
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Configurations.class);

    private static final JaxbFactory<org.syphr.wordplay.xsd.v1.Configuration> FACTORY = JaxbFactory.create(org.syphr.wordplay.xsd.v1.Configuration.class);

    // TODO - this is hack
    public static org.syphr.wordplay.xsd.v1.Configuration toV1BaseConfig(Configuration config)
    {
        if (!(config instanceof XmlV1Configuration))
        {
            throw new IllegalArgumentException("Expected "
                                               + XmlV1Configuration.class
                                               + " but received "
                                               + config.getClass());
        }

        return ((XmlV1Configuration)config).getExternalConfig();
    }

    public static org.syphr.wordplay.xsd.v1.Configuration read(File file) throws IOException
    {
        LOGGER.trace("Reading configuration base from \"{}\"", file.getAbsolutePath());
        return FACTORY.read(file);
    }

    public static org.syphr.wordplay.xsd.v1.Configuration read(InputStream in) throws IOException
    {
        LOGGER.trace("Reading configuration base from a stream");
        return FACTORY.read(in);
    }

    public static Configuration create(org.syphr.wordplay.xsd.v1.Configuration baseConfig)
    {
        LOGGER.trace("Creating configuration from a base configuration");
        return new XmlV1Configuration(baseConfig);
    }

    public static void write(XmlV1Configuration configuration, File file) throws IOException
    {
        LOGGER.trace("Writing configuration to \"{}\"", file.getAbsolutePath());
        write(configuration.getExternalConfig(), file);
    }

    public static void write(XmlV1Configuration configuration, OutputStream out) throws IOException
    {
        LOGGER.trace("Writing configuration to a stream");
        write(configuration.getExternalConfig(), out);
    }

    public static void write(org.syphr.wordplay.xsd.v1.Configuration baseConfig,
                             File file) throws IOException
    {
        LOGGER.trace("Writing configuration base to \"{}\"", file.getAbsolutePath());
        FACTORY.write(baseConfig, file);
    }

    public static void write(org.syphr.wordplay.xsd.v1.Configuration baseConfig,
                             OutputStream out) throws IOException
    {
        LOGGER.trace("Writing configuration base to a stream");
        FACTORY.write(baseConfig, out);
    }

    private Configurations()
    {
        /*
         * Factory pattern
         */
    }
}
