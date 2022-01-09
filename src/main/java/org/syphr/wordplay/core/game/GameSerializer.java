package org.syphr.wordplay.core.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface GameSerializer
{
    void serialize(Game game, OutputStream out) throws IOException;

    Game deserialize(InputStream in) throws IOException;
}
