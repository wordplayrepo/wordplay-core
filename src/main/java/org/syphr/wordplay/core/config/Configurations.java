/*
 * Copyright © 2012-2022 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.wordplay.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.xml.JaxbFactory;

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
