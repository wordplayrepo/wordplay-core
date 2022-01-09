package org.syphr.wordplay.core.game;

public class GameLoadException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = -4321787532903226732L;

    public GameLoadException()
    {
        super();
    }

    public GameLoadException(String message)
    {
        super(message);
    }

    public GameLoadException(Throwable cause)
    {
        super(cause);
    }

    public GameLoadException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GameLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
