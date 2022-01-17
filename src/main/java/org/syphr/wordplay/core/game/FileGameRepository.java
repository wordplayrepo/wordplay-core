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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FileGameRepository implements GameRepository
{
    private final Path path;
    private final GameSerializer serializer;

    @Override
    public Game save(Game game) throws GameSaveException
    {
        log.trace("Saving game to \"{}\"", path.toAbsolutePath());

        try {
            validatePath();
        } catch (IOException e) {
            throw new GameSaveException(e);
        }

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(path.toFile()))) {
            serializer.serialize(game, out);
            return game;
        } catch (IOException e) {
            throw new GameSaveException(e);
        }
    }

    private void validatePath() throws IOException
    {
        Path parentDir = path.getParent();
        if (!Files.isDirectory(parentDir)) {
            Files.createDirectories(parentDir);
        }

        if (!Files.isWritable(parentDir)) {
            throw new IOException("Unable to write to " + path.toAbsolutePath());
        }
    }

    @Override
    public Game load(UUID id) throws GameLoadException
    {
        log.trace("Loading game from \"{}\"", path.toAbsolutePath());

        try (InputStream in = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            return serializer.deserialize(in);
        } catch (IOException e) {
            throw new GameLoadException(e);
        }
    }
}
