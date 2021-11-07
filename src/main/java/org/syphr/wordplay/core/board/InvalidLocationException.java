package org.syphr.wordplay.core.board;

public class InvalidLocationException extends PlacementException
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public InvalidLocationException()
    {
        super();
    }

    public InvalidLocationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidLocationException(String message)
    {
        super(message);
    }

    public InvalidLocationException(Throwable cause)
    {
        super(cause);
    }
}
