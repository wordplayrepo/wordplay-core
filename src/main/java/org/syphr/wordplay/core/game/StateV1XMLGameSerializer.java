package org.syphr.wordplay.core.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateV1XMLGameSerializer implements GameSerializer
{
    private final GameFactory factory;

    @Override
    public void serialize(Game game, OutputStream out) throws IOException
    {
        States.write(game.getState(), out);
    }

    @Override
    public Game deserialize(InputStream in) throws IOException
    {
        return factory.load(States.create(States.read(in)));
    }
}
