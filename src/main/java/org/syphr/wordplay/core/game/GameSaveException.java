package org.syphr.wordplay.core.game;

public class GameSaveException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = -1156770548984033274L;

    public GameSaveException()
    {
        super();
    }

    public GameSaveException(String message)
    {
        super(message);
    }

    public GameSaveException(Throwable cause)
    {
        super(cause);
    }

    public GameSaveException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GameSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
