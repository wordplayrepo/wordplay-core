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
package org.syphr.wordplay.core.game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.config.Configurations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractGameFactory implements GameFactory
{
    @Override
    public Game create()
    {
        return create((Configuration) null);
    }

    @Override
    public Game create(File configurationFile) throws IOException
    {
        log.trace("Creating new game from configuration at \"{}\"", configurationFile.getAbsolutePath());
        return create(Configurations.create(Configurations.read(configurationFile)));
    }

    @Override
    public Game create(InputStream configurationStream) throws IOException
    {
        log.trace("Creating game from configuration stream");
        return create(Configurations.create(Configurations.read(configurationStream)));
    }

    @Override
    public abstract Game create(Configuration configuration);
}
