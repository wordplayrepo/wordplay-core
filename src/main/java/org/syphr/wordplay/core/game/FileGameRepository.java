package org.syphr.wordplay.core.game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(path.toFile()))) {
            serializer.serialize(game, out);
            return game;
        } catch (IOException e) {
            throw new GameSaveException(e);
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
