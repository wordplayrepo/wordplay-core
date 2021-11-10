package org.syphr.wordplay.core.component;

public class NoSuchPieceException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public NoSuchPieceException()
    {
        super();
    }

    public NoSuchPieceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoSuchPieceException(String message)
    {
        super(message);
    }

    public NoSuchPieceException(Throwable cause)
    {
        super(cause);
    }
}
