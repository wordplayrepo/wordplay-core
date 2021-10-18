package org.syphr.wordplay.api;

public class NotEnoughPiecesException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public NotEnoughPiecesException()
    {
        super();
    }

    public NotEnoughPiecesException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NotEnoughPiecesException(String message)
    {
        super(message);
    }

    public NotEnoughPiecesException(Throwable cause)
    {
        super(cause);
    }
}
