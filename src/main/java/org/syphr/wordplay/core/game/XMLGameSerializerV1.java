package org.syphr.wordplay.core.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XMLGameSerializerV1 implements GameSerializer
{
    private final GameFactory factory;

    @Override
    public void serialize(Game game, OutputStream out) throws IOException
    {
        XMLStatesV1.write(XMLStatesV1.create(game), out);
    }

    @Override
    public Game deserialize(InputStream in) throws IOException
    {
        XMLStateV1 state = XMLStatesV1.create(XMLStatesV1.read(in));
        Game game = factory.create(state.getConfiguration());
        state.resumeGame(game);

        return game;
    }
}
