package org.syphr.wordplay.core.board;

public class InvalidWordException extends PlacementException
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public InvalidWordException()
    {
        super();
    }

    public InvalidWordException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidWordException(String message)
    {
        super(message);
    }

    public InvalidWordException(Throwable cause)
    {
        super(cause);
    }
}
